package com.xxyf.tools;


import java.util.List;

/**
 * @Author 小小怡飞
 * @Date 2022/7/8 23:16
 * @Version JDK 8
 */
public class Strif {
    public static boolean strnull(String str){

        return str!=null && str.length()!=0;
    }
    public static <T> boolean arrnull(T[] T){
        return T!=null && T.length!=0;
    }
    public static <T> boolean listnull(List<T> list){
        return list!=null && list.size()!=0;
    }
}
