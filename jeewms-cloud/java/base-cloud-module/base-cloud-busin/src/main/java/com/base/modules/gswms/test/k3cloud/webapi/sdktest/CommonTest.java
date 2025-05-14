package com.base.modules.gswms.test.k3cloud.webapi.sdktest;

import static org.junit.Assert.fail;

import java.util.*;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.gswms.entity.Customer;
import com.base.modules.gswms.test.k3cloud.webapi.sdktest.SeqHelper;
import com.base.modules.jeewms.entity.WmImNoticeH;
import com.base.modules.jeewms.entity.WmImNoticeI;
import com.base.modules.jeewms.service.IWmImNoticeHService;
import com.base.modules.jeewms.service.IWmImNoticeIService;
import com.kingdee.bos.webapi.sdk.*;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.system.base.controller.JeecgController;
import org.junit.Test;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public class CommonTest extends JeecgController<WmImNoticeH, IWmImNoticeHService> {

	@Autowired
	private IWmImNoticeHService wmImNoticeHService;
	@Autowired
	private IWmImNoticeIService wmImNoticeIService;
	@Test
	public void testGson() {

		Gson gson = new Gson();
		String json = "{\"TaskId\":\"7\",\"Status\":2,\"Result\":{\"Result\":{\"ResponseStatus\":{\"IsSuccess\":true,\"Errors\":[],\"SuccessEntitys\":[{\"Id\":100082,\"Number\":\"BC20190914023210001\",\"DIndex\":0},{\"Id\":100083,\"Number\":\"BC20190914023310002\",\"DIndex\":1},{\"Id\":100084,\"Number\":\"BC20190914023310003\",\"DIndex\":2},{\"Id\":100085,\"Number\":\"BC20190914023310004\",\"DIndex\":3},{\"Id\":100086,\"Number\":\"BC20190914023310005\",\"DIndex\":4},{\"Id\":100087,\"Number\":\"BC20190914023310006\",\"DIndex\":5},{\"Id\":100088,\"Number\":\"BC20190914023310007\",\"DIndex\":6},{\"Id\":100089,\"Number\":\"BC20190914023310008\",\"DIndex\":7},{\"Id\":100090,\"Number\":\"BC20190914023310009\",\"DIndex\":8},{\"Id\":100091,\"Number\":\"BC20190914023310010\",\"DIndex\":9},{\"Id\":100092,\"Number\":\"BC20190914023310011\",\"DIndex\":10},{\"Id\":100093,\"Number\":\"BC20190914023310012\",\"DIndex\":11},{\"Id\":100094,\"Number\":\"BC20190914023310013\",\"DIndex\":12},{\"Id\":100095,\"Number\":\"BC20190914023310014\",\"DIndex\":13},{\"Id\":100096,\"Number\":\"BC20190914023310015\",\"DIndex\":14},{\"Id\":100097,\"Number\":\"BC20190914023310016\",\"DIndex\":15},{\"Id\":100098,\"Number\":\"BC20190914023310017\",\"DIndex\":16},{\"Id\":100099,\"Number\":\"BC20190914023310018\",\"DIndex\":17},{\"Id\":100100,\"Number\":\"BC20190914023310019\",\"DIndex\":18},{\"Id\":100101,\"Number\":\"BC20190914023310020\",\"DIndex\":19}],\"SuccessMessages\":[],\"MsgCode\":0},\"NeedReturnData\":[]}},\"Cancelled\":false,\"IsFaulted\":false,\"FaultedException\":null,\"ProgressInfos\":null,\"Successful\":true,\"Message\":\"\",\"Progress\":100,\"ProgressDesc\":\"\"}";
		QueryResultInfo ret = gson.fromJson(json, QueryResultInfo.class);
		String oStr = gson.toJson(ret.getResult());

	}

	/* ���浥���ͻ���Ϣ */
	@Test
	public void testSaveCustomer() throws Exception {
		K3CloudApi api=new K3CloudApi();
		Customer c = new Customer();
		c.setFNumber(SeqHelper.genNumber("BC"));
		c.setFName("aimin_�ͻ�����-A" + UUID.randomUUID().toString());
		c.setFShortName("aimin_�ͻ����-A" + UUID.randomUUID().toString());
		SaveResult sRet = api.save("BD_Customer", new SaveParam<Customer>(c));
		if (sRet.isSuccessfully()) {
			Gson gson = new Gson();
			System.out.println(
					String.format("%s", gson.toJson(sRet.getResult().getResponseStatus().getSuccessEntitys())));
		} else {
			fail("dcs is null!");
		}

	}
	
	/*��������ͻ���Ϣ*/
	@Test
	public void testBatchSaveCustomer() throws Exception {
		K3CloudApi api=new K3CloudApi();
		List<Customer> custs = new ArrayList<Customer>();
		for (int i = 0; i < 10; i++) {
			Customer c = new Customer();
			c.setFNumber(SeqHelper.genNumber("BC"));
			c.setFName("zam_�ͻ�����-����" + UUID.randomUUID().toString());
			c.setFShortName("zam_�ͻ����-����" + UUID.randomUUID().toString());
			custs.add(c);
		}

		// ��������ͻ���Ϣ
		SaveResult sRet = api.batchSave("BD_Customer", new BatchSave<Customer>(custs), InvokeMode.Query);

		if (sRet.isSuccessfully()) {
			Gson gson = new Gson();
			for (int i = 0; i < custs.size(); i++) {
				System.out.println(String.format("get(%s)=%s", i,
						gson.toJson(sRet.getResult().getResponseStatus().getSuccessEntitys().get(i))));
			}
		} else {
			fail("dcs is null!");
		}
	}
	
	/*����ѯ�ӿڣ���ѯ�ͻ���Ϣ*/
	@Test
	public void testQueryCustomer() {
		K3CloudApi api = new K3CloudApi();
		List<Customer> datas = null;
		try {
			datas = api.executeBillQuery(
					new QueryParam().setFormId("BD_Customer")
							.setFieldKeys("FCUSTID,FNumber,FName,FCreateDate"),
					Customer.class);
			if (datas != null) {
				Customer meta = ((Customer) (datas.toArray()[0]));
				System.out.println(String.format("%s", new Gson().toJson(meta)));
			} else {
				fail("datas is null!");
			}
		} catch (Exception e) {

			fail(e.getMessage());
		}

		if (datas != null) {
			System.out.println(String.format("Total:%s", datas.size()));
		} else {
			fail("dcs is null!");
		}
	}

	@Test
	public void getGscgrks() throws Exception {
		K3CloudApi api = new K3CloudApi();
		String date1 = "2019-09-10";
		Date date = cn.hutool.core.date.DateUtil.parse(date1, "yyyy-MM-dd");
		Date newDate2 = cn.hutool.core.date.DateUtil.offsetDay(date, 1);
		String date2 = DateUtil.format(newDate2, "yyyy-MM-dd");
		//     FCREATEDATE >= '+  2019-09-10 + '   and FCREATEDATE <= '2019-09-11'
		Map<String, String> map = new HashMap<>(1024);
		map.put("FormId","STK_InStock");
		map.put("FieldKeys","FBILLNO,FSupplierId.FNumber,FSupplierId.FName,FSTOCKDEPTID.FNumber,FSTOCKDEPTID.FName," +
				"FSTOCKORGID.FNumber,FSTOCKORGID.FName,FSTOCKERID.FName,FPURCHASERID.FName,,FBILLTYPEID.FName");
		map.put("OrderString","FCREATEDATE desc");
		map.put("FilterString","FDocumentStatus = 'C' and FCREATEDATE >= '"+date1+ "'and FCREATEDATE <= '"+date2 +"'");
		Gson gson = new Gson();
		String s = gson.toJson(map);
		List<List<Object>> datas1 = null;
		try {
			datas1 = api.executeBillQuery(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (datas1 != null) {
			for (List<Object> objects : datas1) {
				WmImNoticeH wmImNoticeH = new WmImNoticeH();
				//单据编号
				wmImNoticeH.setU8ReturnCode(String.valueOf(objects.get(0)));
				//供应商编码
				wmImNoticeH.setSupCode(String.valueOf(objects.get(1)));
				//供应商名称
				wmImNoticeH.setSupName(String.valueOf(objects.get(2)));
				//仓库代码
				wmImNoticeH.setStoreCode(String.valueOf(objects.get(3)));
				//仓库名称
				wmImNoticeH.setStoreName(String.valueOf(objects.get(4)));
				//收货组织内码
				wmImNoticeH.setCusCode(String.valueOf(objects.get(5)));
				//收货组织名称
				wmImNoticeH.setCusName(String.valueOf(objects.get(6)));
				//仓管员
				wmImNoticeH.setFstockername(String.valueOf(objects.get(7)));
				//采购员
				wmImNoticeH.setPurchasename(String.valueOf(objects.get(8)));
				//入库类型
				wmImNoticeH.setOrderType(String.valueOf(objects.get(9)));
				wmImNoticeH.setOrderTypes(String.valueOf(objects.get(9)));
				wmImNoticeHService.save(wmImNoticeH);
				//获取子表数据
				List<WmImNoticeI> noticeIS = new ArrayList<>();
				Map<String, String> map1 = new HashMap<>(1024);
				map1.put("FormId","STK_InStock");
				map1.put("FieldKeys","FMaterialId.FNumber,FMaterialId.FName,F_PAEZ_BaseProperty1,FUOM,FBaseUnitID.FName,FMustQty," +
						"FRealQty,FLot,FNote,FContractlNo");
				//FStockId.FName,
				map1.put("OrderString","FCREATEDATE desc");
				map1.put("FilterString","FBILLNO = '"+wmImNoticeH.getU8ReturnCode()+"'");
				String s1 = gson.toJson(map);
				List<List<Object>> datas2 = null;
				try {
					datas2 = api.executeBillQuery(s1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				for (List<Object> objectList : datas2) {
					WmImNoticeI noticeI = new WmImNoticeI();
					//商品编码
					noticeI.setGoodsCode(String.valueOf(objectList.get(0)));
					//商品名称
					noticeI.setGoodsName(String.valueOf(objectList.get(1)));
					//客户料号
					noticeI.setImNoticeItem(String.valueOf(objectList.get(2)));
					//规格
					noticeI.setShpGuiGe(String.valueOf(objectList.get(3)));
					//基本单位
					noticeI.setBaseUnit(String.valueOf(objectList.get(4)));
					//应收数量
					noticeI.setMustqty(String.valueOf(objectList.get(5)));
					//实收数量
					noticeI.setGoodsQmCount(String.valueOf(objectList.get(6)));
					//批号
					noticeI.setGoodsBatch(String.valueOf(objectList.get(7)));
					//备注
					noticeI.setRemark(String.valueOf(objectList.get(8)));
					//收料通知单号
					noticeI.setContractlno(String.valueOf(objectList.get(9)));
					noticeIS.add(noticeI);
				}
				wmImNoticeHService.saveMain(wmImNoticeH,noticeIS);
			}
		}
	}



/*
	@AutoLog(value = "同步国声采购入库单")
	@ApiOperation(value = "同步国声采购入库单", notes = "同步国声采购入库单")
	@GetMapping(value = "/getGscgrk")*/
	public Result<?> getGscgrk() throws Exception {
		K3CloudApi api = new K3CloudApi();
		String date1 = "2019-09-10";
		Date date = cn.hutool.core.date.DateUtil.parse(date1, "yyyy-MM-dd");
		Date newDate2 = cn.hutool.core.date.DateUtil.offsetDay(date, 1);
		String date2 = DateUtil.format(newDate2, "yyyy-MM-dd");

		//     FCREATEDATE >= '+  2019-09-10 + '   and FCREATEDATE <= '2019-09-11'
		Map<String, String> map = new HashMap<>(1024);
		map.put("FormId","STK_InStock");
		map.put("FieldKeys","FBILLNO,FSupplierId.FNumber,FSupplierId.FName,FSTOCKDEPTID.FNumber,FSTOCKDEPTID.FName," +
				"FSTOCKORGID.FNumber,FSTOCKORGID.FName,FSTOCKERID.FName,FPURCHASERID.FName,,FBILLTYPEID.FName");
		map.put("OrderString","FCREATEDATE desc");
		map.put("FilterString","FDocumentStatus = 'C' and FCREATEDATE >= '"+date1+ "'and FCREATEDATE <= '"+date2 +"'");
		Gson gson = new Gson();
		String s = gson.toJson(map);
		List<List<Object>> datas1 = null;
		try {
			datas1 = api.executeBillQuery(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (datas1 != null) {
			for (List<Object> objects : datas1) {
				WmImNoticeH wmImNoticeH = new WmImNoticeH();
				//单据编号
				wmImNoticeH.setU8ReturnCode(String.valueOf(objects.get(0)));
				//供应商编码
				wmImNoticeH.setSupCode(String.valueOf(objects.get(1)));
				//供应商名称
				wmImNoticeH.setSupName(String.valueOf(objects.get(2)));
				//仓库代码
				wmImNoticeH.setStoreCode(String.valueOf(objects.get(3)));
				//仓库名称
				wmImNoticeH.setStoreName(String.valueOf(objects.get(4)));
				//收货组织内码
				wmImNoticeH.setCusCode(String.valueOf(objects.get(5)));
				//收货组织名称
				wmImNoticeH.setCusName(String.valueOf(objects.get(6)));
				//仓管员
				wmImNoticeH.setFstockername(String.valueOf(objects.get(7)));
				//采购员
				wmImNoticeH.setPurchasename(String.valueOf(objects.get(8)));
				//入库类型
				wmImNoticeH.setOrderType(String.valueOf(objects.get(9)));
				wmImNoticeH.setOrderTypes(String.valueOf(objects.get(9)));
//				wmImNoticeHService.save(wmImNoticeH);
				//获取子表数据
				List<WmImNoticeI> noticeIS = new ArrayList<>();
				Map<String, String> map1 = new HashMap<>(1024);
				map1.put("FormId","STK_InStock");
				map1.put("FieldKeys","FMaterialId.FNumber,FMaterialId.FName,F_PAEZ_BaseProperty1,FUOM,FBaseUnitID.FName,FMustQty," +
						"FRealQty,FLot,FNote,FContractlNo");
				//FStockId.FName,
				map1.put("OrderString","FCREATEDATE desc");
				map1.put("FilterString","FBILLNO = '"+wmImNoticeH.getU8ReturnCode()+"'");
				String s1 = gson.toJson(map);
				List<List<Object>> datas2 = null;
				try {
					datas2 = api.executeBillQuery(s1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				for (List<Object> objectList : datas2) {
					WmImNoticeI noticeI = new WmImNoticeI();
					//商品编码
					noticeI.setGoodsCode(String.valueOf(objectList.get(0)));
					//商品名称
					noticeI.setGoodsName(String.valueOf(objectList.get(1)));
					//客户料号
					noticeI.setImNoticeItem(String.valueOf(objectList.get(2)));
					//规格
					noticeI.setShpGuiGe(String.valueOf(objectList.get(3)));
					//基本单位
					noticeI.setBaseUnit(String.valueOf(objectList.get(4)));
					//应收数量
					noticeI.setMustqty(String.valueOf(objectList.get(5)));
					//实收数量
					noticeI.setGoodsQmCount(String.valueOf(objectList.get(6)));
					//批号
					noticeI.setGoodsBatch(String.valueOf(objectList.get(7)));
					//备注
					noticeI.setRemark(String.valueOf(objectList.get(8)));
					//收料通知单号
					noticeI.setContractlno(String.valueOf(objectList.get(9)));
					noticeIS.add(noticeI);
				}
				wmImNoticeHService.saveMain(wmImNoticeH,noticeIS);
			}
		}
		return Result.ok("同步成功！");
	}


	@Test
	public void test1() {
		String post = "{'code':'200','msg':'操作成功'}";
		System.out.println("输出参数："+post);
		JSONObject parse = (JSONObject) JSONObject.parse(post);
		String code = parse.get("code") != null ? parse.get("code").toString() : "";
		System.out.println(code);
	}
	
	
}
