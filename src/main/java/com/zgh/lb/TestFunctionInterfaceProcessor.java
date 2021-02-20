package com.zgh.lb;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <Description> <br>
 *
 * @author 朱光和
 * @version 1.0<br>
 * @createDate 2021/02/18 14:32 <br>
 */
public class TestFunctionInterfaceProcessor {

    public static void main(String[] args) {

        FunctionInterfaceProcessor  fp  = message -> System.out.println("Hello " + message);

        fp.process("abc");

        ArrayList<String> list = new ArrayList<>(Arrays.asList("I", "love", "you", "too", "ab","efdasd"));
//
//        list.forEach( i -> {
//            if(i.length() > 1)
//            {
//                System.out.println(i);
//            }
//        });
//
//
//        list.removeIf(str-> str.length() > 3);
//        System.out.println(list.size());


        list.sort((str1, str2) -> str1.length() - str2.length());

        list.forEach( i -> {
                System.out.println(i);
        });


    }


}
