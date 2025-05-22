package org.jeecgframework.web.cgform.service.impl.config;

import org.apache.commons.lang.StringUtils;

import org.jeecgframework.web.cgform.service.config.DbTableHandleI;
import org.jeecgframework.web.cgform.service.impl.config.util.ColumnMeta;


/**
 * mysql的表工具类
 */
/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public class DbTableMysqlHandleImpl implements DbTableHandleI {


    @Override
    public String getAddColumnSql(ColumnMeta columnMeta) {
        return " ADD COLUMN " + getAddFieldDesc(columnMeta) + ";";
    }


    @Override
    public String getReNameFieldName(ColumnMeta columnMeta) {
        return "CHANGE COLUMN  " + columnMeta.getOldColumnName() + " " + getRenameFieldDesc(columnMeta) + " ;";
    }


    @Override
    public String getUpdateColumnSql(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) {
        return " MODIFY COLUMN  " + getUpdateFieldDesc(cgformcolumnMeta, datacolumnMeta) + ";";
    }


    @Override
    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String result = "";
        if ("varchar".equalsIgnoreCase(dataType)) {
            result = "string";
        } else if ("double".equalsIgnoreCase(dataType)) {
            result = "double";
        } else if ("int".equalsIgnoreCase(dataType)) {
            result = "int";
        } else if ("Date".equalsIgnoreCase(dataType)) {
            result = "date";
        } else if ("Datetime".equalsIgnoreCase(dataType)) {
            result = "date";
        } else if ("decimal".equalsIgnoreCase(dataType)) {
            result = "bigdecimal";
        } else if ("text".equalsIgnoreCase(dataType)) {
            result = "text";
        } else if ("blob".equalsIgnoreCase(dataType)) {
            result = "blob";
        }
        return result;
    }


    @Override
    public String dropTableSQL(String tableName) {
        return " DROP TABLE IF EXISTS " + tableName + " ;";
    }


    @Override
    public String getDropColumnSql(String fieldName) {
        return " DROP COLUMN " + fieldName + ";";
    }

    /**
     * 将get...FieldDesc这些方法中共同的内容抽出来
     *
     * @param cgfromcolumnMeta
     * @param datacolumnMeta
     * @return
     */
    private String getFieldDesc(ColumnMeta cgfromcolumnMeta, ColumnMeta datacolumnMeta) {
        String result = "";
        if ("string".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " varchar(" + cgfromcolumnMeta.getColumnSize() + ")" + " " + ("Y".equals(cgfromcolumnMeta.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("date".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " datetime" + " " + ("Y".equals(cgfromcolumnMeta.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("int".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " int(" + cgfromcolumnMeta.getColumnSize() + ")" + " " + ("Y".equals(cgfromcolumnMeta.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("double".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " double(" + cgfromcolumnMeta.getColumnSize() + "," + cgfromcolumnMeta.getDecimalDigits() + ")" + " " + ("Y".equals(cgfromcolumnMeta.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("bigdecimal".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " decimal(" + cgfromcolumnMeta.getColumnSize() + "," + cgfromcolumnMeta.getDecimalDigits() + ")" + " " + ("Y".equals(cgfromcolumnMeta.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("text".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " text " + ("Y".equals(cgfromcolumnMeta.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("blob".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " blob " + ("Y".equals(cgfromcolumnMeta.getIsNullable()) ? "NULL" : "NOT NULL");
        }
        result += (StringUtils.isNotEmpty(cgfromcolumnMeta.getComment()) ? " COMMENT '" + cgfromcolumnMeta.getComment() + "'" : " ");
        result += (StringUtils.isNotEmpty(cgfromcolumnMeta.getFieldDefault()) ? " DEFAULT " + cgfromcolumnMeta.getFieldDefault() : " ");
        String pkType = cgfromcolumnMeta.getPkType();
        Boolean bol = "id".equalsIgnoreCase(cgfromcolumnMeta.getColumnName()) && pkType != null &&
                ("SEQUENCE".equalsIgnoreCase(pkType) || "NATIVE".equalsIgnoreCase(pkType));
        if (bol) {
            result += " AUTO_INCREMENT ";
        }
        return result;
    }

    private String getUpdateFieldDesc(ColumnMeta cgfromcolumnMeta, ColumnMeta datacolumnMeta) {
        String result = this.getFieldDesc(cgfromcolumnMeta, datacolumnMeta);
        return result;
    }

    private String getAddFieldDesc(ColumnMeta cgfromcolumnMeta) {
        String result = this.getFieldDesc(cgfromcolumnMeta, null);
        return result;
    }

    private String getRenameFieldDesc(ColumnMeta cgfromcolumnMeta) {
        String result = this.getFieldDesc(cgfromcolumnMeta, null);
        return result;
    }

    /**
     * Mysql注释是和修改的sql一起的,所以返回空字符串就可以了
     */

    @Override
    public String getCommentSql(ColumnMeta columnMeta) {
        return "";
    }


    @Override
    public String getSpecialHandle(ColumnMeta cgformcolumnMeta,
                                   ColumnMeta datacolumnMeta) {
        return null;
    }

}
