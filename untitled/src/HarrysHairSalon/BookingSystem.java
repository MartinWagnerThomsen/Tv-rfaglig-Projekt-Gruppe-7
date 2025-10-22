package HarrysHairSalon;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.*;

public class BookingSystem {
    // Attributer
    private ArrayList<Appointment> appointments;
    private ArrayList<Customer> customers;
    private ArrayList<Product> products;
    private Salon salon;
    private User currentUser;
    private static final String DATA_FILE = "booking_data.txt";

    // konstruktør
    public BookingSystem() {
        this.appointments = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.products = new ArrayList<>();
        this.salon = new Salon("Harry's Salon");
        this.currentUser = null;
        initializeProducts();
        loadData();
    }

    // Initialiserer produkter
    private void initializeProducts() {
        products.add(new Product(1, "Head & Shoulders", 49.99, ProductCategory.SHAMPOO));
        products.add(new Product(2, "Coconut milk conditioner", 35.00, ProductCategory.CONDITIONER));
        products.add(new Product(3, "Hair brush", 150.00, ProductCategory.BRUSH));
        products.add(new Product(4, "Hair wax", 199.99, ProductCategory.WAX_AND_GEL));
        products.add(new Product(5, "Comb", 19.99, ProductCategory.OTHER));
    }

    /**
     * Opretter en ny aftale
     * @param customer Kunden der skal bookes
     * @param dateTime Ønsket dato og tidspunkt
     * @param duration Varighed i minutter
     * @return true hvis aftalen blev oprettet, false hvis tidspunktet ikke er ledigt
     */
    public boolean createAppointment(Customer customer, LocalDateTime dateTime, int duration) {
        if (currentUser == null || !currentUser.getPermission().contains("FULL_ACCESS")) {
            System.out.println("You are not allowed to create an appointment without permissions.");
            return false;
        }

        // Tjekker om salonen er åben
        if (!salon.isOpenOn(dateTime.toLocalDate())) {
            System.out.println("The salon is closed this day");
            return false;
        }

        if (!isTimeSlotAvailable(dateTime, duration)) {
            System.out.println("Time slot isn't available");
            return false;
        }

        // Opretter ny aftale
        int newID = appointments.size() + 1;
        Appointment newAppointment = new Appointment(newID, customer, dateTime, duration);
        appointments.add(newAppointment);

        System.out.println("New appointment has been created for " + customer.getName());
        System.out.println("ID: " + newAppointment.getId() +
                ", Date: " + dateTime +
                ", Duration: " + duration + " minutes");

        saveData();
        return true;
    }

