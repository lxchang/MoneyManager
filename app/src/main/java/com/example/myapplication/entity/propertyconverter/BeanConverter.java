package com.example.myapplication.entity.propertyconverter;

import com.example.myapplication.entity.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.List;

public class BeanConverter implements PropertyConverter<List<UserBean>,String> {
    @Override
    public List<UserBean> convertToEntityProperty(String databaseValue) {

//        Type type = new TypeToken<>(){}.getType();
        ArrayList<UserBean> itemList= new Gson().fromJson(databaseValue, ArrayList.class);
        return itemList;
    }


    @Override
    public String convertToDatabaseValue(List entityProperty) {

        String dbString = new Gson().toJson(entityProperty);
        return dbString;
    }
}
