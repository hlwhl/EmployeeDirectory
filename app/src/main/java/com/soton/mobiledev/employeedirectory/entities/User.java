package com.soton.mobiledev.employeedirectory.entities;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser {
    private BmobFile Photo;
    private Boolean isManager;
    private String address;
    private String Department;
    private String phonenum;

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhoto(BmobFile photo) {
        Photo = photo;
    }

    public BmobFile getPhoto() {
        return Photo;
    }

    public void setIsManager(Boolean manager) {
        isManager = manager;
    }

    public Boolean getIsManager() {
        return isManager;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getDepartment() {
        return Department;
    }
}
