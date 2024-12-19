package com.base.modules.jeewms.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;
import com.base.modules.data.Baseoutientity;
import com.base.modules.data.U8SqlConstant;
import com.base.modules.data.dbUtil;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.service.*;
import com.base.modules.util.ConstUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

 /**
 * @Description: U8消息推送
 * @Author: base-boot
 * @Date:   2021-11-22
 * @Version: V1.0
 */
@Api(tags="U8消息推送")
@RestController
@RequestMapping("/jeewms/wmU8PushMessage")
@Slf4j
public class WmU8PushMessageController extends BaseController<WmU8PushMessage, IWmU8PushMessageService> {
	@Autowired
	private IWmU8PushMessageService wmU8PushMessageService;
	@Autowired
	private IWmImNoticeHService wmImNoticeHService;
	@Autowired
	private IWmImNoticeIService wmImNoticeIService;
	@Autowired
	private IWmInQmIService wmInQmIService;
	@Autowired
	private IWmOmNoticeHService wmOmNoticeHService;
	@Autowired
	private IWmOmNoticeIService wmOmNoticeIService;
	@Autowired
	private IWmOmQmIService wmOmQmIService;

	/**
	 * 分页列表查询
	 *
	 * @param wmU8PushMessage
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "U8消息推送-分页列表查询")
	@ApiOperation(value="U8消息推送-分页列表查询", notes="U8消息推送-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmU8PushMessage wmU8PushMessage,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmU8PushMessage> queryWrapper = QueryGenerator.initQueryWrapper(wmU8PushMessage, req.getParameterMap());
		Page<WmU8PushMessage> page = new Page<WmU8PushMessage>(pageNo, pageSize);
		IPage<WmU8PushMessage> pageList = wmU8PushMessageService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmU8PushMessage
	 * @return
	 */
	@AutoLog(value = "U8消息推送-添加")
	@ApiOperation(value="U8消息推送-添加", notes="U8消息推送-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmU8PushMessage wmU8PushMessage) {
		wmU8PushMessageService.save(wmU8PushMessage);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmU8PushMessage
	 * @return
	 */
	@AutoLog(value = "U8消息推送-编辑")
	@ApiOperation(value="U8消息推送-编辑", notes="U8消息推送-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmU8PushMessage wmU8PushMessage) {
		wmU8PushMessageService.updateById(wmU8PushMessage);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "U8消息推送-通过id删除")
	@ApiOperation(value="U8消息推送-通过id删除", notes="U8消息推送-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmU8PushMessageService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "U8消息推送-批量删除")
	@ApiOperation(value="U8消息推送-批量删除", notes="U8消息推送-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmU8PushMessageService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "U8消息推送-通过id查询")
	@ApiOperation(value="U8消息推送-通过id查询", notes="U8消息推送-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmU8PushMessage wmU8PushMessage = wmU8PushMessageService.getById(id);
		if(wmU8PushMessage==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(wmU8PushMessage);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmU8PushMessage
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmU8PushMessage wmU8PushMessage) {
        return super.exportXls(request, wmU8PushMessage, WmU8PushMessage.class, "U8消息推送");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, WmU8PushMessage.class);
    }

	 /**
	  * 全部查询
	  * @param req
	  * @return
	  */
	 @RequestMapping(value = "/allList", method = RequestMethod.GET)
	 @ApiOperation(value = "执行SQL", notes = "执行SQL")
	 public Result<?> queryPageAllList(@RequestParam(name = "outlenth", required = true) Integer outlenth,
									   @RequestParam(name = "sql", required = false) String sql,
									   @RequestParam(name = "para", required = false) String para,
									   @RequestParam(name = "sqltype", required = true) String sqltype,
									   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
									   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
									   HttpServletRequest req) {
		 List<Baseoutientity> reslit = new ArrayList<>();
		 if(StringUtils.isEmpty(sql)){
		 	 if("1".equals(sqltype)){
		 	 	//人员hr_hi_person
			 	sql = U8SqlConstant.HRHIPERSON;
			 }else if("2".equals(sqltype)){
		 	 	//供应商档案Vendor
			 	sql = U8SqlConstant.VENDOR;
			 }else if("3".equals(sqltype)){
		 	 	//物料
			 	sql = U8SqlConstant.DEMO;
			 }else if("4".equals(sqltype)){
		 	 	//存货
			 	sql = U8SqlConstant.INVENTORY;
			 }else if("5".equals(sqltype)){
		 	 	//生产订单
			 	sql = U8SqlConstant.MOMORDER;
			 }else if("6".equals(sqltype)){
		 	 	//组织架构
			 	sql = U8SqlConstant.DEPARTMENT;
			 }else if("7".equals(sqltype)){
		 	 	//采购订单PO_Pomain
			 	sql = U8SqlConstant.POPOMAIN;
			 }else if("8".equals(sqltype)){
		 	 	//采购到货PU_ArrivalVouch
			 	sql = U8SqlConstant.PUARRIVALVOUCH;
			 }



			 reslit = dbUtil.getbysql(sql, para, outlenth);

		 }else{
			 reslit = dbUtil.getbysql(sql, para, outlenth);

		 }
		 System.out.println("SQL======"+sql);
		 System.out.println("SQLTYPE======"+sqltype);
		 System.out.println("reslit======"+reslit);
		 return Result.ok(reslit);
	 }

	 @RequestMapping(value = "/sendWms", method = RequestMethod.POST)
	 @ApiOperation(value = "推送wms接口", notes = "推送wms接口")
	 public Result<?> sendWms(@RequestBody JSONObject jsonObject) {
		 WmU8PushMessage wmU8PushMessage = new WmU8PushMessage();
		 wmU8PushMessage.setContent(JSON.toJSONString(jsonObject));
		 wmU8PushMessageService.save(wmU8PushMessage);
		 if(jsonObject.get("NoticeId") == null){
			 wmU8PushMessage.setCause("参数NoticeId不能为空！");
			 wmU8PushMessage.setSucceed("false");
			 wmU8PushMessageService.updateById(wmU8PushMessage);
			 return Result.error("推送失败，参数NoticeId不能为空！");
		 }
		 if(jsonObject.get("OrderType") == null){
			 wmU8PushMessage.setCause("参数OrderType不能为空！");
			 wmU8PushMessage.setSucceed("false");
			 wmU8PushMessageService.updateById(wmU8PushMessage);
			 return Result.error("推送失败，参数OrderType不能为空！");
		 }
		 if(jsonObject.get("Params") == null){
			 wmU8PushMessage.setCause("参数Params不能为空！");
			 wmU8PushMessage.setSucceed("false");
			 wmU8PushMessageService.updateById(wmU8PushMessage);
			 return Result.error("推送失败，参数Params不能为空！");
		 }
		 String orderNO = jsonObject.get("NoticeId").toString();
		 String orderType = jsonObject.get("OrderType").toString();
		 wmU8PushMessage.setOrderNo(orderNO);
		 wmU8PushMessage.setOrderType(orderType);
		 wmU8PushMessageService.updateById(wmU8PushMessage);
		 String json = JSONObject.parseObject(jsonObject.get("Params").toString()).get("json").toString();
		 JSONObject object = JSONObject.parseObject(json);
		 if("采购入库审核".equals(orderType)){
			 Object cCode = object.get("cCode");//单据号
			 Object cVenCode = object.get("cVenCode");//供应商编码
			 Object dDate = object.get("dDate");//单据日期
			 Object dARVDate = object.get("dARVDate");//到货日期
			 Object cARVCode  = object.get("cARVCode");//采购到货单号
			 Object cMemo = object.get("cMemo");//备注
			 Object cWhCode = object.get("cWhCode");//仓库编码
			 JSONArray details = (JSONArray) JSONArray.parse(object.get("Details").toString());
			 QueryWrapper<WmImNoticeH> queryWrapper = new QueryWrapper<>();
			 queryWrapper.eq("u8_return_code",orderNO);
			 WmImNoticeH wmImNoticeH = wmImNoticeHService.getOne(queryWrapper);
			 if(wmImNoticeH==null){
				 wmU8PushMessage.setCause("单号【"+orderNO+"】未找到对应采购入库单！");
				 wmU8PushMessage.setSucceed("false");
				 wmU8PushMessageService.updateById(wmU8PushMessage);
				 return Result.error("单号【"+orderNO+"】未找到对应采购入库单！");
			 }
			 QueryWrapper<WmInQmI> qmIQueryWrapper = new QueryWrapper<>();
			 qmIQueryWrapper.eq("im_notice_id",wmImNoticeH.getNoticeId());
			 List<WmInQmI> wmInQmIList = wmInQmIService.list(qmIQueryWrapper);
			 List<String> idList = new ArrayList<>();
			 for(WmInQmI wmInQmI : wmInQmIList){
				 idList.add(wmInQmI.getId());
			 }
			 wmInQmIService.upToShelf(idList);
//			 List<WmImNoticeI> wmImNoticeIList = new ArrayList<>();
//			 for(int i=0;i<details.size();i++){
//				 Object iPOsID = ((JSONObject) details.get(i)).get("iPOsID");//u8Cgid(采购订单子表标识)
//				 Object cInvCode = ((JSONObject) details.get(i)).get("cInvCode");//存货编码
//				 Object cBatch = ((JSONObject) details.get(i)).get("cBatch");//批号
//				 Object cFree10 = ((JSONObject) details.get(i)).get("cFree10");//子表ID
//				 Object iQuantity = ((JSONObject) details.get(i)).get("iQuantity");//数量
//				 Object iUnitCost = ((JSONObject) details.get(i)).get("iUnitCost");//单价
//				 Object cbMemo = ((JSONObject) details.get(i)).get("cbMemo");//备注
//				 Object cPosition = ((JSONObject) details.get(i)).get("cPosition");//货位编码
//				 Object cFree9 = ((JSONObject) details.get(i)).get("cFree9");//托盘
//				 if(iPOsID!=null){
//				 	QueryWrapper<WmImNoticeI> queryWrapper1 = new QueryWrapper<>();
//				 }
//			 }
		 }else if("产成品入库审核".equals(orderType)){
			 Object cCode = object.get("cCode");//单据号
			 Object cVenCode = object.get("cVenCode");//供应商编码
			 Object dDate = object.get("dDate");//单据日期
			 Object dARVDate = object.get("dARVDate");//到货日期
			 Object cMPoCode  = object.get("cMPoCode");//生产订单编号
			 Object cMemo = object.get("cMemo");//备注
			 Object cWhCode = object.get("cWhCode");//仓库编码
			 JSONArray details = (JSONArray) JSONArray.parse(object.get("Details").toString());
			 QueryWrapper<WmImNoticeH> queryWrapper = new QueryWrapper<>();
			 queryWrapper.eq("u8_return_code",orderNO);
			 WmImNoticeH wmImNoticeH = wmImNoticeHService.getOne(queryWrapper);
			 if(wmImNoticeH==null){
				 wmU8PushMessage.setCause("单号【"+orderNO+"】未找到对应生产入库单！");
				 wmU8PushMessage.setSucceed("false");
				 wmU8PushMessageService.updateById(wmU8PushMessage);
				 return Result.error("单号【"+orderNO+"】未找到对应生产入库单！");
			 }
			 QueryWrapper<WmInQmI> qmIQueryWrapper = new QueryWrapper<>();
			 qmIQueryWrapper.eq("im_notice_id",wmImNoticeH.getNoticeId());
			 List<WmInQmI> wmInQmIList = wmInQmIService.list(qmIQueryWrapper);
			 List<String> idList = new ArrayList<>();
			 for(WmInQmI wmInQmI : wmInQmIList){
				 idList.add(wmInQmI.getId());
			 }
			 wmInQmIService.upToShelf(idList);
//			 List<WmImNoticeI> wmImNoticeIList = new ArrayList<>();
//			 for(int i=0;i<details.size();i++){
//				 Object cInvCode = ((JSONObject) details.get(i)).get("cInvCode");//存货编码
//				 Object cBatch = ((JSONObject) details.get(i)).get("cBatch");//批号
//				 Object cFree10 = ((JSONObject) details.get(i)).get("cFree10");//子表ID
//				 Object iQuantity = ((JSONObject) details.get(i)).get("iQuantity");//数量
//				 Object iUnitCost = ((JSONObject) details.get(i)).get("iUnitCost");//单价
//				 Object cbMemo = ((JSONObject) details.get(i)).get("cbMemo");//备注
//				 Object cPosition = ((JSONObject) details.get(i)).get("cPosition");//货位编码
//				 Object cFree9 = ((JSONObject) details.get(i)).get("cFree9");//托盘
//				 if(cBatch!=null){
//					 for(WmImNoticeI wmImNoticeI : oldwmImNoticeIList){
//						 if(cInvCode.equals(wmImNoticeI.getGoodsCode())){
//							 if (cBatch != null) { wmImNoticeI.setGoodsBatch(cBatch.toString()); }
//							 if (iQuantity != null) { wmImNoticeI.setGoodsCount(iQuantity.toString()); }
//							 if (iUnitCost != null) { wmImNoticeI.setUnitPrice(new BigDecimal(iUnitCost.toString())); }
//							 if (cbMemo != null) { wmImNoticeI.setImBeizhu(cbMemo.toString()); }
//							 if (cPosition != null) { wmImNoticeI.setBinPlan(cPosition.toString()); }
//							 wmImNoticeIList.add(wmImNoticeI);
//						 }
//					 }
//				 }
//			 }
		 }else if("材料出库审核".equals(orderType)){
			 Object cCode = object.get("cCode");//单据号
			 Object cVenCode = object.get("cVenCode");//供应商编码
			 Object dDate = object.get("dDate");//单据日期
			 Object dARVDate = object.get("dARVDate");//到货日期
			 Object cMPoCode  = object.get("cMPoCode");//生产订单编号
			 Object cMemo = object.get("cMemo");//备注
			 Object cWhCode = object.get("cWhCode");//仓库编码
			 JSONArray details = (JSONArray) JSONArray.parse(object.get("Details").toString());
			 QueryWrapper<WmOmNoticeH> queryWrapper = new QueryWrapper<>();
			 queryWrapper.eq("u8_return_code",orderNO);
			 WmOmNoticeH wmOmNoticeH = wmOmNoticeHService.getOne(queryWrapper);
			 if(wmOmNoticeH==null){
				 wmU8PushMessage.setCause("单号【"+orderNO+"】未找到对应材料出库单！");
				 wmU8PushMessage.setSucceed("false");
				 wmU8PushMessageService.updateById(wmU8PushMessage);
				 return Result.error("单号【"+orderNO+"】未找到对应材料出库单！");
			 }
			 QueryWrapper<WmOmQmI> queryWrapper1 = new QueryWrapper<>();
			 queryWrapper1.eq("om_notice_id",wmOmNoticeH.getOmNoticeId());
			 List<WmOmQmI> wmOmQmIList = wmOmQmIService.list(queryWrapper1);
			 List<String> ids = new ArrayList<>();
			 for(WmOmQmI wmOmQmI : wmOmQmIList){
				 ids.add(wmOmQmI.getId());
			 }
			 wmOmQmIService.dotowavedown(ids,"", ConstUtil.wm_y,null);
//			 for(int i=0;i<details.size();i++){
//				 Object cInvCode = ((JSONObject) details.get(i)).get("cInvCode");//存货编码
//				 Object cBatch = ((JSONObject) details.get(i)).get("cBatch");//批号
//				 Object cFree10 = ((JSONObject) details.get(i)).get("cFree10");//子表ID
//				 Object iQuantity = ((JSONObject) details.get(i)).get("iQuantity");//数量
//				 Object iUnitCost = ((JSONObject) details.get(i)).get("iUnitCost");//单价
//				 Object cbMemo = ((JSONObject) details.get(i)).get("cbMemo");//备注
//				 Object cPosition = ((JSONObject) details.get(i)).get("cPosition");//货位编码
//				 Object cFree9 = ((JSONObject) details.get(i)).get("cFree9");//托盘
//			 }
		 }else if("销售出库审核".equals(orderType)){
			 Object cCode = object.get("cCode");//单据号
			 Object cVenCode = object.get("cVenCode");//供应商编码
			 Object dDate = object.get("dDate");//单据日期
			 Object dARVDate = object.get("dARVDate");//到货日期
			 Object cBusCode  = object.get("cBusCode");//对应业务单号
			 Object cMemo = object.get("cMemo");//备注
			 Object cWhCode = object.get("cWhCode");//仓库编码
			 JSONArray details = (JSONArray) JSONArray.parse(object.get("Details").toString());
			 QueryWrapper<WmOmNoticeH> queryWrapper = new QueryWrapper<>();
			 queryWrapper.eq("u8_return_code",orderNO);
			 WmOmNoticeH wmOmNoticeH = wmOmNoticeHService.getOne(queryWrapper);
			 if(wmOmNoticeH==null){
				 wmU8PushMessage.setCause("单号【"+orderNO+"】未找到对应销售出库单！");
				 wmU8PushMessage.setSucceed("false");
				 wmU8PushMessageService.updateById(wmU8PushMessage);
				 return Result.error("单号【"+orderNO+"】未找到对应销售出库单！");
			 }
			 QueryWrapper<WmOmQmI> queryWrapper1 = new QueryWrapper<>();
			 queryWrapper1.eq("om_notice_id",wmOmNoticeH.getOmNoticeId());
			 List<WmOmQmI> wmOmQmIList = wmOmQmIService.list(queryWrapper1);
			 List<String> ids = new ArrayList<>();
			 for(WmOmQmI wmOmQmI : wmOmQmIList){
				 ids.add(wmOmQmI.getId());
			 }
			 wmOmQmIService.dotowavedown(ids,"", ConstUtil.wm_y,null);
//			 for(int i=0;i<details.size();i++){
//				 Object cInvCode = ((JSONObject) details.get(i)).get("cInvCode");//存货编码
//				 Object cBatch = ((JSONObject) details.get(i)).get("cBatch");//批号
//				 Object cFree10 = ((JSONObject) details.get(i)).get("cFree10");//子表ID
//				 Object iQuantity = ((JSONObject) details.get(i)).get("iQuantity");//数量
//				 Object iUnitCost = ((JSONObject) details.get(i)).get("iUnitCost");//单价
//				 Object cbMemo = ((JSONObject) details.get(i)).get("cbMemo");//备注
//				 Object cPosition = ((JSONObject) details.get(i)).get("cPosition");//货位编码
//				 Object cFree9 = ((JSONObject) details.get(i)).get("cFree9");//托盘
//			 }
		 }else if("调拨单审核".equals(orderType)){
			 Object cTVCode = object.get("cTVCode");//单据号
			 Object dTVDate = object.get("dTVDate");//单据日期
			 Object cIWhCode = object.get("cIWhCode");//转入仓库编码
			 Object cOWhCode  = object.get("cOWhCode");//转出仓库编码
			 Object cTVMemo = object.get("cTVMemo");//备注
			 JSONArray details = (JSONArray) JSONArray.parse(object.get("Details").toString());
			 for(int i=0;i<details.size();i++){
				 Object cInvCode = ((JSONObject) details.get(i)).get("cInvCode");//存货编码
				 Object cTVBatch = ((JSONObject) details.get(i)).get("cTVBatch");//批号
				 Object cFree10 = ((JSONObject) details.get(i)).get("cFree10");//子表ID
				 Object iTVQuantity = ((JSONObject) details.get(i)).get("iTVQuantity");//数量
				 Object iTVACost = ((JSONObject) details.get(i)).get("iTVACost");//单价
				 Object cbMemo = ((JSONObject) details.get(i)).get("cbMemo");//备注
				 Object cinposcode = ((JSONObject) details.get(i)).get("cinposcode");//货位编码
				 Object coutposcode = ((JSONObject) details.get(i)).get("coutposcode");//调出货位编码
			 }

		 }else if("盘点单审核".equals(orderType)){
			 Object cCVCode = object.get("cCVCode");//单据号
			 Object dCVDate = object.get("dCVDate");//供应商编码
			 Object cWhCode = object.get("cWhCode");//仓库编码
			 Object cCVMemo = object.get("cCVMemo");//备注
			 JSONArray details = (JSONArray) JSONArray.parse(object.get("Details").toString());
			 for(int i=0;i<details.size();i++){
				 Object cInvCode = ((JSONObject) details.get(i)).get("cInvCode");//存货编码
				 Object cBVencode = ((JSONObject) details.get(i)).get("cBVencode");//供应商编码
				 Object cCVBatch = ((JSONObject) details.get(i)).get("cCVBatch");//批号
				 Object cFree10 = ((JSONObject) details.get(i)).get("cFree10");//子表ID
				 Object iCVQuantity = ((JSONObject) details.get(i)).get("iCVQuantity");//账面数量
				 Object iCVCQuantity = ((JSONObject) details.get(i)).get("iCVCQuantity");//盘点数量
				 Object iJhdj = ((JSONObject) details.get(i)).get("iJhdj");//单价
				 Object iSjDJ = ((JSONObject) details.get(i)).get("iSjDJ");//盘点单价
				 Object iAdInQuantity = ((JSONObject) details.get(i)).get("iAdInQuantity");//调整入库数量
				 Object iAdOutQuantity = ((JSONObject) details.get(i)).get("iAdOutQuantity");//调整出库数量
				 Object cbMemo = ((JSONObject) details.get(i)).get("cbMemo");//备注
				 Object cPosition = ((JSONObject) details.get(i)).get("cPosition");//货位编码
				 Object cFree9 = ((JSONObject) details.get(i)).get("cFree9");//托盘
			 }


		 }
		 return Result.ok("推送成功！");
	 }



	 public static void main(String[] args){
		 String aa = "{\n" +
				 "\t\"OrderType\": \"采购入库审核\",\n" +
				 "\t\"NoticeId\": \"RV2021110151\",\n" +
				 "\t\"Params\": {\n" +
				 "\t\t\"json\": \"{\\\"cCode\\\":\\\"RV2021110151\\\",\\\"cVenCode\\\":\\\"010219900\\\",\\\"dDate\\\":\\\"2021/11/24 0:00:00\\\",\\\"dARVDate\\\":\\\"0001/1/1 0:00:00\\\",\\\"cARVCode\\\":null,\\\"cWhCode\\\":\\\"13\\\",\\\"cMemo\\\":null,\\\"Details\\\":[{\\\"cInvCode\\\":\\\"SS0040015S\\\",\\\"cBatch\\\":null,\\\"cFree10\\\":null,\\\"iQuantity\\\":\\\"1.0000000000\\\",\\\"iUnitCost\\\":\\\"0\\\",\\\"cbMemo\\\":\\\"补单\\\",\\\"cPosition\\\":null,\\\"cFree9\\\":null}]}\"\n" +
				 "\t}\n" +
				 "}";
		 JSONObject result = JSONObject.parseObject(aa);
		 String bb = result.get("Params").toString();
		 System.out.println(JSONObject.parseObject(aa));
		 JSONObject result2 = JSONObject.parseObject(bb);
		 String cc = result2.get("json").toString();
		 JSONObject result3 = JSONObject.parseObject(cc);
		 Object ll = result3.get("Details");
		 Object cCode = result3.get("cCode");
		 Object cVenCode = result3.get("cVenCode");
		 Object dDate = result3.get("dDate");
		 Object dARVDate = result3.get("dARVDate");
		 Object cARVCode  = result3.get("cARVCode");
		 Object cMemo = result3.get("cMemo");
		 Object cWhCode = result3.get("cWhCode");
		 JSONArray details = (JSONArray) JSONArray.parse(ll.toString());
		 Object cInvCode = ((JSONObject) details.get(0)).get("cInvCode");
		 Object cBatch = ((JSONObject) details.get(0)).get("cBatch");
		 Object cFree10 = ((JSONObject) details.get(0)).get("cFree10");
		 Object iQuantity = ((JSONObject) details.get(0)).get("iQuantity");
		 Object iUnitCost = ((JSONObject) details.get(0)).get("iUnitCost");
		 Object cbMemo = ((JSONObject) details.get(0)).get("cbMemo");
		 Object cPosition = ((JSONObject) details.get(0)).get("cPosition");
		 Object cFree9 = ((JSONObject) details.get(0)).get("cFree9");
		 System.out.println("cInvCode:" + cInvCode + ";cBatch:" + cBatch + ";cFree10:" + cFree10 + ";iQuantity:" + iQuantity);
		 System.out.println("iUnitCost:" + iUnitCost + ";cbMemo:" + cbMemo + ";cPosition:" + cPosition + ";cFree9:" + cFree9);
	 }

}
