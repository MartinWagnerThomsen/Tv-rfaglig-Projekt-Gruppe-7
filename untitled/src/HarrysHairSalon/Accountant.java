package HarrysHairSalon;

public class Accountant extends User {

    //Laver username for accountant, og trækker rollen fra vores UserRole klasse
    public Accountant(String username, String password, UserRole role){
        super(username, password, role);

    }

    //Fortæller at accountant kun kan læse i systemet og har får muligheden for at se dagens revenue + generer en rapport
    @Override
    public String getPermission(){
        return "READ ONLY: Can only see revenue and generate report";

    }

    //Fortæller at accountant får adgang til financials
    @Override
    public boolean canAccessFinancials(){
        return true;

    }

    //Fortæller accountant at de ikke kan lave nye aftaler
    @Override
    public void createAppointment(String appointment){
        System.out.println(username + " " + "CAN'T CREATE APPOINTMENT");

    }

    //Fortæller at accountant ikke kan slette nogen aftaler
    @Override
    public void deleteAppointment(String appointment){
        System.out.println(username + " " + "CAN'T DELETE APPOINTMENT");

    }

    //Laver print text på at den generer en rapport
    @Override
    public void generateFinancialReport(){
        System.out.println(username + " " + "is generating financial report....");

    }

    @Override
    public void viewDailyRevenue(){
        System.out.println(username + " " + "is viewing today's revenue");

    }
}
