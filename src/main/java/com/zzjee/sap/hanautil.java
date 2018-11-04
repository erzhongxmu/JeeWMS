package com.zzjee.sap;

import org.jeecgframework.core.util.ResourceUtil;

import java.sql.*;

public class hanautil {


//    public static void main(String[] args) {
////          String DRIVER = "com.sap.cloud.db.jdbc.Driver";  //jdbc 4.0
////        String cosFilePath = "/jeewx_logo.jpg";
////        String localFilePath1 = "C:\\Users\\qinfeng\\Desktop\\jeewx_logo.jpg";
////        //上传文件
////        JSONObject getFileResult= QCloudUtils.upload(cosFilePath,localFilePath1);
//        QCloudDemo demo = new QCloudDemo();
//        try {
//            demo.select();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //获取文档列表
//        //JSONObject getFileResult= QCloudUtils.listByFoler("/");
////        System.out.println(getFileResult);
//    }
    //根据防伪码获取物料详细信息
    public String select(String id) throws Exception {
        String sap_hana= ResourceUtil.getConfigByName("sap_hana");
        String sap_user=ResourceUtil.getConfigByName("sap_user");
        String sap_password=ResourceUtil.getConfigByName("sap_password");
        String result=null;
        Connection con = this.getConnection("com.sap.db.jdbc.Driver", sap_hana, sap_user, sap_password);
        PreparedStatement pstmt = con.prepareStatement("SELECT T.BRAND, T.SAP,T.STYLE,T.COLOR_CD,BD.BARCODE FROM SMS.BL_PRODUCT T INNER JOIN SMS.BL_DPATCH BD ON T.CODE_NAME = BD.PRODUCT_ID WHERE  BD.BARCODE = '"+id+"'");
        ResultSet rs = pstmt.executeQuery();
        result = this.processResult(rs);
        this.closeConnection(con, pstmt);
        return result;
    }
    //根据防伪码获取物料详细信息
    public String selectdetail(String id) throws Exception {
        String sap_hana= ResourceUtil.getConfigByName("sap_hana");
        String sap_user=ResourceUtil.getConfigByName("sap_user");
        String sap_password=ResourceUtil.getConfigByName("sap_password");
        String result=null;
        Connection con = this.getConnection("com.sap.db.jdbc.Driver", sap_hana, sap_user, sap_password);
        PreparedStatement pstmt = con.prepareStatement("SELECT T.BRAND, T.SAP,T.STYLE,T.COLOR_CD,BD.BARCODE,t.URL FROM SMS.BL_PRODUCT T INNER JOIN SMS.BL_DPATCH BD ON T.CODE_NAME = BD.PRODUCT_ID WHERE  BD.BARCODE = '"+id+"'");
        ResultSet rs = pstmt.executeQuery();
        result = this.processResult(rs);
        this.closeConnection(con, pstmt);
        return result;
    }

    //根据防伪码获取sap物料号
    public String selectmatnr(String id) throws Exception {
        String sap_hana= ResourceUtil.getConfigByName("sap_hana");
        String sap_user=ResourceUtil.getConfigByName("sap_user");
        String sap_password=ResourceUtil.getConfigByName("sap_password");
        String result=null;
        Connection con = this.getConnection("com.sap.db.jdbc.Driver", sap_hana, sap_user, sap_password);
        PreparedStatement pstmt = con.prepareStatement("SELECT  T.SAP FROM SMS.BL_PRODUCT T INNER JOIN SMS.BL_DPATCH BD ON T.CODE_NAME = BD.PRODUCT_ID WHERE BD.BARCODE = '"+id+"'");
        ResultSet rs = pstmt.executeQuery();
        result = this.processResult(rs);
        this.closeConnection(con, pstmt);
        return result;
    }


    private String processResult(ResultSet rs) throws Exception {
        String result=null;
        if (rs.next()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int colNum = rsmd.getColumnCount();
            for (int i = 1; i <= colNum; i++) {
                if (i == 1) {
                    System.out.print(rsmd.getColumnName(i));
                } else {
                    System.out.print("\t" + rsmd.getColumnName(i));
                }

            }
            System.out.print("\n");
            System.out.println("———————–");
            do {
                for (int i = 1; i <= colNum; i++) {
                    if (i == 1) {
                        result  = rs.getString(i);
                    } else {
                        result = result + ":"+rs.getString(i);
                    }

                }
                System.out.print("\n");
            } while (rs.next());
        } else {
            System.out.println("query not result.");
        }
        return  result;
    }

    private Connection getConnection(String driver, String url, String user,
                                     String password) throws Exception {
        Class.forName(driver);
        return DriverManager.getConnection(url, user, password);

    }

    private void closeConnection(Connection con, Statement stmt)
            throws Exception {
        if (stmt != null) {
            stmt.close();
        }
        if (con != null) {
            con.close();
        }
    }

}


