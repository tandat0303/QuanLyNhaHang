package menu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import restaurant.Table;

public class TableManager {
    private List<Table> tableList;

    public TableManager(){
        tableList = new ArrayList<>();
    }

    public List<Table> getTableList() {
        return tableList;
    }

    public void addTable(Table table) {
        if (getTableByNumTable(table.getTableNumber()) != null){
            System.out.println("Ban so " + table.getTableNumber() + " da ton tai!");
        } else {
            tableList.add(table);
            System.out.println("Them ban so " + table.getTableNumber() + " thanh cong!");
        }
    }

    public void deleteTable(int tableNumber) {
        if (getTableByNumTable(tableNumber) != null){
            tableList.removeIf(table -> table.getTableNumber() == tableNumber);
            System.out.println("Xoa ban so " + tableNumber + " thanh cong!");
        } else {
            System.out.println("Khong tim thay ban so " + tableNumber);
        }
    }

    public void displayTables() {
        System.out.println("== Danh sach ban an ==");
        
        for (Table table : tableList) {
            table.displayInfo();
        }
    }

    public void updateTable(int tableNumber) {
        Scanner sc = new Scanner(System.in);
        boolean found = false;

        for (Table table : tableList) {
            if (table.getTableNumber() == tableNumber) {
                System.out.println("Da tim thay ban so: " + table.getTableNumber());
                
                System.out.print("Nhap so ban moi (Nhan -1 de giu nguyen): ");
                int newNumber = sc.nextInt();
                sc.nextLine();
                if (newNumber >= 0) {
                    table.setTableNumber(newNumber);
                }

                found = true;
                System.out.println("So ban da duoc cap nhat!");
                break;
            }
        }

        if (!found) {
            System.out.println("Khong tim thay ban so: " + tableNumber);
        }

        saveTableListToFile();
    }

    public void searchTable(int tableNumber) {
        for (Table table : tableList) {
            if (table.getTableNumber() == tableNumber) {
                table.displayInfo();
                return;
            }
        }
        System.out.println("Khong tim thay ban so: " + tableNumber);
    }

    public void loadTableFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/tables.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                addTableToMenu(new Table(Integer.parseInt(parts[0]), parts[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTableListToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/tables.txt"))) {
            for (Table table : tableList) {
                writer.write(table.getTableNumber() + "," + table.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addTableToMenu(Table table){
        tableList.add(table);
    }

    public Table getTableByNumTable(int tableNumber) {
        for (Table table : tableList) {
            if (table.getTableNumber() == tableNumber) {
                return table;
            }
        }
        return null;
    }
}
