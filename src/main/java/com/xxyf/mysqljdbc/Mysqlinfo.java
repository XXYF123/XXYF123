package com.xxyf.mysqljdbc;

import com.xxyf.erro.Jdbcinfo;
import com.xxyf.tools.Strif;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * @Author 小小怡飞 QQ:2324350936
 * @Date 2022/6/30 1:32
 * @Version JDK 8
 */
//读取配置文件
public class Mysqlinfo {
    private static String url;
    private static String driver;
    private static Properties properties;
    private static String username;
    private static String password;
    protected static int size;

    public Mysqlinfo(){
        this("");
    }
    public Mysqlinfo(String driver){
        this(driver,null);

    }
    public Mysqlinfo(String driver,String url){
        this(driver,url,null,null);
    }
    public Mysqlinfo(String driver,String url,String username,String password,int size){
        this(driver, url, username, password);
        if(size>=0){
            Mysqlinfo.size=size;
        }
    }
    public Mysqlinfo(String driver,String url,String username,String password){
        properties = new Properties();
        if (!Strif.strnull(url) ){
            if(!Strif.strnull(Mysqlinfo.url)) {
                Mysqlinfo.url = "jdbc:mysql://localhost:3306/database?useSSL=false&useUnicode=true&characterEncoding=utf8";
            }
        } else {
                Mysqlinfo.url=url;
            }
        if (!Strif.strnull(driver)) {
            if(!Strif.strnull(Mysqlinfo.driver)) {
                Mysqlinfo.driver = "com.mysql.cj.jdbc.Driver";
            }
        }else {
            setDriver(driver);
        }
        if (!Strif.strnull(username)){
            if(!Strif.strnull(Mysqlinfo.username)){
            Mysqlinfo.username="root";
            }
        } else {
            setUsername(username);
        }
        if (!Strif.strnull(password)) {
            if (!Strif.strnull(Mysqlinfo.password)){
                Mysqlinfo.password = "root";
            }
        }else {
            setPassword(password);
        }
        if(size<=0){
            size=50000;
        }
        properties.put("username",Mysqlinfo.username);
        properties.put("password",Mysqlinfo.password);
    }
    public Mysqlinfo(Properties properties){
        driver=properties.getProperty("driver");
        url=properties.getProperty("url");
        setProperties(properties);
    }
    public Mysqlinfo(Class<?> t,String name)  {
        try (InputStream in = t.getClassLoader().getResourceAsStream(name)){
            initpr(in);
        }catch (IOException | Jdbcinfo e){
            e.printStackTrace();
        }
    }
    public Mysqlinfo(Class<?> t){
        this(t, "a.properties");
    }
    public void initpr(InputStream in) throws Jdbcinfo {
        if (in==null){
            File file = new File("a.properties");

            if (!file.exists()) {
                try {
                    file.createNewFile();
                    FileWriter myWriter = new FileWriter("a.properties");
                    myWriter.write("url="+url+"\ndriver="+driver+"\nusername="+username+"\npassword="+password);
                    myWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                throw new Jdbcinfo("根目录下存在该文件请填写,url,driver,username,password");
            }
            throw new Jdbcinfo("文件不存在，默认文件保存为类目录下的jdbc.properties，现已经自动生成");
        }
        properties = new Properties();
        try {
            properties.load(in);
        }catch (IOException e){
            e.printStackTrace();
        }
        url=properties.getProperty("url");
        driver=properties.getProperty("driver");
        username=properties.getProperty("username");
        password=properties.getProperty("password");
        if(Strif.strnull(properties.getProperty("size"))){
        size= Integer.parseInt(properties.getProperty("size"));
        if(size<=0){
            size=50000;
            }
        }
    }

    public static void setSize(int size) {
        Mysqlinfo.size = size;
    }

    public static int getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }
    public String getDriver() {
        return driver;
    }
    public Properties getProperties() {
        return properties;
    }

    public String getUsername() {
        return username;
    }

    public static void setUrl(String url) {
        Mysqlinfo.url = url;
    }

    public static void setDriver(String driver) {
        Mysqlinfo.driver = driver;
    }

    public static void setProperties(Properties properties) {
        Mysqlinfo.properties = properties;
    }

    public static void setUsername(String username) {
        Mysqlinfo.username = username;
    }

    public static void setPassword(String password) {
        Mysqlinfo.password = password;
    }

    public String getPassword() {
        return password;
    }
}
