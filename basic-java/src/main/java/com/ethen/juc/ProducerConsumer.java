package com.ethen.juc;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * wait/notifyAll实现生产者-消费者
 */
public class ProducerConsumer {


    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        ExecutorService service = Executors.newFixedThreadPool(15);
        //5个线程生产
        for (int i = 0; i < 5; i++) {
            service.submit(new Producer(list, 8));
        }
        //10个线程消费
        for (int j = 0; j < 10; j++) {
            service.submit(new Consumer(list));
        }
    }


    /**
     * 生产者线程
     */
    static class Producer implements Runnable {
        private List<Integer> list;
        private int maxLength;

        public Producer(List<Integer> list, int maxLength) {
            this.list = list;
            this.maxLength = maxLength;
        }

        @Override
        public void run() {
            synchronized (list) {
                try {
                    while (list.size() == maxLength) {//fixme 我认为这一步意在控制生产数据的速率，当消费者消费能力不够，吞吐率较低时，控制生产的速率
                        System.err.println("生产者" + Thread.currentThread().getName() + " list达到最大容量，进行wait");
                        list.wait();
                        System.err.println("生产者" + Thread.currentThread().getName() + " 退出wait");
                    }
                    Random random = new Random();
                    int i = random.nextInt();
                    System.err.println("生产者" + Thread.currentThread().getName() + " 生产数据" + i);
                    list.add(i);
                    list.notifyAll();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 消费者线程
     */
    static class Consumer implements Runnable {
        private List<Integer> lock;

        public Consumer(List<Integer> lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            while (true) {//fixme 外层循环 => 保证消费者持续消费
                synchronized (lock) {
                    try {
                        while (lock.isEmpty()) {//fixme 内层循环 => 保证wait线程被唤醒了之后重新判断消费条件是否满足
                            System.err.println("消费者：" + Thread.currentThread().getName() + " list为空，进行wait");
                            lock.wait();
                            System.err.println("消费者：" + Thread.currentThread().getName() + " 线程被唤醒，退出wait");
                        }
                        Integer element = lock.remove(0);
                        System.err.println("消费者：" + Thread.currentThread().getName() + " 消费数据：" + element);
                        lock.notifyAll();//fixme 消费一次之后，唤醒所有生产者开始做事
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
