package com.zzjee.sap;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.jeecgframework.core.util.ResourceUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 *   连接SAP
 */

public class SapRFC {
	private JCoFunction function;//SAP函数对象

	// 输入参数列表
	private JCoParameterList inPara = null;

	// 输出参数列表

	//	private JCoParameterList outPara = null;
	private JCoParameterList outPara = null;

	private JCoParameterList tabPara = null;

	//	private String functionName;

	// SAP系统别名
	private static String ABAP_AS = "ABAP_AS_WITHOUT_POOL";
	// SAP目的地对象
	private JCoDestination destination;

	// 构造函数，用于初始化连接
	public SapRFC() {
		connect(); // 连接SAP
	}

	// 调用 SapRFC，创建SapRFC对象
	public static SapRFC getInstance(){
		// RFC接口调用开始,创建SApRFC对象
		SapRFC common = new SapRFC();
		return common;
	}

	// 连接 SAP
	public void connect() {
		// SAP连接参数
		String clientName  ;
		String userid  ;
		String password  ;
		String language  ;
		String host  ;
		String system ;
		try{
			 clientName = ResourceUtil.getConfigByName("rfc.clientName");
			 userid = ResourceUtil.getConfigByName("rfc.userid");
			 password = ResourceUtil.getConfigByName("rfc.password");
			 language = ResourceUtil.getConfigByName("rfc.language");
			 host = ResourceUtil.getConfigByName("rfc.host");
			 system = ResourceUtil.getConfigByName("rfc.system");
			// router = ResourceUtil.getConfigByName("rfc.router");
		}catch (Exception e){
			// 如果配置文件读取失败，则使用默认参数
			clientName = "300";
			userid = "**";
			password = "123123";
			language = "ZH";
			host = "**";
			system = "00";
		}
		// 设置SAP的连接参数
		Properties connectProperties = new Properties();
		connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, host);
		connectProperties
				.setProperty(DestinationDataProvider.JCO_SYSNR, system);
		connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT,
				clientName);
		connectProperties.setProperty(DestinationDataProvider.JCO_USER, userid);
		connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,
				password);
		connectProperties.setProperty(DestinationDataProvider.JCO_LANG,
				language);
		//	connectProperties.setProperty(DestinationDataProvider.JCO_SAPROUTER, router);

		try {
			// 创建DestinationDataProvider，创建连接配置文件并获取SAP目的地
			createDataFile(ABAP_AS, "jcoDestination", connectProperties);
			destination = JCoDestinationManager.getDestination(ABAP_AS);

		} catch (JCoException ex) {
			System.out.print("destinoo**++++++"+ex.getMessage());
		}
	}

	/*
	 * 添加参数到输入参数列表
	 * 设置参数 name
	 * - the name of the field to set value
	 * - the value to set for the field
	 */
	public SapRFC addParameter(String name, String value) {
		inPara.setValue(name, value);
		return this;
	}

	// 添加参数到输入参数列表，（使用整数索引）
	public SapRFC addParameter(int name, String value) {
		inPara.setValue(name, value);
		return this;
	}

	// 执行方法，调用SAP函数
	public SapRFC prepare(String functionName) {
		//this.functionName = functionName;
		//Unread field: com.zzjee.sap.SapRFC.functionName
		//Bug type URF_UNREAD_FIELD (click for details)
		try {
			// 取得要执行的方法
			function = destination.getRepository().getFunction(functionName);
		} catch (JCoException e) {
			// throw new SAPException("SAP获取方法"+ functionName +"失败："+
			System.out.print(e.getMessage());
		}
		if (function == null) {
			// throw new SAPException(functionName +"方法不存在");
		}
		// 取得参数列表
		inPara = function.getImportParameterList();
		outPara = function.getExportParameterList();
		tabPara = function.getTableParameterList();
		// logger.info(functionName +"方法调用开始");
		return this;
	}

	// 执行方法
	public JCoParameterList execCall() throws JCoException {
		// Execute
		function.execute(destination);
		// logger.info(functionName +"方法调用结束");
		return outPara;
	}

	// 取得返回的结果
	public JCoTable getResultTable(String tableName) {
		return tabPara.getTable(tableName);
	}

	//取得参数列表
	// public JCoTable getParamTable(String tableName) {
	// return tabPara.getTable(tableName);
	// }

	// SAP传入参数为列表
	public JCoTable getParamTableList(String tableName) {
		return function.getTableParameterList().getTable(tableName);
	}

	// DisConnect, 关闭连接
	public void close() {
		// if (client != null)
		// client.disconnect();
	}

	// 创建连接
	// Creates a connection configuration file based on parameters given above
	static void createDataFile(String name, String suffix, Properties properties) {
		File cfg = new File(name + "." + suffix);
		if (!cfg.exists()) {
			try {
				FileOutputStream fos = new FileOutputStream(cfg, false);
				properties.store(fos, "Destination - ABAP_AS_WITHOUT_POOL");
				fos.close();
			} catch (Exception e) {
				// 输出异常信息
				System.out.print("creat********"+e.getMessage());
			}
		}
	}

	// 将null字符串转换为空字符串
	public String convertNull(String str) {
		if (str == null) {
            return "";
        }
		return str;
	}

}
