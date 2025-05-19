package com.base.modules.bi.service.impl;

import cn.hutool.core.util.ReUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.modules.baseUtil.SqlUtil;
import com.base.modules.baseUtil.SqlUtils;
import com.base.modules.bi.entity.zdOnlCgreportHead;
import com.base.modules.bi.entity.zdOnlCgreportItem;
import com.base.modules.bi.entity.zdOnlCgreportParam;
import com.base.modules.bi.mapper.zdOnlCgreportHeadMapper;
import com.base.modules.bi.mapper.zdOnlCgreportItemMapper;
import com.base.modules.bi.mapper.zdOnlCgreportParamMapper;
import com.base.modules.bi.model.zdOnlCgreportModel;
import com.base.modules.bi.service.IzdOnlCgreportHeadService;
import com.base.modules.bi.service.IzdOnlCgreportItemService;
import com.base.modules.bi.service.IzdOnlCgreportParamService;
import com.base.modules.biconf.biapi.dto.ObjectUtil;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.DynamicDataSourceModel;
import org.jeecg.common.util.dynamic.db.DataSourceCachePool;
import org.jeecg.common.util.dynamic.db.DynamicDBUtil;
import org.jeecg.common.util.oConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jeecg.modules.online.config.b.d;

/**
 * @Description: onl_cgreport_head
 * @Author: wms-cloud
 * @Date: 2020-12-09
 * @Version: V1.0
 */
@Service
public class ZdOnlCgreportHeadServiceImpl extends ServiceImpl<com.base.modules.bi.mapper.zdOnlCgreportHeadMapper, zdOnlCgreportHead> implements IzdOnlCgreportHeadService {
    private static final Logger a = LoggerFactory.getLogger(ZdOnlCgreportHeadServiceImpl.class);
    @Autowired
    private zdOnlCgreportHeadMapper zdOnlCgreportHeadMapper;
    @Autowired
    private com.base.modules.bi.mapper.zdOnlCgreportParamMapper zdOnlCgreportParamMapper;
    @Autowired
    private com.base.modules.bi.mapper.zdOnlCgreportItemMapper zdOnlCgreportItemMapper;

    @Autowired
    private zdOnlCgreportHeadMapper mapper;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    private IzdOnlCgreportParamService onlCgreportParamService;
    @Autowired
    private IzdOnlCgreportItemService onlCgreportItemService;

