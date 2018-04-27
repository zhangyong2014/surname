package com.forward.surname.utils;

import android.support.v4.BuildConfig;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zy on 2017/11/1.
 */

public class LogUtils {
    private static final String TAG="surname";
    private static final int JSON_INDENT = 4;
    private static final boolean isDebug = true;



    /**
     * debug
     */
    public static void d(String str, Object... objects) {
        d(TAG,str, objects);
    }

    /**
     * info
     */
    public static void i(String str, Object... objects) {
        i(TAG,str, objects);
    }

    /**
     * verbose
     */
    public static void v(String str, Object... objects) {

        v(TAG, str, objects);
    }

    /**
     * error
     */
    public static void e(String str, Object... objects) {
        e(TAG, str, objects);
    }

    /**
     * warning
     */
    public static void w(String str, Object... objects) {
        w(TAG,str, objects);
    }




    /**
     * debug
     */
    public static void d(String tag,String str, Object... objects) {
        if (isDebug) {
            Log.d(tag, buildLogString(str, objects));
        }
    }

    /**
     * info
     */
    public static void i(String tag,String str, Object... objects) {
        if (isDebug) {
            Log.i(tag, buildLogString(str, objects));
        }
    }

    /**
     * verbose
     */
    public static void v(String tag,String str, Object... objects) {
        if (isDebug) {
            Log.v(tag, buildLogString(str, objects));
        }
    }

    /**
     * error
     */
    public static void e(String tag,String str, Object... objects) {
        if (isDebug) {
            Log.e(tag, buildLogString(str, objects));
        }
    }

    /**
     * warning
     */
    public static void w(String tag,String str, Object... objects) {
        if (isDebug) {
            Log.w(tag, buildLogString(str, objects));
        }
    }


    /**
     * 根据StackTrace生成带更多信息的log
     * 文件名,方法名,行数
     */
    private static String buildLogString(String str, Object... args) {
        // format string with args
        if (args.length > 0) {
            str = String.format(str, args);
        }
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("(")
                .append(caller.getFileName())
                .append(":")
                .append(caller.getLineNumber())
                .append(").")
                .append(caller.getMethodName())
                .append("():")
                .append(str);
        return stringBuilder.toString();
    }

    /**
     * json with a title
     *
     */
    public static void jsonTitle(String str, String title) {
        json(TAG,str, title);
    }
    /**
     * json with a title
     *
     */
    public static void json(String tag,String str, String title) {
        if (isDebug) {
            d(tag, "|===================================================================");

            if (!TextUtils.isEmpty(title)) {
                d(tag, "| " + title);
                d(tag, "|-------------------------------------------------------------------");
            }

            String message;
            try {
                if (str.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(str);
                    message = jsonObject.toString(JSON_INDENT);
                } else if (str.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(str);
                    message = jsonArray.toString(JSON_INDENT);
                } else {
                    message = str;
                }
            } catch (JSONException e) {
                message = str;
            }

            String[] lines = message.split("\n");
            for (String line : lines) {
                d(tag, line);
            }
            d(tag, "===================================================================|");
        }
    }


    /**
     * json
     */
    public static void json(String str) {
        json(TAG,str, null);
    }

    /**
     * json
     */
    public static void json(String tag,String str) {
        json(tag,str, null);
    }

    /**
     * entity with a title
     */
    public static  void entity(Object obj,Class<?> entityCls,Class<?extends Annotation>  annotationCls,String annotationkey) {
        entity(TAG,obj,entityCls,annotationCls,annotationkey,null);
    }

    /**
     * entity with a title
     */
    public static  void entity(String tag,Object obj,Class<?> entityCls,Class<?extends Annotation>  annotationCls,String annotationkey) {
        entity(tag,obj,entityCls,annotationCls,annotationkey,null);
    }

    /**
     * entity
     */
    public static  void entity(Object obj,Class<?> entityCls,Class<?extends Annotation>  annotationCls,String annotationkey,String title) {
        entity(TAG,obj,entityCls,annotationCls,annotationkey,title);
    }

    /**
     * entity
     */
    public static  void entity(String tag,Object obj,Class<?> entityCls,Class<?extends Annotation>  annotationCls,String annotationkey,String title){
            if (isDebug) {
                d(tag, "|===================================================================");

                if (!TextUtils.isEmpty(title)) {
                    d(tag, "| " + title);
                    d(tag, "|-------------------------------------------------------------------");
                }

                try {
                    Class entity = Class.forName(entityCls.getName());
                    Field[] fields = entity.getDeclaredFields();
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(annotationCls)) {
                            Annotation annotation = field.getAnnotation(annotationCls);
                            String strAnnotation = annotation.toString();
                            String strHashMap = strAnnotation.substring(strAnnotation.indexOf("(") + 1, strAnnotation.indexOf(")"));
                            String strValue = "";
                            Map map = ConvertUtils.strToHashMap(strHashMap, ", ", "=");
                            if (map != null) {
                                Iterator iter = map.entrySet().iterator();
                                while (iter.hasNext()) {
                                    Map.Entry entry = (Map.Entry) iter.next();
                                    Object key = entry.getKey();
                                    if (key.toString().equalsIgnoreCase(annotationkey)) {
                                        strValue = ConvertUtils.objToString(entry.getValue());
                                        break;
                                    }
                                }
                            }
                            d(tag, " %s:%s\n", strValue, ConvertUtils.objToString(field.get(obj)));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                d(tag, "===================================================================|");
            }
    }

}
