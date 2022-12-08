package com.wyg.view;

import com.wyg.controller.Commodity;
import com.wyg.model.Sql;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Menu implements Commodity {

    public static void main(String[] args) throws Exception{
        //检测默认设置
        settings();

        //开始主菜单
        menuMain();
    }

    private static void menuMain() throws Exception{
        while(true) {
            System.out.println("1.文件上传  2.文件查看  3.文件导出  4.设置");
            switch(SCANNER.next()) {
                case "1" :
                    System.out.println("请输入文件上传路径:");
                    Sql.fileAdd(SCANNER.next());
                    break;
                case "2" :
                    fileFind();
                    break;
                case "3" :
                    System.out.println("请输入需要导出的文件名");
                    String fileName = SCANNER.next();
                    Sql.readFileToLocal(fileName);
                    break;
                case "4" :
                    break;
                default :
                    System.out.println("输入有误请重新输入...");
            }
        }
    }

    //文件查看
    private static void fileFind() throws Exception{
        while(true) {
            System.out.println("1.日期  2.文件名  3.查看所有  4.返回");
            switch(SCANNER.next()) {
                case "1" :
                    System.out.println("请输入文件日期");
                    String date = SCANNER.next();
                    Sql.selectText(date,1);
                    break;
                case "2" :
                    System.out.println("请输入文件名");
                    String fileName = SCANNER.next();
                    Sql.selectText(fileName,2);
                    break;
                case "3" :
                    System.out.println("正在查询所有，请稍后...");
                    Sql.selectText("null",3);
                    break;
                case "4" :
                    //settings();
                    return;
                default :
                    System.out.println("输入错误请重新输入");
            }
        }

    }

    //配置文件设置
    @Test
    private static void defaultsSettings() throws Exception{
        String filePath = System.getProperty("user.dir") + "\\src";

        System.out.println("正在检测配置文件...");
        File file = new File(filePath,"Settings.properties");
        // 判断文件是否存在
        if(file.exists()) {
            System.out.println("文件已经存在");
        } else {
            try {
                Properties pro = new Properties();
                pro.put("url","jdbc:mysql://127.0.0.1:3306/sql2_wyg");
                pro.put("username","root");
                pro.put("password","geenkeyes");

                pro.put("savaPath",filePath);     //文件保存默认目录

                FileWriter fileWriter = new FileWriter(filePath + "\\Settings.properties");
                pro.store(fileWriter,"default setting");
                fileWriter.close();     // 关闭流

                System.out.println(file.getName() + "不存在，已完成初始化创建");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
