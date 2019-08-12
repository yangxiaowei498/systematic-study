package com.ethen.steam;


import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * java8新特性Stream操作入门
 */
public class StreamTest01 {


    public static void main(String[] args) {
        //构造流的方式
        //1.直接
        Stream<String> stream = Stream.of("123", "a", "b", "c");
        //2.数组
        String[] strArray = new String[]{"123", "a", "b", "c"};
        Stream stream1 = Stream.of(strArray);
        //3.数组
        Stream stream2 = Arrays.stream(strArray);
        //4.集合
        List<String> list = Arrays.asList(strArray);
        Stream stream3 = list.stream();

        //5.数值流
        IntStream.of(12,23,34).forEach(System.out::println);
        //6.其他
        String[] toArray = stream.toArray(String[]::new);













    }
}
