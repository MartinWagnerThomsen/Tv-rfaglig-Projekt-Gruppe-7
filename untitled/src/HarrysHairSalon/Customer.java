package HarrysHairSalon;

public class Customer {
    private int id;
    private String name;
    private String phone;
    private String mail;
    private boolean hasCredit;

    public Customer(int id, String name, String phone, String mail, boolean hasCredit) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.hasCredit = hasCredit;
    }

    public Customer(int id, String name, String phone, String mail) {
        this(id, name, phone, mail, false);
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return mail;
    }

    public boolean hasCredit() {
        return hasCredit;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setHasCredit(boolean hasCredit) {
        this.hasCredit = hasCredit;
    }


    public String CustomerInfo() {
        return new String("Customer Info:\n" + "ID: " + id + "\nName: " + name +
                "\nPhone: " + phone + "\nMail: " + mail + "\nCredit: " + hasCredit);
    }

    @Override
    public String toString() {
        return CustomerInfo();
    }
}
