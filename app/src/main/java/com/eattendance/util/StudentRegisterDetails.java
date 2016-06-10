package com.eattendance.util;

/**
 * Created by root on 4/6/16.
 */
public class StudentRegisterDetails {
    String name;
    String depart;
    String year;
    String mobNo;

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public StudentRegisterDetails(String name, String depart, String year, String mobNo) {
        this.name = name;
        this.depart = depart;
        this.year = year;
        this.mobNo = mobNo;
    }

    public StudentRegisterDetails(){}
}
