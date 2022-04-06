package com.luxc.moneymanager.utils;

import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.entity.ApplyBean;
import com.luxc.moneymanager.entity.FamilyBean;
import com.luxc.moneymanager.entity.IncomePayRecordBean;
import com.luxc.moneymanager.entity.UserBean;
import com.luxc.moneymanager.greendao.db.ApplyBeanDao;
import com.luxc.moneymanager.greendao.db.IncomePayRecordBeanDao;
import com.luxc.moneymanager.greendao.db.UserBeanDao;

import java.util.List;

public class DaoUtils {
    /**
     * 根据账号 查询用户信息
     *
     * @param userName
     * @return
     */
    public static List<UserBean> queryByName(String userName) {
        return MyApp.getInstance().getDaoSession().getUserBeanDao().queryBuilder().where(UserBeanDao.Properties.PhoneNum.eq(userName)).list();
    }
    /**
     * 根据账号 模糊查询用户信息
     *
     * @param userName
     * @return
     */
    public static List<UserBean> querySearchByName(String userName) {
        return MyApp.getInstance().getDaoSession().getUserBeanDao().queryBuilder().where(UserBeanDao.Properties.FamilyID.eq(null),UserBeanDao.Properties.Name.like("%"+userName+"%")).list();
    }

    /**
     * 根据用户id 查询用户信息
     *
     * @param userId
     * @return
     */
    public static UserBean queryUserById(Long userId) {
        List<UserBean> list = MyApp.getInstance().getDaoSession().getUserBeanDao().queryBuilder().where(UserBeanDao.Properties.Id.eq(userId)).list();
        if (list != null && list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * 查询某家庭成员
     *
     * @param familyId
     * @return
     */
    public static List<UserBean> queryByFamilyId(Long familyId) {
        return MyApp.getInstance().getDaoSession().getUserBeanDao().queryBuilder().where(UserBeanDao.Properties.FamilyID.eq(familyId)).list();
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    public static List<UserBean> queryAllUser() {
        return MyApp.getInstance().getDaoSession().getUserBeanDao().queryBuilder().list();
    }

    /**
     * 通过userId 查询 收支记录
     *
     * @param userId
     * @return
     */
    public static List<IncomePayRecordBean> queryRecordByUserId(Long userId) {
        return MyApp.getInstance().getDaoSession().getIncomePayRecordBeanDao().queryBuilder().where(IncomePayRecordBeanDao.Properties.UserId.eq(userId)).list();
    }

    /**
     * 查询所有家庭
     *
     * @return
     */
    public static List<FamilyBean> queryAllFamily() {
        return MyApp.getInstance().getDaoSession().getFamilyBeanDao().queryBuilder().list();
    }

    /**
     * 通过申请id查询申请记录
     *
     * @param applyId id
     * @return
     */
    public static List<ApplyBean> queryApplyBean(Long applyId) {
        return MyApp.getInstance().getDaoSession().getApplyBeanDao().queryBuilder().where(ApplyBeanDao.Properties.ApplyId.eq(applyId)).list();
    }
}
