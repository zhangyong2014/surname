package com.forward.surname.threadpool.builder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zy on 2017/11/19.
 */

public class ScheduledBuilder extends ThreadPoolBuilder<ExecutorService> {
    /** 固定线程池大小 */
    private int mSize = 1;
    @Override
    protected ExecutorService create() {
        return Executors.newScheduledThreadPool(mSize);
    }

    @Override
    protected ThreadPoolType getType() {
        return null;
    }

    public ScheduledBuilder size(int size) {
        this.mSize = size;
        return this;
    }
}
