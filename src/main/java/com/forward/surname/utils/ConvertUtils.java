package com.forward.surname.utils;

import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zy on 2017/11/1.
 */

public final  class ConvertUtils {
    /**
     * HashMap转字符串
     * 传入参数:map，","，"="
     * 返回值:String 形如 username=chenziwen,password=1234
     */
    public static String transMapToString(Map map,String entrySplit,String itemSplit){
        if(map==null||TextUtils.isEmpty(entrySplit)|| TextUtils.isEmpty(itemSplit))
            return null;
        Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            entry = (Map.Entry)iterator.next();
            sb.append(entry.getKey().toString()).append( itemSplit ).append(null==entry.getValue()?"":
                    entry.getValue().toString()).append (iterator.hasNext() ? entrySplit : "");
        }
        return sb.toString();
    }

    /**
     *字符串转HashMap
     * 形如 username=chenziwen,password=1234
     *  则传入参数:"username=chenziwen,password=1234"，","，"="
     */
    public static Map strToHashMap(String mapString,String entrySplit,String itemSplit){
        if(TextUtils.isEmpty(mapString)||TextUtils.isEmpty(entrySplit)|| TextUtils.isEmpty(itemSplit))
            return null;
        Map map = new HashMap();
        StringTokenizer items;
        for(StringTokenizer entrys = new StringTokenizer(mapString, ", "); entrys.hasMoreTokens();
            map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
            items = new StringTokenizer(entrys.nextToken(), "=");
        return map;
    }



    /**
     * Object转Int
     * @param obj
     * @return
     */
    public static  int objToInt(Object obj){
        if(obj==null) return 0;
        return stringToInt(objToString(obj));
    }

    /**
     *String转Int
     * @param string
     * @return
     */
    public static int stringToInt(String string) {
        if(string==null) return 0;
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(string);
        if (isNum.matches()) {
            return Integer.valueOf(string);
        }
        return 0;
    }

    /**
     * Object转String
     * @param obj
     * @return
     */
    public static String objToString(Object obj) {
        if(obj==null) return "";
        return obj.toString();
    }

    /**
     * Object转Long
     * @param object
     * @return
     */
    public static Long objToLong(Object object){
        try {
            if(object!=null){
                return   Long.parseLong(objToString(object));
            }
        }catch (Exception e){

        }
        return 0l;
    }

    /**
     * Object转Double
     * @param object
     * @return
     */
    public static Double objToDouble(Object object){
        try {
            if(object!=null){
                return   Double.parseDouble(objToString(object));
            }
        }catch (Exception e){

        }
        return 0.0;
    }

    /**
     * String转Long
     * @param str
     * @return
     */
    public static Long stringToLong(String str){
        try {
            if(str!=null){
                return   Long.parseLong(str);
            }
        }catch (Exception e){

        }
        return 0l;
    }

    /**
     * String转Double
     * @param str
     * @return
     */
    public static Double stringToDouble(String str){
        try {
            if(str!=null){
                return   Double.parseDouble(str);
            }
        }catch (Exception e){

        }
        return 0.0;
    }

    /**
     * String转Boolean
     *
     * @param str
     * @return 转换异常返回 false
     */
    public static Boolean stringToBool(String str) {
        try {
            if(str!=null){
                return Boolean.parseBoolean(str);
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * Object转Boolean
     *
     * @param obj
     * @return 转换异常返回 false
     */
    public static Boolean stringToBool(Object obj) {
        try {
            if(obj!=null){
                return Boolean.parseBoolean(objToString(obj));
            }
        } catch (Exception e) {
        }
        return false;
    }


    /**
     * 将字符串转为日期类型
     *
     * @param sdate
     * @return
     */
    public static Date stringToDate(String sdate, SimpleDateFormat dateFormater) {
        try {
            return dateFormater.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * float转换为字符串 保留两位小数
     * @param value
     * @return
     */
    public static String floatToString(float value){
        DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return  decimalFormat.format(value);
    }

}
