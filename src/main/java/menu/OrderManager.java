package menu;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import restaurant.Employee;
import restaurant.Table;
import objects.*;

public class OrderManager {
    private FoodManager fm;
    private TableManager tm;
    private EmployeeManager em;
    private int currentBillId;

    public OrderManager(FoodManager foodManager, TableManager tableManager, EmployeeManager employeeManager) {
        this.fm = foodManager;
        this.tm = tableManager;
        this.em = employeeManager;
        this.currentBillId = loadMaxBillId();
    }

    private int loadMaxBillId() {
        int maxBillId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("data/bills.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int billId = Integer.parseInt(parts[0].trim());
                maxBillId = billId;
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return maxBillId + 1;
    }

    public void placeOrder(String foodName, int quantity, String employeeName, int tableNumber) {
        List<Employee> employeeList = em.getEmployeeList();
        List<Table> tableList = tm.getTableList();
        
        boolean employeeExists = false;
        for (Employee employee : employeeList) {
            if (employee.getName().equalsIgnoreCase(employeeName)) {
                employeeExists = true;
                break;
            }
        }
        if (!employeeExists) {
            System.out.println("Khong tim thay nhan vien : " + employeeName);
            return;
        }
    
        Table selectedTable = null;
        for (Table table : tableList) {
            if (table.getTableNumber() == tableNumber) {
                selectedTable = table;
                break;
            }
        }
        if (selectedTable == null) {
            System.out.println("Khong tim thay ban so : " + tableNumber);
            return;
        }
    
        Food food = fm.getFoodByName(foodName);
        if (food == null) {
            System.out.println("Khong tim thay mon an: " + foodName);
            return;
        }
    
        food.setQuantitySold(food.getQuantitySold() + quantity);
        System.out.println("Dat mon thanh cong: " + food.getName() + " x " + quantity + " tai ban so " + tableNumber);
    
        selectedTable.setStatus("Dang phuc vu");
        tm.saveTableListToFile();
    
        saveOrderToFile(employeeName, foodName, quantity, tableNumber);
    }
    
    public void completeOrder(int tableNumber) {
        List<Table> tableList = tm.getTableList();
        Table selectedTable = null;
        for (Table table : tableList) {
            if (table.getTableNumber() == tableNumber) {
                selectedTable = table;
                break;
            }
        }

        if (selectedTable == null) {
            System.out.println("Khong tim thay ban so: " + tableNumber);
            return;
        }

        if (selectedTable.getStatus().equals("Trong")) {
            System.out.println("Ban so " + selectedTable.getTableNumber() + " hien dang trong");
            return;
        }

        String orderEmployeeName = null;
        List<String> remainingOrders = new ArrayList<>();
        List<String> billDetails = new ArrayList<>();
        double totalAmount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("data/orders.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int orderTableNumber = Integer.parseInt(parts[3]);

                if (orderTableNumber == tableNumber) {
                    if (orderEmployeeName == null) {
                        orderEmployeeName = parts[0];
                    }

                    String foodName = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    Food food = fm.getFoodByName(foodName);

                    double itemTotal = food.getPrice() * quantity;
                    totalAmount += itemTotal;

                    billDetails.add(currentBillId + "," + orderEmployeeName + "," + orderTableNumber + "," + foodName + "," +
                                    food.getPrice() + "," + quantity + "," + itemTotal);
                } else {
                    remainingOrders.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try (BufferedWriter billsWriter = new BufferedWriter(new FileWriter("data/bills.txt", true))) {
            billsWriter.write(currentBillId + "," + formattedDate);
            billsWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter detailsWriter = new BufferedWriter(new FileWriter("data/bills_detail.txt", true))) {
            for (String detail : billDetails) {
                detailsWriter.write(detail + "," + totalAmount);
                detailsWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/orders.txt"))) {
            for (String order : remainingOrders) {
                writer.write(order);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        selectedTable.setStatus("Trong");
        System.out.println("Thanh toan thanh cong ban so " + tableNumber);
        tm.saveTableListToFile();
        currentBillId++;
    }

    public void viewBillList(){
        System.out.println("== Danh sach hoa don ==");

        try (BufferedReader reader = new BufferedReader(new FileReader("data/bills.txt"))){
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String parts[] = line.split(",");
                int billId = Integer.parseInt(parts[0]);
                String billDate = parts[1];
    
                System.out.println("Ma hoa don: " + billId + ", Thoi gian: " + billDate);
                found = true;
            }
    
            if (!found) {
                System.out.println("Khong co hoa don nao.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewBillDetail(int billId) {
        System.out.println("== Chi tiet hoa don " + billId + " ==\n");
    
        try (BufferedReader reader = new BufferedReader(new FileReader("data/bills_detail.txt"))) {
            String line;
            boolean found = false;
            String employeeName = null;
            double totalAmount = 0;
            int tableNumber = 0;
    
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int recordBillId = Integer.parseInt(parts[0]);
    
                if (recordBillId == billId) {
                    if (employeeName == null) {
                        employeeName = parts[1];
                        System.out.println("Ten nhan vien: " + employeeName);
                    }

                    if (tableNumber == 0) {
                        tableNumber = Integer.parseInt(parts[2]);
                        System.out.println("Ban: " + tableNumber);
                    }
                    

                    String foodName = parts[3];
                    double price = Double.parseDouble(parts[4]);
                    int quantity = Integer.parseInt(parts[5]);
                    double itemTotal = Double.parseDouble(parts[6]);
                    System.out.println("Mon an: " + foodName + ", Don gia: " + price + ", So luong: " + quantity
                                         + ", Tong tien mon: " + itemTotal);
                    totalAmount += itemTotal;
                    found = true;
                }
            }
    
            if (found) {
                System.out.println("Tong hoa don: " + totalAmount);
            } else {
                System.out.println("Khong tim thay chi tiet hoa don voi ma so: " + billId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }        

    public void calculateTotalRevenue() {
        int mainDishCount = 0;
        int dessertCount = 0;
        int drinkCount = 0;
        int snackCount = 0;
        
        double total = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader("data/bills_detail.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String foodName = parts[3];
                int quantitySold = Integer.parseInt(parts[5]);
                
                if (fm.getFoodByName(foodName) instanceof MainDish) {
                    mainDishCount += quantitySold;
                } else if (fm.getFoodByName(foodName) instanceof Dessert) {
                    dessertCount += quantitySold;
                } else if (fm.getFoodByName(foodName) instanceof Drink) {
                    drinkCount += quantitySold;
                } else if (fm.getFoodByName(foodName) instanceof Snack) {
                    snackCount += quantitySold;
                }
                
                Food food = fm.getFoodByName(foodName);
                if (food != null) {
                    total += food.getPrice() * quantitySold;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("== Doanh thu nha hang ==\n");
        
        System.out.println("So luong mon chinh da ban: " + mainDishCount);
        System.out.println("So luong mon trang mieng da ban: " + dessertCount);
        System.out.println("So luong do uong da ban: " + drinkCount);
        System.out.println("So luong mon an nhe da ban: " + snackCount);

        System.out.println("Tong doanh thu: " + total + " (VND)");
    }
    
    private void saveOrderToFile(String employeeName, String foodName, int quantity, int tableNumber) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/orders.txt", true))) {
            writer.write(employeeName + "," + foodName + "," + quantity + "," + tableNumber);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
