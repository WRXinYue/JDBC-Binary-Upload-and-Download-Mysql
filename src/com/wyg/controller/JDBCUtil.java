package com.wyg.controller;

import java.sql.*;

import java.util.Properties;

public class JDBCUtil {
    private static final Properties ps = new Properties();

    /**
     * 建立连接方法
     */
    public static Connection getConnection() throws Exception {
        Connection conn;

        String url = (String) SettingUtil.getProperties().get("url");
        String userName = (String) SettingUtil.getProperties().get("username");
        String passWorld = (String) SettingUtil.getProperties().get("password");

        //加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //获取连接
        conn = DriverManager.getConnection(url,userName,passWorld);
        return conn;
    }

    /**
     * 关闭连接
     * @param conn
     * @param pre
     * @param rs
     */
    public static void close(Connection conn, PreparedStatement pre, ResultSet rs) {
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(pre != null){
            try {
                pre.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 关闭连接方法
     * @param conn
     * @param pre
     */
    public static void close(Connection conn, PreparedStatement pre) {
        if (pre != null) {
            try {
                pre.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
