package com.ethen.proxy;

import com.ethen.proxy.impl.AccountToolsImpl;
import org.junit.Test;

/**
 * 代理模式测试类
 */
public class TestProxy {

    @Test
    public void test01() {
        AccountTools accountTools = new AccountTools.AccountToolsProxy(new AccountToolsImpl());
        accountTools.queryAccount("hiyoyo123456");
        accountTools.updateAccount("hiyoyo", "ethen");
    }
}