    /**
     * Sletter en eksisterende aftale
     * @param appointmentId ID på aftalen der skal slettes
     * @return true hvis sletning lykkedes
     */
    public boolean deleteAppointment(int appointmentId) {
        if (currentUser == null || !currentUser.getPermission().startsWith("FULL")) {
            System.out.println("You are not allowed to delete an appointment without permissions.");
            return false;
        }

        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getId() == appointmentId) {
                Appointment apt = appointments.remove(i);
                System.out.println("Appointment has been deleted: ID " + apt.getId());
                saveData();
                return true;
            }
        }

        System.out.println("No appointment with ID: " + appointmentId);
        return false;
    }

    /**
     * Viser ledige tider på en given dato
     * @param date Datoen der skal tjekkes
     */
    public void showAvailableTimeSlots(LocalDate date) {
        System.out.println("\n=== Available Time Slots for " + date + " ===");

        // Tjek om salonen er åben
        if (!salon.isOpenOn(date)) {
            System.out.println("The salon is closed this day");
            return;
        }

        // Åbningstider: 10:00 - 18:00
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        int slotDuration = 60; // 60 minutters slots

        LocalTime currentTime = startTime;

        while (currentTime.isBefore(endTime)) {
            LocalDateTime slotDateTime = LocalDateTime.of(date, currentTime);

            if (isTimeSlotAvailable(slotDateTime, slotDuration)) {
                System.out.println("✓ " + currentTime + " - Available");
            } else {
                System.out.println("✗ " + currentTime + " - Not Available");
            }

            currentTime = currentTime.plusHours(1);
        }
    }

    /**
     * @param dateTime Tidspunkt der skal tjekkes
     * @param duration Varighed i minutter
     * @return true hvis ledigt, false hvis optaget
     */
    private boolean isTimeSlotAvailable(LocalDateTime dateTime, int duration) {
        LocalDateTime endTime = dateTime.plusMinutes(duration);

        for (Appointment apt : appointments) {
            // Spring aflyste aftaler over
            if (apt.getStatus() == AppointmentStatus.CANCELLED) {
                continue;
            }

            LocalDateTime aptStart = apt.getDateTime();
            LocalDateTime aptEnd = aptStart.plusMinutes(apt.getDuration());

            // Tjek for overlap
            if (dateTime.isBefore(aptEnd) && endTime.isAfter(aptStart)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Søg efter kunde baseret på navn eller telefonnummer
     * @param searchTerm Søgeord (navn eller telefon)
     * @return kunde hvis fundet, null hvis ikke
     */
    public Customer searchCustomer(String searchTerm) {
        for (Customer c : customers) {
            if (c.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    c.getPhone().contains(searchTerm)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Tilføjer en ny kunde til systemet
     * @param customer Kunde der skal tilføjes
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
        saveData();
        System.out.println("Customer added: " + customer.getName());
    }

    /**
     * Gemmer alle data til fil
     */
    public void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            // Gem kunder
            writer.println("=== CUSTOMERS ===");
            for (Customer c : customers) {
                writer.println(c.getId() + ";" + c.getName() + ";" +
                        c.getPhone() + ";" + c.getEmail() + ";" + c.hasCredit());
            }

            // Gem aftaler
            writer.println("=== APPOINTMENTS ===");
            for (Appointment apt : appointments) {
                writer.println(apt.getId() + ";" + apt.getCustomer().getId() + ";" +
                        apt.getDateTime() + ";" + apt.getDuration() + ";" +
                        apt.getStatus());
            }

        } catch (IOException e) {
            System.out.println("Error occurred while saving data: " + e.getMessage());
        }
    }

    /**
     * Læser data fra fil
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
                    Customer c = new Customer(id, parts[1], parts[2], parts[3]);
                    c.setHasCredit(Boolean.parseBoolean(parts[4]));
                    customers.add(c);
                }


            }
            System.out.println("Data loaded!");
            System.out.println("Customer amount: " + customers.size());
            System.out.println("Appointment amount: " + appointments.size());

        } catch (IOException e) {
            System.out.println("Error occurred while reading data: " + e.getMessage());
        }
    }

    /**
     * Logger en bruger ind i systemet
     * @param user Brugeren der logger ind
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
        System.out.println("Logged in as: " + user.getUsername() +
                " (" + user.getRole() + ")");
    }

    /**
     * Verificer password for adgang til financials
     * @param password Password at verificere
     * @return true hvis korrekt
     */
    public boolean verifyFinancialPassword(String password) {
        return salon.verifyPassword(password);
    }

    /**
     * Genererer rapport for en specifik dato (kun for Accountant og Hairdresser med password)
     * @param date Datoen for rapporten
     */
    public void generateDailyReport(LocalDate date) {
        if (currentUser == null || !currentUser.canAccessFinancials()) {
            System.out.println("You are not allowed to access financial reports!");
            return;
        }

        System.out.println("\n=== DAILY REPORT FOR " + date + " ===");

        double totalRevenue = 0.0;
        int completedAppointments = 0;

        for (Appointment apt : appointments) {
            if (apt.getDateTime().toLocalDate().equals(date) &&
                    apt.getStatus() == AppointmentStatus.COMPLETED) {

                Order order = apt.getOrder();
                if (order != null && order.getPayment() != null && order.getPayment().isPaid()) {
                    System.out.println("Customer: " + apt.getCustomer().getName() +
                            " | Amount: " + String.format("%.2f", order.getTotalAmount()) + " kr");
                    totalRevenue += order.getTotalAmount();
                    completedAppointments++;
                }
            }
        }

        System.out.println("\nAmount of completed appointments: " + completedAppointments);
        System.out.println("Total revenue: " + String.format("%.2f", totalRevenue) + " kr");
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

    public User getCurrentUser() {
        return currentUser;
    }
}



