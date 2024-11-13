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
            System.out.println("===== Quan ly nha hang =====");
            System.out.println("1. Quan ly thuc don");
            System.out.println("2. Quan ly ban an");
            System.out.println("3. Quan ly nhan vien");
            System.out.println("4. Quan ly don hang");
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
                case 0:
                    System.out.println("Dang thoat ...");
                    break;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon lai.");
                    break;
            }
        } while (choice != 0);
    }

    private void manageFoodMenu(Scanner sc) {
        int choice = -1;

        do {
            System.out.println("===== Quan ly thuc don =====");
            System.out.println("1. Them mon an");
            System.out.println("2. Xoa mon an");
            System.out.println("3. Cap nhat mon an");
            System.out.println("4. Hien thi thuc don");
            System.out.println("5. Luu thuc don");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
                
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Chon loai mon an: ");
                    System.out.println("1. Mon chinh");
                    System.out.println("2. Mon trang mieng");
                    System.out.println("3. Mon an nhe");
                    System.out.println("4. Do uong");
        
                    System.out.print("Nhap lua chon: ");
                    int type = sc.nextInt();
                    sc.nextLine();
        
                    System.out.print("Nhap ten mon an: ");
                    String name = sc.nextLine();
                    System.out.print("Nhap gia mon an: ");
                    double price = sc.nextDouble();
                    sc.nextLine();
        
                    switch (type) {
                        case 1:
                            foodManager.addFood(new MainDish(name, price));
                            System.out.println("Them mon chinh thanh cong!");
                            break;
                        case 2:
                            foodManager.addFood(new Dessert(name, price));
                            System.out.println("Them mon trang mieng thanh cong!");
                            break;
                        case 3:
                            foodManager.addFood(new Snack(name, price));
                            System.out.println("Them mon an nhe thanh cong!");
                            break;
                        case 4:
                            foodManager.addFood(new Drink(name, price));
                            System.out.println("Them do uong thanh cong!");
                            break;
                        default:
                            System.out.println("Lua chon khong hop le!");
                            break;
                    }
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
                    foodManager.displayMenu();
                    break;
                case 5:
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

    private void manageTables(Scanner sc) {
        int choice = -1;
        
        do {
            System.out.println("===== Quan ly ban an =====");
            System.out.println("1. Them ban");
            System.out.println("2. Xoa ban");
            System.out.println("3. Hien thi danh sach ban");
            System.out.println("4. Luu danh sach ban");
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
                    tableManager.displayTables();
                    break;
                case 4:
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

    private void manageEmployees(Scanner sc) {
        int choice = -1;
        
        do {
            System.out.println("===== Quan ly nhan vien =====");
            System.out.println("1. Them nhan vien");
            System.out.println("2. Xoa nhan vien");
            System.out.println("3. Hien thi danh sach nhan vien");
            System.out.println("4. Luu danh sach nhan vien");
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
                    employeeManager.displayEmployees();
                    break;
                case 4:
                    employeeManager.saveEmployeeListToFile();
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

    private void manageOrders(Scanner scanner) {
        System.out.println("===== Quan ly don hang =====");
        System.out.println("1. Dat mon");
        System.out.println("2. Hoan thanh don hang");
        System.out.println("3. Xem chi tiet don hang");
        System.out.println("4. Tinh doanh thu");
        System.out.print("Chon chuc nang: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                // Goi orderManager.placeOrder() sau khi nhan dau vao tu nguoi dung
                break;
            case 2:
                // Goi orderManager.completeOrder() sau khi nhan so ban tu nguoi dung
                break;
            case 3:
                orderManager.viewOrderDetail();
                break;
            case 4:
                orderManager.calculateTotalRevenue();
                break;
            default:
                System.out.println("Lua chon khong hop le.");
                break;
        }
    }
}
