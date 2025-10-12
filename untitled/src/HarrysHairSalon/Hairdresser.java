package HarrysHairSalon;


public class Hairdresser extends User {

    // Bruger vores superklasse til at få username, rolle og password
    public Hairdresser(String username, UserRole role) {
        // Laver hairyharry som password
        super(username, "hairyharry", role);
    }
// Man kan få lov til lave eller slette aftaler
    @Override
    public String getPermission() {
        return "FULL ACCESS: Can choose to create or delete appointments";
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
        System.out.println(username + " " + "deleted appointment" + appointments);
    }
    // Harry skal også kunne generere report
    @Override
    public void generateFinancialReport(){
        System.out.println(username + " " + "is generating report...");
    }
    // Harry skal også have en oversigt over dagens revenue
    @Override
    public void viewDailyRevenue(){
        System.out.println(username + " " + "is viewing today's revenue");
    }
}