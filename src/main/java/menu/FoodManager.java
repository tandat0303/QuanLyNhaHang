package menu;

import java.io.*;
import objects.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FoodManager {
    private List<Food> foodList;

    public FoodManager(){
        foodList = new ArrayList<>();
    }

    public List<Food> getFoodList(){
        return foodList;
    }

    public void addFood(Food food) {
        foodList.add(food);
    }

    public void removeFood(String name) {
        if (getFoodByName(name) != null){
            foodList.removeIf(food -> food.getName().equalsIgnoreCase(name));
            System.out.println("Da xoa mon " + name + "!");
        } else {
            System.out.println("Khong tim thay mon an co ten: " + name);
        }
    }

    public void updateFood(String name) {
        Scanner sc = new Scanner(System.in);
        boolean found = false;

        for (Food food : foodList) {
            if (food.getName().equalsIgnoreCase(name)) {
                System.out.println("Da tim thay mon an: " + food.getName() + " voi gia: " + food.getPrice() + " (VND)");
                
                System.out.print("Nhap ten moi (Nhan Enter de giu nguyen): ");
                String newName = sc.nextLine();
                if (!newName.isEmpty()) {
                    food.setName(newName);
                }

                System.out.print("Nhap gia moi (Nhan -1 de giu nguyen): ");
                double newPrice = sc.nextDouble();
                sc.nextLine();
                if (newPrice >= 0) {
                    food.setPrice(newPrice);
                }

                found = true;
                System.out.println("Mon an da duoc cap nhat!");
                break;
            }
        }

        if (!found) {
            System.out.println("Khong tim thay mon an co ten: " + name);
        }

        saveToFile();
    }

    public void displayMenu() {
        for (Food food : foodList) {
            food.display();
        }
    }

    public void searchFood(String name) {
        for (Food food : foodList) {
            if (food.getName().equalsIgnoreCase(name)) {
                food.display();
                return;
            }
        }
        System.out.println("Khong tim thay mon an: " + name);
    }

    public Food getFoodByName(String name) {
        for (Food food : foodList) {
            if (food.getName().equalsIgnoreCase(name)) {
                return food;
            }
        }
        return null;
    }

    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/menu.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    switch (parts[0].toLowerCase()) {
                        case "mon chinh":
                            addFood(new MainDish(parts[1], Double.parseDouble(parts[2])));
                            break;
                        case "mon trang mieng":
                            addFood(new Dessert(parts[1], Double.parseDouble(parts[2])));
                            break;
                        case "do uong":
                            addFood(new Drink(parts[1], Double.parseDouble(parts[2])));
                            break;
                        case "mon an nhe":
                            addFood(new Snack(parts[1], Double.parseDouble(parts[2])));
                            break;
                        default:
                            System.out.println("Loai mon khong hop le: " + parts[0]);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/menu.txt"))) {
            for (Food food : foodList) {
                String foodLine = food.getType() + "," + food.getName() + "," + food.getPrice();
                writer.write(foodLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
