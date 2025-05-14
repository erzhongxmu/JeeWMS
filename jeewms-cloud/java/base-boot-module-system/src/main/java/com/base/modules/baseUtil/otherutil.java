package com.base.modules.baseUtil;

 import org.apache.commons.collections.map.HashedMap;
 import org.apache.commons.lang.StringUtils;
 import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
 import org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService;
 import org.jeecgframework.core.util.ApplicationContextUtil;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class otherutil {
    public  String  getBione(String configId)  {
        String  result = "0";
        try{
          result =   this.getdatars(configId,null,null).get(0).getFxjx1();
        }catch (Exception e){

        }
        return  result;
    }
    public List<FxjoutputEntity>   getbilist(String configId) {

        return  this.getdatars(configId,null,null);
    }

    public  List<FxjoutputEntity> getdatars(String configId,String page,String rows) {
        IOnlCgreportHeadService cgReportService = ApplicationContextUtil.getContext().getBean(IOnlCgreportHeadService.class);
        OnlCgreportHead  var3 = cgReportService.getById(configId);
        String var4 = var3.getCgrSql().trim();
        String var5 = var3.getDbSource();
        Map<String, Object> var7 = new HashedMap();
        try {
            Map<String, Object> var6 = new HashedMap();
            var6.put("getAll",null);
            var6.put("pageNo","1");
            var6.put("pageSize","99999");
            var6.put("_t","");
            var6.put("order","desc");

            if (StringUtils.isNotBlank(var5)) {
                var7 = cgReportService.executeSelectSqlDynamic(var5, var4, var6, var3.getId());
            } else {


                var7 = cgReportService.executeSelectSql(var4, var3.getId(), var6);
            }
        }catch (Exception e){

        }
//
//        //step.1 根据id获取该动态报表的配置参数
//        Map<String, Object> cgReportMap = null;
//        cgReportMap = cgReportService.queryCgReportConfig(configId);
//        //step.2 获取该配置的查询SQL
//        Map configM = (Map) cgReportMap.get(CgReportConstant.MAIN);
//        //报表 - 查询SQL
//        String querySql = (String) configM.get(CgReportConstant.CONFIG_SQL);
//        //step.4 进行查询返回结果
//        int p = page == null ? 1 : Integer.parseInt(page);
//        int r = rows == null ? 99999 : Integer.parseInt(rows);
//
//        String dbKey = (String) configM.get("db_source");
//        List<Map<String, Object>> result = null;
//        Map params = new HashMap(1024);
//        Map paramData = new HashMap(1024);
//        if (StringUtils.isNotBlank(dbKey)) {
//            result = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySql, params, p, r));
//
//        } else {
//            result = cgReportService.queryByCgReportSql(querySql, params, paramData, p, r);
//        }
        List<Map<String,Object>> result = (List<Map<String,Object>>)var7.get("records");
        List<FxjoutputEntity>  reslist =  this.convertMapListToBeanList(result,FxjoutputEntity.class);
        return  reslist;
    }

    /**
     * 将一个map组成的list转成实体类bean组成的list
     * @param mapList 存了map对象的list
     * @param clazz 需要将这些map转成哪个实体类对象
     * @return
     */
    public <T> List<T> convertMapListToBeanList(List<Map<String,Object>> mapList,Class<T> clazz){
        List<T> list=new ArrayList<T>();
        try{
            for(Map map:mapList){
                try {
                    T obj=clazz.newInstance();//创建bean的实例对象
                    int i=0;
                    for(Object o:map.keySet()){//遍历map的key
                        for(Method m:clazz.getMethods()){//遍历bean的类中的方法，找到set方法进行赋值
                            if(m.getName().toLowerCase().equals("set"+o.toString().toLowerCase())){
                                try{
                                    m.invoke(obj, map.get(o).toString());

                                }catch (Exception e){

                                }

                            }
                        }
                    }
                    list.add(obj);
                }catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }catch (Exception e){

        }

        return list;
    }


}


