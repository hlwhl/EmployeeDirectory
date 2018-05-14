package com.soton.mobiledev.employeedirectory.entities;

import java.io.Serializable;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class Employee implements Serializable {
    private String name;
    private int id;
    private String mail;
    private BmobFile photo;
    private String phonenum;
    private boolean isManager;
    private String managersName;
    private String Department;
    private String location;

    public Employee(String name, int id, String mail, BmobFile photo, String phonenum, boolean isManager) {
        this.name = name;
        this.id = id;
        this.mail = mail;
        this.photo = photo;
        this.phonenum = phonenum;
        this.isManager = isManager;
    }

    public Employee(String name, int id, String mail, BmobFile photo, String phonenum) {
        this.name = name;
        this.id = id;
        this.mail = mail;
        this.photo = photo;
        this.phonenum = phonenum;

    }

    public Employee(String name, int id, String mail, BmobFile photo, String phonenum, boolean isManager, String address) {
        this.name = name;
        this.id = id;
        this.mail = mail;
        this.photo = photo;
        this.phonenum = phonenum;
        this.isManager = isManager;
        this.location = address;
    }

    public Employee(String name, int id, String mail, BmobFile photo, String phonenum, boolean isManager, String address,String department) {
        this.name = name;
        this.id = id;
        this.mail = mail;
        this.photo = photo;
        this.phonenum = phonenum;
        this.isManager = isManager;
        this.location = address;
        this.Department=department;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }


    public void setDepartment(String department) {
        Department = department;
    }

    public String getDepartment() {
        return Department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPhoto(BmobFile photo) {
        this.photo = photo;
    }

    public BmobFile getPhoto() {
        return photo;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public boolean getIsManager() {
        return isManager;
    }

    public void serManagersName(String managersname) {
        this.managersName = managersName;
    }

    public String getManagersName() {
        return this.managersName;
    }
}
