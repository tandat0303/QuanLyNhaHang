package menu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
            employeeList.removeIf(employee -> employee.getName().equalsIgnoreCase(employeeName));
            System.out.println("Da xoa nhan vien " + employeeName + "!");
        } else {
            System.out.println("Khong tim thay nhan vien ten " + employeeName);
        }
    }

    public void updateEmployee(String name) {
        Scanner sc = new Scanner(System.in);
        boolean found = false;

        for (Employee employee : employeeList) {
            if (employee.getName().equalsIgnoreCase(name)) {
                System.out.println("Da tim thay nhan vien: " + employee.getName() + " voi muc luong: " + employee.getSalary() + " VND/h");
                
                System.out.print("Nhap ten moi (Nhan Enter de giu nguyen): ");
                String newName = sc.nextLine();
                if (!newName.isEmpty()) {
                    employee.setName(newName);
                }

                System.out.print("Nhap muc luong moi (Nhan -1 de giu nguyen): ");
                double newSalary = sc.nextDouble();
                sc.nextLine();
                if (newSalary >= 0) {
                    employee.setSalary(newSalary);
                }

                found = true;
                System.out.println("Thong tin nhan vien da duoc cap nhat!");
                break;
            }
        }

        if (!found) {
            System.out.println("Khong tim thay nhan vien co ten: " + name);
        }

        saveEmployeeListToFile();
    }

    public void searchEmployee(String name) {
        for (Employee employee : employeeList) {
            if (employee.getName().equalsIgnoreCase(name)) {
                employee.displayInfo();
                return;
            }
        }
        System.out.println("Khong tim thay nhan vien: " + name);
    }

    public void displayEmployees() {
        System.out.println("== Danh sach nhan vien ==");
        
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
