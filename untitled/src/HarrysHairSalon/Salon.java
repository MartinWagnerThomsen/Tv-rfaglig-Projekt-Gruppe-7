package HarrysHairSalon;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
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
        this.password = "hairyharry";  // RETTET: Sættes til korrekt password
        this.openingHours = new HashMap<>();
        this.closedDates = new ArrayList<>();
        initializeOpeningHours();  // TILFØJET: Initialiserer åbningstider
    }

    // TILFØJET: Initialiserer standard åbningstider (man-fre 10-18)
    private void initializeOpeningHours() {
        LocalTime openTime = LocalTime.of(10, 0);
        LocalTime closeTime = LocalTime.of(18, 0);
        TimeSlot workHours = new TimeSlot(openTime, closeTime, true);

        // Mandag til fredag
        openingHours.put(DayOfWeek.MONDAY, workHours);
        openingHours.put(DayOfWeek.TUESDAY, workHours);
        openingHours.put(DayOfWeek.WEDNESDAY, workHours);
        openingHours.put(DayOfWeek.THURSDAY, workHours);
        openingHours.put(DayOfWeek.FRIDAY, workHours);
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

    // Tjek om salonen er åben på en specifik dato
    public boolean isOpenOn(LocalDate date) {
        // Tjek om datoen er en lukkedag
        if (closedDates.contains(date)) {
            return false;
        }

        // Tjek om der er åbningstider for denne ugedag
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return openingHours.containsKey(dayOfWeek);
    }

    // RETTET: Kalder isOpenOn i stedet for at returnere false
    public boolean isOpen(LocalDate date) {
        return isOpenOn(date);
    }

    // Tilføj en lukkedag (ferie/helligdag)
    public void addClosedDate(LocalDate date) {
        if (!closedDates.contains(date)) {
            closedDates.add(date);
            System.out.println("Added closed date: " + date);
        }
    }

    // Fjern en lukkedag
    public void removeClosedDate(LocalDate date) {
        if (closedDates.remove(date)) {
            System.out.println("Removed closed date: " + date);
        }
    }

    // Verificer password for adgang til financials
    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    // Tilføj åbningstider for en specifik dag
    public void addOpeningHours(DayOfWeek day, TimeSlot timeSlot) {
        openingHours.put(day, timeSlot);
    }

    // Fjern åbningstider for en specifik dag (fx hvis lukket weekenden)
    public void removeOpeningHours(DayOfWeek day) {
        openingHours.remove(day);
    }

    @Override
    public String toString() {
        return "Salon: " + name;
    }
}

