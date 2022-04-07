package com.luxc.moneymanager.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.math.BigDecimal;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class IncomePayRecordBean {
    @Id(autoincrement = true)
    private Long id;
    /**
     * type 1:收入 2：支出
     */
    private int type;
    private String time;
    private Long userId;
    private String user;
    private String money;
    private String title;
    private String description;

    @Generated(hash = 294039746)
    public IncomePayRecordBean(Long id, int type, String time, Long userId,
            String user, String money, String title, String description) {
        this.id = id;
        this.type = type;
        this.time = time;
        this.userId = userId;
        this.user = user;
        this.money = money;
        this.title = title;
        this.description = description;
    }

    @Generated(hash = 2109134377)
    public IncomePayRecordBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getUser() {
        return this.user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getMoney() {
        return this.money;
    }
    public void setMoney(String money) {
        this.money = money;
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
