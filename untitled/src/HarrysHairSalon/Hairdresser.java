package HarrysHairSalon;

// Laver kodeordet "hairyharry"
public class Hairdresser extends User {
    private String password = "hairyharry";

    // Bruger vores superklasse til at få username, rolle og password
    public Hairdresser(String username, UserRole role) {
        super(username, password, role);
    }
// Fortæller at man kan logge ind hvis password er det rigtige password
    public boolean logIn(String password) {
        return this.password.equals(password);
    }
// Man kan få lov til lave eller slette aftaler
    @Override
    public String getPermission() {
        return "Can choose to create or delete appointments";
    }
// At man får adgang til financials
    @Override
    public boolean canAccesFinancials() {
        return true;
    }
// Man kan lave aftaler, og får et output på hvilken aftale man har lavet
    @Override
    public void createAppointment(String appointments) {
        System.out.println(username + " " + "creates appointment" + appointments);
    }
// Man kan slette aftaler, og får et output på hvilken aftale man så har slettet
    @Override
    public void deleteAppointment(String appointments) {
        System.out.println(username + " " + "deletes appointment" + appointments);
    }
}