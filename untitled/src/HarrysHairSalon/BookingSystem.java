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
     * @param customer Kunden der skal bookes
     * @param dateTime Ønsket dato og tidspunkt
     * @param duration Varighed i minutter
     * @return true hvis aftalen blev oprettet, false hvis tidspunktet ikke er ledigt
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
        if (!isTimeSlotAvailable(LocalTime.from(dateTime), duration)) {
            System.out.println("Time slot isn't available");
            return false;
        }
        // Make new appointment
        int newID = appointments.size() + 1;
        Appointment newAppointment = new Appointment(newID, customer, dateTime, duration);
        appointments.add(newAppointment);

        System.out.println("New appointment has been created");
        System.out.println(newAppointment.getAppointmentDetails());

        saveData();
        return true;
    }

    /**
     * Delete existing appointment
     *
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
     *
     * @param date check Date
     */
    public void showAvailableTimeSlots(LocalDate date) {
        System.out.println("\n=== Available Time Slots for " + date + " ===");

        // Check if salon is open
        if (!salon.isOpen(date)) {
            System.out.println("The salon is closed this day");
            return;
        }
        // Opening hours: 10:00 - 18:00
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        int slotDuration = 60; // 60 minute slots

        LocalTime currentTime = startTime;

        while (currentTime.isBefore(endTime)) {
            LocalDateTime slotDateTime = LocalDateTime.of(date, currentTime);

            if (isTimeSlotAvailable(LocalTime.from(slotDateTime), slotDuration)) {
                System.out.println("✓ " + currentTime + " - Available");
            } else {
                System.out.println("✗ " + currentTime + " -  Not Available ");
            }

            currentTime = currentTime.plusHours(slotDuration);
        }
    }

    /**
     * @param dateTime dateTime that is getting checked
     * @param duration duration in minutes
     * @return true if available, false if not
     */
    private boolean isTimeSlotAvailable(LocalTime dateTime, int duration) {
        LocalDateTime endTime = LocalDateTime.from(dateTime.plusMinutes(duration));

        for (Appointment apt : AppointmentStatus.CANCELLED) {
            continue;
        }

        Appointment apt = null;
        LocalDateTime aptStart = apt.getDateTime();
        LocalDateTime aptEnd = aptStart.plusMinutes(apt.getDuration());

        // check for overlap
        if (dateTime.isBefore(LocalTime.from(aptEnd)) && endTime.isAfter(aptStart)) {
            return false;
        }

    return true;
}

/**
 * Search for customer based on name or phone number
 *
 * @param searchTerm searchTerm (name or phone number)
 * @return customer if found, null if not
 */
public Customer searchCustomer(String searchTerm) {
    for (Customer c : customers) {
        if (c.getName().toLowerCase().contains(searchTerm.toLowerCase())) ||
        c.getPhone().contains(searchTerm)) {
            return c;
        }
    }
    return null;
}

/**
 * Adds a new customer to the system
 *
 * @param customer customer that's getting added
 */
public void addCustomer(Customer customer) {
    customers.add(customer);
    saveData();
    System.out.println("Customer added: " + customer.getName());
}

// Saves all data to file
public void saveData() {
    try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
        // Save customer
        writer.println("=== CUSTOMERS ===");
        for (Customer c : customers) {
            writer.println(c.getId() + ";" + c.getName() + ";" +
                    c.getPhone() + ";" + c.getEmail() + ";" + c.hasCredit());
        }

        // Save appointments
        writer.println("=== APPOINTMENTS ===");
        for (Appointment apt : appointments) {
            writer.println(apt.getId() + ";" + apt.getCustomer().getId() + "," +
                    apt.getDateTime() + ";" + apt.getDuration() + "," +
                    apt.getStatus());
        }
        System.out.println("Data saved!");

    } catch (IOException e) {
        System.out.println("Error occurred while saving data " + e.getMessage());
    }
}

/**
 * Reading data from file
 */
public void loadData() {
    File file = new File(DATA_FILE);
    if (!file.exists()) {
        System.out.println("Data file does not exist. Starting from scratch");
        return;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
        String line;
        String section = "";

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("===")) {
                section = line;
                continue;
            }

            if (section.contains("CUSTOMERS") && !line.isEmpty()) {
                String[] parts = line.split(";");
                int id = Integer.parseInt(parts[0]);
                Customer c = new Customer(id, parts[1], parts[2], parts[3], );
                c.setHasCredit(Boolean.parseBoolean(parts[4]));
                customers.add(c);
            }

            // Appointments can be added here
        }
        System.out.println("Data saved!");
        System.out.println("Customer amount: " + customers.size());
        System.out.println("Appointment amount: " + appointments.size());

    } catch (IOException e) {
        System.out.println("Error occurred while reading data " + e.getMessage());
    }
}

/**
 * Logs a user into the system
 *
 * @param user the user that is loggin in
 */
public void setCurrentUser(User user) {
    this.currentUser = user;
    System.out.println("Logged in as: " + user.getUsername() +
            " (" + user.getRole() + ")");
}

/**
 * Generates report for a specific date (only for the Accountant)
 *
 * @param date the date for the report
 */
public void generateDailyReport(LocalDate date) {
    if (currentUser == null || !currentUser.canAccessFinancials()) {
        System.out.println("You are not allowed to access financial reports!.");
        return;
    }

    System.out.println("\n=== DAILY REPORT FOR " + date + " ===");

    double totalRevenue = 0.0;
    int completedAppointments = 0;

    for (Appointment apt : appointments) {
        if (apt.getDateTime().toLocalDate().equals(date) &&
                apt.getStatus() == AppointmentStatus.COMPLETED) {

            Order order = apt.getOrder();
            if (order != null && order.getPayment().isPaid()) {
                System.out.println("Customer: " + apt.getCustomer().getName() +
                        " | Amount: " + order.getTotalAmount() + " kr");
                totalRevenue += order.getTotalAmount();
                completedAppointments++;
            }
        }
    }

    System.out.println("\nAmount of completed appointments: " + completedAppointments);
    System.out.println("Total revenue: " + totalRevenue + " kr");
}

// Getters
public ArrayList<Appointment> getAppointments() {
    return appointments;
}

public ArrayList<Customer> getCustomers() {
    return customers;
}

public ArrayList<Product> getProducts() {
    return products;
}

public Salon getSalon() {
    return salon;
}

public User getCurrentsUser() {
    return currentUser;
}
}





