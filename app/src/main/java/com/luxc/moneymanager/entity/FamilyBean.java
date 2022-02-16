package com.luxc.moneymanager.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 *
 */
@Entity
public class FamilyBean implements Serializable{
    public static final long serialVersionUID=536871008L;

    @Id(autoincrement = true)
    private Long familyId;
    private String familyName;
    private String creater;
    private Long createUserId;

    @Generated(hash = 2106885228)
    public FamilyBean(Long familyId, String familyName, String creater,
            Long createUserId) {
        this.familyId = familyId;
        this.familyName = familyName;
        this.creater = creater;
        this.createUserId = createUserId;
    }

    @Generated(hash = 323948625)
    public FamilyBean() {
    }

    public Long getFamilyId() {
        return this.familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getCreater() {
        return this.creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Long getCreateUserId() {
        return this.createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
}
