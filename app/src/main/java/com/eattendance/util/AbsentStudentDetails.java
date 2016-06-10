package com.eattendance.util;

/**
 * Created by root on 5/6/16.
 */
public class AbsentStudentDetails {
    String name;
    String dept;
    String year;
    String date;
    String mobNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public AbsentStudentDetails(String name, String dept, String year, String date, String mobNo) {
        this.name = name;
        this.dept = dept;
        this.year = year;
        this.date = date;
        this.mobNo = mobNo;
    }



    public AbsentStudentDetails(){}
}
