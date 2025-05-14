package com.base.modules.bi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.common.util.BrowserUtils;
import com.base.modules.bi.entity.zdOnlCgreportHead;
import com.base.modules.bi.entity.zdOnlCgreportItem;
import com.base.modules.bi.entity.zdOnlCgreportParam;
import com.base.modules.bi.mapper.zdOnlCgreportHeadMapper;
import com.base.modules.bi.service.IzdOnlCgreportHeadService;
import com.base.modules.bi.service.IzdOnlCgreportItemService;
import com.base.modules.bi.service.IzdOnlCgreportParamService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.DynamicDataSourceModel;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgreport.util.CgReportSqlUtil;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

@RestController("ZdOnlCgreportAPI")
@RequestMapping({"/zdonline/cgreport/api"})
public class ZdOnlCgreportAPI {
    private static final Logger a = LoggerFactory.getLogger(org.jeecg.modules.online.cgreport.a.a.class);
    @Autowired
    private IzdOnlCgreportHeadService onlCgreportHeadService;
    @Autowired
    private IzdOnlCgreportItemService onlCgreportItemService;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    private IzdOnlCgreportParamService onlCgreportParamService;

    public ZdOnlCgreportAPI() {
    }

    @GetMapping({"/getColumnsAndData/{code}"})
    @PermissionData
    public Result<?> a(@PathVariable("code") String var1, HttpServletRequest var2) {
        zdOnlCgreportHead var3 = (zdOnlCgreportHead)this.onlCgreportHeadService.getById(var1);
        if (var3 == null) {
            return Result.error("实体不存在");
        } else {
            Result var4 = this.b(var1, var2);
            if (var4.getCode().equals(200)) {
                JSONObject var5 = JSON.parseObject(JSONObject.toJSONString(var4.getResult()));
                JSONArray var6 = var5.getJSONArray("records");
                QueryWrapper var7 = new QueryWrapper();
                ((QueryWrapper)((QueryWrapper)var7.eq("cgrhead_id", var1)).eq("is_show", 1)).orderByAsc("order_num");
                List var8 = this.onlCgreportItemService.list(var7);
                HashMap var9 = new HashMap(1024);
                JSONArray var10 = new JSONArray();
                JSONArray var11 = new JSONArray();
                Iterator var12 = var8.iterator();

                while(var12.hasNext()) {
                   zdOnlCgreportItem var13 = (zdOnlCgreportItem)var12.next();
                    JSONObject var14 = new JSONObject(4);
                    var14.put("title", var13.getFieldTxt());
                    var14.put("dataIndex", var13.getFieldName());
                    var14.put("align", "center");
                    var14.put("sorter", "true");
                    String var15;
                    if (StringUtils.isNotBlank(var13.getFieldHref())) {
                        var15 = "fieldHref_" + var13.getFieldName();
                        JSONObject var16 = new JSONObject();
                        var16.put("customRender", var15);
                        var14.put("scopedSlots", var16);
                        JSONObject var17 = new JSONObject();
                        var17.put("slotName", var15);
                        var17.put("href", var13.getFieldHref());
                        var10.add(var17);
                    }

                    var11.add(var14);
                    var15 = var13.getDictCode();
                    List var19 = this.a(var15, var6, var13.getFieldName());
                    if (var19 != null) {
                        var9.put(var13.getFieldName(), var19);
                        var14.put("customRender", var13.getFieldName());
                    }
                }

                HashMap var18 = new HashMap(3);
                var18.put("data", var4.getResult());
                var18.put("columns", var11);
                var18.put("dictOptions", var9);
                var18.put("fieldHrefSlots", var10);
                var18.put("cgreportHeadName", var3.getName());
                return Result.ok(var18);
            } else {
                return var4;
            }
        }
    }

