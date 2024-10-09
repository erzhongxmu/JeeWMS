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

import com.zzjee.wm.entity.WmOmNoticeHEntity;
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
import com.zzjee.wmapi.entity.WvGiNoticeEntity;
import com.zzjee.wmapi.service.WvGiNoticeServiceI;
import com.zzjee.wmutil.wmUtil;


/**
 * @author erzhongxmu
 * @version V1.0
 * @Title: Controller
 * @Description: wv_gi_notice
 * @date 2018-05-30 20:20:38
 */
@Controller
@RequestMapping("/wvGiNoticeController")
public class WvGiNoticeController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(WvGiNoticeController.class);
    @Autowired
    private WvGiNoticeServiceI wvGiNoticeService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> list() {
        ResultDO D0 = new ResultDO();
        List<WvGiNoticeEntity> listWvGiNotices = wvGiNoticeService.getList(WvGiNoticeEntity.class);
        D0.setOK(true);
        D0.setObj(listWvGiNotices);
        return new ResponseEntity(D0, HttpStatus.OK);
    }

    //下架任务  PDA接口
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> lists(@RequestParam(value = "username", required = false) String username,
                                   @RequestParam(value = "searchstr", required = false) String searchstr,
                                   @RequestParam(value = "searchstr2", required = false) String searchstr2,
                                   @RequestParam(value = "searchstr3", required = false) String searchstr3) {
        ResultDO D0 = new ResultDO();
        String hql = " from WvGiNoticeEntity where 1 = 1 ";
        D0.setOK(true);
        // 如果searchstr不为空，则拼接HQL查询条件
        if (!StringUtil.isEmpty(searchstr)) {
            hql = hql + "  and omNoticeId like '%" + searchstr + "%'" + "  or imCusCode like '%" + searchstr + "%'";
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
                hql = hql + "  and goodsId like '%" + searchstr2 + "%'";
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
        if (!StringUtil.isEmpty(searchstr3)) {
            hql = hql + "  and binId = '" + searchstr3 + "'";
        }
        List<WvGiNoticeEntity> listWvGiNotices = wvGiNoticeService.findHql(hql);
        if (listWvGiNotices == null || listWvGiNotices.size() == 0) {
            hql = " from WvGiNoticeEntity where 1 = 1 ";

            if (!StringUtil.isEmpty(searchstr)) {
                hql = hql + "  and omNoticeId = '" + searchstr + "'";
            }
            listWvGiNotices = wvGiNoticeService.findHql(hql);
        }
        D0.setOK(true);
        List<WvGiNoticeEntity> result = new ArrayList<WvGiNoticeEntity>();
        int i = 0;
        for (WvGiNoticeEntity t : listWvGiNotices) {
            if (StringUtil.isNotEmpty(username) && StringUtil.isNotEmpty(t.getAssignTo())) {
                if (!username.equals(t.getAssignTo())) {
                    continue;
                }
            }
            i++;
            if (i > 100) {
                break;
            }
            try {
                String siji = "";
                String chehao = "";
                WmOmNoticeHEntity wmom = systemService.findUniqueByProperty(WmOmNoticeHEntity.class, "omNoticeId", t.getOmNoticeId());
                siji = wmom.getReMember();
                chehao = wmom.getReCarno();
                if (StringUtil.isNotEmpty(t.getOmBeiZhu())) {
                    t.setOmBeiZhu(t.getOmBeiZhu() + siji + chehao);
                } else {
                    t.setOmBeiZhu(siji + chehao);
                }
            } catch (Exception e) {
            }

            result.add(t);
        }

        D0.setObj(result);
        return new ResponseEntity(D0, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        // 通过服务层获取指定 ID 的 WvGiNotice 实体
        WvGiNoticeEntity task = wvGiNoticeService.get(WvGiNoticeEntity.class, id);
        // 如果未找到对应的实体，则返回 404 Not Found 状态码
        if (task == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        // 如果找到了对应的实体，则返回 200 OK 状态码及实体数据
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody WvGiNoticeEntity wvGiNotice, UriComponentsBuilder uriBuilder) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<WvGiNoticeEntity>> failures = validator.validate(wvGiNotice);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }
        //保存
        try {
            wvGiNoticeService.save(wvGiNotice);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        //按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
        String id = wvGiNotice.getId();
        URI uri = uriBuilder.path("/rest/wvGiNoticeController/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody WvGiNoticeEntity wvGiNotice) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<WvGiNoticeEntity>> failures = validator.validate(wvGiNotice);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }
        //保存
        try {
            wvGiNoticeService.saveOrUpdate(wvGiNotice);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        //按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        wvGiNoticeService.deleteEntityById(WvGiNoticeEntity.class, id);
    }
}
