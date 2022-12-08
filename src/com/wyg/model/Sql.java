package com.wyg.model;


import com.wyg.controller.Commodity;
import com.wyg.controller.JDBCUtil;

import java.io.*;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Sql implements Commodity{
    /**
     * 文件上传方法
     * @param filePath
     */
    public static void fileAdd(String filePath){
        String sql="INSERT INTO file(file_name,file_date,file_path,file_data) VALUES (?,?,?,?)";
        PreparedStatement ps = null;
        int result;
        //用文件流输入字段名
        try {
            ps = JDBCUtil.getConnection().prepareStatement(sql);
            File file = new File(filePath);
            //用FileInputStream来存文件
            InputStream in = new FileInputStream(file);

            ps.setString(1,file.getName());
            ps.setString(2,sdf.format(file.lastModified()));
            ps.setString(3,file.getAbsolutePath());
            ps.setBinaryStream(4,in,(int)file.length());
            result = ps.executeUpdate();
            in.close();

            if (result>0){
                System.out.println("文件写入成功!");
            } else {
                System.out.println("文件写入失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //释放资源
            try {
                if (ps != null){
                    ps.close();
                }
                if(JDBCUtil.getConnection() != null){
                    JDBCUtil.getConnection().close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 文件下载方法
     * @param fileName
     */
    public static void readFileToLocal(String fileName){
        //设置搜索结果数量
        int count = 0;
        System.out.println("正在查询文件...");
        String sql = "SELECT * FROM file WHERE file_name = ?";

        //从Settings.properties获取配置文件
        Properties properties = new Properties();
        Reader reader;
        try {
            reader = new FileReader(System.getProperty("user.dir")+"\\src\\Settings.properties");
            properties.load(reader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String path = (String) properties.get("savaPath");

        System.out.println(path);
        File file = new File(path,fileName);                                       //保存位置的路径

        //创建预编译对象
        PreparedStatement ps = null;
        FileOutputStream outStream = null;
        try {
            ps = JDBCUtil.getConnection().prepareStatement(sql);
            ps.setString(1,fileName);   //设置Mysql执行语句
            ResultSet result = ps.executeQuery();   //执行sql语句

            if (result.next()) {
                System.out.println("查询成功,正在进行文件编码");
                count++;    //计数自增
                //使用Blob对象接收数据库里的图片
                Blob blob = result.getBlob("file_data");
                //获取Blob对象的二进制流
                InputStream in = blob.getBinaryStream();
                //文件输出流
                outStream = new FileOutputStream(file);    //文件输出流将数据写入文件

                //写出文件夹
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (JDBCUtil.getConnection() != null) {
                    JDBCUtil.getConnection().close();
                }
                if (outStream != null) {
                    outStream.close();
                }
                if (count != 0) {
                    System.out.println(fileName + "文件编码成功");
                    System.out.println("成功构建" + count + "个文件");
                } else {
                    System.out.println("未查询到" + fileName);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 查询方法
     * @param str
     * @param input
     */
    public static void selectText(String str,int input) {
        /**
         * 定义SQL查询语句
         * 1日期 2文件名 3全部查询
         */
        String sql = null;
        if(input == 1) {
            sql = "SELECT * FROM file WHERE file_date = = '" + str + "'";
        }
        else if (input == 2) {
            sql = "SELECT * FROM file WHERE file_name LIKE '%" +  str + "%'";
        }
        else if (input == 3) {
            sql = "SELECT * FROM file";
        }

        PreparedStatement pstmt;
        List<Brand> brands;
        ResultSet rs;
        try {
            //获取pstmt对象
            pstmt = JDBCUtil.getConnection().prepareStatement(sql);
            //执行SQL
            rs = pstmt.executeQuery();
            //处理结果 List<Brand>
            Brand brand;
            brands = new ArrayList<>();
            while (rs.next()){
                //id, file_name, file_date, file_path, file_data
                int fileId = rs.getInt("file_id");
                String fileName = rs.getString("file_name");
                String fileDate = rs.getString("file_date");
                String filePath = rs.getString("file_path");
                //封装Brand对象
                brand = new Brand();
                brand.setFileId(fileId);
                brand.setFileName(fileName);
                brand.setFileDate(fileDate);
                brand.setFilePath(filePath);
                //装载集合
                brands.add(brand);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //增强for输出
        for (Brand brand1 : brands) {
            System.out.print("文件ID：" + brand1.getFileId() + "\t");
            System.out.print("文件名：" + brand1.getFileName() + "\t\t");
            System.out.print("文件日期：" + brand1.getFileDate() + "\t\t");
            System.out.print("文件路径：" +brand1.getFilePath() + "\t\t");
            System.out.println();
        }

        //资源释放
        try {
            JDBCUtil.close(JDBCUtil.getConnection(),pstmt,rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("查询结束");
    }
}
