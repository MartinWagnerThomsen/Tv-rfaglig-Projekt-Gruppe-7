package HarrysHairSalon;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Salon {
    private String name;
    private Map<DayOfWeek, TimeSlot> openingHours;
    private ArrayList<LocalDate> closedDates;
    private String password;

    // Constructor
    public Salon(String name) {
        this.name = name;
        this.password = password;
        this.openingHours = new HashMap<>();
        this.closedDates = new ArrayList<LocalDate>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<DayOfWeek, TimeSlot> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(Map<DayOfWeek, TimeSlot> openingHours) {
        this.openingHours = openingHours;
    }

    public ArrayList<LocalDate> getClosedDates() {
        return closedDates;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Check if salon is open on a specific date
    public boolean isOpenOn(LocalDate date) {
        // Check if date is in closed dates
        if (closedDates.contains(date)) {
            return false;
        }

        // Check if there are opening hours for this day of week
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return openingHours.containsKey(dayOfWeek);
    }

    // Add a closed date
    public void addClosedDate(LocalDate date) {
        if (!closedDates.contains(date)) {
            closedDates.add(date);
        }
    }

    // Remove a closed date
    public void removeClosedDate(LocalDate date) {
        closedDates.remove(date);
    }

    // Verify password
    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    // Helper method to add opening hours for a specific day
    public void addOpeningHours(DayOfWeek day, TimeSlot timeSlot) {
        openingHours.put(day, timeSlot);
    }

    @Override
    public String toString() {
        return "Salon: " + name;
    }

    public boolean isOpen(LocalDate date) {
        return false;
    }
}



