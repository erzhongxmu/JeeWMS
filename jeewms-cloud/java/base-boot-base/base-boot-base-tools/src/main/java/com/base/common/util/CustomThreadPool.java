package com.base.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class CustomThreadPool extends ThreadPoolExecutor {

    private ConcurrentHashMap<String, Long> startTimes;

    private String poolName;

    public CustomThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                            TimeUnit unit,
                            BlockingQueue<Runnable> workQueue,
                            String poolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.poolName = poolName;
        this.startTimes = new ConcurrentHashMap<>();
    }

    public CustomThreadPool(int corePoolSize,
                            int maximumPoolSize,
                            long keepAliveTime,
                            TimeUnit unit,
                            BlockingQueue<Runnable> workQueue,
                            ThreadFactory threadFactory,
                            RejectedExecutionHandler handler,String poolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.poolName = poolName;
        this.startTimes = new ConcurrentHashMap<>();
    }

    public static ExecutorService newFixedThreadPool(int nThreads, String poolName) {
        return new CustomThreadPool(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), poolName);
    }


    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        log.info(String.valueOf(r.hashCode())+"添加任务");
        startTimes.put(String.valueOf(r.hashCode()), System.currentTimeMillis());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        long startDate = startTimes.remove(String.valueOf(r.hashCode()));
        long finishDate = System.currentTimeMillis();
        long diff = finishDate - startDate;
        // 统计任务耗时、初始线程数、核心线程数、正在执行的任务数量、
        // 已完成任务数量、任务总数、队列里缓存的任务数量、池中存在的最大线程数、
        // 最大允许的线程数、线程空闲时间、线程池是否关闭、线程池是否终止
        log.info("{}-pool-monitor: " +
                        "该任务耗时: {} ms, 初始线程数: {}, 核心线程数: {}, 正在执行任务数: {}, " +
                        "已完成任务数: {}, 任务总数: {}, 队列缓存数: {}, 池中存在的最大线程数: {}, " +
                        "最大允许的线程数: {},  线程空闲时间: {}, 线程池是否关闭: {}, 线程池是否终止: {}",
                this.poolName,
                diff, this.getPoolSize(), this.getCorePoolSize(), this.getActiveCount(),
                this.getCompletedTaskCount(), this.getTaskCount(), this.getQueue().size(), this.getLargestPoolSize(),
                this.getMaximumPoolSize(), this.getKeepAliveTime(TimeUnit.MILLISECONDS), this.isShutdown(), this.isTerminated());
    }

    /**
     * 关闭线程池，任务照样执行
     */
    @Override
    public void shutdown() {
        log.info("{} 正在停止. 已完成任务数: {}, 正在执行任务书: {}, 等待执行任务数: {}",
                this.poolName, this.getCompletedTaskCount(), this.getActiveCount(), this.getQueue().size());
        super.shutdown();
    }

    /**
     * 终止线程池，不再执行任务包括正在执行的
     * @return
     */
    @Override
    public List<Runnable> shutdownNow() {
        log.info("{} 立即停止. 已完成任务数: {}, 正在执行: {}, 等待执行: {}",
                this.poolName, this.getCompletedTaskCount(), this.getActiveCount(), this.getQueue().size());
        return super.shutdownNow();
    }
}