    private List<DictModel> a(String var1, JSONArray var2, String var3) {
        List var4 = null;
        if (oConvertUtils.isNotEmpty(var1)) {
            if (var1.trim().toLowerCase().indexOf("select ") == 0 && (var3 == null || var2.size() > 0)) {
                var1 = var1.trim();
                int var5 = var1.lastIndexOf(";");
                if (var5 == var1.length() - 1) {
                    var1 = var1.substring(0, var5);
                }

                String var6 = "SELECT * FROM (" + var1 + ") temp ";
                String var12;
                if (var2 != null) {
                    ArrayList var7 = new ArrayList();

                    for(int var8 = 0; var8 < var2.size(); ++var8) {
                        JSONObject var9 = var2.getJSONObject(var8);
                        String var10 = var9.getString(var3);
                        if (StringUtils.isNotBlank(var10)) {
                            var7.add(var10);
                        }
                    }

                    var12 = "'" + StringUtils.join(var7, "','") + "'";
                    var6 = var6 + "WHERE temp.value IN (" + var12 + ")";
                }

                List var11 = ((zdOnlCgreportHeadMapper)this.onlCgreportHeadService.getBaseMapper()).executeSelete(var6);
                if (var11 != null && var11.size() != 0) {
                    var12 = JSON.toJSONString(var11);
                    var4 = JSON.parseArray(var12, DictModel.class);
                }
            } else {
                var4 = this.sysBaseAPI.queryDictItemsByCode(var1);
            }
        }

        return var4;
    }

    /** @deprecated */
    @Deprecated
    @GetMapping({"/getColumns/{code}"})
    public Result<?> a(@PathVariable("code") String var1) {
        zdOnlCgreportHead var2 = (zdOnlCgreportHead)this.onlCgreportHeadService.getById(var1);
        if (var2 == null) {
            return Result.error("实体不存在");
        } else {
            QueryWrapper var3 = new QueryWrapper();
            var3.eq("cgrhead_id", var1);
            var3.eq("is_show", 1);
            var3.orderByAsc("order_num");
            List var4 = this.onlCgreportItemService.list(var3);
            ArrayList var5 = new ArrayList();
            HashMap var6 = new HashMap(1024);
            Iterator var7 = var4.iterator();

            while(var7.hasNext()) {
                zdOnlCgreportItem var8 = (zdOnlCgreportItem)var7.next();
                HashMap var9 = new HashMap(3);
                var9.put("title", var8.getFieldTxt());
                var9.put("dataIndex", var8.getFieldName());
                var9.put("align", "center");
                var9.put("sorter", "true");
                var5.add(var9);
                String var10 = var8.getDictCode();
                if (oConvertUtils.isNotEmpty(var10)) {
                    List var11 = null;
                    if (var10.toLowerCase().indexOf("select ") == 0) {
                        List var12 = ((zdOnlCgreportHeadMapper)this.onlCgreportHeadService.getBaseMapper()).executeSelete(var10);
                        if (var12 != null && var12.size() != 0) {
                            String var13 = JSON.toJSONString(var12);
                            var11 = JSON.parseArray(var13, DictModel.class);
                        }
                    } else {
                        var11 = this.sysBaseAPI.queryDictItemsByCode(var10);
                    }

                    if (var11 != null) {
                        var6.put(var8.getFieldName(), var11);
                        var9.put("customRender", var8.getFieldName());
                    }
                }
            }

            HashMap var14 = new HashMap(1);
            var14.put("columns", var5);
            var14.put("dictOptions", var6);
            var14.put("cgreportHeadName", var2.getName());
            return Result.ok(var14);
        }
    }

