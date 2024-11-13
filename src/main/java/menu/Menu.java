package menu;

import java.util.Scanner;
import objects.Dessert;
import objects.Drink;
import objects.MainDish;
import objects.Snack;
import restaurant.Employee;
import restaurant.Table;

public class Menu {
    private FoodManager foodManager;
    private TableManager tableManager;
    private EmployeeManager employeeManager;
    private OrderManager orderManager;

    public Menu() {
        foodManager = new FoodManager();
        tableManager = new TableManager();
        employeeManager = new EmployeeManager();
        orderManager = new OrderManager(foodManager, tableManager, employeeManager);
    }   
    
    public void showMainMenu() {
        foodManager.loadFromFile();
        tableManager.loadTableFromFile();
        employeeManager.loadEmployeeFromFile();
        
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Quan ly nha hang =====");
            System.out.println("1. Quan ly thuc don");
            System.out.println("2. Quan ly ban an");
            System.out.println("3. Quan ly nhan vien");
            System.out.println("4. Quan ly don hang");
            System.out.println("5. Dat mon an");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    manageFoodMenu(sc);
                    break;
                case 2:
                    manageTables(sc);
                    break;
                case 3:
                    manageEmployees(sc);
                    break;
                case 4:
                    manageOrders(sc);
                    break;
                case 5:
                    orderFood(sc);
                    break;
                case 0:
                    System.out.println("Dang thoat ...");
                    break;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon lai.");
                    break;
            }
        } while (choice != 0);
    }

    public void manageFoodMenu(Scanner sc) {
        int choice = -1;

        do {
            System.out.println("===== Quan ly thuc don =====");
            System.out.println("1. Them mon an");
            System.out.println("2. Xoa mon an");
            System.out.println("3. Cap nhat mon an");
            System.out.println("4. Tim mon an");
            System.out.println("5. Hien thi thuc don");
            System.out.println("6. Luu thuc don");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
                
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    int type = -1;

                    do {
                        System.out.println("Chon loai mon an: ");
                        System.out.println("1. Mon chinh");
                        System.out.println("2. Mon trang mieng");
                        System.out.println("3. Mon an nhe");
                        System.out.println("4. Do uong");
            
                        System.out.print("Nhap lua chon: ");
                        type = sc.nextInt();
                        sc.nextLine();
            
                        System.out.print("Nhap ten mon an: ");
                        String name = sc.nextLine();
                        System.out.print("Nhap gia mon an: ");
                        double price = sc.nextDouble();
                        sc.nextLine();

                        switch (type) {
                            case 1:
                                foodManager.addFood(new MainDish(name, price));
                                break;
                            case 2:
                                foodManager.addFood(new Dessert(name, price));
                                break;
                            case 3:
                                foodManager.addFood(new Snack(name, price));
                                break;
                            case 4:
                                foodManager.addFood(new Drink(name, price));
                                break;
                            default:
                                System.out.println("Lua chon khong hop le!");
                                break;
                        }
                    } while (type != 0);
                    break;        
                case 2:
                    System.out.print("Nhap ten mon can xoa: ");
                    String nameToRemove = sc.nextLine();
                    foodManager.removeFood(nameToRemove);
                    break;
                case 3:
                    System.out.print("Nhap ten mon can sua: ");
                    String nameToUpdate = sc.nextLine();
                    foodManager.updateFood(nameToUpdate);
                    break;
                case 4:
                    System.out.print("Nhap ten mon can tim: ");
                    String nameToSearch = sc.nextLine();
                    foodManager.searchFood(nameToSearch);;
                    break;
                case 5:
                    foodManager.displayMenu();
                    break;
                case 6:
                    foodManager.saveToFile();
                    System.out.println("Da luu thuc don!");
                    break;
                case 0:
                    System.out.print("Dang thoat ...");
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
                    break;
            }
        } while (choice != 0);
    }        

    public void manageTables(Scanner sc) {
        int choice = -1;
        
        do {
            System.out.println("===== Quan ly ban an =====");
            System.out.println("1. Them ban");
            System.out.println("2. Xoa ban");
            System.out.println("3. Sua so ban");
            System.out.println("4. Tim ban");
            System.out.println("5. Hien thi danh sach ban");
            System.out.println("6. Luu danh sach ban");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Nhap so ban: ");
                    int tableNumber = sc.nextInt();
                    sc.nextLine();

                    tableManager.addTable(new Table(tableNumber, "Trong"));
                    System.out.println("Them ban so " + tableNumber + " thanh cong!");
                    break;
                case 2:
                    System.out.println("Nhap so ban can xoa: ");
                    int tableDelete = sc.nextInt();
                    sc.nextLine();

                    tableManager.deleteTable(tableDelete);
                    break;
                case 3:
                    System.out.println("Nhap so ban can sua: ");
                    int tableUpdate = sc.nextInt();
                    sc.nextLine();

                    tableManager.updateTable(tableUpdate);
                    break;
                case 4:
                    System.out.println("Nhap so ban can tim: ");
                    int tableSearch = sc.nextInt();
                    sc.nextLine();

                    tableManager.searchTable(tableSearch);
                    break;    
                case 5:
                    tableManager.displayTables();
                    break;
                case 6:
                    tableManager.saveTableListToFile();
                    System.out.println("Da luu danh sach ban");
                    break;
                case 0:
                    System.out.println("Dang thoat ...");
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
                    break;
            }
        } while (choice != 0);
    }

    public void manageEmployees(Scanner sc) {
        int choice = -1;
        
        do {
            System.out.println("===== Quan ly nhan vien =====");
            System.out.println("1. Them nhan vien");
            System.out.println("2. Xoa nhan vien");
            System.out.println("3. Sua nhan vien");
            System.out.println("4. Tim nhan vien");
            System.out.println("5. Hien thi danh sach nhan vien");
            System.out.println("6. Luu danh sach nhan vien");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Nhap ten nhan vien: ");
                    String name = sc.nextLine();
                    System.out.println("Nhap luong: ");
                    double salary = sc.nextDouble();
                    sc.nextLine();

                    employeeManager.addEmployee(new Employee(name, salary));
                    System.out.println("Da them nhan vien");
                    break;
                case 2:
                    System.out.println("Nhap ten nhan vien can xoa: ");
                    String nameDelete = sc.nextLine();

                    employeeManager.deleteEmployee(nameDelete);
                    break;
                case 3:
                    System.out.println("Nhap ten nhan vien can sua: ");
                    String nameUpdate = sc.nextLine();

                    employeeManager.updateEmployee(nameUpdate);
                    break;
                case 4:
                    System.out.println("Nhap ten nhan vien can tim: ");
                    String employeeSearch = sc.nextLine();

                    employeeManager.searchEmployee(employeeSearch);
                    break;
                case 5:
                    employeeManager.displayEmployees();
                    break;
                case 6:
                    employeeManager.saveEmployeeListToFile();
                    System.out.println("Da luu danh sach nhan vien");
                    break;
                case 0:
                    System.out.println("Dang thoat ...");
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
                    break;
            }
        } while (choice != 0);
    }

    public void manageOrders(Scanner sc) {
        int choice = -1;
        
        do {
            System.out.println("===== Quan ly don hang =====");
            System.out.println("1. Xem chi tiet don hang");
            System.out.println("2. Tinh doanh thu");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    orderManager.viewOrderDetail();
                    break;
                case 2:
                    orderManager.calculateTotalRevenue();
                    break;
                case 3:
                    System.out.println("Dang thoat ...");
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
                    break;
            }
        } while (choice != 0);
    }

    public void orderFood(Scanner sc) {
        int choice = -1;
        
        do {
            System.out.println("===== Dat mon an =====");
            System.out.println("1. Xem thuc don");
            System.out.println("2. Tim mon");
            System.out.println("3. Goi mon");
            System.out.println("4. Thanh toan");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    foodManager.displayMenu();
                    break;
                case 2:
                    System.out.print("Nhap ten mon can tim: ");
                    String nameToSearch = sc.nextLine();
                    foodManager.searchFood(nameToSearch);;
                    break;
                case 3:
                    System.out.println("Nhap ten mon an: ");
                    String foodName = sc.nextLine();
                    System.out.println("Nhap so luong: ");
                    int quantity = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Nhap ten nhan vien order: ");
                    String employeeName = sc.nextLine();
                    System.out.println("Nhap so ban: ");
                    int tableNumber = sc.nextInt();
                    sc.nextLine();

                    orderManager.placeOrder(foodName, quantity, employeeName, tableNumber);
                    break;
                case 4:
                    System.out.println("Nhap so ban thanh toan: ");
                    int tableCheckOut = sc.nextInt();
                    sc.nextLine();

                    orderManager.completeOrder(tableCheckOut);
                    break;
                case 0:
                    System.out.println("Dang thoat ...");
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
                    break;
            }
        } while (choice != 0);
    }
}
