package com.luxc.moneymanager.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class UserBean implements Serializable {
    public static final long serialVersionUID=536871008;
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String age;
    private String sex;
    private String birthday;
    // userType 用户类型 0 超级管理员 1：家庭管理员  2；普通用户（家庭成员）
    private int userType=2;
    //对应所属家庭id
    private Long familyID;
    private String familyName="";
    private String password;
    private String phoneNum;

    @Generated(hash = 247182974)
    public UserBean(Long id, String name, String age, String sex, String birthday,
            int userType, Long familyID, String familyName, String password,
            String phoneNum) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
        this.userType = userType;
        this.familyID = familyID;
        this.familyName = familyName;
        this.password = password;
        this.phoneNum = phoneNum;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Long getFamilyID() {
        return this.familyID;
    }

    public void setFamilyID(Long familyID) {
        this.familyID = familyID;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", userType=" + userType +
                ", familyID=" + familyID +
                ", familyName='" + familyName + '\'' +
                ", password='" + password + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}
