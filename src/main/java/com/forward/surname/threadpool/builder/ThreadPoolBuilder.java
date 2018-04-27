package com.forward.surname.threadpool.builder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * Created by zy on 2017/11/19.
 */

public abstract class ThreadPoolBuilder<T extends ExecutorService>{
    protected static Map<String, ExecutorService> mThreadPoolMap = new ConcurrentHashMap<String, ExecutorService>();
    protected ExecutorService mExecutorService = null;
    protected String mPoolName = "default";
    protected abstract T create();
    protected abstract ThreadPoolType getType();

    public ExecutorService builder(){
        if (mThreadPoolMap.get(getType() + "_" + mPoolName) != null) {
            mExecutorService = mThreadPoolMap.get(getType() + "_" + mPoolName);
        } else {
            mExecutorService = create();
            mThreadPoolMap.put(getType() + "_" + mPoolName, mExecutorService);
        }
        return mExecutorService;
    }

    public ThreadPoolBuilder<T> poolName(String poolName) {
        if (poolName != null && poolName.length() > 0) {
            mPoolName = poolName;
        }
        return this;
    }
}
