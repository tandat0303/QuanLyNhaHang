package menu;

import java.util.Scanner;

import Interface.Manageable;
import objects.Dessert;
import objects.Drink;
import objects.Food;
import objects.MainDish;
import objects.Snack;
import restaurant.Employee;
import restaurant.Table;

public class Menu implements Manageable {
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
                                addFood(new MainDish(name, price));
                                break;
                            case 2:
                                addFood(new Dessert(name, price));
                                break;
                            case 3:
                                addFood(new Snack(name, price));
                                break;
                            case 4:
                                addFood(new Drink(name, price));
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
                    removeFood(nameToRemove);
                    break;
                case 3:
                    System.out.print("Nhap ten mon can sua: ");
                    String nameToUpdate = sc.nextLine();
                    updateFood(nameToUpdate);
                    break;
                case 4:
                    System.out.print("Nhap ten mon can tim: ");
                    String nameToSearch = sc.nextLine();
                    searchFood(nameToSearch);;
                    break;
                case 5:
                    displayMenu();
                    break;
                case 6:
                    saveToFile();
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
                    System.out.print("Nhap so ban: ");
                    int tableNumber = sc.nextInt();
                    sc.nextLine();

                    addTable(new Table(tableNumber, "Trong"));
                    break;
                case 2:
                    System.out.print("Nhap so ban can xoa: ");
                    int tableDelete = sc.nextInt();
                    sc.nextLine();

                    deleteTable(tableDelete);
                    break;
                case 3:
                    System.out.print("Nhap so ban can sua: ");
                    int tableUpdate = sc.nextInt();
                    sc.nextLine();

                    updateTable(tableUpdate);
                    break;
                case 4:
                    System.out.print("Nhap so ban can tim: ");
                    int tableSearch = sc.nextInt();
                    sc.nextLine();

                    searchTable(tableSearch);
                    break;    
                case 5:
                    displayTables();
                    break;
                case 6:
                    saveTableListToFile();
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
                    System.out.print("Nhap ten nhan vien: ");
                    String name = sc.nextLine();
                    System.out.print("Nhap luong: ");
                    double salary = sc.nextDouble();
                    sc.nextLine();

                    addEmployee(new Employee(name, salary));
                    System.out.println("Da them nhan vien");
                    break;
                case 2:
                    System.out.print("Nhap ten nhan vien can xoa: ");
                    String nameDelete = sc.nextLine();

                    deleteEmployee(nameDelete);
                    break;
                case 3:
                    System.out.print("Nhap ten nhan vien can sua: ");
                    String nameUpdate = sc.nextLine();

                    updateEmployee(nameUpdate);
                    break;
                case 4:
                    System.out.print("Nhap ten nhan vien can tim: ");
                    String employeeSearch = sc.nextLine();

                    searchEmployee(employeeSearch);
                    break;
                case 5:
                    displayEmployees();
                    break;
                case 6:
                    saveEmployeeListToFile();
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
            System.out.println("1. Hien thi danh sach hoa don");
            System.out.println("2. Xem chi tiet hoa don");
            System.out.println("3. Xem doanh thu");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewBillList();
                    break;
                case 2:
                    System.out.print("Nhap ma hoa don: ");
                    int billID = sc.nextInt();
                    sc.nextLine();

                    viewBillDetail(billID);
                    break;
                case 3:
                    calculateTotalRevenue();
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
                    displayMenu();
                    break;
                case 2:
                    System.out.print("Nhap ten mon can tim: ");
                    String nameToSearch = sc.nextLine();
                    searchFood(nameToSearch);;
                    break;
                case 3:
                    System.out.print("Nhap ten mon an: ");
                    String foodName = sc.nextLine();
                    System.out.print("Nhap so luong: ");
                    int quantity = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nhap ten nhan vien order: ");
                    String employeeName = sc.nextLine();
                    System.out.print("Nhap so ban: ");
                    int tableNumber = sc.nextInt();
                    sc.nextLine();

                    placeOrder(foodName, quantity, employeeName, tableNumber);
                    break;
                case 4:
                    System.out.print("Nhap so ban thanh toan: ");
                    int tableCheckOut = sc.nextInt();
                    sc.nextLine();

                    completeOrder(tableCheckOut);
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

    @Override
    public void addFood(Food food){
        foodManager.addFood(food);
    }

    @Override
    public void removeFood(String name){
        foodManager.removeFood(name);
    }

    @Override
    public void updateFood(String name){
        foodManager.updateFood(name);
    }

    @Override
    public void searchFood(String name){
        foodManager.searchFood(name);
    }

    @Override
    public void saveToFile(){
        foodManager.saveToFile();
    }

    @Override
    public void displayMenu(){
        foodManager.displayMenu();
    }

    @Override
    public void addTable(Table table){
        tableManager.addTable(table);
    }

    @Override
    public void deleteTable(int tableNumber){
        tableManager.deleteTable(tableNumber);
    }

    @Override
    public void updateTable(int tableNumber){
        tableManager.updateTable(tableNumber);
    }

    @Override
    public void searchTable(int tableNumber){
        tableManager.searchTable(tableNumber);
    }

    @Override
    public void displayTables(){
        tableManager.displayTables();
    }

    @Override
    public void saveTableListToFile(){
        tableManager.saveTableListToFile();
    }

    @Override
    public void addEmployee(Employee employee){
        employeeManager.addEmployee(employee);
    }

    @Override
    public void deleteEmployee(String employeeName){
        employeeManager.deleteEmployee(employeeName);
    }

    @Override
    public void updateEmployee(String employeeName){
        employeeManager.updateEmployee(employeeName);
    }

    @Override
    public void searchEmployee(String employeeName){
        employeeManager.searchEmployee(employeeName);
    }

    @Override
    public void displayEmployees(){
        employeeManager.displayEmployees();
    }

    @Override
    public void saveEmployeeListToFile(){
        employeeManager.saveEmployeeListToFile();
    }

    @Override
    public void viewBillList(){
        orderManager.viewBillList();
    }

    @Override
    public void viewBillDetail(int billID){
        orderManager.viewBillDetail(billID);
    }

    @Override
    public void calculateTotalRevenue(){
        orderManager.calculateTotalRevenue();
    }

    @Override
    public void placeOrder(String foodName, int quantity, String employeeName, int tableNumber){
        orderManager.placeOrder(foodName, quantity, employeeName, tableNumber);
    }

    @Override
    public void completeOrder(int tableNumber){
        orderManager.completeOrder(tableNumber);
    }
}