    @GetMapping({"/getData/{code}"})
    @PermissionData
    public Result<?> b(@PathVariable("code") String var1, HttpServletRequest var2) {
        zdOnlCgreportHead var3 = (zdOnlCgreportHead)this.onlCgreportHeadService.getById(var1);
        if (var3 == null) {
            return Result.error("实体不存在");
        } else {
            String var4 = var3.getCgrSql().trim();
            String var5 = var3.getDbSource();

            try {
                Map var6 = CgReportSqlUtil.a(var2);
                Map var7;
                if (StringUtils.isNotBlank(var5)) {
                    a.info("Online报表: 走了多数据源逻辑");
                    var7 = this.onlCgreportHeadService.executeSelectSqlDynamic(var5, var4, var6, var3.getId());
                } else {
                    a.info("Online报表: 走了稳定逻辑");
                    var7 = this.onlCgreportHeadService.executeSelectSql(var4, var3.getId(), var6);
                }

                return Result.ok(var7);
            } catch (Exception var8) {
                a.error(var8.getMessage(), var8);
                return Result.error("SQL执行失败：" + var8.getMessage());
            }
        }
    }

    @GetMapping({"/getQueryInfo/{code}"})
    public Result<?> b(@PathVariable("code") String var1) {
        try {
            List var2 = this.onlCgreportItemService.getAutoListQueryInfo(var1);
            return Result.ok(var2);
        } catch (Exception var3) {
            a.info(var3.getMessage(), var3);
            return Result.error("查询失败");
        }
    }

    @GetMapping({"/getParamsInfo/{code}"})
    public Result<?> c(@PathVariable("code") String var1) {
        try {
            LambdaQueryWrapper<zdOnlCgreportParam> var2 = new LambdaQueryWrapper();
            var2.eq(zdOnlCgreportParam::getCgrheadId, var1);
            List var3 = this.onlCgreportParamService.list(var2);
            return Result.ok(var3);
        } catch (Exception var4) {
            a.info(var4.getMessage(), var4);
            return Result.error("查询失败");
        }
    }

