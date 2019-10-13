package com.example.philonoist;

import java.io.Serializable;
import java.util.ArrayList;

public class Tuition implements Serializable {
    private String name;
    private String email;
    private String salary;
    private ArrayList<String> _class;
    private String location;

    public Tuition(String name, String salary, ArrayList<String> _class, String location) {
        this.name = name;
        this.salary = salary;
        this._class = _class;
        this.location = location;
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
    }
}
