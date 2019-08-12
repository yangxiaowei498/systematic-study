package com.ethen.steam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamOpsDemo {


    public static void main(String[] args) {
        String[] words = {"aa", "this", "Hello", "WORLd", "yeah"};
        Integer[] nums = {12, 23, 5, 9};
        List<String> wordList = Arrays.asList(words);
        List<Integer> numList = Arrays.asList(nums);
        //1.转换大小写
        List<String> output = wordList.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.err.println("uptput => " + output);

        //2.计算平方数
        List<Integer> output2 = numList.stream().map(n -> n * n).collect(Collectors.toList());
        System.err.println("uptput2 => " + output2);

        //3.数据过滤
        Integer[] output3 = numList.stream().filter(n -> n % 2 == 0).toArray(Integer[]::new);
        System.err.println("uptput3 => " + output3);

        byte bt = 0x12;

    }


}
