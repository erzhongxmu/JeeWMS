package com.zzjee.wmapi.controller;

import com.zzjee.api.ResultDO;
import com.zzjee.wmapi.entity.WvGiEntity;
import com.zzjee.wmapi.entity.WvGiNoticeEntity;
import com.zzjee.wmapi.service.WvGiServiceI;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zzjee.wmutil.wmUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.TreeChildCount;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;

import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;
import java.util.HashMap;

import org.jeecgframework.core.util.ExceptionUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author erzhongxmu
 * @version V1.0
 * @Title: Controller
 * @Description: wv_gi
 * @date 2018-05-30 20:21:50
 */
@Controller
@RequestMapping("/wvGiController")
public class WvGiController extends BaseController {

    @Autowired
    private WvGiServiceI wvGiService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam(value = "username", required = false) String username,
                                  @RequestParam(value = "searchstr", required = false) String searchstr,
                                  @RequestParam(value = "searchstr2", required = false) String searchstr2) {
        ResultDO D0 = new ResultDO();
        String hql = " from WvGiEntity where downSta is null ";
        D0.setOK(true);
        // 如果searchstr不为空，则拼接HQL查询条件
        if (!StringUtil.isEmpty(searchstr)) {
            hql = hql + "  and orderId = '" + searchstr + "'" + "  or imCusCode like '%" + searchstr + "%'";
        }
        // 如果searchstr2不为空，则尝试获取商品编码，并拼接HQL查询条件
        if (!StringUtil.isEmpty(searchstr2)) {
            try {
                String shpbianma = wmUtil.getmdgoodsbytiaoma(searchstr2);
                if (StringUtil.isNotEmpty(shpbianma)) {
                    searchstr2 = shpbianma;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String[] ss = searchstr2.split(",");
            if (ss.length == 1) {
                hql = hql + "  and goodsId = '" + searchstr2 + "'";
            } else {
                String insearch = "";
                for (String s : ss) {
                    if (StringUtil.isNotEmpty(insearch)) {
                        insearch = insearch + " or  goodsId = '" + s + "'";
                    } else {
                        insearch = "goodsId = '" + s + "'";
                    }
                }
                hql = hql + "  and  (" + insearch + ")";
            }
        }
        // 根据HQL查询条件获取WvNoticeEntity列表
        List<WvGiEntity> listWvGis = wvGiService.findHql(hql);
        D0.setOK(true);
        List<WvGiEntity> result = new ArrayList<WvGiEntity>();
        int i = 0;
        // 遍历查询结果，最多取前100条记录
        for (WvGiEntity t : listWvGis) {
            i++;
            if (i > 100) {
                break;
            }
            result.add(t);
        }
        // 将结果存入ResultDO对象
        D0.setObj(result);
        return new ResponseEntity(D0, HttpStatus.OK);
    }
}