package com.xxyf.mysqljdbc;

import com.xxyf.mysqlzr.Mysqlzr;
import com.xxyf.mysqlzr.Mzr;
import com.xxyf.tools.Strif;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/**
 * @Author 小小怡飞 QQ:2324350936
 * @Date 2022/6/30 1:32
 * @Version JDK 8
 */
//数据库公共类
public class Mysqljdbc extends Mysqlinfo implements Mjdbc {
    //定义了 connection连接
    private  Connection connection = null;
    private ResultSet resultSet =null;
    private PreparedStatement preparedStatement =null;
    private Map<String,ResultSet> buffer;
    private boolean iscolse =false;
    private boolean isnotc =false;
    private int index;
    {

        buffer= new HashMap<>();
    }
    public Mysqljdbc(String driver){
        super(driver);
        initConnection();
    }
    public Mysqljdbc(String driver,String url){
        super(driver,url);
        initConnection();
    }
    public Mysqljdbc(String driver,String url,String username,String password){
        super(driver,url,username,password);
        initConnection();
    }
    public Mysqljdbc(){
        super();
        initConnection();
    }
    public Mysqljdbc(Properties properties){
        super(properties);
        initConnection();
    }


    //构造connection初始化
    public Mysqljdbc(Class<?> t,String name){
        super(t,name);
        initConnection();
    }
    public Mysqljdbc(Class<?> t){
        super(t);
        initConnection();
    }
    @Override
    public void setConnection(Connection connection){
        this.connection=connection;
    }
    //初始化方法
    @Override
    public void initConnection(){
        try {
            Class.forName(getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Properties properties = getProperties();
        String s = getUrl();
        try {
            connection = DriverManager.getConnection(s, properties);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Connection getCnnection()  {
        return this.connection;
    }
    private void setBuffer(String sql, ResultSet re, Object... i){

        if (index>size){
            colseBuffer();
        }
            String s = sql+ Arrays.toString(i);
            buffer.put(s,re);
            index++;


    }
    public void colseBuffer(){
        buffer=new HashMap<>();
        index=0;
    }


    //查询
    @Override
    public ResultSet sqlquery(String sql, Object... i)  {
        if(buffer.containsKey(sql+ Arrays.toString(i))){
            return buffer.get(sql+ Arrays.toString(i));
        }
        PreparedStatement pr= null;
        pr = execut(sql,i);
        ResultSet re = null;
        try {
            re = pr.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setBuffer(sql,re,i);
        setPreparedStatement(pr);
        setResultSet(re);
        return re;
    }
    //编译sql
    @Override
    public PreparedStatement execut(String sql, Object... i)  {
        PreparedStatement pr = null;
        try {
            pr = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(i!=null){
            for (int i1 =1;i1<=i.length;i1++){
                try {
                    pr.setObject(i1,i[i1-1]);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return pr;
    }
    //更新
    @Override
    public int sqlupdata(String sql, Object... i)  {
        PreparedStatement pr = execut(sql,i);

        int index = 0;
        try {
            index = pr.executeUpdate();
            colseBuffer();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        colseBuffer();
        setPreparedStatement(pr);
        return index;
    }

    @Override
    public ResultSet getResultSet() {
        return resultSet;
    }

    @Override
    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    @Override
    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }
    public boolean colsenotc(){
        if(resultSet!=null){
            try {
                resultSet.close();
                resultSet=null;
                this.isnotc=true;
            }catch (SQLException e){
                this.isnotc=false;
                e.printStackTrace();
            }
        };
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
                preparedStatement=null;
                this.isnotc=true;
            }catch (SQLException e){
                this.isnotc=false;
                e.printStackTrace();
            }
        };
        colseBuffer();
        return this.isnotc;
    }
    public String inre(ResultSet resultSet){
        String pojos = null;
        try {
            if (resultSet.next()){
                pojos=resultSet.getString(1);
                resultSet.previous();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pojos;
    }
    public String[] inrearr(ResultSet resultSet){
        String[] pojos=null;
        int index=0;
        try {
            index = resultSet.getMetaData().getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (resultSet.next()){


                pojos =new String[index];
                for (int i=1;i<=index ; i++) {
                    pojos[i-1] = resultSet.getString(i);
                }
                resultSet.previous();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pojos;
    }
    public String inre(String pojo,ResultSet resultSet){
        if(!Strif.strnull(pojo)){
            return inre(resultSet);
        }
        String pojos = null;
        try {
            if (resultSet.next()){
                try {
                    pojos = resultSet.getString(pojo);
                }catch (SQLException ignored){
                }

                resultSet.previous();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pojos;
    }
    public String[] inre(String[] pojo,ResultSet resultSet){
        String[] pojos;
        if(Strif.arrnull(pojo)){
            pojos = new String[pojo.length];
        }else {
            return inrearr(resultSet);
        }
        try {
            if (resultSet.next()){
                for (int i=0;i<pojos.length ; i++) {
                    try {
                        pojos[i] = resultSet.getString(pojo[i]);
                    }catch (SQLException e){
                        pojos[i]=null;
                    }

                }
                resultSet.previous();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pojos;
    }

    @Override
    public boolean colse() {

        if(resultSet!=null){
            try {
                resultSet.close();
                resultSet=null;
                this.iscolse=true;
            }catch (SQLException e){
                this.iscolse=false;
                e.printStackTrace();
            }
        }
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
                preparedStatement=null;
                this.iscolse=true;
            }catch (SQLException e){
                this.iscolse=false;
                e.printStackTrace();
            }
        }
        if(connection!=null){
            try {
                connection.close();
                connection=null;
                this.iscolse=true;
            }catch (SQLException e){
                this.iscolse=false;
                e.printStackTrace();
            }
        }
        colseBuffer();
        return iscolse;
    }
    @Override
    public boolean iscolse(){
        return iscolse;
    }
    @Override
    public void steAutoCommit(boolean t) throws SQLException {
        connection.setAutoCommit(t);
    }
    @Override
    public void commit() throws SQLException {
        connection.commit();
    }
    @Override
    public void rollback() throws SQLException {
        connection.rollback();
        steAutoCommit(true);
    }

}
