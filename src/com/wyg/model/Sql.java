package com.wyg.model;


import java.io.*;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sql{

    //文件上传
    public static void fileAdd(String filePath) throws Exception{
        String sql="INSERT INTO file(file_name,file_date,file_path,file_data) VALUES (?,?,?,?)";
        PreparedStatement ps = null;
        int result = 0;
        //用文件流输入字段名
        try {
            ps = JDBCUtil.getConnection().prepareStatement(sql);
            File file = new File(filePath);
            //用FileInputStream来存文件
            InputStream in = new FileInputStream(file);

            ps.setString(1,file.getName());
            ps.setString(2, String.valueOf(file.lastModified()));
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
            if (ps != null){
                ps.close();
            }
            if(JDBCUtil.getConnection()!=null){
                JDBCUtil.getConnection().close();
            }
        }
    }

    //文件导出
    public static void readFileToLocal(String fileName) throws Exception {
        System.out.println("正在编码导出...");
        String sql = "SELECT * FROM file WHERE file_name = ?";
        String path = System.getProperty("user.dir" + "\\" + fileName);   //保存路径
        //创建预编译的statement对象
        PreparedStatement ps = null;
        try {
            ps = JDBCUtil.getConnection().prepareStatement(sql);
            ps.setString(1,fileName);   //设置Mysql执行语句
            ResultSet result = ps.executeQuery();   //执行sql语句

            if (result.next()) {
                //使用Blob对象接收数据库里的图片
                Blob blob = result.getBlob("file_data");
                //获取Blob对象的二进制流
                InputStream in = blob.getBinaryStream();
                //文件输出流
                OutputStream out = new FileOutputStream(new File(fileName));    //文件路径

                //写出文件夹
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                System.out.println(fileName + "文件写出成功");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (JDBCUtil.getConnection() != null) {
                JDBCUtil.getConnection().close();
            }
        }
    }
    //查询方法
    public static void selectText(String str,int input) throws Exception {
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

        //获取pstmt对象
        PreparedStatement pstmt = JDBCUtil.getConnection().prepareStatement(sql);
        //执行SQL
        ResultSet rs = pstmt.executeQuery();
        //处理结果 List<Brand>
        Brand brand;
        List<Brand> brands = new ArrayList<>();
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
        //增强for输出
        for (Brand brand1 : brands) {
            System.out.print("文件ID：" + brand1.getFileId() + "\t");
            System.out.print("文件名：" + brand1.getFileName() + "\t");
            System.out.print("文件日期：" + brand1.getFileDate() + "\t");
            System.out.print("文件路径：" +brand1.getFilePath() + "\t");
            System.out.println();
        }

        //资源释放
        JDBCUtil.close(JDBCUtil.getConnection(),pstmt,rs);
        System.out.println("查询结束");
    }
}
