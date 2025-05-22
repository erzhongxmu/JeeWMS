package org.jeecgframework.web.cgform.service.impl.config;

import org.apache.commons.lang.StringUtils;

import org.jeecgframework.web.cgform.exception.BusinessException;
import org.jeecgframework.web.cgform.exception.DBException;
import org.jeecgframework.web.cgform.service.config.DbTableHandleI;
import org.jeecgframework.web.cgform.service.impl.config.util.ColumnMeta;


/**
 * postgres的表工具类
 */

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public class DbTablePostgresHandleImpl implements DbTableHandleI {


    @Override
    public String getAddColumnSql(ColumnMeta columnMeta) {
        return " ADD COLUMN " + getAddFieldDesc(columnMeta) + ";";
    }


    @Override
    public String getReNameFieldName(ColumnMeta columnMeta) {
        return " RENAME  COLUMN  " + columnMeta.getOldColumnName() + " to " + columnMeta.getColumnName() + ";";
    }


    @Override
    public String getUpdateColumnSql(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) throws DBException {
        return "  ALTER  COLUMN   " + getUpdateFieldDesc(cgformcolumnMeta, datacolumnMeta) + ";";
    }


    @Override
    public String getSpecialHandle(ColumnMeta cgformcolumnMeta,
                                   ColumnMeta datacolumnMeta) {
        return "  ALTER  COLUMN   " + getUpdateFieldDefault(cgformcolumnMeta, datacolumnMeta) + ";";
    }


    @Override
    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String result = "";
        if ("varchar".equalsIgnoreCase(dataType)) {
            result = "string";
        } else if ("double".equalsIgnoreCase(dataType)) {
            result = "double";
        } else if (dataType.contains("int")) {
            result = "int";
        } else if ("Date".equalsIgnoreCase(dataType)) {
            result = "date";
        } else if ("timestamp".equalsIgnoreCase(dataType)) {
            result = "date";
        } else if ("bytea".equalsIgnoreCase(dataType)) {
            result = "blob";
        } else if ("text".equalsIgnoreCase(dataType)) {
            result = "text";
        } else if ("decimal".equalsIgnoreCase(dataType)) {
            result = "bigdecimal";
        } else if ("numeric".equalsIgnoreCase(dataType)) {
            //double和decimal都会返回numeric，先暂时返回bigdecimal
            result = "bigdecimal";
        }
        return result;
    }


    @Override
    public String dropTableSQL(String tableName) {
        return " DROP TABLE  " + tableName + " ;";
    }


    @Override
    public String getDropColumnSql(String fieldName) {
        return " DROP COLUMN " + fieldName + ";";
    }

    private String getUpdateFieldDesc(ColumnMeta cgfromcolumnMeta, ColumnMeta datacolumnMeta) throws DBException {
        String result = "";
        //TODO 对于非空情况 ，需要特殊增加约束方法，默认是空
        if ("string".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + "  type character varying(" + cgfromcolumnMeta.getColumnSize() + ")" + " ";
        } else if ("date".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + "  type datetime" + " ";
        } else if ("int".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            //postpres数据库整形没有长度概念
            result = cgfromcolumnMeta.getColumnName() + " type int4 ";
        } else if ("double".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " type  numeric(" + cgfromcolumnMeta.getColumnSize() + "," + cgfromcolumnMeta.getDecimalDigits() + ")" + " ";
        } else if ("BigDecimal".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " type  decimal(" + cgfromcolumnMeta.getColumnSize() + "," + cgfromcolumnMeta.getDecimalDigits() + ")" + " ";
        } else if ("text".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + "  type text(" + cgfromcolumnMeta.getColumnSize() + ")" + " ";
        } else if ("blob".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
//			bytea类型不可修改，修改会报错
            throw new DBException("blob类型不可修改");
        }
        return result;
    }

    private String getUpdateFieldDefault(ColumnMeta cgfromcolumnMeta, ColumnMeta datacolumnMeta) {
        String result = "";

        if (!cgfromcolumnMeta.equalsDefault(datacolumnMeta)) {
            if ("string".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
                result = cgfromcolumnMeta.getColumnName();
                result += (StringUtils.isNotEmpty(cgfromcolumnMeta.getFieldDefault()) ? " SET DEFAULT " + cgfromcolumnMeta.getFieldDefault() : " DROP DEFAULT");
            } else if ("date".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
                result = cgfromcolumnMeta.getColumnName();
                result += (StringUtils.isNotEmpty(cgfromcolumnMeta.getFieldDefault()) ? " SET DEFAULT " + cgfromcolumnMeta.getFieldDefault() : " DROP DEFAULT");
            } else if ("int".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
                result = cgfromcolumnMeta.getColumnName();
                result += (StringUtils.isNotEmpty(cgfromcolumnMeta.getFieldDefault()) ? " SET DEFAULT " + cgfromcolumnMeta.getFieldDefault() : " DROP DEFAULT");
            } else if ("double".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
                result = cgfromcolumnMeta.getColumnName();
                result += (StringUtils.isNotEmpty(cgfromcolumnMeta.getFieldDefault()) ? " SET DEFAULT " + cgfromcolumnMeta.getFieldDefault() : " DROP DEFAULT");
            } else if ("bigdecimal".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
                result = cgfromcolumnMeta.getColumnName();
                result += (StringUtils.isNotEmpty(cgfromcolumnMeta.getFieldDefault()) ? " SET DEFAULT " + cgfromcolumnMeta.getFieldDefault() : " DROP DEFAULT");
            } else if ("text".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
                result = cgfromcolumnMeta.getColumnName();
                result += (StringUtils.isNotEmpty(cgfromcolumnMeta.getFieldDefault()) ? " SET DEFAULT " + cgfromcolumnMeta.getFieldDefault() : " DROP DEFAULT");
            }

        }
        return result;
    }


    private String getAddFieldDesc(ColumnMeta cgfromcolumnMeta) {
        String result = "";
        if ("string".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " character varying(" + cgfromcolumnMeta.getColumnSize() + ")" + " ";
        } else if ("date".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " datetime" + " ";
        } else if ("int".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " int4";
        } else if ("double".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " numeric(" + cgfromcolumnMeta.getColumnSize() + "," + cgfromcolumnMeta.getDecimalDigits() + ")" + " ";
        } else if ("bigdecimal".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " decimal(" + cgfromcolumnMeta.getColumnSize() + "," + cgfromcolumnMeta.getDecimalDigits() + ")" + " ";
        } else if ("blob".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " bytea(" + cgfromcolumnMeta.getColumnSize() + ")" + " ";
        } else if ("text".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " text(" + cgfromcolumnMeta.getColumnSize() + ")" + " ";
        }
        result += (StringUtils.isNotEmpty(cgfromcolumnMeta.getFieldDefault()) ? " DEFAULT " + cgfromcolumnMeta.getFieldDefault() : " ");
        return result;
    }

    private String getRenameFieldDesc(ColumnMeta cgfromcolumnMeta) {
        String result = "";
        if ("string".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " character varying(" + cgfromcolumnMeta.getColumnSize() + ")" + " ";
        } else if ("date".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " datetime" + " ";
        } else if ("int".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " int(" + cgfromcolumnMeta.getColumnSize() + ")" + " ";
        } else if ("double".equalsIgnoreCase(cgfromcolumnMeta.getColunmType())) {
            result = cgfromcolumnMeta.getColumnName() + " numeric(" + cgfromcolumnMeta.getColumnSize() + "," + cgfromcolumnMeta.getDecimalDigits() + ")" + " ";
        }
        return result;
    }


    @Override
    public String getCommentSql(ColumnMeta columnMeta) {
        return "COMMENT ON COLUMN " + columnMeta.getTableName() + "." + columnMeta.getColumnName() + " IS '" + columnMeta.getComment() + "'";
    }


}
