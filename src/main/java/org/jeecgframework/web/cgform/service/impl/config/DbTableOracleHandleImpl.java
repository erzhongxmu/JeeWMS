package org.jeecgframework.web.cgform.service.impl.config;

import org.apache.commons.lang.StringUtils;

import org.jeecgframework.web.cgform.service.config.DbTableHandleI;
import org.jeecgframework.web.cgform.service.impl.config.util.ColumnMeta;


/**
 * oracle的表工具类
 * oracle语句结尾不能使用 ;
 */

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public class DbTableOracleHandleImpl implements DbTableHandleI {


    @Override
    public String getAddColumnSql(ColumnMeta columnMeta) {
        return " ADD  " + getAddFieldDesc(columnMeta) + "";
    }


    @Override
    public String getReNameFieldName(ColumnMeta columnMeta) {
        return "RENAME COLUMN  " + columnMeta.getOldColumnName() + " TO " + columnMeta.getColumnName() + "";
    }


    @Override
    public String getUpdateColumnSql(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) {
        return " MODIFY   " + getUpdateFieldDesc(cgformcolumnMeta, datacolumnMeta) + "";
    }


    @Override
    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String result = "";
        if ("varchar2".equalsIgnoreCase(dataType)) {
            result = "string";
        } else if ("double".equalsIgnoreCase(dataType)) {
            result = "double";
        } else if ("number".equalsIgnoreCase(dataType) && digits == 0) {
            result = "int";
        } else if ("number".equalsIgnoreCase(dataType) && digits != 0) {
            result = "double";
        } else if ("int".equalsIgnoreCase(dataType)) {
            result = "int";
        } else if ("Date".equalsIgnoreCase(dataType)) {
            result = "date";
        } else if ("Datetime".equalsIgnoreCase(dataType)) {
            result = "date";
        }
        return result;
    }


    @Override
    public String dropTableSQL(String tableName) {
        return " DROP TABLE  " + tableName.toLowerCase() + " ";
    }


    @Override
    public String getDropColumnSql(String fieldName) {
        return " DROP COLUMN " + fieldName.toUpperCase() + "";
    }

    private String getAddFieldDesc(ColumnMeta columnMeta) {
        String result = "";
        if ("string".equalsIgnoreCase(columnMeta.getColunmType())) {
            result = columnMeta.getColumnName() + " varchar2(" + columnMeta.getColumnSize() + ")";
        } else if ("date".equalsIgnoreCase(columnMeta.getColunmType())) {
            result = columnMeta.getColumnName() + " datetime";
        } else if ("int".equalsIgnoreCase(columnMeta.getColunmType())) {
            result = columnMeta.getColumnName() + " NUMBER(" + columnMeta.getColumnSize() + ")";
        } else if ("double".equalsIgnoreCase(columnMeta.getColunmType())) {
            result = columnMeta.getColumnName() + " NUMBER(" + columnMeta.getColumnSize() + "," + columnMeta.getDecimalDigits() + ")";
        } else if ("bigdecimal".equalsIgnoreCase(columnMeta.getColunmType())) {
            result = columnMeta.getColumnName() + " NUMBER(" + columnMeta.getColumnSize() + "," + columnMeta.getDecimalDigits() + ")";
        } else if ("text".equalsIgnoreCase(columnMeta.getColunmType())) {
            result = columnMeta.getColumnName() + " CLOB ";
        } else if ("blob".equalsIgnoreCase(columnMeta.getColunmType())) {
            result = columnMeta.getColumnName() + " BLOB ";
        }
        result += (StringUtils.isNotEmpty(columnMeta.getFieldDefault()) ? " DEFAULT " + columnMeta.getFieldDefault() : " ");
        result += ("Y".equals(columnMeta.getIsNullable()) ? " NULL" : " NOT NULL");
        return result;
    }

    private String getUpdateFieldDesc(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) {
        String result = "";
        String isnull = "";
        //oracle对于是否为空必须跟原来的比对
        if (!datacolumnMeta.getIsNullable().equals(cgformcolumnMeta.getIsNullable())) {
            isnull = ("Y".equals(cgformcolumnMeta.getIsNullable()) ? "NULL" : "NOT NULL");
        }

        if ("string".equalsIgnoreCase(cgformcolumnMeta.getColunmType()) || "text".equalsIgnoreCase(cgformcolumnMeta.getColunmType())) {
            result = cgformcolumnMeta.getColumnName() + " varchar2(" + cgformcolumnMeta.getColumnSize() + ")" + isnull;

        } else if ("date".equalsIgnoreCase(cgformcolumnMeta.getColunmType())) {
            result = cgformcolumnMeta.getColumnName() + " date " + isnull;

        } else if ("int".equalsIgnoreCase(cgformcolumnMeta.getColunmType())) {
            result = cgformcolumnMeta.getColumnName() + " NUMBER(" + cgformcolumnMeta.getColumnSize() + ") " + isnull;

        } else if ("double".equalsIgnoreCase(cgformcolumnMeta.getColunmType())) {
            result = cgformcolumnMeta.getColumnName() + " NUMBER(" + cgformcolumnMeta.getColumnSize() + "," + cgformcolumnMeta.getDecimalDigits() + ") " + isnull;
        } else if ("bigdecimal".equalsIgnoreCase(cgformcolumnMeta.getColunmType())) {
            result = cgformcolumnMeta.getColumnName() + " NUMBER(" + cgformcolumnMeta.getColumnSize() + "," + cgformcolumnMeta.getDecimalDigits() + ") " + isnull;
        }
        result += (StringUtils.isNotEmpty(cgformcolumnMeta.getFieldDefault()) ? " DEFAULT " + cgformcolumnMeta.getFieldDefault() : " ");
        result += isnull;
        return result;
    }


    @Override
    public String getCommentSql(ColumnMeta columnMeta) {
        return "COMMENT ON COLUMN " + columnMeta.getTableName() + "." + columnMeta.getColumnName() + " IS '" + columnMeta.getComment() + "'";
    }


    @Override
    public String getSpecialHandle(ColumnMeta cgformcolumnMeta,
                                   ColumnMeta datacolumnMeta) {
        return null;
    }

}
