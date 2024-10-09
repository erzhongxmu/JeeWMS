package com.zzjee.wmapi.controller;

import com.zzjee.api.ResultDO;
import com.zzjee.md.entity.MdGoodsEntity;
import com.zzjee.wmapi.entity.WmToDownGoodsErpEntity;
import com.zzjee.wmapi.entity.WmToUpGoodsErpEntity;
import com.zzjee.wmapi.entity.WvGiEntity;
import com.zzjee.wmapi.service.WvGiServiceI;
import com.zzjee.wmutil.wmUtil;
import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.sms.util.task.CostTask;
import org.jeecgframework.web.system.sms.util.task.SmsSendTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author erzhongxmu
 * @version V1.0
 * @Title: wmBaseControllerController
 * @Description: wmBaseController
 * @date 2018-05-30 20:21:50
 */
@Controller
@RequestMapping("/wmBaseController")
public class wmBaseController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(wmBaseController.class);

    //	@Autowired
//	private WvGiServiceI wvGiService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private SmsSendTask smsSendTask;
    @Autowired
    private CostTask costTask;
//	@Autowired
//	private Validator validator;

    /**
     * 获取图片流/获取文件用于下载
     *
     * @param response
     * @param request code128 格式
     * @throws Exception http://localhost:8080/zzjee/wmOmNoticeHController/showOrDownbarcodeByurl.do?&qrvalue=1111223333  调用
     */

    @RequestMapping(value = "showOrDownbarcodeByurl", method = RequestMethod.GET)
    public void getbarcodeImgByurl(HttpServletResponse response, HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String flag = request.getParameter("down");//是否下载否则展示图片

        String qrvalue = request.getParameter("qrvalue");
        String dbpath = qrvalue + ".jpg";
        String localPath = ResourceUtil.getConfigByName("webUploadpath");

        try {
            String imgurl = localPath + File.separator + dbpath;
//			QRcodeUtil.encode(qrvalue,imgurl);
            BarcodeUtil.generateFile(qrvalue, imgurl);
        } catch (Exception e) {

        }
        if ("1".equals(flag)) {
            response.setContentType("application/x-msdownload;charset=utf-8");
            String fileName = dbpath.substring(dbpath.lastIndexOf(File.separator) + 1);

            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            }
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);

        } else {
            response.setContentType("image/jpeg;charset=utf-8");
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String imgurl = localPath + File.separator + dbpath;
            inputStream = new BufferedInputStream(new FileInputStream(imgurl));
            outputStream = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            response.flushBuffer();
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionMessage(e));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }


    /**
     * 获取图片流/获取文件用于下载
     *
     * @param response
     * @param request
     * @throws Exception http://localhost:8080/zzjee/wmOmNoticeHController/showOrDownqrcodeByurl.do?&qrvalue=1111223333  调用
     */

    @RequestMapping(value = "showOrDownqrcodeByurl", method = RequestMethod.GET)
    public void getQrImgByurl(HttpServletResponse response, HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String flag = request.getParameter("down");//是否下载否则展示图片

        String qrvalue = request.getParameter("qrvalue");
        String dbpath = qrvalue + ".jpg";
        String localPath = ResourceUtil.getConfigByName("webUploadpath");

        try {
            String imgurl = localPath + File.separator + dbpath;
            QRcodeUtil.encode(qrvalue, imgurl);
        } catch (Exception e) {

        }
        if ("1".equals(flag)) {
            response.setContentType("application/x-msdownload;charset=utf-8");
            String fileName = dbpath.substring(dbpath.lastIndexOf(File.separator) + 1);

            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            }
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);

        } else {
            response.setContentType("image/jpeg;charset=utf-8");
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String imgurl = localPath + File.separator + dbpath;
            inputStream = new BufferedInputStream(new FileInputStream(imgurl));
            outputStream = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            response.flushBuffer();
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionMessage(e));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    @RequestMapping(value = "geterpim", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> listim(@RequestParam(value = "username", required = false) String username,
                                    @RequestParam(value = "all", required = false) String all,

                                    @RequestParam(value = "searchstr", required = false) String searchstr,
                                    @RequestParam(value = "searchstr2", required = false) String searchstr2,
                                    @RequestParam(value = "searchstrin1", required = false) String searchstrin1,
                                    @RequestParam(value = "searchstrin2", required = false) String searchstrin2,
                                    @RequestParam(value = "searchstrin3", required = false) String searchstrin3) {


        ResultDO D0 = new ResultDO();

        String hql = " from WmToUpGoodsErpEntity where 1 = 1  ";
        D0.setOK(true);

        List<WmToUpGoodsErpEntity> listerp = systemService.findHql(hql);
        D0.setOK(true);


        D0.setObj(listerp);
        return new ResponseEntity(D0, HttpStatus.OK);
    }

    @RequestMapping(value = "geterpom", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> listom(@RequestParam(value = "username", required = false) String username,
                                    @RequestParam(value = "all", required = false) String all,

                                    @RequestParam(value = "searchstr", required = false) String searchstr,
                                    @RequestParam(value = "searchstr2", required = false) String searchstr2,
                                    @RequestParam(value = "searchstrin1", required = false) String searchstrin1,
                                    @RequestParam(value = "searchstrin2", required = false) String searchstrin2,
                                    @RequestParam(value = "searchstrin3", required = false) String searchstrin3) {


        ResultDO D0 = new ResultDO();

        String hql = " from WmToDownGoodsErpEntity where 1 = 1  ";
        D0.setOK(true);

        List<WmToDownGoodsErpEntity> listerp = systemService.findHql(hql);
        D0.setOK(true);


        D0.setObj(listerp);
        return new ResponseEntity(D0, HttpStatus.OK);
    }

    @RequestMapping(value = "runtask", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> runtask(@RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "all", required = false) String all,

                                     @RequestParam(value = "searchstr", required = false) String searchstr,
                                     @RequestParam(value = "searchstr2", required = false) String searchstr2,
                                     @RequestParam(value = "searchstrin1", required = false) String searchstrin1,
                                     @RequestParam(value = "searchstrin2", required = false) String searchstrin2,
                                     @RequestParam(value = "searchstrin3", required = false) String searchstrin3) {


        ResultDO D0 = new ResultDO();
        smsSendTask.run();
        return new ResponseEntity(D0, HttpStatus.OK);
    }

    //结转库存
    @RequestMapping(value = "runtaskone", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> runtaskone(@RequestParam(value = "username", required = false) String username,
                                        @RequestParam(value = "all", required = false) String all,

                                        @RequestParam(value = "searchstr", required = false) String searchstr,
                                        @RequestParam(value = "searchstr2", required = false) String searchstr2,
                                        @RequestParam(value = "searchstrin1", required = false) String searchstrin1,
                                        @RequestParam(value = "searchstrin2", required = false) String searchstrin2,
                                        @RequestParam(value = "searchstrin3", required = false) String searchstrin3) {


        ResultDO D0 = new ResultDO();
        costTask.run();
        return new ResponseEntity(D0, HttpStatus.OK);
    }
}
