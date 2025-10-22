package HarrysHairSalon;

import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private Customer customer;
    private LocalDateTime dateTime;
    private int duration;
    private Order order;
    private AppointmentStatus status;

    //Konstruktøre
    public Appointment(int id, Customer customer, LocalDateTime dateTime, int duration){
        this.id = id;
        this.customer = customer;
        this.dateTime = dateTime;
        this.duration = duration;
        this.order = null;
        this.status = AppointmentStatus.SCHEDULED;
    }

    //Gettere
    public int getId() {return id; }
    public Customer getCustomer() { return customer; }
    public LocalDateTime getDateTime() { return dateTime; }
    public int getDuration() { return duration; }
    public Order getOrder() { return order; }
    public AppointmentStatus getStatus() { return status; }

    // Setter for order -
    public void setOrder(Order order) {
        this.order = order;
    }

    //Laver detajlerne for aftalen
    public String getAppointmentDetails() {
        return "Appointment ID: " + id +
                "\nCustomer: " + customer.getName() +
                "\nDate and time: " + dateTime +
                "\nDuration in minutes: " + duration +
                "\nOrder: " + order.getOrderDetails() +
                "\nStatus: " + status;
    }

    //Fuldføre aftalen
    public void completeAppointment(){
        if (status != AppointmentStatus.CANCELLED) {
            status = AppointmentStatus.COMPLETED;
            System.out.println("Appointment " + id + " is booked");
        } else {
            System.out.println("Appointment has been cancelled");
        }
    }

    //Aflys aftalerne
    public void cancelAppointment(){
        if (status == AppointmentStatus.COMPLETED) {
            System.out.println("Cannot cancel appointment");
        } else {
            status = AppointmentStatus.CANCELLED;
            System.out.println("Appointment has been cancelled");

        }
    }

    //Find ud af om din booking er gennemført
    public boolean isCompleted(){
        return status == AppointmentStatus.COMPLETED;
    }

    @Override
    public String toString(){
        return getAppointmentDetails();
    }


    public int getId() {
        return 0;
    }
}

