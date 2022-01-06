package com.example.myapplication.entity;

import com.example.myapplication.entity.propertyconverter.BeanConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;

@Entity
public class UserManager {
    @Convert(columnType = String.class,converter = BeanConverter.class)
    private List<UserBean> userList;

    @Generated(hash = 82737538)
    public UserManager(List<UserBean> userList) {
        this.userList = userList;
    }

    @Generated(hash = 356581807)
    public UserManager() {
    }

    public List<UserBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserBean> userList) {
        this.userList = userList;
    }
}
