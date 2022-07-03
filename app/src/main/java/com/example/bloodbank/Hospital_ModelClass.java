package com.example.bloodbank;

public class Hospital_ModelClass {
    String name;
    String blood;
    String address;
    String numofbottles;
    String city;
    String reason;
    String requestedby;
    String requestdto;
    String contact;
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumofbottles() {
        return numofbottles;
    }

    public void setNumofbottles(String numofbottles) {
        this.numofbottles = numofbottles;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRequestedby() {
        return requestedby;
    }

    public void setRequestedby(String requestedby) {
        this.requestedby = requestedby;
    }

    public String getRequestdto() {
        return requestdto;
    }

    public void setRequestdto(String requestdto) {
        this.requestdto = requestdto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }





    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }




}
