package com.example.lightsout;

public class Users {
    private String Name;
    private long phoneNumber;
    private String Email;
    private String password;
    public Users() {

    }

    // created getter and setter methods
    // for all our variables.
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public long getphoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long employeeContactNumber) {
        this.phoneNumber = employeeContactNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String p) {
        this.password = p;
    }

}
