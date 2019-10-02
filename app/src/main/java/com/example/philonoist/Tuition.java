package com.example.philonoist;

import java.util.ArrayList;

public class Tuition {
    private String tuitionAdvertiser;
    private String remuneration;
    private ArrayList<String> _class;
    private String location;

    public Tuition(String tuitionAdvertiser, String remuneration, ArrayList<String> _class, String location) {
        this.tuitionAdvertiser = tuitionAdvertiser;
        this.remuneration = remuneration;
        this._class = _class;
        this.location = location;
    }

    public String getTuitionAdvertiser() {
        return tuitionAdvertiser;
    }

    public String getRemuneration() {
        return remuneration;
    }

    public void setRemuneration(String remuneration) {
        this.remuneration = remuneration;
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

    public void setTuitionAdvertiser(String tuitionAdvertiser) {
        this.tuitionAdvertiser = tuitionAdvertiser;
    }
}
