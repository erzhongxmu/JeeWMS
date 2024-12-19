package com.base.modules.data;

import com.alibaba.druid.util.StringUtils;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dbUtil {
    public static Connection getConnection(){
        Connection conn=null;
        try {
            String url="jdbc:sqlserver://192.168.12.103:1433;DatabaseName=UFDATA_888_2020";
            String user="sa";
            String password="Zx%kU#3*32";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//加载数据驱动
            conn = DriverManager.getConnection(url, user, password);// 连接数据库
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("加载数据库驱动失败");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("连接数据库失败");
        }
        return conn;
    }
    public static List<Baseoutientity> getbysql(String sql, String para, int i) {
        List<Baseoutientity> reslit = new ArrayList<Baseoutientity>();
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = dbUtil.getConnection();
            pstmt2 = conn.prepareStatement(sql);
            if(!StringUtils.isEmpty(para)){
                pstmt2.setString(1, para);
            }
            rs = pstmt2.executeQuery();
            while (rs.next()){
                Baseoutientity t = new Baseoutientity();
                try {
                    Class cl = Class.forName("com.base.modules.data.Baseoutientity");//反射得到类
                    Object obj = cl.newInstance();//新建一个实例
                    int ilen = i;
                    for(int k =1;k<=ilen;k++){
                        try{
                            String methodstr = "setOutX"+k;
                            Method method = cl.getMethod(methodstr,String.class);
                            method.invoke(obj,rs.getString(k));
                        }catch (Exception e){

                        }

                    }
                    t = (Baseoutientity) obj;
                    reslit.add(t);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return reslit;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (pstmt2 != null) {
                    pstmt2.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
