package com.zzjee.sdk;


import com.zzjee.conf.entity.WxConfigEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.JDOMException;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.xiaoleilu.hutool.date.DateTime.now;

@Api(value="tenPayController",description="微信支付",tags="tenPayController")
@Controller
@RequestMapping("/tenPayController")
public class TenpayController {


    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;


//    @Autowired
//    private PayRecordService payRecordService;
//    @Autowired
//    private AppCustomerService appCustomerService;

    private String out_trade_no = "";

    /**
     * 生成预支付订单，获取prepayId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
//    @Auth



    @ApiOperation(value = "wxauthv3")
    @RequestMapping(value = "/authv3",method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<?> authV3(@RequestParam(value="CODE", required=false)  String CODE, @RequestParam(value="appCode", required=false) String appCode) {

        // 验证
        if (StringUtils.isEmpty(CODE)) {
            return Result.error("JSCODE不能为空!");
        }
//		// 验证
//		if (StringUtils.isEmpty(username)) {
//			return new ResponseEntity("用户密码不能为空!", HttpStatus.NOT_FOUND);
//		}
//        https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
//        Assert.notNull(JSCODE, "JSCODE can not be empty");
//		Assert.notNull(password, "password can not be empty");
//        String miniappid=ResourceUtil.getConfigByName("mini.appid") ;
//        String minisecret=ResourceUtil.getConfigByName("mini.secret");
        if(StringUtil.isEmpty(appCode)){
            appCode = "fxjpay";
        }
        WxConfigEntity wxConfigEntity = systemService.findUniqueByProperty(WxConfigEntity.class,"appCode",appCode);

        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wxConfigEntity.getAppId() +
                "&secret=" + wxConfigEntity.getAppSecret() +
                "&code=" +
                CODE+"&grant_type=authorization_code";
        String result= com.xiaoleilu.hutool.http.HttpUtil.get(url);


        JSONObject resultJson = JSONObject.fromObject(result);
        String openid = String.valueOf(resultJson.get("openid"));
         return Result.success(openid,(long)0);
     }



    @RequestMapping(value = "/app/tenpay/prepay", method = RequestMethod.GET)
    @ApiOperation(value="生成预支付订单",produces="application/json",httpMethod="GET")
    public @ResponseBody   Map<String, Object> getOrder(@RequestParam(value="jzProName", required=false)  String jzProName,
                                                        @RequestParam(value="jzProject", required=false)  String jzProject,
                                                        @RequestParam(value="jzRemark", required=false)  String jzRemark,
                                                        @RequestParam(value="jzName", required=false)  String jzName,
                                                        @RequestParam(value="code", required=false)  String code,
                                                        @RequestParam(value="total_fees", required=true)  String total_fees,
                                                        @RequestParam(value="appCode", required=false) String appCode,
                                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if(StringUtil.isEmpty(appCode)){
            appCode = "fxjpay";
        }
        WxConfigEntity wxConfigEntity = systemService.findUniqueByProperty(WxConfigEntity.class,"appCode",appCode);

        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wxConfigEntity.getAppId() +
                "&secret=" + wxConfigEntity.getAppSecret() +
                "&code=" +
                code+"&grant_type=authorization_code";
        String result= com.xiaoleilu.hutool.http.HttpUtil.get(url);


        JSONObject resultJson = JSONObject.fromObject(result);
         String openid = String.valueOf(resultJson.get("openid"));

        Map<String, Object> map = new HashMap<String, Object>();
        // 获取生成预支付订单的请求类
        PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);
//        String totalFee = request.getParameter("total_fee");
        int total_fee=(int) (Float.valueOf(total_fees)*100);
        System.out.println("total:"+total_fee);
        System.out.println("total_fee:" + total_fee);
        prepayReqHandler.setParameter("appid", wxConfigEntity.getAppId());
        prepayReqHandler.setParameter("body", ConstantUtil.BODY);
        prepayReqHandler.setParameter("mch_id", wxConfigEntity.getMchId());
        String nonce_str = WXUtil.getNonceStr();
        prepayReqHandler.setParameter("nonce_str", nonce_str);
        prepayReqHandler.setParameter("notify_url", wxConfigEntity.getNotifyUrl());
        out_trade_no = String.valueOf(UUID.next());
        prepayReqHandler.setParameter("out_trade_no", out_trade_no);
        prepayReqHandler.setParameter("spbill_create_ip", request.getRemoteAddr());
        String timestamp = WXUtil.getTimeStamp();
        prepayReqHandler.setParameter("time_start", timestamp);
        System.out.println(String.valueOf(total_fee));
        prepayReqHandler.setParameter("total_fee", String.valueOf(total_fee));
        prepayReqHandler.setParameter("openid",openid);
        prepayReqHandler.setParameter("trade_type", "JSAPI");
//        prepayReqHandler.setParameter("trade_type", "JSAPI");

        /**
         * 注意签名（sign）的生成方式，具体见官方文档（传参都要参与生成签名，且参数名按照字典序排序，最后接上APP_KEY,转化成大写）
         */
        prepayReqHandler.setParameter("sign", prepayReqHandler.createMD5Sign());
        prepayReqHandler.setGateUrl(ConstantUtil.GATEURL);
        String prepayid = prepayReqHandler.sendPrepay();
        // 若获取prepayid成功，将相关信息返回客户端
        if (prepayid != null && !prepayid.equals("")) {


            String signs = "appId=" + wxConfigEntity.getAppId() + "&nonceStr=" + nonce_str + "&package=prepay_id="
                    + prepayid + "&signType=MD5"   + "&timeStamp=" + timestamp + "&key="
                    + wxConfigEntity.getAppKey();
            map.put("code", 0);
            map.put("info", "success");
            map.put("prepayid", prepayid);
            /**
             * 签名方式与上面类似
             */
            map.put("sign", MD5Util.MD5Encode(signs, "utf8").toUpperCase());
            map.put("appid", wxConfigEntity.getAppId());
            map.put("timestamp", timestamp);  //等于请求prepayId时的time_start
            map.put("noncestr", nonce_str);   //与请求prepayId时值一致
            map.put("package", "prepay_id="+prepayid);  //固定常量
            map.put("partnerid", ConstantUtil.PARTNER_ID);
        } else {
            map.put("code", 1);
            map.put("info", "获取prepayid失败");
        }
        return map;
    }

    /**
     * 接收微信支付成功通知
     * @param request
     * @param response
     * @throws IOException
     */

    @RequestMapping(value = "/app/tenpay/notify")
    @ApiOperation(value="接收微信支付成功通知",produces="application/json",httpMethod="POST")
    public void getnotify(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        System.out.println("微信支付回调");
        PrintWriter writer = response.getWriter();
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        String result = new String(outSteam.toByteArray(), "utf-8");
        System.out.println("微信支付通知结果：" + result);
        Map<String, String> map = null;
        try {
            /**
             * 解析微信通知返回的信息
             */
            map = XMLUtil.doXMLParse(result);
        } catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("=========:"+result);
        // 若支付成功，则告知微信服务器收到通知
        if (map.get("return_code").equals("SUCCESS")) {
            if (map.get("result_code").equals("SUCCESS")) {
                System.out.println("充值成功！");

                System.out.println("订单号："+Long.valueOf(map.get("out_trade_no")));
                    String notifyStr = XMLUtil.setXML("SUCCESS", "");
                    writer.write(notifyStr);
                    writer.flush();
//                }
            }
        }
    }

}
