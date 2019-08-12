package com.ethen.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicRefTest {

    public static void main(String[] args) throws InterruptedException {
        AtomicReference<Integer> refInteger = new AtomicReference<Integer>(1000);

        List<Thread> list = new LinkedList<Thread>();

        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(new Task(refInteger), "Thread-" + i);
            list.add(t);
            t.start();
        }

        for (Thread t : list) {
            t.join();
        }

        System.err.println(refInteger.get());
    }
}


class Task implements Runnable {
    private AtomicReference<Integer> ref;

    public Task(AtomicReference<Integer> ref) {
        this.ref = ref;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run() {
        for (; ; ) {  //自旋操作
            Integer oldValue = ref.get();
            if (ref.compareAndSet(oldValue, oldValue + 10))
                break;
        }

    }
}