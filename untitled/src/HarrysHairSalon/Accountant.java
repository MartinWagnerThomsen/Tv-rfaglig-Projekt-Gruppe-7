package HarrysHairSalon;

public class Accountant extends User {

    //Laver username for accountant, og trækker rollen fra vores UserRole klasse
    public Accountant(String username, UserRole role){
        super(username, role);
    }
    //Fortæller at accountant kun kan læse i systemet og har får muligheden for at se dagens revenue + generer en rapport
    @Override
    public String getPermissions(){
        return "READ ONLY CAN SEE DAILY REVENUE OR GENERATE REPORT";
    }
    //Fortæller at accountant får adgang til financials
    @Override
    public boolean canAccesFinancials(){
        return true;
    }
    //Fortæller accountant at de ikke kan lave nye aftaler
    @Override
    public void createAppointment(String appointments){
        System.out.println(username + " " + "CAN'T CREATE APPOINTMENT");
    }
    //Fortæller at accountant ikke kan slette nogen aftaler
    @Override
    public void deleteAppointment(String appointments){
        System.out.println(username + " " + "CAN'T DELETE APPOINTMENT");
    }
    //Laver print text på at den generer en rapport
    public void generateFinancialReport(){
        System.out.println(username + " " + "GENERATING FINANCIAL REPORT....");
    }
    public void viewDailyRevenue(){
        System.out.println(username + " " + "HERE IS A VIEW OF THE DAILY REVENUE");
    }
}
