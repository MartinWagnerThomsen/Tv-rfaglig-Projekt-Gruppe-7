import HarrysHairSalon.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static BookingSystem bookingSystem;
    private static Scanner scanner;

    public static void main(String[] args) {
        bookingSystem = new BookingSystem();
        scanner = new Scanner(System.in);

        System.out.println("===========================================");
        System.out.println("   Welcome to Harry's Hair Salon System   ");
        System.out.println("===========================================\n");

        // Login
        login();

        // Main menu
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = getIntInput("Choose an option: ");

            switch (choice) {
                case 1:
                    createNewAppointment();
                    break;
                case 2:
                    showAvailableSlots();
                    break;
                case 3:
                    viewAllAppointments();
                    break;
                case 4:
                    deleteAppointment();
                    break;
                case 5:
                    addNewCustomer();
                    break;
                case 6:
                    searchForCustomer();
                    break;
                case 7:
                    viewDailyReport();
                    break;
                case 8:
                    addClosedDate();
                    break;
                case 9:
                    viewAllProducts();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using Harry's Salon System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void login() {
        System.out.println("=== LOGIN ===");
        System.out.println("1. Harry (Hairdresser)");
        System.out.println("2. Harriet (Hairdresser)");
        System.out.println("3. Accountant");

        int choice = getIntInput("Choose user: ");

        User user = null;
        switch (choice) {
            case 1:
                user = new Hairdresser("Harry", UserRole.HAIRDRESSER);
                break;
            case 2:
                user = new Hairdresser("Harriet", UserRole.HAIRDRESSER);
                break;
            case 3:
                user = new Accountant("Revisor", "hairyharry", UserRole.ACCOUNTANT);
                break;
            default:
                System.out.println("Invalid choice. Logging in as Harry.");
                user = new Hairdresser("Harry", UserRole.HAIRDRESSER);
        }

        bookingSystem.setCurrentUser(user);
        System.out.println();
    }

    private static void printMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Create new appointment");
        System.out.println("2. Show available time slots");
        System.out.println("3. View all appointments");
        System.out.println("4. Delete appointment");
        System.out.println("5. Add new customer");
        System.out.println("6. Search for customer");
        System.out.println("7. View daily report");
        System.out.println("8. Add closed date (holiday/vacation)");
        System.out.println("9. View all products");
        System.out.println("0. Exit");
        System.out.println();
    }

    private static void createNewAppointment() {
        System.out.println("\n=== CREATE NEW APPOINTMENT ===");

        // Søg eller opret kunde
        System.out.print("Search for customer (name or phone): ");
        String searchTerm = scanner.nextLine();

        Customer customer = bookingSystem.searchCustomer(searchTerm);
        if (customer == null) {
            System.out.println("Customer not found.");
            System.out.print("Create new customer? (yes/no): ");
            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                customer = createCustomer();
            } else {
                return;
            }
        } else {
            System.out.println("Found: " + customer.getName());
        }

        // Dato og tid
        int year = getIntInput("Year (e.g., 2025): ");
        int month = getIntInput("Month (1-12): ");
        int day = getIntInput("Day: ");
        int hour = getIntInput("Hour (10-17): ");

        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, 0);
        int duration = getIntInput("Duration in minutes (default 60): ");
        if (duration <= 0) duration = 60;

        bookingSystem.createAppointment(customer, dateTime, duration);
    }

    private static void showAvailableSlots() {
        System.out.println("\n=== AVAILABLE TIME SLOTS ===");
        int year = getIntInput("Year: ");
        int month = getIntInput("Month: ");
        int day = getIntInput("Day: ");

        LocalDate date = LocalDate.of(year, month, day);
        bookingSystem.showAvailableTimeSlots(date);
    }

    private static void viewAllAppointments() {
        System.out.println("\n=== ALL APPOINTMENTS ===");
        if (bookingSystem.getAppointments().isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            for (Appointment apt : bookingSystem.getAppointments()) {
                System.out.println("\n" + apt.getAppointmentDetails());
                System.out.println("---");
            }
        }
    }

    private static void deleteAppointment() {
        System.out.println("\n=== DELETE APPOINTMENT ===");
        int id = getIntInput("Enter appointment ID to delete: ");
        bookingSystem.deleteAppointment(id);
    }

    private static void addNewCustomer() {
        System.out.println("\n=== ADD NEW CUSTOMER ===");
        Customer customer = createCustomer();
        if (customer != null) {
            bookingSystem.addCustomer(customer);
        }
    }

    private static Customer createCustomer() {
        int id = bookingSystem.getCustomers().size() + 1;

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Has credit? (yes/no): ");
        boolean hasCredit = scanner.nextLine().equalsIgnoreCase("yes");

        return new Customer(id, name, phone, email, hasCredit);
    }

    private static void searchForCustomer() {
        System.out.println("\n=== SEARCH CUSTOMER ===");
        System.out.print("Enter name or phone: ");
        String searchTerm = scanner.nextLine();

        Customer customer = bookingSystem.searchCustomer(searchTerm);
        if (customer != null) {
            System.out.println("\nFound:");
            System.out.println(customer);
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void viewDailyReport() {
        System.out.println("\n=== DAILY REPORT ===");

        // Verificer password hvis nødvendigt
        if (bookingSystem.getCurrentUser().canAccessFinancials()) {
            System.out.print("Enter password (hairyharry): ");
            String password = scanner.nextLine();

            if (!bookingSystem.verifyFinancialPassword(password)) {
                System.out.println("Incorrect password!");
                return;
            }
        }

        int year = getIntInput("Year: ");
        int month = getIntInput("Month: ");
        int day = getIntInput("Day: ");

        LocalDate date = LocalDate.of(year, month, day);
        bookingSystem.generateDailyReport(date);
    }

    private static void addClosedDate() {
        System.out.println("\n=== ADD CLOSED DATE ===");
        int year = getIntInput("Year: ");
        int month = getIntInput("Month: ");
        int day = getIntInput("Day: ");

        LocalDate date = LocalDate.of(year, month, day);
        bookingSystem.getSalon().addClosedDate(date);
    }

    private static void viewAllProducts() {
        System.out.println("\n=== AVAILABLE PRODUCTS ===");
        for (Product product : bookingSystem.getProducts()) {
            System.out.println(product);
        }
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Please enter a valid number: ");
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }
}