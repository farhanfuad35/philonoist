package com.example.philonoist;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;

import java.io.Serializable;
import java.util.ArrayList;

public class Offer implements Serializable{

    private String objectId;
    private String salary;
    private String _class;
    private String contact;
    private String remarks;
    private GeoPoint location;
    private String  name;
    private String subject;
    private String email;
    private boolean active;

    public String getObjectId() {
        return objectId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    private int ID;

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String  get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getName() { return name; }

    public void setName( String name) { this.name = name; }

    public String getSubject() { return subject; }

    public void setSubject( String subject) { this.subject = subject; }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() { return contact; }

    public void setContact(String contact) {this.contact = contact;}

    public String getRemarks() {return remarks; }

    public void setRemarks(String remarks) { this.remarks = remarks; }


}



