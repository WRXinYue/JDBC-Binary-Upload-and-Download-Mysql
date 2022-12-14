package com.wyg.controller;

import org.junit.Test;

import java.io.*;
import java.util.Properties;

public class SettingUtil {

    //从Settings.properties获取配置文件
    public static Properties getProperties() {
        Properties properties = new Properties();
        Reader reader = null;
        try {
            reader = new FileReader(System.getProperty("user.dir") + "\\src\\Settings.properties");
            properties.load(reader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    //配置文件设置
    @Test
    public static void defaultsSettings(){
        //配置文件位置,莫动
        String filePath = System.getProperty("user.dir") + "\\src";
        System.out.println("正在检测配置文件...");
        File file = new File(filePath,"Settings.properties");
        /**
         * 判断文件是否存在
         * 如果不存在就开始创建并进行Settings.properties文件初始化
         */
        if(file.exists()) {
            System.out.println("文件已经存在");
        } else {
            try {
                Properties pro = new Properties();
                pro.put("url","jdbc:mysql://127.0.0.1:3306/sql2_wyg");      //数据库本地连接
                pro.put("username","root");                                 //用户名
                pro.put("password","geenkeyes");                            //用户密码
                pro.put("savaPath",System.getProperty("user.dir"));         //导出文件默认保存位置

                FileWriter fileWriter = new FileWriter(filePath + "\\Settings.properties");
                pro.store(fileWriter,"default setting");
                fileWriter.close();                                         //关闭流

                System.out.println(file.getName() + "配置文件不存在，已完成初始化构建");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
