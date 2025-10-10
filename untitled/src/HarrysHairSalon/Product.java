package HarrysHairSalon;

public class Product {

    private int productId;
    private String name;
    private double price;
    private ProductCategory category;

    public Product(int productId, String name, double price, ProductCategory category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // Getters
    public int getProductId() {return productId;}
    public String getName() {return name;}
    public double getPrice() {return price;}
    public ProductCategory category() {return category;}

    // Setters
    public void setProductId(int productId) {this.productId = productId;}
    public void setName(String name) {this.name = name;}
    public void setPrice(double price) {this.price = price;}
    public ProductCategory getCategory() {return category;}


    public String getProductinfo() {
        return new String("Product ID: " + productId + "\nName: " + name +
                "\nPrice: " + price + "\nCategory: " + category);
    }
    @Override
    public String toString(){
        return getProductinfo();
    }
}