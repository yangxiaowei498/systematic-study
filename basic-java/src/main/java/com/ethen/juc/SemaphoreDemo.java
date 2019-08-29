package com.ethen.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 【信号量的使用案例】
 * note: 信号量常用来作为访问关键资源的流量控制。在限制资源使用的的应用场景下，semaphore很适合的。
 * <p>
 * 一下代码模拟一个场景，老师需要10个同学到讲台填表格，但同时只有5根粉笔，因此只能保证同时5个同学能够拿到粉笔并填写表格
 * 没有获得粉笔的同学只能等前面的同学用完之后，才能拿到笔去填写，
 */
public class SemaphoreDemo {
    //表示老师只要5根粉笔
    private static Semaphore semaphore = new Semaphore(5);

    public static void main(String[] args) {
        //模拟10个同学填表格
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(
                    () -> {
                        try {
                            System.err.println(Thread.currentThread().getName() + " 同学准备获取笔。。。");
                            semaphore.acquire();//获取许可(拿到笔)
                            System.err.println(Thread.currentThread().getName() + " 同学获取到笔!!!!!");
                            System.err.println(Thread.currentThread().getName() + " 同学正在填表格。。。");
                            TimeUnit.SECONDS.sleep(3);//填表格
                            semaphore.release();//释放许可(归还笔)
                            System.err.println(Thread.currentThread().getName() + " 同学填完表格，归还了笔$$$$");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
        executorService.shutdown();
    }


}
