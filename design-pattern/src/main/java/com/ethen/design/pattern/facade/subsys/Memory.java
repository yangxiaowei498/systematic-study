package com.ethen.design.pattern.facade.subsys;

import java.util.Date;

public class Memory {
    public void start() {
        System.err.println("Memory is start..." + new Date());
    }

    public void shotdown() {
        System.err.println("Memory is shutdown..." + new Date());
    }

}
