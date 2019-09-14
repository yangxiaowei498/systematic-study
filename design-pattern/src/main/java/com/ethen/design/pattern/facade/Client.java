package com.ethen.design.pattern.facade;

/**
 * 客户 调用方 => 调用门面，屏蔽细节
 * <p>
 * 门面设计模式demo
 *
 * @see {http://www.cnblogs.com/lthIU/p/5860607.html}
 * @since 20190507
 * <p>
 * 每个Computer都有CPU、Memory、Disk。在Computer开启和关闭的时候，
 * 相应的部件也会开启和关闭，所以，使用了该外观模式后，会使用户和部件之间解耦。
 */
public class Client {

    public static void main(String[] args) {
        Computer computer = new Computer();
        computer.start();
        System.err.println("=>=>=>=>=>=>=>=>=>=>=>=>=>=>=>=>=>=>=>=>=>=>=>");
        computer.shutdown();
    }
}
