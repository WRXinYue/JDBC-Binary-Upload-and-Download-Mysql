package com.wyg.model;

import java.io.FileReader;
import java.io.Reader;
import java.sql.*;

import java.util.Properties;

public class JDBCUtil {
    private static final Properties ps = new Properties();

    /**
     * 建立连接方法
     */
    public static Connection getConnection() throws Exception {
        Connection conn;
        //从Settings.properties获取配置文件
        Properties properties = new Properties();
        Reader reader = new FileReader(System.getProperty("user.dir")+"\\src\\Settings.properties");
        properties.load(reader);
        String url = (String) properties.get("url");
        String userName = (String) properties.get("username");
        String passWorld = (String) properties.get("password");

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
