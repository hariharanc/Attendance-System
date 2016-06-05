package com.eattendance.util;

/**
 * Created by root on 4/6/16.
 */
public class StudentRegisterDetails {
    String name;
    String depart;
    String year;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }


    public StudentRegisterDetails(String name, String depart, String year) {
        this.name = name;
        this.depart = depart;
        this.year = year;
    }

    public StudentRegisterDetails(){}
}
