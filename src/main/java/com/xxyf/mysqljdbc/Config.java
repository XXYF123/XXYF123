package com.xxyf.mysqljdbc;

import java.sql.SQLException;

/**
 * @Author 小小怡飞 QQ:2324350936
 * @Date 2022/6/30 1:32
 * @Version JDK 8
 */

public interface Config {

    //更新
    int sqlupdata(String sql, Object... i) throws SQLException;
    //关闭
    boolean colse() throws SQLException;
    //检查是否关闭
    boolean iscolse();

    boolean colsenotc();
    //关闭缓存
    void colseBuffer();
    void steAutoCommit(boolean t) throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

}
