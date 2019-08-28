package com.ethen.juc;

/**
 * => wait/notify消息机制潜在问题 => notify早期通知问题 改进！！！
 *
 * @see {https://www.javazhiyin.com/18117.html}
 * @see EarlyNotify
 * <p>
 * 这段代码只是增加了一个isWait状态变量，NotifyThread调用notify方法后会对状态变量进行更新，在WaitThread中调用wait方法之前会先对
 * 状态变量进行判断，在该示例中，调用notify后将状态变量isWait改变为false，因此，在WaitThread中while对isWait判断后就不会执行wait方法，
 * 从而避免了Notify过早通知造成遗漏的情况
 */
public class EnhanceEarlyNotify {
    private static String lockObject = "";
    private static boolean isWait = true;

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
                while (isWait) {
                    try {
                        System.err.println(Thread.currentThread().getName() + "进去代码块");
                        System.err.println(Thread.currentThread().getName() + "开始wait");
                        lock.wait();
                        System.err.println(Thread.currentThread().getName() + "结束wait");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.err.println("直接获得对象监视器锁~~~");
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
                isWait = false;
                System.err.println(Thread.currentThread().getName() + "结束notify");
            }
        }
    }
}

