package com.forward.surname.manager;

import android.app.Activity;

import java.util.ArrayList;

/**
 * 管理当前的Acitivity栈顺序
 * Created by zy on 2017/6/15.
 */
public  class ActivityManager {
    ArrayList<Activity> mArrActivity = new ArrayList<Activity>();
    private volatile static ActivityManager mInstance;
    private ActivityManager() {

    }
    /**单例**/
    public static ActivityManager getInstance() {
        if (mInstance == null) {
            synchronized (ActivityManager.class) {
                if (mInstance == null) {
                    mInstance = new ActivityManager();
                }
            }
        }
        return mInstance;
    }
    /**
     * 查找指定的activity
     * @param activity
     * @return
     */
    public int findActivity(Activity activity) {
        for (int index = 0; index < mArrActivity.size(); ++index) {
            if (mArrActivity.get(index) == activity)
                return index;
        }
        return -1;
    }

    /**
     * 查找指定的activity
     * @param cls
     * @return
     */
    public int findActivity(Class<?> cls) {
        for (int index = 0; index < mArrActivity.size(); ++index) {
            if (cls.isInstance(mArrActivity.get(index)))
                return index;
        }
        return -1;
    }

    /**
     * 入栈
     * @param activity
     */
    public void pushActivity(Activity activity) {
        if (findActivity(activity) != -1)
            return;
        mArrActivity.add(activity);
    }

    /**
     * 出栈
     * @return
     */
    public Activity  popActivity() {
        if (mArrActivity.size() == 0)
            return null;
        return mArrActivity.remove(mArrActivity.size() - 1);
    }
    /**
     * 出栈
     * @param activity
     */
    public void popActivity(Activity activity) {
        mArrActivity.remove(activity);
    }


    /**
     * 移除指定Activity
     * @param activity
     */
    public void removeActivity(Activity activity) {
        mArrActivity.remove(activity);
    }

    /**
     * 退出程序
     */
    public void exitApplication(){
        Activity activity = popActivity();
        while (activity != null) {
            activity.finish();
            activity = popActivity();
        }
    }
}
