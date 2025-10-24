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


    // Konstruktør
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

    //Tilføj produkt og opdater beløb
    public void addProduct(Product product){
        products.add(product);
        totalAmount += product.getPrice();
    }

    //Beregn fulde beløb
    public void calculateTotal(){
        totalAmount = haircutPrice;
        for (Product p : products) {
            totalAmount += p.getPrice();
        }
    }

    //Gennemfører betaling
    public void processPayment() {
        if (payment != null){
            payment.markAsPaid();
        }
    }

    //Få alle ordre oplysninger
    public String getOrderDetails() {
        String details = "Order #" + orderId + "\n";
        details += "Date: " + orderDate + " " + orderTime + "\n";
        details += "Haircut: " + haircutPrice + " DKK\n";

        //Tilføj produkt, hvis der er nogle
        if (!products.isEmpty()) {
            details += "Products:\n";
            for (Product p : products) {
                details += "  - " + p.getName() + ": " + p.getPrice() + " DKK\n";
            }
        }

        //Fulde beløb
        details += "Total: " + totalAmount + " DKK\n";

        //Betalingstatus
        if (payment != null) {
            details += "Payment: " + payment.getPaymentStatus();
        } else {
            details += "Payment: Not processed yet";
        }

        return details;
    }

    //Oversigt over ordren
    public String toString() {
        return "Order #" + orderId + " | Total: " + totalAmount + " DKK";
    }
}