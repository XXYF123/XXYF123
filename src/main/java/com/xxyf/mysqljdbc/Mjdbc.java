package com.xxyf.mysqljdbc;

import com.xxyf.erro.Jdbcinfo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @Author 小小怡飞 QQ:2324350936
 * @Date 2022/6/30 1:32
 * @Version JDK 8
 */
public interface Mjdbc extends Config {
    void setConnection(Connection connection);

    //初始化方法
    void initConnection();

    Connection getCnnection() throws Jdbcinfo, IOException;
    //查询
    ResultSet sqlquery(String sql, Object... i) throws SQLException;
    //编译sql
    PreparedStatement execut(String sql, Object... i) throws SQLException;

    ResultSet getResultSet();

    void setResultSet(ResultSet resultSet);

    PreparedStatement getPreparedStatement();

    void setPreparedStatement(PreparedStatement preparedStatement);

}
