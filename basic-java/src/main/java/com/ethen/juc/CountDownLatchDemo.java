package com.ethen.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo {
    //模拟裁判信号枪
    private static CountDownLatch startSignal = new CountDownLatch(1);
    //表示裁判需要维护的6个运动员
    private static CountDownLatch endSignal = new CountDownLatch(6);


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        for (int i = 0; i < 6; i++) {
            executorService.execute(
                    () -> {
                        try {
                            System.err.println(Thread.currentThread().getName() + " 运动员正在跑到待命。。。");
                            startSignal.await();
                            System.err.println(Thread.currentThread().getName() + " 运动员正在全力冲刺！！！");
                            endSignal.countDown();
                            System.err.println(Thread.currentThread().getName() + " 到达终点啦啦啦啦啦~~~");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
        Thread.sleep(3000);//信号枪之前的宁静
        System.err.println(Thread.currentThread().getName() + " 裁判员发号施令啦^^^^^^^^^^^^^^^^^^^^^");
        startSignal.countDown();
        endSignal.await();//fixme 所有运动员到达才结束比赛和计时
        System.err.println(Thread.currentThread().getName() + " 所有运动员到达终点，比赛结束^^^^^^^^^^^");
        executorService.shutdown();
    }
}
