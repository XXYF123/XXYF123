package com.xxyf.mysqlzr;

import com.xxyf.mysqljdbc.Config;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
/**
 * @Author 小小怡飞 QQ:2324350936
 * @Date 2022/6/30 1:32
 * @Version JDK 8
 */
public interface Mzr extends Config {

    String[] indivarr(String sql, Object... param);

    List<String[]> indivarrall(String sql, Object... param);
    String indiv(String sql, Object... param);

    String[] indivall(String sql, Object... param);

    String indiv(String pojo, String sql, Object... param);

    String[] indivall(String pojo, String sql, Object... param);

    String[] indivarr(String[] pojo, String sql, Object... param);

    List<String[]> indivarrall(String[] pojo, String sql, Object... param);
    static <T> Object[][] getFile(Class<T> clz) {
        //获取类反射

        //返回属性
        Field[] fields = clz.getDeclaredFields();
        //定义属性与名字
        String type = "";
        String name = "";
        //定义二维数组存储方法与属性
        Object[][] typea_name = new Object[4][fields.length];
        int i1 = 0;

        for (Field field : fields) {
            //获取类型
            Class<?> type1 = field.getType();
            //获取属性类型名称
            type = type1.toString();
            if(type1==Integer.class){
                type="int";
            }else if(type1==Double.class){
                type="double";
            }else if(type1==Long.class){
                type="long";
            }else if(type1==Byte.class){
                type="byte";
            }else if(type1==Character.class){
                type="char";
            }else if(type1==Short.class){
                type="short";
            }else if(type1==Boolean.class){
                type="boolean";
            }else if(type1==Float.class){
                type="float";
            }

            name = field.getName();
            //获取名字首位大写
            char[] cs = name.toCharArray();
            if (cs[0] >= 97) {
                cs[0] -= 32;
            }
            name = String.valueOf(cs);
            //处理类型名只保留.后面的
            int i = type.lastIndexOf(".");

            if (i != -1) {
                type = type.substring(i + 1);
            }
            //首字母大写类型名
            char[] c = type.toCharArray();
            if (c[0] >= 97) {
                c[0] -= 32;
            }
            type = String.valueOf(c);
            typea_name[0][i1] = type;
            typea_name[1][i1] = name;
            //储存类型类
            typea_name[2][i1] = type1;
            //获取属性对应的set方法
            Method method = null;
            try {
                method = clz.getMethod("set" + (String) typea_name[1][i1], (Class<?>) typea_name[2][i1]);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            typea_name[3][i1] = method;
            i1++;
        }


        return typea_name;
    }
    <T> T into(Class<T> pojo,String sql , Object... param);
    <T> T into(Class<T> pojo, String sql, Map<String,String> maps, Object... param);
    <T> List<T> listinto(Class<T> t, String sql , Object... param);
    <T> List<T> listinto(Class<T> pojo, String sql,Map<String,String> maps ,Object... param);
    <T> T into(Class<T> pojo,ResultSet resultSet);
    <T> T into(Class<T> pojo, ResultSet resultSet, Map<String,String> maps);

}
