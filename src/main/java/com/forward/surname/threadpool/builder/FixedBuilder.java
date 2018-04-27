package com.forward.surname.threadpool.builder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zy on 2017/11/19.
 */

public class FixedBuilder extends ThreadPoolBuilder<ExecutorService> {
    /** 固定线程池  */
    private int mSize = 1;
    @Override
    protected ExecutorService create() {
        return Executors.newFixedThreadPool(mSize);
    }

    @Override
    protected ThreadPoolType getType() {
        return ThreadPoolType.FIXED;
    }

    public FixedBuilder setSize(int size) {
        mSize = size;
        return this;
    }
}
