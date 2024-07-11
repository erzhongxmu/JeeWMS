package com.zzjee.wmapi.controller;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.zzjee.wmapi.entity.WvNoticeEntity;
import com.zzjee.wmapi.service.WvNoticeServiceI;
import com.zzjee.wmutil.wmUtil;

/**
 * @author erzhongxmu
 * @version V1.0
 * @Title: Controller
 * @Description: wv_notice
 * @date 2018-05-30 20:21:05
 */
@Controller
@RequestMapping("/wvNoticeController")
public class WvNoticeController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(WvNoticeController.class);

    @Autowired
    private WvNoticeServiceI wvNoticeService;

    @Autowired
    private Validator validator;

    //PDA接口
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam(value = "username", required = false) String username,
                                  @RequestParam(value = "searchstr", required = false) String searchstr,
                                  @RequestParam(value = "searchstr2", required = false) String searchstr2) {
        ResultDO D0 = new ResultDO();
        String hql = " from WvNoticeEntity where 1 = 1 ";
        D0.setOK(true);
        // 如果searchstr不为空，则拼接HQL查询条件
        if (!StringUtil.isEmpty(searchstr)) {
            hql = hql + "  and noticeId like '%" + searchstr + "%'" + "  or imCusCode like '%" + searchstr + "%'";
        }
        // 如果searchstr2不为空，则尝试获取商品编码，并拼接HQL查询条件
        if (!StringUtil.isEmpty(searchstr2)) {
            try {
                String shpbianma = wmUtil.getmdgoodsbytiaoma(searchstr2);
                if (StringUtil.isNotEmpty(shpbianma)) {
                    searchstr2 = shpbianma;
                }
            } catch (Exception e) {

            }
            String[] ss = searchstr2.split(",");
            if (ss.length == 1) {
                hql = hql + "  and (goodsCode like '%" + searchstr2 + "%'" + "or shp_ming_cheng like '%" + searchstr2 + "%')";
            } else {
                String insearch = "";
                for (String s : ss) {
                    if (StringUtil.isNotEmpty(insearch)) {
                        insearch = insearch + " or  goodsCode = '" + s + "'";
                    } else {
                        insearch = "goodsCode = '" + s + "'";
                    }
                }
                hql = hql + "  and  (" + insearch + ")";
            }
        }
        // 根据HQL查询条件获取WvNoticeEntity列表
        List<WvNoticeEntity> listWvNotices = wvNoticeService.findHql(hql);
        D0.setOK(true);
        List<WvNoticeEntity> result = new ArrayList<WvNoticeEntity>();
        int i = 0;
        // 遍历查询结果，最多取前100条记录
        for (WvNoticeEntity t : listWvNotices) {
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