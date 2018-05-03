package com.forward.surname.utils;

import java.lang.reflect.Method;

/**
 * Created by mings on 2018/5/3.
 */

public class PropertyUtils {
    /**
     * 设置属性
     * @param key
     * @param value
     */
    public static void setProperties(String key,String value){
        try {
            Class cls=Class.forName("android.os.SystemProperties");;
            Method method=cls.getMethod("set",String.class,String.class);
            method.invoke(cls,key,value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取属性
     * @param key
     * @param defaultValue
     */
    public static String getProperties(String key,String defaultValue){
        String value = defaultValue;
        try {
            Class cls=Class.forName("android.os.SystemProperties");;
            Method method=cls.getMethod("get",String.class,String.class);
            value=(String)method.invoke(cls,key,defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  value;
    }
}
