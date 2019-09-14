package com.ethen.design.pattern.facade.subsys;

import java.util.Date;

public class Disk {
    public void start() {
        System.err.println("Disk is start..." + new Date());
    }

    public void shotdown() {
        System.err.println("Disk is shutdown..." + new Date());
    }
}
