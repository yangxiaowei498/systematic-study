package com.ethen.design.pattern.facade.subsys;

import java.util.Date;

public class CPU {

    public void start() {
        System.err.println("CPU is start..." + new Date());
    }

    public void shotdown() {
        System.err.println("CPU is shutdown..." + new Date());
    }

}
