package menu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
        tableList.add(table);
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
        for (Table table : tableList) {
            table.displayInfo();
        }
    }

    public void loadTableFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/tables.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                addTable(new Table(Integer.parseInt(parts[0]), parts[1]));
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

    public Table getTableByNumTable(int tableNumber) {
        for (Table table : tableList) {
            if (table.getTableNumber() == tableNumber) {
                return table;
            }
        }
        return null;
    }
}
