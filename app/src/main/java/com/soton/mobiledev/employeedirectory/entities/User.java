package com.soton.mobiledev.employeedirectory.entities;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser {
    private BmobFile Photo;
    private Boolean isManager;

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
}
