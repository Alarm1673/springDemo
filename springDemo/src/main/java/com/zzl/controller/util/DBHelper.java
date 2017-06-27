package com.zzl.controller.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Class <code>Object</code> is the root of the class hierarchy.
 * Every class has <code>Object</code> as a superclass. All objects,
 * including arrays, implement the methods of this class.
 *
 * @author Administrator
 * @version 1.0
 * @see
 * @since JDK1.7
 * <p>
 * History
 * Created by Administrator on 2017/3/28 0028.
 */
public class DBHelper {
    private static final Logger logger = LoggerFactory.getLogger(DBHelper.class);

    public static final String url = "jdbc:mysql://10.1.77.106:3306/DPEmidas";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "dpemidas_r";
    public static final String password = "dp!@VU074l7qm";

    public Connection conn = null;
    public PreparedStatement pst = null;

    public DBHelper(String sql) {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.conn.close();
            this.pst.close();
        } catch (SQLException e) {
            logger.error("DBHelper mysql close error",e);
        }
    }

    static String sql = null;
    static DBHelper db1 = null;
    static ResultSet ret = null;

    public static void main(String[] args) {
        sql = "select * from ACT_Activity order by id desc limit 10";//SQL语句
        db1 = new DBHelper(sql);//创建DBHelper对象

        try {
            ret = db1.pst.executeQuery();//执行语句，得到结果集
            while (ret.next()) {
                String uid = ret.getString(1);
                String ufname = ret.getString(2);
                String ulname = ret.getString(3);
                String udate = ret.getString(4);
                System.out.println(uid + "\t" + ufname + "\t" + ulname + "\t" + udate );
            }//显示数据
            ret.close();
            db1.close();//关闭连接
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
