package com.base.modules.jeewms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.util.QRcodeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Api(tags = "二维码")
@RestController
@RequestMapping("/jeewms/qrcode")
@Slf4j
public class QrCodeController {

    @AutoLog(value = "二维码")
    @ApiOperation(value = "二维码", notes = "二维码")
    @GetMapping("/get")
    public void get(@RequestParam("q") String q,
                    HttpServletRequest request,
                    HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String flag = request.getParameter("down");//是否下载否则展示图片

        String qrvalue = request.getParameter("qrvalue");
        if (q != null && !q.isEmpty()) {
            qrvalue = q;
        }
        String dbpath = qrvalue + ".jpg";
        String localPath = "C://upFiles";

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
            log.error(e.toString());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
