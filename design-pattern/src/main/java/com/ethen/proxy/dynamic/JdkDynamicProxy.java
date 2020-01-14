package com.ethen.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 使用JDK动态代理
 * {@link} https://www.cnblogs.com/jqyp/archive/2010/08/20/1805041.html
 */
public class JdkDynamicProxy implements InvocationHandler {
    //被代理的对象
    private Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }

    /**
     * 绑定委托对象，并返回一个代理对象
     *
     * @param target
     * @return
     */
    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),// 需要绑定接口 -> CGlib无需绑定接口
                this);
    }

}
