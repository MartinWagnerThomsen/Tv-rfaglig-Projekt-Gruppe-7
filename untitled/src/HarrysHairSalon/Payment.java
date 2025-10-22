package HarrysHairSalon;

import java.time.LocalDate;
import java.time.LocalTime;

public class Payment {
    private boolean isPaid;
    private LocalDate paymentDate;
    private LocalTime paymentTime;
    private PaymentMethod paymentMethod;
    private double amount;

    public Payment(boolean isPaid, LocalDate paymentDate, LocalTime paymentTime,
                   PaymentMethod paymentMethod, double amount) {
        this.isPaid = isPaid;
        this.paymentDate = paymentDate;
        this.paymentTime = paymentTime;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }


        public Payment(PaymentMethod paymentMethod, double amount) {
            this(false, null, null, paymentMethod, amount);
        }

    //Getters
    public boolean isPaid() {
        return isPaid;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public LocalTime getPaymentTime() {
        return paymentTime;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    //Setters
    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentTime(LocalTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    // Marker regning som betalt
    public void markAsPaid() {
        this.isPaid = true;
        this.paymentDate = LocalDate.now();
        this.paymentTime = LocalTime.now();
        System.out.println("Payment of " + amount + " DKK marked as paid on " + paymentDate + " " + paymentTime);
    }

    // Få betalingsstatus
    public String getPaymentStatus() {
        if (isPaid) {
            return "PAID on " + paymentDate + " " + paymentTime + " via " + paymentMethod;
        } else {
            return "UNPAID - Amount due: " + amount + "DKK";
        }
    }

    // få betalingsdetaljer
    public String getPaymentDetails() {
        return "Payment Details:\n" +
               "Amount: " + amount + "\n" +
               "Method: " + paymentMethod + "\n" +
               "Status: " + (isPaid ? "PAID" : "UNPAID") + "\n" +
               (isPaid ? "Date and time: " + paymentDate + paymentTime : "");
    }

    @Override
    public String toString() {
        return getPaymentStatus();
    }
}
