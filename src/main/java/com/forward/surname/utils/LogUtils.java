package com.forward.surname.utils;


import android.text.TextUtils;
import android.util.Log;

import com.forward.surname.Config;

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
    private static final int JSON_INDENT = 4;

    /**
     * 获取TAG
     */
    private static String getTag() {
        return Config.getInstance().getTag();
    }

    /**
     * 获取DEBUG
     */
    private static boolean getDebug() {
        return Config.getInstance().getDebug();
    }

    /**
     * 获取stacktrace
     */
    private static boolean getStackTrack() {
        return Config.getInstance().getStackTrace();
    }


    /**
     * debug
     */
    public static void d(String str, Object... objects) {
        if (!getDebug()) return;
        Log.d(getTag(), buildLogString(str, objects));
    }

    /**
     * info
     */
    public static void i(String str, Object... objects) {
        if (!getDebug()) return;
        Log.i(getTag(), buildLogString(str, objects));
    }

    /**
     * verbose
     */
    public static void v(String str, Object... objects) {
        if (!getDebug()) return;
        Log.v(getTag(), buildLogString(str, objects));
    }

    /**
     * error
     */
    public static void e(String str, Object... objects) {
        if (!getDebug()) return;
        Log.e(getTag(), buildLogString(str, objects));
    }

    /**
     * warning
     */
    public static void w(String str, Object... objects) {
        if (!getDebug()) return;
        Log.w(getTag(), buildLogString(str, objects));
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
        if (getStackTrack()) {
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
        return str;
    }

    /**
     * json with a title
     */
    public static void json(String str, String title) {
        if (!getDebug()) return;
        Log.d(getTag(), buildLogString("|==================================================================="));
        if (!TextUtils.isEmpty(title)) {
            Log.d(getTag(), buildLogString("| " + title));
            Log.d(getTag(), buildLogString("|-------------------------------------------------------------------"));
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
            Log.d(getTag(), buildLogString(line));
        }
        Log.d(getTag(), buildLogString("===================================================================|"));
    }


    /**
     * entity with a title
     */
    public static void entity(Object obj, Class<?> entityCls, Class<? extends Annotation> annotationCls, String annotationkey, String title) {
        if (!getDebug()) return;

        Log.d(getTag(), buildLogString("|==================================================================="));

        if (!TextUtils.isEmpty(title)) {
            Log.d(getTag(), buildLogString("| " + title));
            Log.d(getTag(), buildLogString("|-------------------------------------------------------------------"));
        }

        try {
            Class entity = Class.forName(entityCls.getName());
            Field[] fields = entity.getDeclaredFields();
            for (int i=fields.length-1;i>=0;i--) {
                Field field=fields[i];
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
                    Log.d(getTag(), buildLogString("    %s:%s\n", strValue, ConvertUtils.objToString(field.get(obj))));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(getTag(), buildLogString("===================================================================|"));
    }
}
