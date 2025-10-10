package HarrysHairSalon;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.*;

public class BookingSystem {
    // Attributes
    private ArrayList<Appointment> appointments;
    private ArrayList<Customer> customers;
    private ArrayList<Product> products;
    private Salon salon;
    private User currentUser;
    private static final String DATA_FILE = "booking_data.txt";

// Constructor
public BookingSystem() {
    this.appointments = new ArrayList<>();
    this.customers = new ArrayList<>();
    this.products = new ArrayList<>();
    this.salon = new Salon("Harry's Salon");
    this.currentUser = null;
    initializeProducts();
    loadData();
}

private void initializeProducts() {
    products.add(new Product(1, "Head & Shoulders", 49.99, ProductCategory.SHAMPOO));
    products.add(new Product(2, "Coconut milk conditioner", 35.00, ProductCategory.CONDITIONER));
    products.add(new Product(3, "Hair brush", 150.00, ProductCategory.BRUSH));
    products.add(new Product(4, "Hair wax", 199.99, ProductCategory.WAX_AND_GEL));
    products.add(new Product(5, "Comb", 19.99, ProductCategory.OTHER));
}
    /**
    @param customer Kunden der skal bookes
    @param dateTime Ønsket dato og tidspunkt
    @param duration Varighed i minutter
    @return true hvis aftalen blev oprettet, false hvis tidspunktet ikke er ledigt
     */
public boolean createAppointment(Customer customer, LocalDateTime dateTime, int duration) {
    if (currentUser == null || !currentUser.getPermisions().equals("FULL_ACCESS")) {
        System.out.println("You are not allowed to create an appointment without permissions.");
        return false;
    }
    // Check if salon is open
    if (!salon.isOpenOn(dateTime.toLocalDate())) {
        System.out.println("The salon is closed this day");
        return false;
    }
    // Check if timeslot is available
    if (!isTimeSlotAvailable(dateTime, duration)) {
        System.out.println("Time slot isn't available");
        return false;
    }
    // Make new appointment
    int newID = appointment.size() + 1;
    Appointment newAppointment = new Appointment(newId, customer, dateTime, duration);
    appointments.add(newAppointment);

    System.out.println("New appointment has been created");
    System.out.println(newAppointment.getAppointmentDetails());

    saveData();
    return true;
}
/**
 * Delete existing appointment
 * @param appointmentId of the appointment that is getting deleted
 * @return true is deletion succeeds
 */
public boolean deleteAppointment(int appointmentId) {
    // Check rights
    if (currentUser == null || !currentUser.getPermissions().equals("FULL ACCESS")) {
        System.out.println("You are not allowed to delete an appointment without permissions.");
        return false;
    }

    for (int i = 0; i < appointments.size(); i++) {
        if (appointments.get(i).getId() == appointmentId) {
            Appointment apt = appointments.remove(i);
            System.out.println("Appointment has been deleted" + apt.getAppointmentDetails());
            saveData();
            return true;
        }
    }

    System.out.println("No appointment with ID: " + appointmentId);
    return false;
}
    /**
     * Shows available time slots on a given date
     * @param Check date
     */
    public void showAvailableTimeSlots(LocalDate date) {
        System.out.println("\n=== Available Time Slots for " + date + " ===");
    }
}
