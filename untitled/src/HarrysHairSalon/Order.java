package HarrysHairSalon;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class Order {
    private int orderId;
    private double haircutPrice;
    private List<Product> products;
    private Payment payment;
    private double totalAmount;
    private LocalDate orderDate;
    private LocalTime orderTime;


    public Order(int orderId, double haircutPrice){
        this.orderId = orderId;
        this.haircutPrice = haircutPrice;
        this.products = new ArrayList<>();
        this.totalAmount = haircutPrice;
        this.orderDate = LocalDate.now();
        this.orderTime = LocalTime.now();
    }

    public int getOrderId(){return orderId;}
    public List<Product> getProducts() {return products;}
    public double getTotalAmount() {return totalAmount;}

    public void setPayment(Payment payment) {this.payment = payment;}


    public void addProduct(Product product){
        products.add(product);
        totalAmount += product.getPrice();
    }

    public void calculateTotal(){
        totalAmount = haircutPrice;
        for (Product p : products) {
            totalAmount += p.getPrice();
        }
    }

    public void processPayment() {
        if (payment != null){
            payment.markAsPaid();
        }
    }

    @Override
    public String toString(){
        return "Order #" + orderId + "\nAmount: " + totalAmount + " DKK";
    }
}
