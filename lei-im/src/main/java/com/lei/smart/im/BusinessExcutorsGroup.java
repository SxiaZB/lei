package com.lei.smart.im;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BusinessExcutorsGroup {
    public static final ExecutorService COLLECT_LOG_EXECUTOR =
            new ThreadPoolExecutor(2,4, 0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(2000),
            new ThreadFactoryBuilder().setNameFormat("BUSINESS").build(),new ThreadPoolExecutor.AbortPolicy());
}
