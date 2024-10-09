package com.zzjee.wm.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.zzjee.rfid.entity.RfidBuseEntity;
import com.zzjee.rfid.service.RfidBuseServiceI;
import com.zzjee.wm.entity.*;
import com.zzjee.wm.page.confrowpage;
import com.zzjee.wm.page.wminqmpage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.zzjee.api.ResultDO;
import com.zzjee.md.entity.MdCusEntity;
import com.zzjee.md.entity.MdGoodsEntity;
import com.zzjee.md.entity.MvGoodsEntity;
import com.zzjee.wm.service.WmInQmIServiceI;
import com.zzjee.wmutil.wmUtil;

import static com.xiaoleilu.hutool.date.DateTime.now;

/**
 * @author erzhongxmu
 * @version V1.0
 * @Title: Controller
 * @Description: 批量收货
 * @date 2017-08-20 19:48:00
 */
@Controller
@RequestMapping("/wmInQmIController")
public class WmInQmIController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(WmInQmIController.class);

    @Autowired
    private WmInQmIServiceI wmInQmIService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;
    @Autowired
    private RfidBuseServiceI rfidBuseService;

    /**
     * 批量收货列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/zzjee/wm/wmInQmIList");
    }

    @RequestMapping(params = "tlist")
    public ModelAndView tlist(HttpServletRequest request) {
        return new ModelAndView("com/zzjee/wm/wmIntQmIList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(WmInQmIEntity wmInQmI, HttpServletRequest request,
                         HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(WmInQmIEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
                wmInQmI, request.getParameterMap());
        try {
            // 自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("imNoticeId", "desc");
        cq.setOrder(map1);
        if (wmInQmI.getBinSta() == null) {
            cq.eq("binSta", "N");
        }
//		cq.like("imNoticeId", "RK%");
        cq.add();

        this.wmInQmIService.getDataGridReturn(cq, true);
        List<WmInQmIEntity> resultold = dataGrid.getResults();
        List<WmInQmIEntity> resultnew = new ArrayList<>();
        for (WmInQmIEntity t : resultold) {
            if (StringUtil.isEmpty(t.getGoodsName())) {
                try {
                    MvGoodsEntity goods = systemService.findUniqueByProperty(MvGoodsEntity.class, "goodsCode", t.getGoodsId());
                    if (goods != null) {
                        t.setGoodsName(goods.getGoodsName());
                    }
                } catch (Exception e) {

                }

            }

            resultnew.add(t);
        }
        dataGrid.setResults(resultnew);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "datagridt")
    public void datagridt(WmInQmIEntity wmInQmI, HttpServletRequest request,
                          HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(WmInQmIEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
                wmInQmI, request.getParameterMap());
        try {
            // 自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("imNoticeId", "desc");
        cq.setOrder(map1);
        if (wmInQmI.getBinSta() == null) {
            cq.eq("binSta", "N");
        }
        cq.like("imNoticeId", "TH%");
        cq.add();
        this.wmInQmIService.getDataGridReturn(cq, true);
        List<WmInQmIEntity> resultold = dataGrid.getResults();
        List<WmInQmIEntity> resultnew = new ArrayList<>();
        for (WmInQmIEntity t : resultold) {
            if (StringUtil.isEmpty(t.getGoodsName())) {
                try {
                    MvGoodsEntity goods = systemService.findUniqueByProperty(MvGoodsEntity.class, "goodsCode", t.getGoodsId());
                    if (goods != null) {
                        t.setGoodsName(goods.getGoodsName());
                    }
                } catch (Exception e) {

                }

            }

            resultnew.add(t);
        }
        dataGrid.setResults(resultnew);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除批量收货
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(WmInQmIEntity wmInQmI, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        wmInQmI = systemService.getEntity(WmInQmIEntity.class, wmInQmI.getId());
        message = "批量收货删除成功";
        try {
            wmInQmIService.delete(wmInQmI);
            systemService.addLog(message, Globals.Log_Type_DEL,
                    Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "批量收货删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "dotoup")
    @ResponseBody
    public AjaxJson dotoup(HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "上架成功";
        try {
            System.out.println(request.getParameter("id"));
            boolean isup = toup(request.getParameter("id"),"","","");
            if (!isup) {
                j.setSuccess(false);
                message = "上架失败";
                return j;

            }

            systemService.addLog(message, Globals.Log_Type_DEL,
                    Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            j.setSuccess(false);
            e.printStackTrace();
            message = "上架失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    public boolean toup(String id,String kuweiBianMa,String username,String realname) {
//		List<WmToUpGoodsEntity> wmToUpGoodsList = new ArrayList<WmToUpGoodsEntity>();
        String hql0 = "from WmInQmIEntity where binSta = 'N' and  id = ?";
        List<WmInQmIEntity> WmInQmIEntityList = systemService.findHql(hql0,
                id);// 获取行项目
        System.out.println(id + "111111");
        for (WmInQmIEntity wmInQmIEntity : WmInQmIEntityList) {
            System.out.println(wmInQmIEntity.getId() + "222222");

            try {
                WmToUpGoodsEntity wmToUpGoodsEntityold = systemService.findUniqueByProperty(WmToUpGoodsEntity.class, "orderIdI", wmInQmIEntity.getId());
                if (wmToUpGoodsEntityold != null) {
                    wmInQmIEntity.setBinSta("Y");
                    systemService.saveOrUpdate(wmInQmIEntity);
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(wmInQmIEntity.getId() + "33333");

            WmToUpGoodsEntity wmToUpGoodsEntity = new WmToUpGoodsEntity();
            if(!StringUtil.isEmpty(username)){
                wmToUpGoodsEntity.setCreateBy(username);
            }
            if(!StringUtil.isEmpty(realname)){
                wmToUpGoodsEntity.setCreateName(realname);
            }
            wmToUpGoodsEntity.setCreateDate(now());
            wmToUpGoodsEntity.setGoodsId(wmInQmIEntity.getGoodsId());
            wmToUpGoodsEntity.setGoodsProData(wmInQmIEntity.getProData());
            wmToUpGoodsEntity.setGoodsBatch(wmInQmIEntity.getGoodsBatch());
            wmToUpGoodsEntity.setGoodsQua(wmInQmIEntity.getQmOkQuat());
            wmToUpGoodsEntity.setGoodsUnit(wmInQmIEntity.getGoodsUnit());
            wmToUpGoodsEntity.setOrderIdI(wmInQmIEntity.getId());
            wmToUpGoodsEntity.setOrderId(wmInQmIEntity.getImNoticeId());
            wmToUpGoodsEntity.setBinId(wmInQmIEntity.getTinId());
            if(!StringUtil.isEmpty(kuweiBianMa)){
                wmToUpGoodsEntity.setKuWeiBianMa(kuweiBianMa);
            }else{
                wmToUpGoodsEntity.setKuWeiBianMa(wmInQmIEntity.getBinId());
            }

            wmToUpGoodsEntity.setCusCode(wmInQmIEntity.getCusCode());
            wmToUpGoodsEntity.setGoodsName(wmInQmIEntity.getGoodsName());
            wmToUpGoodsEntity.setActTypeCode("01");
            wmToUpGoodsEntity.setWmToUpId(wmInQmIEntity.getId());
//			String sql = "select     md.suo_shu_ke_hu as cuscode from    md_bin md  where    md.ku_wei_bian_ma = '"
//					+ wmInQmIEntity.getBinId() + "'";
//			Map<String, Object> binMap = systemService.findOneForJdbc(sql);
            System.out.println(wmInQmIEntity.getBinId() + "444444");

            if (!wmUtil.checkbin(wmToUpGoodsEntity.getKuWeiBianMa())) {
                throw new RuntimeException("储位不存在或已停用！");
            }
            System.out.println(wmInQmIEntity.getBinId() + "555555");

            try {

//				MvGoodsEntity mvgoods = new MvGoodsEntity();
                MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
                        MvGoodsEntity.class, "goodsCode",
                        wmToUpGoodsEntity.getGoodsId());
                wmToUpGoodsEntity.setBaseUnit(mvgoods.getBaseunit());
                wmToUpGoodsEntity.setGoodsUnit(mvgoods.getShlDanWei());

                if (!mvgoods.getBaseunit().equals(mvgoods.getShlDanWei())) {
                    try {
                        wmToUpGoodsEntity.setBaseGoodscount(String.valueOf(
                                Double.parseDouble(mvgoods.getChlShl())
                                        * Double.parseDouble(wmToUpGoodsEntity.getGoodsQua())));
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }

                } else {
                    wmToUpGoodsEntity.setBaseGoodscount(wmToUpGoodsEntity
                            .getGoodsQua());
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            wmInQmIEntity.setBinSta("Y");
            systemService.saveOrUpdate(wmInQmIEntity);
            //重复增加二次判断
            List<WmToUpGoodsEntity> listall = systemService.findByProperty(WmToUpGoodsEntity.class, "orderIdI", wmToUpGoodsEntity.getOrderIdI());
            if (listall != null && listall.size() > 0) {
                return true;
            }
            //重复增加二次判断

            systemService.save(wmToUpGoodsEntity);
            systemService.addLog("上架:" + wmInQmIEntity.getGoodsId(), Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        }
        return true;
    }

    /**
     * 批量删除批量收货
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "批量收货删除成功";
        try {
            for (String id : ids.split(",")) {
                WmInQmIEntity wmInQmI = systemService.getEntity(
                        WmInQmIEntity.class, id);
                wmInQmIService.delete(wmInQmI);
                systemService.addLog(message, Globals.Log_Type_DEL,
                        Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "批量收货删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doGethuozhu", method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public AjaxJson doGethuozhu(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        WmImNoticeHEntity wmImNoticeHEntity = systemService
                .findUniqueByProperty(WmImNoticeHEntity.class, "noticeId",
                        oConvertUtils.getString(request
                                .getParameter("noticeid")));
        if (wmImNoticeHEntity != null) {
            MdCusEntity md = systemService.findUniqueByProperty(
                    MdCusEntity.class, "keHuBianMa",
                    wmImNoticeHEntity.getCusCode());
            j.setObj(md);
        }

        return j;
    }

    @RequestMapping(params = "doGettext", method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public AjaxJson doGettext(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        MdGoodsEntity mdgoods = new MdGoodsEntity();
        MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
                MvGoodsEntity.class, "goodsCode",
                oConvertUtils.getString(request.getParameter("goodsid")));
        if (mvgoods == null) {
            j.setSuccess(false);
            j.setMsg("不存在此商品");
        } else {
            mdgoods.setChlShl("0");
            double goodsno = 0.00;
            System.out
                    .println("*******************8"
                            + oConvertUtils.getString(request
                            .getParameter("noticeid")));
            WmImNoticeHEntity wmImNoticeHEntity = systemService
                    .findUniqueByProperty(WmImNoticeHEntity.class, "noticeId",
                            oConvertUtils.getString(request
                                    .getParameter("noticeid")));
            String hql0 = "from WmImNoticeIEntity where 1 = 1 AND imNoticeId = ? and goodsCode = ? ";
            List<WmImNoticeIEntity> wmImNoticeIEntityList = systemService
                    .findHql(hql0, wmImNoticeHEntity.getNoticeId(),
                            oConvertUtils.getString(request
                                    .getParameter("goodsid")));// 获取行项目
            for (WmImNoticeIEntity wmImNoticeIEntity : wmImNoticeIEntityList) {
                goodsno = Double.parseDouble(wmImNoticeIEntity.getGoodsCount())
                        - Double.parseDouble(wmImNoticeIEntity.getGoodsQmCount());
                if (goodsno > 0) {
                    long quat1 = Long.parseLong(mvgoods.getMpCengGao()) * Long.parseLong(mvgoods.getMpDanCeng());
                    if (quat1 < goodsno) {
                        goodsno = quat1;
                    }
                    mdgoods.setChlShl(Double.toString(goodsno));
                    break;
                }
            }
            if (goodsno <= 0) {
                j.setSuccess(false);
                j.setMsg("已经批量收货完毕");
            }
        }
        mdgoods.setShpMingCheng(mvgoods.getGoodsName());
        j.setObj(mdgoods);
        return j;
    }

    /**
     * 添加批量收货
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(WmInQmIEntity wmInQmI) {
        String message = null;
        AjaxJson j = new AjaxJson();
        String flag = "N";
        message = "批量收货添加成功";
        try {
            if (Double.parseDouble(wmInQmI.getQmOkQuat()) <= 0) {
                j.setSuccess(false);
                message = "数量不能为0";
                j.setMsg(message);
                return j;
            }
        } catch (Exception e) {
            j.setSuccess(false);
            message = "数量 错误";
            j.setMsg(message);
            return j;
            // TODO: handle exception
        }
        if (StringUtil.isNotEmpty(wmInQmI.getBinId())) {
            if (!wmUtil.checkbin(wmInQmI.getBinId())) {
                j.setSuccess(false);
                message = wmInQmI.getBinId() + "储位不存在或已停用";
                j.setMsg(message);
                return j;
            }
        }
        try {
            //托盘占用判断
            if ("yes".equals(ResourceUtil.getConfigByName("usetuopan"))) {
                if (StringUtils.isEmpty(wmInQmI.getTinId())) {
                    throw new BusinessException("请填写托盘");
                }
            } else {
                if (StringUtil.isEmpty(wmInQmI.getTinId())) {
                    wmInQmI.setTinId(ResourceUtil.getConfigByName("tuopanma"));
                }
            }
            //托盘占用判断
            if(!wmUtil.checkys(wmInQmI.getGoodsId(),wmInQmI.getProData())){
                j.setSuccess(false);
                message = "超过允收期";
                j.setMsg(message);
                return j;
            }



            WmImNoticeHEntity wmImNoticeHEntity = systemService
                    .findUniqueByProperty(WmImNoticeHEntity.class, "noticeId",
                            wmInQmI.getImNoticeId());
            if (wmImNoticeHEntity != null) {
                wmInQmI.setCusCode(wmImNoticeHEntity.getCusCode());
                wmInQmI.setImCusCode(wmImNoticeHEntity.getImCusCode());

                flag = "X";


            } else {
                j.setSuccess(false);
                message = "收货通知不存在";
                j.setMsg(message);
                return j;
            }
            if (!flag.equals("X")) {
                j.setSuccess(false);
                message = "收货通知下此商品不存在或已经全部收货";
                j.setMsg(message);
                return j;
            }
            if (flag.equals("X")) {
                MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
                        MvGoodsEntity.class, "goodsCode", wmInQmI.getGoodsId());
                if (mvgoods != null) {
                    wmInQmI.setGoodsName(mvgoods.getGoodsName());
                    try {
                        wmInQmI.setTinTj(String.valueOf(Double.parseDouble(mvgoods
                                .getTiJiCm())
                                * Double.parseDouble(wmInQmI.getQmOkQuat())));
                        wmInQmI.setTinZhl(String.valueOf(Double.parseDouble(mvgoods
                                .getZhlKg())
                                * Double.parseDouble(wmInQmI.getQmOkQuat())));
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                    wmInQmI.setGoodsUnit(mvgoods.getShlDanWei());
                }

                String flagchsh = "y";

                WmImNoticeIEntity wmImNoticeIEntity = systemService.get(WmImNoticeIEntity.class, wmInQmI.getImNoticeItem());
                if (wmImNoticeIEntity != null) {
                    wmInQmI.setImQuat(wmImNoticeIEntity.getGoodsCount());
                    if ("n".equals(ResourceUtil.getConfigByName("chaoshou"))) {
                        Double weiq = Double.parseDouble(wmImNoticeIEntity
                                .getGoodsCount())
                                - Double.parseDouble(wmImNoticeIEntity
                                .getGoodsQmCount());
                        if (Double.parseDouble(wmInQmI.getQmOkQuat()) > weiq) {
                            flagchsh = "n";
                        }
                    }

                }


                if ("n".equals(flagchsh)) {
                    j.setSuccess(false);
                    message = "不允许超收";
                    j.setMsg(message);
                    return j;
                }

                Double GoodsQmCount = 0.00;
                GoodsQmCount = Double.parseDouble(wmImNoticeIEntity
                        .getGoodsQmCount()) + Double.parseDouble(wmInQmI.getQmOkQuat());
                wmImNoticeIEntity.setGoodsQmCount(GoodsQmCount.toString());
                systemService.updateEntitie(wmImNoticeIEntity);
                String id = wmInQmIService.save(wmInQmI).toString();
                if ("on".equals(ResourceUtil.getConfigByName("webonestepup")) && StringUtil.isNotEmpty(wmInQmI.getBinId())) {
                    toup(id,"","","");
                }
                systemService.addLog("批量收货" + wmInQmI.getGoodsId(), Globals.Log_Type_INSERT,
                        Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "批量收货添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        return j;
    }


    @RequestMapping(params = "saveRows")
    @ResponseBody
    public AjaxJson saveRows(wminqmpage page, HttpServletRequest request) {
        String message = null;
        List<WmImNoticeIEntity> demos = page.getWminqmrows();
        AjaxJson j = new AjaxJson();
        if (CollectionUtils.isNotEmpty(demos)) {
            for (WmImNoticeIEntity jeecgDemo : demos) {
                if (StringUtil.isNotEmpty(jeecgDemo.getGoodsWqmCount())) {
                    WmInQmIEntity wminqm = new WmInQmIEntity();
                    wminqm.setQmOkQuat(jeecgDemo.getGoodsWqmCount());
                    wminqm.setImNoticeItem(jeecgDemo.getId());
                    wminqm.setGoodsId(jeecgDemo.getGoodsCode());
                    wminqm.setProData(DateUtils.date2Str(jeecgDemo.getGoodsPrdData(), DateUtils.date_sdf));

                    wminqm.setImNoticeId(jeecgDemo.getImNoticeId());
                    wminqm.setGoodsName(jeecgDemo.getGoodsName());
                    wminqm.setBinId(jeecgDemo.getBinPlan());
                    wminqm.setTinId(jeecgDemo.getTinId());
                    wminqm.setGoodsUnit(jeecgDemo.getGoodsUnit());
                    wminqm.setGoodsBatch(jeecgDemo.getGoodsBatch());
                    if (StringUtil.isEmpty(wminqm.getGoodsBatch())) {
                        wminqm.setGoodsBatch(wminqm.getProData());
                    }
                    wminqm.setBinSta("N");
                    this.doAdd(wminqm);

                }
            }
        }
        return j;
    }


    /**
     * 更新批量收货
     *
     * @return
     */
    @RequestMapping(params = "dobatchUpdatedate")
    @ResponseBody
    public AjaxJson dobatchUpdatedate(WmInQmIEntity wmInQmI, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "批量收货更新成功";
        WmInQmIEntity t = wmInQmIService.get(WmInQmIEntity.class,
                request.getParameter("id"));

        String batchdate = request.getParameter("batchdate");
        if (StringUtil.isEmpty(batchdate)) {
            message = "日期不能为空";
            j.setSuccess(false);
        } else {
            t.setGoodsBatch(batchdate);
            t.setProData(batchdate);
            systemService.updateEntitie(t);
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 更新批量收货
     *
     * @return
     */
    @RequestMapping(params = "dobatchUpdate")
    @ResponseBody
    public AjaxJson dobatchUpdate(WmInQmIEntity wmInQmI, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "批量收货更新成功";
        WmInQmIEntity t = wmInQmIService.get(WmInQmIEntity.class,
                request.getParameter("id"));

        String binid = request.getParameter("binid");

        if (StringUtil.isNotEmpty(binid)) {
            if (!wmUtil.checkbin(binid)) {
                j.setSuccess(false);
                message = wmInQmI.getBinId() + "储位不存在";
                return j;
            }
        }


        if (StringUtil.isEmpty(binid)) {
            message = "储位不能为空";
            j.setSuccess(false);
        } else {

            t.setBinId(binid);
            systemService.updateEntitie(t);
//			}


        }
        j.setMsg(message);
        return j;
    }


    /**
     * 更新批量收货
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(WmInQmIEntity wmInQmI, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "批量收货更新成功";
        WmInQmIEntity t = wmInQmIService.get(WmInQmIEntity.class,
                wmInQmI.getId());
        try {

            if (StringUtil.isNotEmpty(wmInQmI.getBinId())) {
                if (!wmUtil.checkbin(wmInQmI.getBinId())) {
                    j.setSuccess(false);
                    message = wmInQmI.getBinId() + "储位不存在";
                    return j;
                }
            }

            MyBeanUtils.copyBeanNotNull2Bean(wmInQmI, t);
            t.setBaseUnit(null);
            wmInQmIService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE,
                    Globals.Log_Leavel_INFO);
//			}

        } catch (Exception e) {
            e.printStackTrace();
            message = "批量收货更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量收货新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(WmInQmIEntity wmInQmI, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(wmInQmI.getId())) {
            wmInQmI = wmInQmIService.getEntity(WmInQmIEntity.class,
                    wmInQmI.getId());
            req.setAttribute("wmInQmIPage", wmInQmI);
        }
        return new ModelAndView("com/zzjee/wm/wmInQmI-add");
    }


    @RequestMapping(params = "goAddBatch")
    public ModelAndView goAddBatch(WmInQmIEntity wmInQmI, HttpServletRequest req) {
        WmImNoticeIEntity WmImNoticeI = systemService.getEntity(WmImNoticeIEntity.class,
                req.getParameter("id").toString());
        wmInQmI.setImNoticeId(WmImNoticeI.getImNoticeId());
        wmInQmI.setGoodsId(WmImNoticeI.getGoodsCode());
        wmInQmI.setImNoticeItem(WmImNoticeI.getId());
        Long quat = (long) 0;
        Long quat1 = (long) 0;
        try {
            quat = Long.parseLong(WmImNoticeI.getGoodsCount()) - Long.parseLong(WmImNoticeI.getGoodsQmCount());

        } catch (Exception e) {
            // TODO: handle exception
        }

        if (ResourceUtil.getConfigByName("giwq").equals("no")) {
            MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
                    MvGoodsEntity.class, "goodsCode",
                    WmImNoticeI.getGoodsCode());
            if (mvgoods != null) {
                try {
                    quat1 = Long.parseLong(mvgoods.getMpCengGao()) * Long.parseLong(mvgoods.getMpDanCeng());
                    wmInQmI.setGoodsUnit(mvgoods.getShlDanWei());
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
            if (quat1 < quat) {
                quat = quat1;
            }
        }

        wmInQmI.setQmOkQuat(Long.toString(quat));
        wmInQmI.setTinId(wmUtil.gettuopanma());

        if (ResourceUtil.getConfigByName("sys.weight").equals("on")) {
            String rfidcontent = "";
            try{
                CriteriaQuery cq;
                cq = new CriteriaQuery(RfidBuseEntity.class);
                cq.eq("rfidType", "weight");
                cq.ge("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(DateUtils.getDate("yyyy-MM-dd")));
                cq.add();

                List<RfidBuseEntity> listByCriteriaQuery = rfidBuseService.getListByCriteriaQuery(cq, false);
                RfidBuseEntity  rfidBuseEntity = listByCriteriaQuery.get(listByCriteriaQuery.size()-1);
                rfidcontent = rfidBuseEntity.getRfidBusecont();
            }catch (Exception e){
                e.printStackTrace();
            }

            wmInQmI.setBaseInGoodscount(rfidcontent);
        }




        req.setAttribute("wmInQmIPage", wmInQmI);

        return new ModelAndView("com/zzjee/wm/wmInQmI-add");
    }

    /**
     * 批量收货编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(WmInQmIEntity wmInQmI, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(wmInQmI.getId())) {
            wmInQmI = wmInQmIService.getEntity(WmInQmIEntity.class,
                    wmInQmI.getId());

            if (ResourceUtil.getConfigByName("sys.weight").equals("on")&&StringUtil.isEmpty(wmInQmI.getBaseOutGoodscount())) {
                String rfidcontent = "";
                try{
                    CriteriaQuery cq;
                    cq = new CriteriaQuery(RfidBuseEntity.class);
                    cq.eq("rfidType", "weight");
                    cq.ge("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(DateUtils.getDate("yyyy-MM-dd")));
                    cq.add();

                    List<RfidBuseEntity> listByCriteriaQuery = rfidBuseService.getListByCriteriaQuery(cq, false);
                    RfidBuseEntity  rfidBuseEntity = listByCriteriaQuery.get(listByCriteriaQuery.size()-1);
                    rfidcontent = rfidBuseEntity.getRfidBusecont();
                }catch (Exception e){
                    e.printStackTrace();
                }
                wmInQmI.setBaseOutGoodscount(rfidcontent);
                if(StringUtil.isNotEmpty(wmInQmI.getBaseInGoodscount())&&StringUtil.isNotEmpty(wmInQmI.getBaseOutGoodscount())){
                    Double basecount = Double.parseDouble(wmInQmI.getBaseOutGoodscount()) -  Double.parseDouble(wmInQmI.getBaseInGoodscount());
                    String basecountStr  = String.format("%.2f", basecount);
//                    wmInQmI.setImQuat(basecountStr);
                    wmInQmI.setQmOkQuat(basecountStr);
                    wmInQmI.setBaseGoodscount(basecountStr);
                    wmInQmI.setTinZhl(basecountStr);
                }
            }
            req.setAttribute("wmInQmIPage", wmInQmI);
        }
        return new ModelAndView("com/zzjee/wm/wmInQmI-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "wmInQmIController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(WmInQmIEntity wmInQmI, HttpServletRequest request,
                            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(WmInQmIEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
                wmInQmI, request.getParameterMap());
        List<WmInQmIEntity> wmInQmIs = new ArrayList<WmInQmIEntity>();
        List<WmInQmIEntity> wmInQmIso = this.wmInQmIService
                .getListByCriteriaQuery(cq, false);
        for (WmInQmIEntity wmInQmIEntity : wmInQmIso) {
            try {
//		MvGoodsEntity mvgoods = new MvGoodsEntity();
                MvGoodsEntity mvgoods = systemService.findUniqueByProperty(MvGoodsEntity.class, "goodsCode", wmInQmIEntity.getGoodsId());
                wmInQmIEntity.setGoodsName(mvgoods.getShpMingCheng());
            } catch (Exception e) {
                // TODO: handle exception
            }
            try {
                MdCusEntity md = systemService.findUniqueByProperty(MdCusEntity.class, "keHuBianMa", wmInQmIEntity.getCusCode());

                wmInQmIEntity.setCusName(md.getZhongWenQch());
            } catch (Exception e) {
                // TODO: handle exception
            }
            wmInQmIs.add(wmInQmIEntity);
        }
        modelMap.put(NormalExcelConstants.FILE_NAME, "批量收货");
        modelMap.put(NormalExcelConstants.CLASS, WmInQmIEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("批量收货列表",
                "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, wmInQmIs);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(WmInQmIEntity wmInQmI,
                               HttpServletRequest request, HttpServletResponse response,
                               DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(NormalExcelConstants.FILE_NAME, "批量收货");
        modelMap.put(NormalExcelConstants.CLASS, WmInQmIEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("批量收货列表",
                "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, new ArrayList());
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    //	@SuppressWarnings("unchecked")
    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(HttpServletRequest request,
                                HttpServletResponse response) {
        AjaxJson j = new AjaxJson();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<WmInQmIEntity> listWmInQmIEntitys = ExcelImportUtil
                        .importExcel(file.getInputStream(),
                                WmInQmIEntity.class, params);
                for (WmInQmIEntity wmInQmI : listWmInQmIEntitys) {
                    wmInQmIService.save(wmInQmI);
                }
                j.setMsg("文件导入成功！");
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                logger.error(ExceptionUtil.getExceptionMessage(e));
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return j;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam(value = "username", required = false) String username,
                                  @RequestParam(value = "searchstr", required = false) String searchstr,
                                  @RequestParam(value = "searchstr2", required = false) String searchstr2) {
//		return listWvGis;


        ResultDO D0 = new ResultDO();
        String hql = " from WmInQmIEntity where 1 = 1 and binSta = 'N'  ";
        D0.setOK(true);
        if (!StringUtil.isEmpty(searchstr)) {
            hql = hql + "  and (imNoticeId like '%" + searchstr    + "%'  or imCusCode like '%" + searchstr + "%'"  + " or tin_id like '%" + searchstr + "%' )";
        }
        if (!StringUtil.isEmpty(searchstr2)) {
            try {
                String shpbianma = wmUtil.getmdgoodsbytiaoma(searchstr2);
                if (StringUtil.isNotEmpty(shpbianma)) {
                    searchstr2 = shpbianma;
                }
            } catch (Exception e) {

            }
            hql = hql + "  and goodsId like '%" + searchstr2 + "%'";
        }

        List<WmInQmIEntity> listWmInQmIs = wmInQmIService.findHql(hql);

        List<WmInQmIEntity> result = new ArrayList<WmInQmIEntity>();
        int i = 0;
        for (WmInQmIEntity t : listWmInQmIs) {

            i++;
            if (i > 100) {
                break;
            }
            result.add(t);
        }
        D0.setOK(true);
        D0.setObj(result);
        return new ResponseEntity(D0, HttpStatus.OK);

    }

    /**
     * pda收货
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        WmInQmIEntity task = wmInQmIService.get(WmInQmIEntity.class, id);
        if (task == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }


    @RequestMapping(value = "/update/qty", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateqty(@RequestParam(value = "username", required = false) String username,
                                       @RequestParam(value = "id", required = false) String id,
                                  @RequestParam(value = "qty1", required = false) String qty1,
                                  @RequestParam(value = "qty2", required = false) String qty2) {
//		return listWvGis;


        ResultDO D0 = new ResultDO();
        WmInQmIEntity wmInQmIEntity = systemService.get(WmInQmIEntity.class,id);
        if(StringUtil.isNotEmpty(qty1)){
            wmInQmIEntity.setBaseInGoodscount(qty1);
        }
        if(StringUtil.isNotEmpty(qty2)){
            wmInQmIEntity.setBaseOutGoodscount(qty2);
        }
        if(StringUtil.isNotEmpty(qty1)&&StringUtil.isNotEmpty(qty2)){
            try{
                double basegoods = Double.parseDouble(qty1) -Double.parseDouble(qty2);
                wmInQmIEntity.setBaseGoodscount(Double.toString(basegoods));
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        systemService.updateEntitie(wmInQmIEntity);
        D0.setOK(true);
        D0.setObj("操作成功");
        return new ResponseEntity(D0, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestParam String wmInQmIstr,
                                    UriComponentsBuilder uriBuilder) {
        ResultDO D0 = new ResultDO();
        WmInQmIEntity wmInQmI = (WmInQmIEntity) JSONHelper.json2Object(wmInQmIstr, WmInQmIEntity.class);
        // 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<WmInQmIEntity>> failures = validator
                .validate(wmInQmI);
        if (!failures.isEmpty()) {
            return new ResponseEntity(
                    BeanValidators.extractPropertyAndMessage(failures),
                    HttpStatus.BAD_REQUEST);
        }

        try {
            WmImNoticeIEntity wmImNoticeIEntity = systemService.get(WmImNoticeIEntity.class, wmInQmI.getImNoticeItem());
            if (wmImNoticeIEntity != null) {
                wmInQmI.setGoodsId(wmImNoticeIEntity.getGoodsCode());
                wmInQmI.setImNoticeId(wmImNoticeIEntity.getImNoticeId());
                wmInQmI.setGoodsUnit(wmImNoticeIEntity.getGoodsUnit());
                wmInQmI.setBaseUnit(wmImNoticeIEntity.getBaseUnit());
                wmInQmI.setGoodsBatch(wmInQmI.getProData());
                wmInQmI.setBaseGoodscount(wmInQmI.getQmOkQuat());
                wmInQmI.setBaseQmcount(wmInQmI.getQmOkQuat());
                wmInQmI.setImQuat(wmImNoticeIEntity.getGoodsCount());
                wmInQmI.setImNoticeItem(wmImNoticeIEntity.getId());
                 try {
                    WmImNoticeHEntity wmImNoticeHEntity = systemService.findUniqueByProperty(WmImNoticeHEntity.class, "noticeId", wmImNoticeIEntity.getImNoticeId());
                    wmInQmI.setCusCode(wmImNoticeHEntity.getCusCode());
                    wmInQmI.setImCusCode(wmImNoticeHEntity.getImCusCode());

                    if (StringUtil.isNotEmpty(wmInQmI.getCusCode())) {

                        MdCusEntity mdcus = systemService.findUniqueByProperty(MdCusEntity.class, "keHuBianMa", wmInQmI.getCusCode());
                        if (mdcus != null) {
                            wmInQmI.setCusName(mdcus.getZhongWenQch());
                        }
                    }
                    wmInQmI.setImCusCode(wmImNoticeHEntity.getImCusCode());
                } catch (Exception e) {

                }


            }
            wmInQmI.setCreateDate(DateUtils.getDate());
            wmInQmI.setBinSta("N");


                if (StringUtil.isNotEmpty(wmInQmI.getProData())) {//8位日期转yyyy-mm-dd
                    try {
                        if (StringUtil.getStringLen(wmInQmI.getProData()) == 8) {
                            String prodate = wmInQmI.getProData().substring(0, 4) + "-" +
                                    wmInQmI.getProData().substring(4, 6) + "-" +
                                    wmInQmI.getProData().substring(6, 8);
                            wmInQmI.setProData(prodate);
                        }
                    } catch (Exception e) {
                    }
                }
                //查询create_name
                TSBaseUser user = systemService.findUniqueByProperty(TSBaseUser.class, "userName", wmInQmI.getCreateBy());
                if (user != null) {
                    wmInQmI.setCreateName(user.getRealName());
                }

                AjaxJson ajaxJson = this.doAdd(wmInQmI);

                D0.setOK(ajaxJson.isSuccess());
            D0.setErrorMsg(ajaxJson.getMsg());
        } catch (Exception e) {
            D0.setOK(false);
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(D0, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody WmInQmIEntity wmInQmI) {
        // 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<WmInQmIEntity>> failures = validator
                .validate(wmInQmI);
        if (!failures.isEmpty()) {
            return new ResponseEntity(
                    BeanValidators.extractPropertyAndMessage(failures),
                    HttpStatus.BAD_REQUEST);
        }

        // 保存
        try {
            wmInQmIService.saveOrUpdate(wmInQmI);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        // 按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        wmInQmIService.deleteEntityById(WmInQmIEntity.class, id);
    }


    @RequestMapping(value = "/save",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> save(@RequestParam String wmToUpGoodsstr, UriComponentsBuilder uriBuilder) {
        ResultDO D0 = new  ResultDO();
        WmToUpGoodsEntity wmToUpGoods = (WmToUpGoodsEntity) JSONHelper.json2Object(wmToUpGoodsstr,WmToUpGoodsEntity.class);
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<WmToUpGoodsEntity>> failures = validator.validate(wmToUpGoods);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        if(StringUtil.isEmpty(wmToUpGoods.getKuWeiBianMa())){
            D0.setOK(false);
            D0.setErrorMsg("储位不能为空");
            return new ResponseEntity(D0, HttpStatus.OK);
        }else{
            if (!wmUtil.checkbin(wmToUpGoods.getKuWeiBianMa())) {
                D0.setOK(false);
                D0.setErrorMsg("储位不存在");
                return new ResponseEntity(D0, HttpStatus.OK);            }
        }


        //保存
        try{
            D0.setOK(true);
            WmInQmIEntity wmInQmIEntity = systemService.get(WmInQmIEntity.class,wmToUpGoods.getWmToUpId());
            if(wmInQmIEntity!=null){
                if("Y".equals(wmInQmIEntity.getBinSta())){
                    D0.setOK(false);
                    D0.setErrorMsg("已经上架，不能重复上架");
                    return new ResponseEntity(D0, HttpStatus.OK);
                }
//                wmInQmIEntity.setBinSta("Y");
//                systemService.updateEntitie(wmInQmIEntity);
            }else{
                D0.setOK(false);
                D0.setErrorMsg("验收任务已经删除，不能上架");

                return new ResponseEntity(D0, HttpStatus.OK);
            }



            TSBaseUser user = systemService.findUniqueByProperty(TSBaseUser.class,"userName",wmToUpGoods.getCreateBy());
            if (user != null ) {
                wmToUpGoods.setCreateName(user.getRealName());
            }
            toup(wmToUpGoods.getWmToUpId(),wmToUpGoods.getKuWeiBianMa(),wmToUpGoods.getCreateBy(),wmToUpGoods.getCreateName());
        } catch (Exception e) {
            e.printStackTrace();
            D0.setOK(false);
        }

        return new ResponseEntity(D0, HttpStatus.OK);
    }

}
