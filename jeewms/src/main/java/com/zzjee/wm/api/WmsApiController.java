package com.zzjee.wm.api;

import com.zzjee.ba.entity.BaGoodsCategoryEntity;
import com.zzjee.ba.entity.BaGoodsTypeEntity;
import com.zzjee.ba.service.BaGoodsCategoryServiceI;
import com.zzjee.ba.service.BaGoodsTypeServiceI;
import com.zzjee.md.entity.*;
import com.zzjee.md.service.MdBinServiceI;
import com.zzjee.md.service.MdCusServiceI;
import com.zzjee.md.service.MdGoodsServiceI;
import com.zzjee.md.service.MdSupServiceI;
import com.zzjee.wm.entity.*;
import com.zzjee.wm.page.WmImNoticeHPage;
import com.zzjee.wm.page.WmOmNoticeHPage;
import com.zzjee.wm.service.WmImNoticeHServiceI;
import com.zzjee.wm.service.WmOmNoticeHServiceI;
import com.zzjee.wmutil.wmUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.DataGridReturn;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.jwt.util.JwtUtils;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.sms.util.Constants;
import org.jeecgframework.web.system.sms.util.TuiSongMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package com.zzjee.wm.controller
 * @date 2021/7/22 13:39
 * @description
 */
@RestController
@RequestMapping("/wmsApiController")
public class WmsApiController {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(WmsApiController.class);

    @Autowired
    private MdGoodsServiceI mdGoodsService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private MdCusServiceI mdCusService;
    @Autowired
    private MdBinServiceI mdBinService;
    @Autowired
    private WmImNoticeHServiceI wmImNoticeHService;
    @Autowired
    private WmOmNoticeHServiceI wmOmNoticeHService;
    @Autowired
    private BaGoodsTypeServiceI baGoodsTypeService;
    @Autowired
    private BaGoodsCategoryServiceI baGoodsCategoryService;
    @Autowired
    private MdSupServiceI mdSupServiceI;

    @RequestMapping(params = "getToken")
    @ResponseBody
    public ResponseMessage test(HttpServletRequest request, HttpServletResponse response){

        Map<String,Object> data = new HashMap<>();

        data.put("token", JwtUtils.buildJWT("wmsAccount"));

        return Result.success(data);
    }

