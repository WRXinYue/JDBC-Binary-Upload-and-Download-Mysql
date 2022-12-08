package com.wyg.view;

import com.wyg.controller.Commodity;
import com.wyg.controller.SettingUtil;
import com.wyg.model.Sql;

import java.io.*;

public class Menu implements Commodity {

    public static void main(String[] args){
        //检测默认设置
        SettingUtil.defaultsSettings();

        //开始主菜单
        menuMain();
    }

    private static void menuMain(){
        while(true) {
            System.out.println("1.文件上传  2.文件查看  3.文件导出  4.设置  5.退出");
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
                    settings();
                    break;
                case "5" :
                    System.out.println("退出成功");
                    return;
                default :
                    System.out.println("输入有误请重新输入...");
            }
        }
    }

    private static void settings() {
        while (true) {
            System.out.println("1.数据库管理   2.文件保存位置更改  3.返回");
            switch (SCANNER.next()) {
                case "1" :
                    //settingMysql();
                case "2" :
                    System.out.println("功能开发中...");
                    //settingFiles();
                    break;
                case "3" :
                    return;
                default :
                    System.out.println("输出有误请重新输入");
            }
        }
    }


/*    private static void settingMysql() {
    }

    private static void settingFiles() {
        System.out.println("当前文件路径：" + SettingUtil.getProperties().get("savaPath"));
        System.out.println("请输入修改后文件路径：");
        while (true) {
            String input = SCANNER.next();
            System.out.println("确认修改吗 Y/N");
            switch (SCANNER.next()) {
                case "y", "Y" -> {
                    try {
                        SettingUtil.getProperties().setProperty("savaPath" , input);
                        System.out.println("修改成功，当前路径为" + SettingUtil.getProperties().get("savaPath"));
                    } catch (Exception e) {
                        System.out.println("修改失败");
                        throw new RuntimeException(e);
                    }
                }
                case "n", "N" -> {
                    System.out.println("取消成功");
                    return;
                }
                default -> System.out.println("输入有误请重新输入：");
            }
        }
    }*/

    //文件查看
    private static void fileFind(){
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
                    return;
                default :
                    System.out.println("输入错误请重新输入");
            }
        }
    }
}
