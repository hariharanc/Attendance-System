package com.eattendance.util;

/**
 * Created by root on 5/6/16.
 */
public class AbsentStudentDetails {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String name,dept,year,date;

    public AbsentStudentDetails(String name, String year, String dept, String date) {
        this.name = name;
        this.year = year;
        this.dept = dept;
        this.date = date;
    }

    public AbsentStudentDetails(){}
}