    /**
     * 获取商品
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "goodsList")
    @ResponseBody
    public ResponseMessage<DataGridReturn> datagrid(MdGoodsEntity mdGoods, HttpServletRequest request,
                                                    HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(MdGoodsEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
                mdGoods, request.getParameterMap());

        if(StringUtil.isNotEmpty(mdGoods.getSuoShuKeHu())){
            cq.eq("suoShuKeHu", mdGoods.getSuoShuKeHu());
        }
        if(StringUtil.isNotEmpty(mdGoods.getShpBianMakh())){
            cq.eq("shpBianMakh", mdGoods.getShpBianMakh());
        }


//		if(mdGoods.getZhuangTai()==null){
//			cq.notEq("zhuangTai", "Y");
//		}
        cq.add();
       return Result.success(this.mdGoodsService.getDataGridReturn(cq, true));
    }

    /**
     * 添加商品信息
     *
     * @return
     */
    @RequestMapping(params = "goodsDoAdd")
    @ResponseBody
    public AjaxJson doAdd(MdGoodsEntity mdGoods, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "商品信息添加成功";
        try {
            MdGoodsEntity mdGoods1 = systemService.findUniqueByProperty(
                    MdGoodsEntity.class, "sku", mdGoods.getSku());
            if (StringUtils.isEmpty(mdGoods.getCategoryCode())) {
                j.setSuccess(false);
                j.setMsg("类目编码为空");
                return j;
            }

            if(mdGoods1 ==null ){
                Map<String, Object> countMap = systemService.findOneForJdbc("select right(shp_bian_ma,7) shp_bian_ma  from md_goods where category_code =? and suo_shu_ke_hu  = ? and shp_bian_ma like ? ORDER BY shp_bian_ma desc LIMIT 1",mdGoods.getCategoryCode(),mdGoods.getSuoShuKeHu(),mdGoods.getSuoShuKeHu()+mdGoods.getCategoryCode()+"%");
                if (countMap == null) {
                    mdGoods.setShpBianMa(mdGoods.getSuoShuKeHu()+mdGoods.getCategoryCode()+String.format("%07d", 1));
                }else {
                    Object goodsCode = countMap.get("shp_bian_ma");
                    if (goodsCode != null) {
                        mdGoods.setShpBianMa(mdGoods.getSuoShuKeHu()+mdGoods.getCategoryCode()+String.format("%07d",Integer.parseInt(((String) goodsCode))+1));
                    }else {
                        mdGoods.setShpBianMa(mdGoods.getSuoShuKeHu()+mdGoods.getCategoryCode()+String.format("%07d", 1));
                    }
                }
                if(StringUtil.isEmpty(mdGoods.getChlKongZhi()) ){
                    mdGoods.setChlKongZhi("N");
                }
                if("N".equals(mdGoods.getChlKongZhi() )){
                    mdGoods.setChlShl("1");
                    mdGoods.setJshDanWei(mdGoods.getShlDanWei());

                }

                try {
                    if(StringUtil.isEmpty(mdGoods.getZhlKgm())){
                        if(!StringUtil.isEmpty(mdGoods.getBzhiQi())){
                            int bzhiq = Integer.parseInt(mdGoods.getBzhiQi());
                            mdGoods.setZhlKgm(Integer.toString(bzhiq));
                        }

                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                mdGoodsService.save(mdGoods);
                systemService.addLog(message, Globals.Log_Type_INSERT,
                        Globals.Log_Leavel_INFO);
                j.setObj(mdGoods);
            }else{
                try {
                    if(StringUtil.isEmpty(mdGoods.getZhlKgm())){
                        if(!StringUtil.isEmpty(mdGoods.getBzhiQi())){
                            int bzhiq = Integer.parseInt(mdGoods.getBzhiQi());
                            mdGoods.setZhlKgm(Integer.toString(bzhiq));
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // TODO: handle exception
                }
                MyBeanUtils.copyBeanNotNull2Bean(mdGoods, mdGoods1);
                mdGoodsService.updateEntitie(mdGoods1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = "商品信息添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新商品信息
     *
     * @return
     */
    @RequestMapping(params = "goodsDoUpdate")
    @ResponseBody
    public AjaxJson doUpdate(MdGoodsEntity mdGoods, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "商品信息更新成功";
        MdGoodsEntity t = mdGoodsService.get(MdGoodsEntity.class,
                mdGoods.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(mdGoods, t);
            mdGoodsService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE,
                    Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "商品信息更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 客户信息查询
     * @param mdCus
     * @param request
     * @param response
     * @param dataGrid
     * @return
     */
    @RequestMapping(params = "cusList")
    @ResponseBody
    public ResponseMessage<DataGridReturn> datagrid(MdCusEntity mdCus, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(MdCusEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, mdCus, request.getParameterMap());
        try{
            //自定义追加查询条件
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("zhuJiMa", "desc");
        cq.setOrder(map1);
        cq.add();
        return Result.success(this.mdCusService.getDataGridReturn(cq, true));
    }

    /**
     * 添加客户
     *
     * @return
     */
    @RequestMapping(params = "cusDoAdd")
    @ResponseBody
    public AjaxJson doAdd(MdCusEntity mdCus, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "客户添加成功";
        try{
            MdCusEntity mdcus1 = systemService.findUniqueByProperty(MdCusEntity.class, "keHuBianMa", mdCus.getKeHuBianMa());
            if(mdcus1==null){
                mdCusService.save(mdCus);
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            }else{
                message = "客户编码已经存在";
                j.setSuccess(false);
            }

        }catch(Exception e){
            e.printStackTrace();
            message = "客户添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新客户
     *
     * @return
     */
    @RequestMapping(params = "cusDoUpdate")
    @ResponseBody
    public AjaxJson doUpdate(MdCusEntity mdCus, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "客户更新成功";
        MdCusEntity t = mdCusService.get(MdCusEntity.class, mdCus.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(mdCus, t);
            mdCusService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "客户更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 获取储位信息
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "binList")
    @ResponseBody
    public ResponseMessage<DataGridReturn> datagrid(MdBinEntity mdBin, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(MdBinEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, mdBin, request.getParameterMap());
        try{
            //自定义追加查询条件
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        return Result.success(this.mdBinService.getDataGridReturn(cq, true));
    }

    /**
     * 添加仓位定义
     *
     * @return
     */
    @RequestMapping(params = "binDoAdd")
    @ResponseBody
    public AjaxJson doAdd(MdBinEntity mdBin, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "仓位定义添加成功";
        try{

            MdBinEntity mdb = null;
            List<MdBinEntity> mdblist =	systemService.findByProperty(MdBinEntity.class, "kuWeiBianMa", mdBin.getKuWeiBianMa());
            for (MdBinEntity t:mdblist){
                if(t.getBinStore().equals(mdBin.getBinStore())){
                    mdb = t;
                }
            }

//		    MdBinEntity mdBin1 = systemService.findUniqueByProperty(MdBinEntity.class, "kuWeiBianMa", mdBin.getKuWeiBianMa());
            if(mdb ==null ){
                mdBinService.save(mdBin);
                j.setObj(mdBin);
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            }else{
                message = "库位编码或者库位条码已经存在";
                j.setSuccess(false);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "仓位定义添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加进货通知抬头
     *
     * @param
     * @return
     */
    @RequestMapping(params = "imNoticeHDoAdd")
    @ResponseBody
    public AjaxJson doAdd(WmImNoticeHEntity wmImNoticeH,
                          WmImNoticeHPage wmImNoticeHPage, HttpServletRequest request) {
        List<WmImNoticeIEntity> wmImNoticeIList = wmImNoticeHPage
                .getWmImNoticeIList();
        AjaxJson j = new AjaxJson();
        String message = "进货通知添加成功";
        try {

            String noticeid =  wmUtil.getNextNoticeid(wmImNoticeH.getOrderTypeCode()) ;
            wmImNoticeH.setNoticeId(noticeid);
            wmImNoticeH.setCreateDate(DateUtils.getDate());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", wmImNoticeH.getNoticeId());
            wmImNoticeH.setImSta(Constants.wm_sta0);
            //获取供应商
            if(StringUtil.isNotEmpty(wmImNoticeH.getSupCode())){
                try{
                    MdSupEntity mdSupEntity = systemService.findUniqueByProperty(MdSupEntity.class,"gysBianMa",wmImNoticeH.getSupCode());
                    wmImNoticeH.setSupName(mdSupEntity.getZhongWenQch());
                }catch (Exception e){
                }
            }
            //
            List<WmImNoticeIEntity> wmImNoticeIListnew = new ArrayList<WmImNoticeIEntity>();
            for (WmImNoticeIEntity wmImNoticeIEntity : wmImNoticeIList) {
                if(!StringUtil.isEmpty(wmImNoticeIEntity.getGoodsCode())){
                    try {
                        MvGoodsEntity mvgoods = systemService.findUniqueByProperty(MvGoodsEntity.class,"goodsId",wmImNoticeIEntity.getGoodsCode());
//					String date[]=wmImNoticeIEntity.getGoodsCode().split("-");

                        long hiti = 0;
                        try {
                            if(StringUtil.isEmpty(wmImNoticeIEntity.getBinPlan())){
                                hiti = Long.parseLong(wmImNoticeIEntity.getGoodsCount())/ ( Long.parseLong(mvgoods.getMpCengGao()) * Long.parseLong(mvgoods.getMpDanCeng()) * Long.parseLong(mvgoods.getChlShl()));
                                wmImNoticeIEntity.setBinPlan(Long.toString(hiti));
                            }
                        } catch (Exception e) {
                        }

                        wmImNoticeIEntity.setGoodsCode(mvgoods.getGoodsCode());
                        wmImNoticeIEntity.setGoodsName(mvgoods.getShpMingCheng());
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    wmImNoticeIEntity.setBinPre("I");
                    wmImNoticeIListnew.add(wmImNoticeIEntity);
                }
            }
            wmImNoticeHService.addMain(wmImNoticeH, wmImNoticeIListnew);
            try {
                TuiSongMsgUtil.sendMessage("收货通知", Constants.SMS_SEND_TYPE_3,
                        "RKYYTZ", map, "admin", ResourceUtil.getSessionUserName()
                                .getUserName());
            } catch (Exception e) {
                // TODO: handle exception
            }

            systemService.addLog(message, Globals.Log_Type_INSERT,
                    Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "进货通知添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 删除进货通知抬头
     *
     * @return
     */
    @RequestMapping(params = "imNoticeHDoDel")
    @ResponseBody
    public AjaxJson doDel(WmImNoticeHEntity wmImNoticeH,
                          HttpServletRequest request) {
        boolean  deltrue = true;
        if(!"database".equals(ResourceUtil.getConfigByName("sys.del"))){
            deltrue=false;
        }
        AjaxJson j = new AjaxJson();
        wmImNoticeH = systemService.getEntity(WmImNoticeHEntity.class,
                wmImNoticeH.getId());
        String message = "进货通知删除成功";
        try {
            WmPlatIoEntity wmPlatIo = systemService.findUniqueByProperty(
                    WmPlatIoEntity.class, "docId", wmImNoticeH.getNoticeId());// 删除月台计划
            if (wmPlatIo != null) {
                systemService.delete(wmPlatIo);
            }
            wmImNoticeH.setImSta(Constants.wm_sta3);
            if(deltrue){
                wmImNoticeHService.delete(wmImNoticeH);

            }else{
                wmImNoticeHService.saveOrUpdate(wmImNoticeH);

            }
            systemService.addLog(message, Globals.Log_Type_DEL,
                    Globals.Log_Leavel_INFO);
            Object id0 = wmImNoticeH.getNoticeId();
            // ===================================================================================
            // 查询-进货通知明细

            if(wmImNoticeH.getOrderTypeCode().equals("04")){
                String 	tsql = "delete  from wm_in_qm_i where im_notice_id = ?";
                systemService.executeSql(tsql, wmImNoticeH.getNoticeId());
            }
            String hql0 = "from WmImNoticeIEntity where 1 = 1 AND iM_NOTICE_ID = ? ";
            try {
                List<WmImNoticeIEntity> wmImNoticeIEntityList = systemService
                        .findHql(hql0, id0);
                for (WmImNoticeIEntity wmImNoticeIEntity : wmImNoticeIEntityList) {
                    wmImNoticeIEntity.setBinPre("Y");
                    if(deltrue){
                        systemService.delete(wmImNoticeIEntity);

                    }else{
                        systemService.updateEntitie(wmImNoticeIEntity);

                    }
                }
            }catch (Exception e) {

            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "进货通知删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加出货通知
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "omNoticeHDoAdd")
    @ResponseBody
    public AjaxJson doAdd(WmOmNoticeHEntity wmOmNoticeH, WmOmNoticeHPage wmOmNoticeHPage, HttpServletRequest request) {
        List<WmOmNoticeIEntity> wmOmNoticeIList =  wmOmNoticeHPage.getWmOmNoticeIList();
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        try{
            String noticeid = wmUtil.getNextomNoticeId(wmOmNoticeH.getOrderTypeCode());
            WmPlatIoEntity wmPlatIo = new WmPlatIoEntity();
            wmPlatIo.setCarno(wmOmNoticeH.getReCarno());
            wmPlatIo.setDocId(noticeid);
            wmPlatIo.setPlanIndata(wmOmNoticeH.getDelvData());
            wmPlatIo.setPlatId(wmOmNoticeH.getOmPlatNo());
            wmPlatIo.setPlatSta(Constants.wm_sta1);
            wmPlatIo.setPlatBeizhu("司机:" + wmOmNoticeH.getReMember() + "电话:"
                    + wmOmNoticeH.getReMobile());
            wmPlatIo.setCreateDate(DateUtils.getDate());
            systemService.save(wmPlatIo);
            wmOmNoticeH.setOmNoticeId(noticeid);
            wmOmNoticeH.setOmSta(Constants.wm_sta1);
            wmOmNoticeH.setCreateDate(DateUtils.getDate());
            if(wmOmNoticeH.getCusCode()==null){
                if(StringUtil.isNotEmpty(wmUtil.getCusCode())){
                    wmOmNoticeH.setCusCode(wmUtil.getCusCode());
                }
            }


            List<WmOmNoticeIEntity> wmomNoticeIListnew = new ArrayList<WmOmNoticeIEntity>();
            for (WmOmNoticeIEntity wmomNoticeIEntity : wmOmNoticeIList) {
                if(!StringUtil.isEmpty(wmomNoticeIEntity.getGoodsId())){
                    try {


                        MvGoodsEntity mvgoods = systemService.findUniqueByProperty(MvGoodsEntity.class,"goodsId",wmomNoticeIEntity.getGoodsId());

//					String date[]=wmImNoticeIEntity.getGoodsCode().split("-");
//						wmImNoticeIEntity.setGoodsCode(mvgoods.getGoodsCode());
//						wmImNoticeIEntity.setGoodsName(mvgoods.getShpMingCheng());
//						String date[]=wmomNoticeIEntity.getGoodsId().split("-");
                        wmomNoticeIEntity.setGoodsId(mvgoods.getGoodsCode());
                        wmomNoticeIEntity.setGoodsName(mvgoods.getShpMingCheng());
                    } catch (Exception e) {
                        // TODO: handle exception
                        logger.error(ExceptionUtil.getExceptionMessage(e));
                    }
                    wmomNoticeIListnew.add(wmomNoticeIEntity);
                }
            }
            if(StringUtil.isNotEmpty( wmOmNoticeH.getOcusCode())){
//				String datecuso[]= wmOmNoticeH.getOcusCode().split("-");
                MdCusOtherEntity mdcusother = systemService.findUniqueByProperty(MdCusOtherEntity.class, "keHuBianMa",wmOmNoticeH.getOcusCode());
                if (mdcusother != null) {
                    wmOmNoticeH.setOcusCode(wmOmNoticeH.getOcusCode());
                    wmOmNoticeH.setOcusName(mdcusother.getZhongWenQch());
                }
            }
            wmOmNoticeHService.addMain(wmOmNoticeH, wmomNoticeIListnew);



            Map<String ,Object> map = new HashMap<String ,Object>();
            map.put("id", wmOmNoticeH.getOmNoticeId());
            try {
                TuiSongMsgUtil.sendMessage("出货通知", Constants.SMS_SEND_TYPE_3, "CKYYTZ", map, "admin", ResourceUtil.getSessionUserName().getUserName());
            } catch (Exception e) {
                // TODO: handle exception
                logger.error(ExceptionUtil.getExceptionMessage(e));
            }
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }catch(Exception e){
            e.printStackTrace();
            message = "出货通知添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 删除出货通知
     *
     * @return
     */
    @RequestMapping(params = "omNoticeHDoDel")
    @ResponseBody
    public AjaxJson doDel(WmOmNoticeHEntity wmOmNoticeH, HttpServletRequest request) {
        String deltype = ResourceUtil.getConfigByName("del.type");

        AjaxJson j = new AjaxJson();
        wmOmNoticeH = systemService.getEntity(WmOmNoticeHEntity.class, wmOmNoticeH.getId());
        String message = "出货通知删除成功";
        try{
            wmOmNoticeH.setOmSta("已删除");
            Object id0 = wmOmNoticeH.getOmNoticeId();
            //===================================================================================
            //1.查询出数据库的明细数据-出货商品明细
            String hql0 = "from WmOmNoticeIEntity where 1 = 1 AND oM_NOTICE_ID = ? ";
            List<WmOmNoticeIEntity> wmOmNoticeIOldList = systemService.findHql(hql0,id0);
            for (WmOmNoticeIEntity wmOmNoticeIEntity : wmOmNoticeIOldList) {
                wmOmNoticeIEntity.setOmSta("已删除");
                wmOmNoticeIEntity.setPlanSta("Y");
                if("database".equals(deltype)){
                    systemService.delete(wmOmNoticeIEntity);

                }else{
                    systemService.saveOrUpdate(wmOmNoticeIEntity);

                }
            }
            if("database".equals(deltype)){
                wmOmNoticeHService.delete(wmOmNoticeH);
            }else{
                wmOmNoticeHService.saveOrUpdate(wmOmNoticeH);

            }
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        }catch(Exception e){
            e.printStackTrace();
            message = "出货通知删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params = "imNoticeHList")
    @ResponseBody
    public ResponseMessage<DataGridReturn> datagrid(WmImNoticeHEntity wmImNoticeH,
                                                    HttpServletRequest request, HttpServletResponse response,
                                                    DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(WmImNoticeHEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
                wmImNoticeH);
        try {
            // 自定义追加查询条件
            String query_imData_begin = request.getParameter("imData_begin1");
            String query_imData_end = request.getParameter("imData_end2");

            if (StringUtil.isNotEmpty(query_imData_begin)) {
                cq.ge("imData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                        .parse(query_imData_begin));
            }
            if (StringUtil.isNotEmpty(query_imData_end)) {
                cq.le("imData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                        .parse(query_imData_end));
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        if(StringUtil.isNotEmpty(wmUtil.getCusCode())){
            cq.eq("cusCode", wmUtil.getCusCode());
        }
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("createDate", "desc");
        cq.setOrder(map1);
        cq.add();
        return Result.success(this.wmImNoticeHService.getDataGridReturn(cq, true));
    }


    @RequestMapping(params = "omNoticeHList")
    @ResponseBody
    public ResponseMessage<DataGridReturn> datagrid(WmOmNoticeHEntity wmOmNoticeH, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(WmOmNoticeHEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmOmNoticeH);

        try {
            // 自定义追加查询条件
            String query_imData_begin = request.getParameter("delvData_begin1");
            String query_imData_end = request.getParameter("delvData_end2");

            if (StringUtil.isNotEmpty(query_imData_begin)) {
                cq.ge("delvData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                        .parse(query_imData_begin));
            }
            if (StringUtil.isNotEmpty(query_imData_end)) {
                cq.le("delvData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                        .parse(query_imData_end));
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        if(StringUtil.isNotEmpty(wmUtil.getCusCode())){
            cq.eq("cusCode", wmUtil.getCusCode());

        }
        if (wmOmNoticeH.getOmSta() == null) {
            cq.notEq("omSta", Constants.wm_sta4);
        }
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("createDate", "desc");
        cq.setOrder(map1);
        cq.add();
        return Result.success(this.wmOmNoticeHService.getDataGridReturn(cq, true));
    }

    @RequestMapping(params = "getStock")
    @ResponseBody
    public ResponseMessage<List<Map<String, Object>>> getStock (String sku) {
        List<Map<String,Object>> data = systemService.findForJdbc("SELECT goods_id,shp_ming_cheng goods_name,sku,base_goodscount,goods_unit from wv_stock where base_goodscount <> 0 and sku = ? GROUP BY goods_id",sku);

        return Result.success(data);
    }

    @RequestMapping(params = "goodsTypeList")
    @ResponseBody
    public void goodsTypeList(BaGoodsTypeEntity baGoodsType, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(BaGoodsTypeEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, baGoodsType, request.getParameterMap());
        try{
            //自定义追加查询条件
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.baGoodsTypeService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 添加产品属性
     *
     * @param baGoodsType
     * @return
     */
    @RequestMapping(params = "addGoodsType")
    @ResponseBody
    public AjaxJson addGoodsType(BaGoodsTypeEntity baGoodsType, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "产品属性添加成功";
        try{
            //查询编码是否重复
            BaGoodsTypeEntity goodsType = systemService.findUniqueByProperty(
                    BaGoodsTypeEntity.class, "goodsTypeCode", baGoodsType.getGoodsTypeCode());
            if (goodsType != null ) {
                j.setSuccess(false);
                j.setMsg("编码重复");
                return j;
            }
            baGoodsTypeService.save(baGoodsType);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }catch(Exception e){
            e.printStackTrace();
            message = "产品属性添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goodsCategoryList")
    public void datagrid(BaGoodsCategoryEntity baGoodsCategory, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(BaGoodsCategoryEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, baGoodsCategory, request.getParameterMap());
        try{
            //自定义追加查询条件
            String query_createTime_begin = request.getParameter("createTime_begin");
            String query_createTime_end = request.getParameter("createTime_end");
            if(StringUtil.isNotEmpty(query_createTime_begin)){
                cq.ge("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createTime_begin));
            }
            if(StringUtil.isNotEmpty(query_createTime_end)){
                cq.le("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createTime_end));
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.baGoodsCategoryService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 添加商品类目
     *
     * @param baGoodsCategory
     * @return
     */
    @RequestMapping(params = "addGoodsCategory")
    @ResponseBody
    public AjaxJson doAdd(BaGoodsCategoryEntity baGoodsCategory, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "商品类目添加成功";
        try{
            baGoodsCategoryService.save(baGoodsCategory);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }catch(Exception e){
            e.printStackTrace();
            message = "商品类目添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 供应商信息查询
     * @param mdSup
     * @param request
     * @param response
     * @param dataGrid
     * @return
     */
    @RequestMapping(params = "supList")
    @ResponseBody
    public ResponseMessage<DataGridReturn> datagrid(MdSupEntity mdSup, HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(MdSupEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
                mdSup, request.getParameterMap());
        try {
            // 自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        return Result.success(this.mdSupServiceI.getDataGridReturn(cq, true));
    }
    /**
     * 添加供应商信息
     *
     * @param mdSup
     * @return
     */
    @RequestMapping(params = "supDoAdd")
    @ResponseBody
    public AjaxJson doAdd(MdSupEntity mdSup, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "供应商添加成功";
        try {
            MdSupEntity mdSup1 = systemService.findUniqueByProperty(
                    MdSupEntity.class, "gysBianMa", mdSup.getGysBianMa());
            if (mdSup1 == null) {
                mdSupServiceI.save(mdSup);
                systemService.addLog(message, Globals.Log_Type_INSERT,
                        Globals.Log_Leavel_INFO);
            } else {
                j.setSuccess(false);
                message = "供应商编码已经存在";
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = "供应商添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新供应商信息
     *
     * @param mdSup
     * @return
     */
    @RequestMapping(params = "supDoUpdate")
    @ResponseBody
    public AjaxJson doUpdate(MdSupEntity mdSup, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "供应商更新成功";
        MdSupEntity t = mdSupServiceI.get(MdSupEntity.class, mdSup.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(mdSup, t);
            mdSupServiceI.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE,
                    Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "供应商更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
}
