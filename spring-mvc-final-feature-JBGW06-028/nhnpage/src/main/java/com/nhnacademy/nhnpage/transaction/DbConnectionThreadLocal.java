package com.nhnacademy.nhnpage.transaction;

import com.nhnacademy.nhnpage.util.DBUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class DbConnectionThreadLocal {
    private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> sqlErrorThreadLocal = ThreadLocal.withInitial(()->false);

    public static void initialize(){
        try{
            Connection connection = DBUtils.getDataSource().getConnection();
            connectionThreadLocal.set(connection);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            sqlErrorThreadLocal.set(false);
        }catch (SQLException e){
            throw new RuntimeException("Error initializing DBConnectionThreadLocal", e);
        }
    }
    public static Connection getConnection(){
        return connectionThreadLocal.get();
    }
    public static void setSqlError(boolean sqlError){
        sqlErrorThreadLocal.set(sqlError);
    }
    public static boolean getSqlError(){
        return sqlErrorThreadLocal.get();
    }
    public static void reset(){
        Connection connection = connectionThreadLocal.get();
        if(connection != null){

            try{
                if (getSqlError()){
                    connection.rollback();
                    log.error("SQL Error occuered. Rolling back transaction.");
                }else{
                    connection.commit();
                    log.debug("Transaction committed.");
                }
            }catch (SQLException e){
                log.error("Error closing connection.1",e);
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Error closing connection.2", e);
                }
            }

            connectionThreadLocal.remove();
            sqlErrorThreadLocal.remove();
        }
    }
}
