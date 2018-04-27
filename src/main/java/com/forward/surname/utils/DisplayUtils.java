/**
 * Copyright 2014 KJFrameForAndroid Open Source Project,张涛,Zhenguo Jin (jinzhenguo1990@gmail.com)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.forward.surname.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.forward.surname.R;

import java.lang.reflect.Field;

/**
 * 系统显示相关工具类
 *
 * @author jingle1267@163.com
 */
public final class DisplayUtils {

    /**
     * Don't let anyone instantiate this class.
     */
    private DisplayUtils() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue 尺寸dip
     * @return 像素值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文
     * @param pxValue 尺寸像素
     * @return DIP值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     *
     * @param context 上下文
     * @param pxValue 尺寸像素
     * @return SP值
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px
     *
     * @param context 上下文
     * @param spValue SP值
     * @return 像素值
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取dialog宽度
     *
     * @param activity Activity
     * @return Dialog宽度
     */
    public static int getDialogW(Activity activity) {
        DisplayMetrics dm;
        dm = activity.getResources().getDisplayMetrics();
        int w = dm.widthPixels - 100;
        return w;
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity Activity
     * @return 屏幕宽度
     */
    public static int getScreenW(Activity activity) {
        DisplayMetrics dm ;
        dm = activity.getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        return w;
    }

    /**
     * 获取屏幕高度
     *
     * @param activity Activity
     * @return 屏幕高度
     */
    public static int getScreenH(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = activity.getResources().getDisplayMetrics();
        int h = dm.heightPixels;
        return h;
    }

    /**
     * Toggle keyboard If the keyboard is visible,then hidden it,if it's
     * invisible,then show it
     *
     * @param context 上下文
     */
    public static void toggleKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 显示输入法
     * @param context
     * @param view
     */
    public static void showKeyboard(Context context, View view) {
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideKeyboard(Context context) {

        try {
            Activity activity = (Activity) context;
            final View v = activity.getWindow().getDecorView();
            if (v != null && v.getWindowToken() != null) {
                InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
    }

    /**
     * 隐藏输入法
     * @param context
     * @param view
     */
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    /**
     * 获取状态栏高度
     * 注: 该方法在onCreate中获取值为0
     *
     * @param activity
     * @return
     */
    public static int statusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    /**
     * 获取状态栏高度
     * 注: 该方法在onCreate中获取值为0
     *
     * @param resources
     * @return
     */
    public static int statusBarHeight(Resources resources) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.Rdimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = resources.getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取标题栏高度
     *
     * @param activity
     * @return
     */
    public static int titleBarHeight(Activity activity) {
        int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight = contentTop - statusBarHeight(activity);
        return titleBarHeight;
    }

    /**
     * 是否为平板
     *
     * @param context
     * @return
     */
    public static boolean tablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * ****************************
     * 各大设备密度判断
     * ****************************
     */
    public static boolean ldpi(Context context) {
        return context.getResources().getBoolean(R.bool.less_ldpi);
    }

    public static boolean mdpi(Context context) {
        return context.getResources().getBoolean(R.bool.less_mdpi);
    }

    public static boolean tvdpi(Context context) {
        return context.getResources().getBoolean(R.bool.less_tvdpi);
    }

    public static boolean hdpi(Context context) {
        return context.getResources().getBoolean(R.bool.less_hdpi);
    }

    public static boolean xhdpi(Context context) {
        return context.getResources().getBoolean(R.bool.less_xhdpi);
    }

    public static boolean xxhdpi(Context context) {
        return context.getResources().getBoolean(R.bool.less_xxhdpi);
    }

    public static boolean xxxhdpi(Context context) {
        return context.getResources().getBoolean(R.bool.less_xxxhdpi);
    }

}
