package com.example.philonoist;

import java.io.Serializable;
import java.util.ArrayList;

public class Offer implements Serializable {
    private String salary;
    private ArrayList<String> _class;
    private String location;
    private String email;



    public Offer(String salary, ArrayList<String> _class, String location) {
        this.salary = salary;
        this._class = _class;
        this.location = location;
        this.email = email;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public ArrayList<String> get_class() {
        return _class;
    }

    public void set_class(ArrayList<String> _class) {
        this._class = _class;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail(String email) { return email; }

    public void setEmail( String email) { this.email = email; }





}
