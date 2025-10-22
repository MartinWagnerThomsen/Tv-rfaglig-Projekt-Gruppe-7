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
        this.payment = null;
    }


    // Getters
    public int getOrderId(){return orderId;}
    public List<Product> getProducts() {return products;}
    public double getTotalAmount() {return totalAmount;}
    public LocalDate getOrderDate() {return orderDate;}
    public LocalTime getOrderTime() {return orderTime;}

    // Setter
    public void setPayment(Payment payment) {this.payment = payment;}

    public Payment getPayment(){
        return payment;
    }


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

    public String getOrderDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Order #").append(orderId).append("\n");
        details.append("Date: ").append(orderDate).append(" ").append(orderTime).append("\n");
        details.append("Haircut: ").append(String.format("%.2f", haircutPrice)).append(" DKK\n");

        if (!products.isEmpty()) {
            details.append("Products:\n");
            for (Product p : products) {
                details.append("  - ").append(p.getName())
                        .append(": ").append(String.format("%.2f", p.getPrice())).append(" DKK\n");
            }
        }

        details.append("Total: ").append(String.format("%.2f", totalAmount)).append(" DKK\n");

        if (payment != null) {
            details.append("Payment: ").append(payment.getPaymentStatus());
        } else {
            details.append("Payment: Not processed yet");
        }

        return details.toString();
    }

    @Override
    public String toString(){
        return "Order #" + orderId + " | Total: " + String.format("%.2f", totalAmount) + " DKK";
    }
}
