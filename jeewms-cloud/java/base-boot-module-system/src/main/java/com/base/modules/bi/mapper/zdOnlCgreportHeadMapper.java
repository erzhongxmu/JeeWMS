package com.base.modules.bi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.bi.entity.zdOnlCgreportHead;
import com.base.modules.bi.entity.zdOnlCgreportParam;
import com.google.gson.JsonObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * @Description: onl_cgreport_head
 * @Author: wms-cloud
 * @Date:   2020-12-09
 * @Version: V1.0
 */
public interface zdOnlCgreportHeadMapper extends BaseMapper<zdOnlCgreportHead> {

    @Select({"${sql}"})
    @ResultType(List.class)
    public List<Map<String,Object>> executeQuery(@Param("sql") String sql);


    List<Map<?, ?>> executeSelete(@Param("sql") String var1);

    IPage<Map<String, Object>> selectPageBySql(Page<Map<String, Object>> var1, @Param("sqlStr") String var2);

    Long queryCountBySql(@Param("sql") String var1);

    Map<String, Object> queryCgReportMainConfig(@Param("reportId") String var1);

    List<Map<String, Object>> queryCgReportItems(@Param("cgrheadId") String var1);

    List<zdOnlCgreportParam> queryCgReportParams(@Param("cgrheadId") String var1);

}
