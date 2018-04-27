package com.forward.surname.threadpool.builder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zy on 2017/11/19.
 */

public class SingleBuilder extends ThreadPoolBuilder<ExecutorService> {
    @Override
    protected ExecutorService create() {
        return Executors.newSingleThreadExecutor();

    }

    @Override
    protected ThreadPoolType getType() {
        return ThreadPoolType.SINGLE;
    }
}
