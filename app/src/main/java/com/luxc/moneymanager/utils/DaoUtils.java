package com.luxc.moneymanager.utils;

import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.entity.FamilyBean;
import com.luxc.moneymanager.entity.IncomePayRecordBean;
import com.luxc.moneymanager.entity.UserBean;
import com.luxc.moneymanager.greendao.db.IncomePayRecordBeanDao;
import com.luxc.moneymanager.greendao.db.UserBeanDao;

import java.util.List;

public class DaoUtils {
    /**
     * 根据账号 查询用户信息
     * @param userName
     * @return
     */
    public static List<UserBean> queryByName(String userName){
        return MyApp.getInstance().getDaoSession().getUserBeanDao().queryBuilder().where(UserBeanDao.Properties.PhoneNum.eq(userName)).list();
    }

    /**
     * 查询某家庭成员
     * @param familyId
     * @return
     */
    public static List<UserBean> queryByFamilyId(Long familyId){
        return MyApp.getInstance().getDaoSession().getUserBeanDao().queryBuilder().where(UserBeanDao.Properties.FamilyID.eq(familyId)).list();
    }

    /**
     * 查询所有用户
     * @return
     */
    public static List<UserBean> queryAllUser(){
        return MyApp.getInstance().getDaoSession().getUserBeanDao().queryBuilder().list();
    }

    /**
     * 通过userId 查询 收支记录
     * @param userId
     * @return
     */
    public static List<IncomePayRecordBean> queryRecordByUserId(Long userId){
        return MyApp.getInstance().getDaoSession().getIncomePayRecordBeanDao().queryBuilder().where(IncomePayRecordBeanDao.Properties.UserId.eq(userId)).list();
    }

    /**
     * 查询所有家庭
     * @return
     */
    public static List<FamilyBean> queryAllFamily(){
        return MyApp.getInstance().getDaoSession().getFamilyBeanDao().queryBuilder().list();
    }
}
