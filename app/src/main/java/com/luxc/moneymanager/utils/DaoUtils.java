package com.luxc.moneymanager.utils;

import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.entity.FamilyBean;
import com.luxc.moneymanager.entity.UserBean;
import com.luxc.moneymanager.greendao.db.UserBeanDao;

import java.util.List;

public class DaoUtils {
    /**
     * 根据用户名称 查询用户信息
     * @param userName
     * @return
     */
    public static List<UserBean> queryByName(String userName){
        return MyApp.getInstance().getDaoSession().getUserBeanDao().queryBuilder().where(UserBeanDao.Properties.Name.eq(userName)).list();
    }

    /**
     * 查询所有用户
     * @return
     */
    public static List<UserBean> queryAllUser(){
        return MyApp.getInstance().getDaoSession().getUserBeanDao().queryBuilder().list();
    }

    /**
     * 查询所有家庭
     * @return
     */
    public static List<FamilyBean> queryAllFamily(){
        return MyApp.getInstance().getDaoSession().getFamilyBeanDao().queryBuilder().list();
    }
}
