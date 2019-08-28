package com.ethen.juc;

/**
 * => wait/notify消息机制潜在问题 => notify早期通知问题
 *
 * @see {https://www.javazhiyin.com/18117.html}
 * <p>
 * 示例中开启了两个线程，一个是WaitThread，另一个是NotifyThread。NotifyThread会先启动，先调用notify方法。然后WaitThread线程才启动，
 * 调用wait方法，但是由于通知过了，wait方法就无法再获取到相应的通知，因此WaitThread会一直在wait方法出阻塞，这种现象就是通知过早的现象。
 * 针对这种现象，解决方法，一般是添加一个状态标志，让waitThread调用wait方法前先判断状态是否已经改变了没，如果通知早已发出的话，WaitThread就不再去wait。
 */
public class EarlyNotify {
    private static String lockObject = "";

    public static void main(String[] args) {
        WaitThread waitThread = new WaitThread(lockObject);
        NotifyThread notifyThread = new NotifyThread(lockObject);
        notifyThread.start();
        try {
            System.err.println(Thread.currentThread().getName());
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitThread.start();
    }


    /**
     * wait线程 （静态内部类）
     */
    static class WaitThread extends Thread {
        private String lock;

        public WaitThread(String lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    System.err.println(Thread.currentThread().getName() + "进去代码块");
                    System.err.println(Thread.currentThread().getName() + "开始wait");
                    lock.wait();
                    System.err.println(Thread.currentThread().getName() + "结束wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * notify线程 （静态内部类）
     */
    static class NotifyThread extends Thread {
        private String lock;

        public NotifyThread(String lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.err.println(Thread.currentThread().getName() + "进去代码块");
                System.err.println(Thread.currentThread().getName() + "开始notify");
                lock.notify();
                System.err.println(Thread.currentThread().getName() + "结束notify");
            }
        }
    }
}

