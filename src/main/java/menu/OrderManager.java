package menu;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import restaurant.Employee;
import restaurant.Table;
import objects.*;

public class OrderManager {
    private FoodManager fm;
    private TableManager tm;
    private EmployeeManager em;

    public OrderManager(FoodManager foodManager, TableManager tableManager, EmployeeManager employeeManager) {
        this.fm = foodManager;
        this.tm = tableManager;
        this.em = employeeManager;
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
        
        List<String> remainingOrders = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader("data/orders.txt"));
             BufferedWriter historyWriter = new BufferedWriter(new FileWriter("data/orders_history.txt", true))) {
    
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int orderTableNumber = Integer.parseInt(parts[3]);
    
                if (orderTableNumber == tableNumber) {
                    historyWriter.write(line);
                    historyWriter.newLine();
                } else {
                    remainingOrders.add(line);
                }
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
    
        for (Table table : tableList) {
            if (table.getTableNumber() == tableNumber) {
                table.setStatus("Trong");
                System.out.println("Thanh toan thanh cong ban so " + tableNumber);
                tm.saveTableListToFile();
                return;
            }
        }
        System.out.println("Khong tim thay ban so: " + tableNumber);
    }

    public void viewOrderDetail() {
        System.out.println("Lich su don hang:\n");
        try (BufferedReader reader = new BufferedReader(new FileReader("data/orders_history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String employeeName = parts[0];
                String foodName = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                int tableNumber = Integer.parseInt(parts[3]);
                
                System.out.println("Nhan vien order: " + employeeName + ", " + foodName + " x " + quantity + ", Ban so " + tableNumber);
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
        
        try (BufferedReader reader = new BufferedReader(new FileReader("data/orders_history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String foodName = parts[1];
                int quantitySold = Integer.parseInt(parts[2]);
                
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
        
        System.out.println("Doanh thu nha hang: \n");
        
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
