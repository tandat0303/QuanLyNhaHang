package objects;

public abstract class Food {
    protected String name;
    protected double price;
    private int quantitySold;
    private static int totalFoodCreated = 0;

    public Food(String name, double price) {
        this.name = name;
        this.price = price;
        totalFoodCreated++;
    }

    public void display() {
        System.out.println("   - " + name + " --- " + price);
    }
    
    public abstract String getType();

    public void setName(String n){
        name = n;
    }
    
    public String getName() {
        return name;
    }

    public void setPrice(double p){
        price = p;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setQuantitySold(int qs) {
        quantitySold = qs;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public static int getTotalFoodCreated() {
        return totalFoodCreated;
    }

    public static void decreaseTotalFoodCreated() {
        if (totalFoodCreated > 0) {
            totalFoodCreated--;
        }
    }
}