    @Override
    @Transactional
    public void saveMain(zdOnlCgreportHead zdOnlCgreportHead, List<zdOnlCgreportParam> zdOnlCgreportParamList, List<zdOnlCgreportItem> zdOnlCgreportItemList) {
        zdOnlCgreportHeadMapper.insert(zdOnlCgreportHead);
        if (zdOnlCgreportParamList != null && zdOnlCgreportParamList.size() > 0) {
            for (zdOnlCgreportParam entity : zdOnlCgreportParamList) {
                //外键设置
                entity.setCgrheadId(zdOnlCgreportHead.getId());
                zdOnlCgreportParamMapper.insert(entity);
            }
        }
        if (zdOnlCgreportItemList != null && zdOnlCgreportItemList.size() > 0) {
            for (zdOnlCgreportItem entity : zdOnlCgreportItemList) {
                //外键设置
                entity.setCgrheadId(zdOnlCgreportHead.getId());
                zdOnlCgreportItemMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void updateMain(zdOnlCgreportHead zdOnlCgreportHead, List<zdOnlCgreportParam> zdOnlCgreportParamList, List<zdOnlCgreportItem> zdOnlCgreportItemList) {
        zdOnlCgreportHeadMapper.updateById(zdOnlCgreportHead);

        //1.先删除子表数据
        zdOnlCgreportParamMapper.deleteByMainId(zdOnlCgreportHead.getId());
        zdOnlCgreportItemMapper.deleteByMainId(zdOnlCgreportHead.getId());

        //2.子表数据重新插入
        if (zdOnlCgreportParamList != null && zdOnlCgreportParamList.size() > 0) {
            for (zdOnlCgreportParam entity : zdOnlCgreportParamList) {
                //外键设置
                entity.setCgrheadId(zdOnlCgreportHead.getId());
                zdOnlCgreportParamMapper.insert(entity);
            }
        }
        if (zdOnlCgreportItemList != null && zdOnlCgreportItemList.size() > 0) {
            for (zdOnlCgreportItem entity : zdOnlCgreportItemList) {
                //外键设置
                entity.setCgrheadId(zdOnlCgreportHead.getId());
                zdOnlCgreportItemMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void delMain(String id) {
        zdOnlCgreportParamMapper.deleteByMainId(id);
        zdOnlCgreportItemMapper.deleteByMainId(id);
        zdOnlCgreportHeadMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            zdOnlCgreportParamMapper.deleteByMainId(id.toString());
            zdOnlCgreportItemMapper.deleteByMainId(id.toString());
            zdOnlCgreportHeadMapper.deleteById(id);
        }
    }

    @Override
    public Object BigScreenBiById(String Id) {
        //根据Id查询sql语句
        zdOnlCgreportHead zdOnlCgreportHead = zdOnlCgreportHeadMapper.selectById(Id);
        if (ObjectUtil.isEmpty(zdOnlCgreportHead)) {
            return "";
        }
        String sql = zdOnlCgreportHead.getCgrSql();
        List<Map<String, Object>> ob = zdOnlCgreportHeadMapper.executeQuery(sql);
        if (ob.size() == 0) {
            if (!"超期物料列表".equals(zdOnlCgreportHead.getName())) {
                return new HashMap<>(1024);
            } else {
                return new ArrayList<>();
            }
        }
        if (!"超期物料列表".equals(zdOnlCgreportHead.getName())) {
            return ob.get(0);
        }
        return ob;
    }

    @Override
    public Object BigScreenBiHistogram(String Id) {
        //根据Id查询sql语句
        zdOnlCgreportHead zdOnlCgreportHead = zdOnlCgreportHeadMapper.selectById(Id);
        if (ObjectUtil.isEmpty(zdOnlCgreportHead)) {
            return "";
        }
        String sql = zdOnlCgreportHead.getCgrSql();
        List<Map<String, Object>> ob = zdOnlCgreportHeadMapper.executeQuery(sql);
        Map<String, Object> map = new HashMap<>(1024);
        List<String> tape = new ArrayList<>();
        List<String> value = new ArrayList<>();
        Map<String, Object> map2 = new HashMap<>(1024);
        List<Map<String, Object>> list = new ArrayList<>();
        if (ob.size() > 0) {
            for (Map<String, Object> map1 : ob) {
                tape.add(map1.get("type").toString());
                value.add(map1.get("value").toString());
            }
            map2.put("data", value);
            list.add(map2);
        }
        map.put("categories", tape);
        map.put("series", list);
        return map;
    }

    @Override
    public String queryBiData(String id) {
        zdOnlCgreportHead zdOnlCgreportHead = zdOnlCgreportHeadMapper.selectById(id);
        if (ObjectUtil.isEmpty(zdOnlCgreportHead)) {
            return "";
        }
        String sql = zdOnlCgreportHead.getCgrSql();
        List<Map<String, Object>> res = zdOnlCgreportHeadMapper.executeQuery(sql);

        return JSON.toJSONString(res);
    }

    @Override
    public String queryOne(String id) {
        zdOnlCgreportHead zdOnlCgreportHead = zdOnlCgreportHeadMapper.selectById(id);
        if (ObjectUtil.isEmpty(zdOnlCgreportHead)) {
            return "";
        }
        String sql = zdOnlCgreportHead.getCgrSql();
        List<Map<String, Object>> res = zdOnlCgreportHeadMapper.executeQuery(sql);

        return JSON.toJSONString(res.get(0));
    }

    @Override
    public Map<String, Object> executeSelectSql(String sql, String onlCgreportHeadId, Map<String, Object> params) throws SQLException {
        String var4 = this.sysBaseAPI.getDatabaseType();
        LambdaQueryWrapper<zdOnlCgreportParam> var5 = new LambdaQueryWrapper();
        var5.eq(zdOnlCgreportParam::getCgrheadId, onlCgreportHeadId);
        List var6 = this.onlCgreportParamService.list(var5);
        zdOnlCgreportParam var8;
        String var10;
        if (var6 != null && var6.size() > 0) {
            for(Iterator var7 = var6.iterator(); var7.hasNext(); sql = sql.replace("${" + var8.getParamName() + "}", var10)) {
                var8 = (zdOnlCgreportParam)var7.next();
                Object var9 = params.get("self_" + var8.getParamName());
                var10 = "";
                if (var9 != null) {
                    var10 = var9.toString();
                } else if (var9 == null && oConvertUtils.isNotEmpty(var8.getParamValue())) {
                    var10 = var8.getParamValue();
                }
            }
        }

        HashMap var18 = new HashMap(1024);
        Integer var19 = oConvertUtils.getInt(params.get("pageSize"), 10);
        Integer var20 = oConvertUtils.getInt(params.get("pageNo"), 1);
        Page var21 = new Page((long)var20, (long)var19);
        LambdaQueryWrapper<zdOnlCgreportItem> var11 = new LambdaQueryWrapper();
        var11.eq(zdOnlCgreportItem::getCgrheadId, onlCgreportHeadId);
        var11.eq(zdOnlCgreportItem::getIsSearch, 1);
        List var12 = this.onlCgreportItemService.list(var11);
        String var13 = "jeecg_rp_temp.";
        String var14 = org.jeecg.modules.online.cgreport.util.a.a(var12, params, var13,null);
        if (ReUtil.contains(" order\\s+by ", sql.toLowerCase()) && "SQLSERVER".equalsIgnoreCase(var4)) {
            throw new JeecgBootException("SqlServer不支持SQL内排序!");
        } else {
            String var15 = "select * from (" + sql + ") jeecg_rp_temp  where 1=1 " + var14;
            var15 = SqlUtil.b(var15);
            Object var16 = params.get("column");
            if (var16 != null) {
                var15 = var15 + " order by jeecg_rp_temp." + var16.toString() + " " + params.get("order").toString();
            }

            a.info("报表查询sql=>\r\n" + var15);
            IPage var17 = this.mapper.selectPageBySql(var21, var15);
            var18.put("total", var17.getTotal());
            var18.put("records", org.jeecg.modules.online.cgform.util.b.d(var17.getRecords()));
            return var18;
        }
    }
    @Override
    public Map<String, Object> executeSelectSqlDynamic(String dbKey, String sql, Map<String, Object> params, String onlCgreportHeadId) {
        DynamicDataSourceModel var5 = DataSourceCachePool.getCacheDynamicDataSourceModel(dbKey);
        String var6 = (String)params.get("order");
        String var7 = (String)params.get("column");
        int var8 = oConvertUtils.getInt(params.get("pageNo"), 1);
        int var9 = oConvertUtils.getInt(params.get("pageSize"), 10);
        a.info("【Online多数据源逻辑】报表查询参数params: " + JSON.toJSONString(params));
        LambdaQueryWrapper<zdOnlCgreportParam> var10 = new LambdaQueryWrapper();
        var10.eq(zdOnlCgreportParam::getCgrheadId, onlCgreportHeadId);
        List var11 = this.onlCgreportParamService.list(var10);
        zdOnlCgreportParam var13;
        String var15;
        if (var11 != null && var11.size() > 0) {
            for(Iterator var12 = var11.iterator(); var12.hasNext(); sql = sql.replace("${" + var13.getParamName() + "}", var15)) {
                var13 = (zdOnlCgreportParam)var12.next();
                Object var14 = params.get("self_" + var13.getParamName());
                var15 = "";
                if (var14 != null) {
                    var15 = var14.toString();
                } else if (var14 == null && oConvertUtils.isNotEmpty(var13.getParamValue())) {
                    var15 = var13.getParamValue();
                }
            }
        }

        LambdaQueryWrapper<zdOnlCgreportItem> var23 = new LambdaQueryWrapper();
        var23.eq(zdOnlCgreportItem::getCgrheadId, onlCgreportHeadId);
        var23.eq(zdOnlCgreportItem::getIsSearch, 1);
        List var24 = this.onlCgreportItemService.list(var23);
        if (ReUtil.contains(" order\\s+by ", sql.toLowerCase()) && "3".equalsIgnoreCase(var5.getDbType())) {
            throw new JeecgBootException("SqlServer不支持SQL内排序!");
        } else {
            String var25 = "jeecg_rp_temp.";
            var15 = org.jeecg.modules.online.cgreport.util.a.a(var24, params, var25,null);
            String var16 = "select * from (" + sql + ") jeecg_rp_temp  where 1=1 " + var15;
            var16 = SqlUtil.b(var16);
            String var17 = SqlUtils.getCountSql(var16);
            Object var18 = params.get("column");
            if (var18 != null) {
                var16 = var16 + " order by jeecg_rp_temp." + var18.toString() + " " + params.get("order").toString();
            }

            String var19 = SqlUtils.createPageSqlByDBType(var5.getDbType(), var16, var8, var9);
            a.info("多数据源 报表查询sql=>querySql: " + var16);
            a.info("多数据源 报表查询sql=>pageSQL: " + var19);
            a.info("多数据源 报表查询sql=>countSql: " + var17);
            HashMap var20 = new HashMap(1024);
            Map var21 = (Map) DynamicDBUtil.findOne(dbKey, var17, new Object[0]);
            var20.put("total", var21.get("total"));
            List var22 = DynamicDBUtil.findList(dbKey, var19, new Object[0]);
            var20.put("records", org.jeecg.modules.online.cgform.util.b.d(var22));
            return var20;
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    @Override
    public Result<?> editAll(zdOnlCgreportModel values) {
        zdOnlCgreportHead var2 = values.getHead();
        zdOnlCgreportHead var3 = (zdOnlCgreportHead)super.getById(var2.getId());
        if (var3 == null) {
            return Result.error("未找到对应实体");
        } else {
            super.updateById(var2);
            LambdaQueryWrapper<zdOnlCgreportItem> var4 = new LambdaQueryWrapper();
            var4.eq(zdOnlCgreportItem::getCgrheadId, var2.getId());
            this.onlCgreportItemService.remove(var4);
            LambdaQueryWrapper<zdOnlCgreportParam> var5 = new LambdaQueryWrapper();
            var5.eq(zdOnlCgreportParam::getCgrheadId, var2.getId());
            this.onlCgreportParamService.remove(var5);
            Iterator var6 = values.getParams().iterator();

            while(var6.hasNext()) {
                zdOnlCgreportParam var7 = (zdOnlCgreportParam)var6.next();
                var7.setCgrheadId(var2.getId());
            }

            var6 = values.getItems().iterator();

            while(var6.hasNext()) {
                zdOnlCgreportItem var8 = (zdOnlCgreportItem)var6.next();
                var8.setFieldName(var8.getFieldName().trim().toLowerCase());
                var8.setCgrheadId(var2.getId());
            }

            this.onlCgreportItemService.saveBatch(values.getItems());
            this.onlCgreportParamService.saveBatch(values.getParams());
            return Result.ok("全部修改成功");
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    @Override
    public Result<?> delete(String id) {
        boolean var2 = super.removeById(id);
        if (var2) {
            LambdaQueryWrapper<zdOnlCgreportItem> var3 = new LambdaQueryWrapper();
            var3.eq(zdOnlCgreportItem::getCgrheadId, id);
            this.onlCgreportItemService.remove(var3);
            LambdaQueryWrapper<zdOnlCgreportParam> var4 = new LambdaQueryWrapper();
            var4.eq(zdOnlCgreportParam::getCgrheadId, id);
            this.onlCgreportParamService.remove(var4);
        }

        return Result.ok("删除成功");
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    @Override
    public Result<?> bathDelete(String[] ids) {
        String[] var2 = ids;
        int var3 = ids.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String var5 = var2[var4];
            boolean var6 = super.removeById(var5);
            if (var6) {
                LambdaQueryWrapper<zdOnlCgreportItem> var7 = new LambdaQueryWrapper();
                var7.eq(zdOnlCgreportItem::getCgrheadId, var5);
                this.onlCgreportItemService.remove(var7);
                LambdaQueryWrapper<zdOnlCgreportParam> var8 = new LambdaQueryWrapper();
                var8.eq(zdOnlCgreportParam::getCgrheadId, var5);
                this.onlCgreportParamService.remove(var8);
            }
        }

        return Result.ok("删除成功");
    }
    @Override
    public List<String> getSqlFields(String sql, String dbKey) throws SQLException {
        List var3 = null;
        if (StringUtils.isNotBlank(dbKey)) {
            var3 = this.a(sql, dbKey);
        } else {
            var3 = this.a(sql, (String)null);
        }

        return var3;
    }
    @Override
    public List<String> getSqlParams(String sql) {
        if (oConvertUtils.isEmpty(sql)) {
            return null;
        } else {
            ArrayList var2 = new ArrayList();
            String var3 = "\\$\\{\\w+\\}";
            Pattern var4 = Pattern.compile(var3);
            Matcher var5 = var4.matcher(sql);

            while(var5.find()) {
                String var6 = var5.group();
                var2.add(var6.substring(var6.indexOf("{") + 1, var6.indexOf("}")));
            }

            return var2;
        }
    }

    private List<String> a(String var1, String var2) throws SQLException {
        if (oConvertUtils.isEmpty(var1)) {
            return null;
        } else {
            var1 = var1.trim();
            if (var1.endsWith(";")) {
                var1 = var1.substring(0, var1.length() - 1);
            }

            var1 = QueryGenerator.convertSystemVariables(var1);
            var1 = SqlUtil.a(var1);
            Set var3;
            if (StringUtils.isNotBlank(var2)) {
                a.info("parse sql : " + var1);
                DynamicDataSourceModel var4 = DataSourceCachePool.getCacheDynamicDataSourceModel(var2);
                if (ReUtil.contains(" order\\s+by ", var1.toLowerCase()) && "3".equalsIgnoreCase(var4.getDbType())) {
                    throw new JeecgBootException("SqlServer不支持SQL内排序!");
                }

                if ("1".equals(var4.getDbType())) {
                    var1 = "SELECT * FROM (" + var1 + ") temp LIMIT 1";
                } else if ("2".equals(var4.getDbType())) {
                    var1 = "SELECT * FROM (" + var1 + ") temp WHERE ROWNUM <= 1";
                } else if ("3".equals(var4.getDbType())) {
                    var1 = "SELECT TOP 1 * FROM (" + var1 + ") temp";
                }

                a.info("parse sql with page : " + var1);
                Map var5 = (Map)DynamicDBUtil.findOne(var2, var1, new Object[0]);
                if (var5 == null) {
                    throw new JeecgBootException("该报表sql没有数据");
                }

                var3 = var5.keySet();
            } else {
                a.info("parse sql: " + var1);
                if (ReUtil.contains(" order\\s+by ", var1.toLowerCase()) && "SQLSERVER".equalsIgnoreCase(this.sysBaseAPI.getDatabaseType())) {
                    throw new JeecgBootException("SqlServer不支持SQL内排序!");
                }

                IPage var6 = this.mapper.selectPageBySql(new Page(1L, 1L), var1);
                List var7 = var6.getRecords();
                if (var7.size() < 1) {
                    throw new JeecgBootException("该报表sql没有数据");
                }

                var3 = ((Map)var7.get(0)).keySet();
            }

            if (var3 != null) {
                var3.remove("ROW_ID");
            }

            return new ArrayList(var3);
        }
    }
    @Override
    public Map<String, Object> queryCgReportConfig(String reportId) {
        HashMap var2 = new HashMap(0);
        Map var3 = this.mapper.queryCgReportMainConfig(reportId);
        List var4 = this.mapper.queryCgReportItems(reportId);
        List var5 = this.mapper.queryCgReportParams(reportId);
        if (d.a()) {
            var2.put("main", org.jeecg.modules.online.cgform.util.b.b(var3));
            var2.put("items", org.jeecg.modules.online.cgform.util.b.d(var4));
        } else {
            var2.put("main", var3);
            var2.put("items", var4);
        }

        var2.put("params", var5);
        return var2;
    }
    @Override
    public List<Map<?, ?>> queryByCgReportSql(String sql, Map params, Map paramData, int pageNo, int pageSize) {
        String var6 = SqlUtil.a(sql, params);
        List var7 = null;
        if (paramData != null && paramData.size() == 0) {
            paramData = null;
        }

        if (pageNo == -1 && pageSize == -1) {
            var7 = this.mapper.executeSelete(var6);
        } else {
            Page var8 = new Page((long)pageNo, (long)pageSize);
            IPage var9 = this.mapper.selectPageBySql(var8, var6);
            if (var9.getRecords() != null && var9.getRecords().size() > 0) {
                var7.addAll(var9.getRecords());
            }
        }

        return var7;
    }

}
