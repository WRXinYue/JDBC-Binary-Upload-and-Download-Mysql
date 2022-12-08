# JDBC-Binary-Upload-and-Download-Mysql
Java使用JDBC连接MySql，进行文件二进制上下载，练习测试

涉及范围：I/O, 异常, 线程, MySQl查询 

本程序使用MVC架构进行设计，
controller控制层: 将用户用户输入指令和数据传递给业务模型
model 模型层： 进行业务逻辑判断、数据库读取
View 视图： 根据业务逻辑选择不同的视图

代码文件解析
 * controller
   * Commodity -- 存放常用工具类
   * JDBCUtil -- JDBC数据库管理，实现数据库连接、关闭流进行封装
   * SettingUtil -- 负责Settings.properties文件管理，现在负责对properties文件初始化
   
 * Model
   * Brand -- 定义sql id, file_name, file_date, file_path，进行查询Brand方法进行封装
   * Sql -- 提供文件上传、下载和查询方法，实现对文件进行增删改查
   
 * View
   * Menu -- 菜单显示，负责命令面板控制