package menu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import restaurant.Employee;

public class EmployeeManager {
    private List<Employee> employeeList;
    
    public EmployeeManager(){
        employeeList = new ArrayList<>();
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }
    
    public void addEmployee(Employee employee) {
        employeeList.add(employee);
    }

    public void deleteEmployee(String employeeName) {
        if (getEmployeeByName(employeeName) != null){
            employeeList.removeIf(employee -> employee.getName().equals(employeeName));
            System.out.println("Da xoa nhan vien " + employeeName + "!");
        } else {
            System.out.println("Khong tim thay nhan vien ten " + employeeName);
        }
    }

    public void displayEmployees() {
        for (Employee employee : employeeList) {
            employee.displayInfo();
        }
    }

    public void loadEmployeeFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/employees.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                addEmployee(new Employee(parts[0], Double.parseDouble(parts[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveEmployeeListToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/employees.txt"))) {
            for (Employee employee : employeeList) {
                writer.write(employee.getName() + "," + employee.getSalary());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Employee getEmployeeByName(String name) {
        for (Employee employee : employeeList) {
            if (employee.getName().equalsIgnoreCase(name)) {
                return employee;
            }
        }
        return null;
    }
}
