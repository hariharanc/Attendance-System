package com.eattendance.util;

/**
 * Created by root on 4/6/16.
 */
public class StaffRegisterDetails {

    private String mobNo;
    private String staffName;
    private String department;
    private String pin;

    public StaffRegisterDetails() {
    }

    public StaffRegisterDetails(String mobNo, String staffName, String department, String pin) {
        this.mobNo = mobNo;
        this.staffName = staffName;
        this.department = department;
        this.pin = pin;
    }

    public String getMobNo() {
        return mobNo;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setMobNo(String mobNo) {

        this.mobNo = mobNo;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }


}

