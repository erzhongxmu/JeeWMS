package com.base.modules.bi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.modules.bi.entity.zdOnlCgreportHead;
import com.base.modules.bi.entity.zdOnlCgreportItem;
import com.base.modules.bi.entity.zdOnlCgreportParam;
import com.base.modules.bi.model.zdOnlCgreportModel;
import org.jeecg.common.api.vo.Result;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description: onl_cgreport_head
 * @Author: wms-cloud
 * @Date: 2020-12-09
 * @Version: V1.0
 */
public interface IzdOnlCgreportHeadService extends IService<zdOnlCgreportHead> {

    /**
     * 添加一对多
     */
    public void saveMain(zdOnlCgreportHead zdOnlCgreportHead, List<zdOnlCgreportParam> zdOnlCgreportParamList, List<zdOnlCgreportItem> zdOnlCgreportItemList);

    /**
     * 修改一对多
     */
    public void updateMain(zdOnlCgreportHead zdOnlCgreportHead, List<zdOnlCgreportParam> zdOnlCgreportParamList, List<zdOnlCgreportItem> zdOnlCgreportItemList);

    /**
     * 删除一对多
     */
    public void delMain(String id);

    /**
     * 批量删除一对多
     */
    public void delBatchMain(Collection<? extends Serializable> idList);


    public Object BigScreenBiById(String Id);

    public Object BigScreenBiHistogram(String Id);

    String queryBiData(String id);

    String queryOne(String id);

    Result<?> editAll(zdOnlCgreportModel var1);

    Result<?> delete(String var1);

    Result<?> bathDelete(String[] var1);

    Map<String, Object> executeSelectSql(String var1, String var2, Map<String, Object> var3) throws SQLException;

    Map<String, Object> executeSelectSqlDynamic(String var1, String var2, Map<String, Object> var3, String var4);

    List<String> getSqlFields(String var1, String var2) throws SQLException;

    List<String> getSqlParams(String var1);

    Map<String, Object> queryCgReportConfig(String var1);

    List<Map<?, ?>> queryByCgReportSql(String var1, Map var2, Map var3, int var4, int var5);

}
