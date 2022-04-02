package com.luxc.moneymanager.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ApplyBean implements Serializable {
    public static final long serialVersionUID=536871008;

    @Id(autoincrement = true)
    private Long applyId;
    private Long applyUserId;
    private String applyUserName;
    private int status;

    @Generated(hash = 1547555326)
    public ApplyBean(Long applyId, Long applyUserId, String applyUserName,
            int status) {
        this.applyId = applyId;
        this.applyUserId = applyUserId;
        this.applyUserName = applyUserName;
        this.status = status;
    }

    @Generated(hash = 2037612428)
    public ApplyBean() {
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Long applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
