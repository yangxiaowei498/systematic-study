package com.ethen.juc;

import java.util.LinkedList;
import java.util.List;

/**
 * => wait/notify消息机制潜在问题 => 等待wait的条件发生变化
 * 如果线程在等待时接受到了通知，但是之后等待的条件发生了变化，并没有再次对等待条件进行判断，也会导致程序出现错误。
 * <p>
 * note => 总结：在使用线程的等待/通知机制时，一般都要在 while 循环中调用 wait()方法，因此xuy配合使用一个 boolean 变量（或其他能判断真假的条件，
 * 如本文中的 list.isEmpty()），满足 while 循环的条件时，进入 while 循环，执行 wait()方法，不满足 while 循环的条件时，跳出循环，执行后面的代码。
 */
public class ConditionChange {
    private static List<String> lockObject = new LinkedList<>();

    public static void main(String[] args) {
        Consumer consumer1 = new Consumer(lockObject);
        Consumer consumer2 = new Consumer(lockObject);

        Producer producer = new Producer(lockObject);

        consumer1.start();
        consumer2.start();
        producer.start();

    }


    /**
     * 消费者线程 wait线程
     */
    static class Consumer extends Thread {
        private List<String> lock;

        public Consumer(List<String> lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                   /* if*/ //*fixme 所以这里将 if->while 线程恢复之后重新判断了一次
                    while (lock.isEmpty()) {
                        System.err.println(Thread.currentThread().getName() + " list为空");
                        System.err.println(Thread.currentThread().getName() + " 调用wait方法");
                        lock.wait();//* fixme 调用notifyAll之后，线程从这里的下一行恢复执行
                        System.err.println(Thread.currentThread().getName() + " wait方法结束");
                    }
                    String element = lock.remove(0);
                    System.err.println(Thread.currentThread().getName() + " 取出第一个元素为：" + element);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 生产者线程 notifyAll线程
     */
    static class Producer extends Thread {
        private List<String> lock;

        public Producer(List<String> lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.err.println(Thread.currentThread().getName() + " 开始添加元素");
                lock.add(Thread.currentThread().getName());
                lock.notifyAll();//唤醒所有在该对象锁上的wait线程，是他们有机会获取对象监视器锁
            }
        }
    }


}
