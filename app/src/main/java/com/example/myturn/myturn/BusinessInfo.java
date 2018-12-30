package com.example.myturn.myturn;

import java.util.Date;
import java.util.Set;

public class BusinessInfo {
    private String BusinessName;
    private String Address;
    private String phone;
    private Set<Date> Turns;

    public BusinessInfo(String BusinessName,
                        String Adress ,
                        String phone) {
        this.BusinessName=BusinessName;
        this.Address=Adress;
        this.phone=phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public String getAddress() {
        return Address;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
