package com.base.modules.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.baseUtil.StringUtil;
import com.base.modules.bi.entity.zdOnlCgreportHead;
import com.base.modules.bi.entity.zdOnlCgreportItem;
import com.base.modules.bi.mapper.zdOnlCgreportHeadMapper;
import com.base.modules.bi.service.IzdOnlCgreportHeadService;
import com.base.modules.bi.service.IzdOnlCgreportItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgreport.util.CgReportSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Api(tags = "报表管理")
@RestController
@RequestMapping("/sys/reportApi")
@Slf4j
public class ApiReportControler {
    private static final Logger a = LoggerFactory.getLogger(org.jeecg.modules.online.cgreport.a.a.class);

    @Autowired
    private ISysBaseAPI sysBaseAPI;

    @Autowired
    private IzdOnlCgreportHeadService onlCgreportHeadService;


    @Autowired
    private zdOnlCgreportHeadMapper zdOnlCgreportHeadMapper;

    //获取的字段
    @Autowired
    private IzdOnlCgreportItemService onlCgreportItemService;


    @AutoLog(value = "报表管理")
    @ApiOperation(value = "报表管理", notes = "报表管理")
    @GetMapping(value = "/getlist")
    public Result<?> getlist(@RequestParam(name = "id", required = false) String id,
                             @RequestParam(name = "tenantId", required = false) String  tenantId,
                             @RequestParam(name = "pageNo", required = false) Integer pageNo,
                             @RequestParam(name = "pageSize", required = false)  Integer pageSize,
                             HttpServletRequest req) {
        synchronized (this) {

            Result var4 = this.b(id, req);
            HashMap var18 = new HashMap(1024);

            Page page = new Page(pageNo, pageSize);
            zdOnlCgreportHead cgreportHead = onlCgreportHeadService.getById(id);
            String cgrSql = cgreportHead.getCgrSql();
            //报表配置集合
            List<zdOnlCgreportItem> cgreportConditions = onlCgreportItemService.lambdaQuery()
                    .eq(zdOnlCgreportItem::getCgrheadId, cgreportHead.getId())
                    .eq(zdOnlCgreportItem::getIsShow, "1")
                    .list();
            List<Map> mapList = new ArrayList<>();

            IPage var17 = null;
            try {
                var17 = zdOnlCgreportHeadMapper.selectPageBySql(page, cgrSql);
                List<Map<String, Object>> maps = zdOnlCgreportHeadMapper.executeQuery(cgrSql);
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<Map> mapList1 = new ArrayList<>();
            List<Map<String, Object>> maps = zdOnlCgreportHeadMapper.executeQuery(cgrSql);
            for (Map<String, Object> map : maps) {
                if (StringUtils.isNotBlank(tenantId) ){
                    if (map.containsKey("tenant_id")){
                        Integer tenant_id = (Integer) map.get("tenant_id");
                        String s = tenant_id.toString();
                        if (tenantId.equals(s)){
                            mapList1.add(map);
                        }
                    }
                }else {
                    mapList1.add(map);
                }
            }
            /*List<Map> records = var17.getRecords();
            List<Map> recorders = new ArrayList<>();
            for (Map record : records) {
                if (record.containsKey("tenant_id")){
                    Integer tenant_id = (Integer) record.get("tenant_id");
                    String s = tenant_id.toString();
                    if (tenantId.equals(s)){
                        recorders.add(record);
                    }
                }
            }

            var17.setRecords(recorders);*/
            var17.setRecords(mapList1);
            var17.setSize(mapList1.size());
            var4.setResult(var17);

            JSONObject var5 = JSON.parseObject(JSONObject.toJSONString(var4.getResult()));
            JSONArray var6 = var5.getJSONArray("records");

            HashMap var9 = new HashMap(1024);

            for (zdOnlCgreportItem cgreportCondition : cgreportConditions) {
                HashMap var1 = new HashMap(1024);
                var1.put("align","center");
                var1.put("dataIndex",cgreportCondition.getFieldName());
                var1.put("fieldType",cgreportCondition.getFieldType());
//                var1.put("sorter","false");
                var1.put("title",cgreportCondition.getFieldTxt());

                String var15 = cgreportCondition.getDictCode();
                List var19 = this.a(var15, var6, cgreportCondition.getFieldName());
                if (var19 != null) {
                    var9.put(cgreportCondition.getFieldName(), var19);
                    var1.put("customRender", cgreportCondition.getFieldName());
                }
                mapList.add(var1);
            }
            var18.put("cgreportHeadName", cgreportHead.getName());
            var18.put("data", var4.getResult());
            var18.put("columns", mapList);
            var18.put("dictOptions", var9);
            return Result.ok(var18);
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

}
