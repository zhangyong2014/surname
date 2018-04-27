package com.forward.surname;

import android.content.Context;

/**
 * Created by mings on 2018/4/27.
 */

public class Config {
    private  String mTag="surname";
    private  boolean mDebug = true;
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

    /**
     * 设置调试状态
     * @param debug
     * @return
     */
    public   Config  setDebug(boolean debug){
        mDebug=debug;
        return mInstance;
    }

    /**
     * 获取调试状态
     * @return
     */
    public   boolean  getDebug(){
        return  mDebug;
    }

    /**
     * 设置tag
     * @param tag
     * @return
     */
    public   Config  setTag(String tag){
        mTag=tag;
        return mInstance;
    }

    /**
     * 获取tag
     * @return
     */
    public  String getTag(){
        return  mTag;
    }
}
