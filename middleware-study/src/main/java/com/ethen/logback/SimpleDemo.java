package com.ethen.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * logback日志体系学习（一）
 * https://www.cnblogs.com/duhuo/p/7376786.html
 * <p>
 * Note: 日志打印级别 =>
 * logger 有日志打印级别，可以为一个 logger 指定它的日志打印级别。
 * 如果不为一个 logger 指定打印级别，那么它将继承离他最近的一个有指定打印级别的祖先的打印级别。这里有一个容易混淆想不清楚的地方，如果 logger 先找它的父亲，
 * 而它的父亲没有指定打印级别，那么它会立即忽略它的父亲，往上继续寻找它爷爷，直到它找到 root logger。因此，也能看出来，要使用 logback， 必须为 root logger
 * 指定日志打印级别。
 * <p>
 * 日志打印级别从低级到高级排序的顺序是：
 * TRACE < DEBUG < INFO < WARN < ERROR
 * 如果一个 logger 允许打印一条具有某个日志级别的信息，那么它也必须允许打印具有比这个日志级别更高级别的信息，而不允许打印具有比这个日志级别更低级别的信息。
 */
public class SimpleDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDemo.class);

    public static void main(String[] args) {
        LOGGER.info("this is a line of log message by logback");

        //*打印logback内部状态
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);


        //*日志打印级别
        //强转是为了能设置日志级别
        ch.qos.logback.classic.Logger fooLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.foo");
        fooLogger.setLevel(Level.INFO);

        fooLogger.warn("can be print cause WARN > INFO");
        fooLogger.debug("can not be print cause DEBUG < INFO");
        //barLogger从上层节点继承日志级别
        Logger barLogger = LoggerFactory.getLogger("com.foo.bar");
        barLogger.warn("can be print cause WARN > INFO2");
        barLogger.debug("can not be print cause DEBUG < INFO2");

    }
}