    @PermissionData
    @RequestMapping({"/exportXls/{reportId}"})
    public void a(@PathVariable("reportId") String var1, HttpServletRequest var2, HttpServletResponse var3) {
        String var4 = "报表";
        String var5 = "导出信息";
        if (!oConvertUtils.isNotEmpty(var1)) {
            throw new JeecgBootException("参数错误");
        } else {
            Map var6 = null;

            try {
                var6 = this.onlCgreportHeadService.queryCgReportConfig(var1);
            } catch (Exception var31) {
                throw new JeecgBootException("动态报表配置不存在!");
            }

            List var7 = (List)var6.get("items");
            Result var8 = this.b(var1, var2);
            List var9 = null;
            if (var8.getCode().equals(200)) {
                Map var10 = (Map)var8.getResult();
                var9 = (List)var10.get("records");
            }

            ArrayList var32 = new ArrayList();

            String var12;
            for(int var11 = 0; var11 < var7.size(); ++var11) {
                if ("1".equals(oConvertUtils.getString(((Map)var7.get(var11)).get("is_show")))) {
                    var12 = ((Map)var7.get(var11)).get("field_name").toString();
                    ExcelExportEntity var13 = new ExcelExportEntity(((Map)var7.get(var11)).get("field_txt").toString(), var12, 15);
                    Object var14 = ((Map)var7.get(var11)).get("dict_code");
                    JSONArray var15 = JSONObject.parseArray(JSONObject.toJSONString(var9));
                    List var16 = this.a(oConvertUtils.getString(var14), var15, var12);
                    if (var16 != null && var16.size() > 0) {
                        ArrayList var17 = new ArrayList();
                        Iterator var18 = var16.iterator();

                        while(var18.hasNext()) {
                            DictModel var19 = (DictModel)var18.next();
                            var17.add(var19.getText() + "_" + var19.getValue());
                        }

                        var13.setReplace((String[])var17.toArray(new String[var17.size()]));
                    }

                    Object var36 = ((Map)var7.get(var11)).get("replace_val");
                    if (oConvertUtils.isNotEmpty(var36)) {
                        var13.setReplace(var36.toString().split(","));
                    }

                    var32.add(var13);
                }
            }

            var3.setContentType("application/vnd.ms-excel");
            ServletOutputStream var33 = null;

            try {
                var12 = BrowserUtils.checkBrowse(var2);
                if ("MSIE".equalsIgnoreCase(var12.substring(0, 4))) {
                    var3.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(var4, "UTF-8") + ".xls");
                } else {
                    String var34 = new String(var4.getBytes("UTF-8"), "ISO8859-1");
                    var3.setHeader("content-disposition", "attachment;filename=" + var34 + ".xls");
                }

                Workbook var35 = ExcelExportUtil.exportExcel(new ExportParams((String)null, var5), var32, var9);
                var33 = var3.getOutputStream();
                var35.write(var33);
            } catch (Exception var29) {
            } finally {
                try {
                    var33.flush();
                    var33.close();
                } catch (Exception var28) {
                }

            }

        }
    }

    @GetMapping({"/getRpColumns/{code}"})
    public Result<?> d(@PathVariable("code") String var1) {
        LambdaQueryWrapper<zdOnlCgreportHead> var2 = new LambdaQueryWrapper();
        var2.eq(zdOnlCgreportHead::getCode, var1);
        zdOnlCgreportHead var3 = (zdOnlCgreportHead)this.onlCgreportHeadService.getOne(var2);
        if (var3 == null) {
            return Result.error("实体不存在");
        } else {
            QueryWrapper var4 = new QueryWrapper();
            var4.eq("cgrhead_id", var3.getId());
            var4.eq("is_show", 1);
            var4.orderByAsc("order_num");
            List var5 = this.onlCgreportItemService.list(var4);
            ArrayList var6 = new ArrayList();
            HashMap var7 = new HashMap(1024);

            HashMap var10;
            for(Iterator var8 = var5.iterator(); var8.hasNext(); var6.add(var10)) {
                zdOnlCgreportItem var9 = (zdOnlCgreportItem)var8.next();
                var10 = new HashMap(3);
                var10.put("title", var9.getFieldTxt());
                var10.put("dataIndex", var9.getFieldName());
                var10.put("align", "center");
                String var11 = var9.getFieldType();
                if ("Integer".equals(var11) || "Date".equals(var11) || "Long".equals(var11)) {
                    var10.put("sorter", "true");
                }

                String var12 = var9.getDictCode();
                if (oConvertUtils.isNotEmpty(var12)) {
                    List var13 = this.a(var9.getDictCode(), (JSONArray)null, (String)null);
                    var7.put(var9.getFieldName(), var13);
                    var10.put("customRender", var9.getFieldName());
                }
            }

            HashMap var14 = new HashMap(1);
            var14.put("columns", var6);
            var14.put("dictOptions", var7);
            var14.put("cgRpConfigId", var3.getId());
            var14.put("cgRpConfigName", var3.getName());
            return Result.ok(var14);
        }
    }

    @PostMapping({"/testConnection"})
    public Result a(@RequestBody DynamicDataSourceModel var1) {
        Connection var2 = null;

        Result var3;
        try {
            Result var4;
            try {
                Class.forName(var1.getDbDriver());
                var2 = DriverManager.getConnection(var1.getDbUrl(), var1.getDbUsername(), var1.getDbPassword());
                if (var2 != null) {
                    var3 = Result.ok("数据库连接成功");
                    return var3;
                }

                var3 = Result.ok("数据库连接失败：错误未知");
            } catch (ClassNotFoundException var17) {
                a.error(var17.toString());
                var4 = Result.error("数据库连接失败：驱动类不存在");
                return var4;
            } catch (Exception var18) {
                a.error(var18.toString());
                var4 = Result.error("数据库连接失败：" + var18.getMessage());
                return var4;
            }
        } finally {
            try {
                if (var2 != null && !var2.isClosed()) {
                    var2.close();
                }
            } catch (SQLException var16) {
                a.error(var16.toString());
            }

        }

        return var3;
    }
}
