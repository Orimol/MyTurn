package com.example.myturn.myturn;

public class PersonalInfo {
    private String firstname;
    private String lastname;
    private String Phone;

    public PersonalInfo(String firstname, String lastname, String phone){
        this.firstname = firstname;
        this.lastname = lastname;
        this.Phone = phone;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return Phone;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}

