package com.ethen.juc;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 采用lock中Conditon的消息通知原理来实现生产者-消费者问题，原理同使用wait/notifyAll一样
 */
public class LockProducerConsumer {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition full = lock.newCondition();
    private static Condition empty = lock.newCondition();


    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        int len = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(15);
        //五个生产者线程
        for (int i = 0; i < 5; i++) {
            executorService.submit(new Producer(list, len, lock));
        }
        for (int j = 0; j < 10; j++) {
            executorService.submit(new Consumer(list, lock));
        }
    }


    /**
     * 生产者线程
     */
    static class Producer implements Runnable {
        private List<Integer> list;
        private int maxLength;
        private Lock lock;

        public Producer(List<Integer> list, int maxLength, Lock lock) {
            this.list = list;
            this.maxLength = maxLength;
            this.lock = lock;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (list.size() == maxLength) {
                        System.err.println("生产者" + Thread.currentThread().getName() + " list达到最大容量，进行wait");
                        full.await();
                        System.err.println("生产者" + Thread.currentThread().getName() + " list低于最大容量，退出wait");
                    }
                    Random random = new Random();
                    int num = random.nextInt();
                    System.err.println("生产者" + Thread.currentThread().getName() + " 生产新的元素 " + num);
                    list.add(num);
                    empty.signalAll();//fixme
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }


    static class Consumer implements Runnable {
        private List<Integer> list;
        private Lock lock;

        public Consumer(List<Integer> list, Lock lock) {
            this.list = list;
            this.lock = lock;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    //判空
                    while (list == null || list.isEmpty()) {
                        System.err.println("消费者" + Thread.currentThread().getName() + "  list为空，进行wait");
                        empty.await();
                        System.err.println("消费者" + Thread.currentThread().getName() + "  list不为空，退出wait");
                    }
                    int element = list.remove(0);
                    System.err.println("消费者" + Thread.currentThread().getName() + "  消费元素 " + element);
                    full.signalAll();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
