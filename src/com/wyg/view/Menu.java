package com.wyg.view;

import com.wyg.controller.Commodity;
import com.wyg.model.Settings;
import com.wyg.model.Sql;

public class Menu implements Commodity {

    public static void main(String[] args) throws Exception{
        //检测默认设置
        Settings.defaultsSettings();

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
                case "5" :
                    System.out.println("退出成功");
                    return;
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
}
