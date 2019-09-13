package com.example.philonoist;

public class Tuition {
    private String tuitionName;
    private String tuitionAdvertiser;
    private String remuneration;
    private String _class;

    public Tuition(String s) {
        tuitionName = s;
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

    public String get_class() {
        return _class;
    }

    public String getTuitionName() {
        return tuitionName;
    }

    public void setTuitionName(String tuitionName) {
        this.tuitionName = tuitionName;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public void setTuitionAdvertiser(String tuitionAdvertiser) {
        this.tuitionAdvertiser = tuitionAdvertiser;
    }
}
