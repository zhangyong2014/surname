package com.forward.surname;

import android.content.Context;

/**
 * Created by mings on 2018/4/27.
 */

public class Config {
    private  String mTag="surname";
    private  boolean mDebug = true;//是否在调试状态
    private  boolean mStackTrace=true;//是否输出stacktrace 信息
    private volatile static  Config mInstance=null;
    private  Config(){}
    /**单例**/
    public static Config getInstance() {
        if (mInstance == null) {
            synchronized (Config.class) {
                if (mInstance == null) {
                    mInstance = new Config();
                }
            }
        }
        return mInstance;
    }


    public   Config  setDebug(boolean debug){
        mDebug=debug;
        return mInstance;
    }

    public   boolean  getDebug(){
        return  mDebug;
    }


    public   Config  setTag(String tag){
        mTag=tag;
        return mInstance;
    }


    public  String getTag(){
        return  mTag;
    }

    public boolean getStackTrace() {
        return mStackTrace;
    }


    public void setStackTrace(boolean stackTrace) {
        mStackTrace = stackTrace;
    }
}
