package HarrysHairSalon;

public abstract class User {
    protected String username;
    protected String password;
    protected UserRole role;

    public User(String username, String password, UserRole role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getRole() {return role;}

    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setRole(UserRole role) {this.role = role;}

    public String getUserInfo(){
        return username + password + role;
    }

    @Override
    public String toString(){
        return getUserInfo();
    }

    public boolean login (String password){
        return this.password.equals(password);
    }

    public abstract Permission getPermission();

    public abstract boolean canAccessFinacials();
}
