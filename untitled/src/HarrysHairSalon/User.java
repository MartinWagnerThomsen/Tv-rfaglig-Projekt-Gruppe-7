package HarrysHairSalon;

public abstract class User {
    protected String username;
    protected String password;
    protected UserRole role;

    //Konstruktør
    public User(String username, String password, UserRole role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public UserRole getRole() {return role;}

    // Setters
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setRole(UserRole role) {this.role = role;}

    //Få bruger info
    public String getUserInfo(){
        return username + password + role;
    }

    @Override
    public String toString(){
        return getUserInfo();
    }

    // Login metode
    public boolean login (String password){
        return this.password.equals(password);
    }

    // Abstrakte metoder
    public abstract String getPermission();
    public abstract boolean canAccessFinancials();
    public abstract void createAppointment(String appointment);
    public abstract void deleteAppointment(String appointment);
    public abstract void generateFinancialReport();
    public abstract void viewDailyRevenue();
}
