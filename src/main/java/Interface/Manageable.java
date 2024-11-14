package Interface;

import objects.Food;
import restaurant.Employee;
import restaurant.Table;

public interface Manageable{
    void addFood(Food food);
    void removeFood(String name);
    void updateFood(String name);
    void displayMenu();
    void searchFood(String name);
    void saveToFile();
    void addTable(Table table);
    void deleteTable(int tableNumber);
    void updateTable(int tableNumber);
    void searchTable(int tableNumber);
    void displayTables();
    void saveTableListToFile();
    void addEmployee(Employee employee);
    void deleteEmployee(String employeeName);
    void updateEmployee(String employeeName);
    void searchEmployee(String employeeName);
    void displayEmployees();
    void saveEmployeeListToFile();
    void viewBillList();
    void viewBillDetail(int billID);
    void calculateTotalRevenue();
    void placeOrder(String foodName, int quantity, String employeeName, int tableNumber);
    void completeOrder(int tableNumber);
}
