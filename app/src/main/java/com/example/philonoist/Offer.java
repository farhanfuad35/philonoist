package com.example.philonoist;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;

import java.io.Serializable;
import java.util.ArrayList;

public class Offer implements Serializable {

    private String salary;
    private String _class;
    private String location;
    private String  name;
    private String subject;



//    public Offer(String salary, ArrayList<String> _class, String location) {
//        this.salary = salary;
//        this._class = _class;
//        this.location = location;
//        this.email = email;
//    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() { return name; }

    public void setName( String name) { this.name = name; }

    public String getSubject() { return subject; }

    public void setSubject( String subject) { this.subject = subject; }



}



