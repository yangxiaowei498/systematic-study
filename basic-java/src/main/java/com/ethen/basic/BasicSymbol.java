package com.ethen.basic;

/**
 * Java常用位运算符整理
 * & -> 位与
 * | -> 位或
 * ^ -> 位异或
 * ~ -> 位非
 * << 按位左移 通常格式位：value >> num ,value按位左移num位
 * >> 按位右移
 * >>> 无符号按位右移
 */
public class BasicSymbol {
    //8的二进制表示为  1000
    private static final int m = 8;
    //11的二进制表示为 1011
    private static final int n = 11;
    private static final int p = 10;
    private static final int q = -10;

    static {
        System.err.println("二进制 m => " + Integer.toBinaryString(m));
        System.err.println("二进制 n => " + Integer.toBinaryString(n));

    }

    /**
     * 异或运算规则：两个数转为二进制，然后从高位开始比较，如果相同则为0，不同则为1.
     */
    public void xor() {
        System.err.println("xor:" + Integer.toBinaryString(m ^ n));
    }


    /**
     * 位或算规则：两个数转为二进制，然后从高位开始比较，两个数只要有一个为1就得1，否则为0.
     */
    public void or() {
        System.err.println("or:" + Integer.toBinaryString(m | n));
    }


    /**
     * 位与运算规则：两个数转为二进制，然后从高位开始比较，如果两个数都为1则得1，否则得0.
     */
    public void and() {
        System.err.println("and:" + Integer.toBinaryString(m & n));
    }


    /**
     * 位非运算规则：两个数转为二进制，然后从高位开始比较，两个数只要有一个为1就得1，否则为0.
     */
    public void not() {
        System.err.println("not:" + Integer.toBinaryString(~n));
    }

    /**
     * 按位左移
     */
    public void moveLeft() {
        System.err.println("moveLeft: " + (p << 1));
    }

    /**
     * 按位右移
     */
    public void moveRight() {
        System.err.println("moveRight: " + (p >> 1));
    }

    /**
     * 无符号按位右移
     */
    public void moveRightWithoutSymbol() {
        int pl = q >>> 1;
        System.err.println("pl:"+ pl);
        System.err.println("moveRightWithoutSymbol: " + Integer.toBinaryString(pl));
    }


    public static void main(String[] args) {
        BasicSymbol bs = new BasicSymbol();
        bs.and();
        bs.or();
        bs.xor();
        bs.not();
        bs.moveLeft();
        bs.moveRight();
        bs.moveRightWithoutSymbol();
    }

}
