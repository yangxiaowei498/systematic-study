package com.ethen.basic;

/**
 * 一些代码执行【加载】顺序
 * java中静态变量，静态代码块，静态方法，实例变量，匿名代码块等的加载顺序
 * 1.普通代码块:就是类中方法的方法体
 * public void xxx(){
 * // code
 * }
 * <p>
 * <p>
 * 2.构造块:用{}裹起来的代码片段，构造块在创建对象时会被调用，每次创建对象时都会被调用，并且优先于类构造函数执行。 构造块中定义的变量是局部变量。
 * {
 * //code
 * }
 * <p>
 * <p>
 * 3.静态块:用static{}裹起来的代码片段，只会被执行一次(第一次加载此类时执行,比如说用Class.forName("")加载类时就会执行static block)，静态块优先于构造块执行。
 * static{
 * //code
 * }
 * <p>
 * 4.同步代码块:使用synchronized(obj){}裹起来的代码块，在多线程环境下，对共享数据进行读写操作是需要互斥进行的，否则会导致数据的不一致性。
 * 常见的是synchronized用来修饰方法，其语义是任何线程进入synchronized需要先取得对象锁如果被占用了，则阻塞，实现了互斥访问共享资源。而
 * synchronized也是有代价的。一个常见的场景是,一个冗长的方法中，其实只有一小段代码需要访问共享资源，这时使用同步块，就只将这小段代码裹在
 * synchronized block，既能够实现同步访问，也能够减少同步引入的开销。 同步代码块须写在方法中。
 * <p>
 * synchronized(obj) {
 * //code
 * }
 */

public class ExecuteSequence {//1.第一步，准备加载类

    public static void main(String[] args) {
        new ExecuteSequence();             //4.第四步，new一个类，但在new之前要处理匿名代码块
    }

    static int num = 4;                    //2.第二步，静态变量和静态代码块的加载顺序由编写先后决定

    {
        num += 3;
        System.out.println("b");           //5.第五步，按照顺序加载匿名代码块，代码块中有打印
    }

    int a = 5;                             //6.第六步，按照顺序加载变量

    { // 成员变量第三个
        System.out.println("c");           //7.第七步，按照顺序打印c
    }

    ExecuteSequence() { // 类的构造方法，第四步加载
        System.out.println("d");           //8.第八步，最后加载构造函数，完成对象的建立
    }

    static {                              // 3.第三步，静态块，然后执行静态代码块，因为有输出，故打印a
        System.out.println("a");
    }

    static void run()                    // 静态方法，调用的时候才加载// 注意看，e没有加载
    {
        System.out.println("e");
    }
}
