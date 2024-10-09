package test;

import HslCommunication.BasicFramework.SoftBasic;
import HslCommunication.Core.Types.OperateResult;
import HslCommunication.Core.Types.OperateResultExOne;
import HslCommunication.Profinet.Siemens.SiemensPLCS;
import HslCommunication.Profinet.Siemens.SiemensS7Net;
import com.sap.conn.jco.JCoTable;
import com.zzjee.sap.SapRFC;
import org.jeecgframework.codegenerate.window.CodeWindow;
import org.jeecgframework.core.util.DateUtils;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * 【单表模型】代码生成器入口
 * @author admin
 * @site www.jeecg.org
 *
 */
public class JeecgOneGUI {

	public static void main(String[] args) throws ParseException {
		SiemensPLCS siemensPLCS = SiemensPLCS.S200Smart;
		SiemensS7Net siemensS7Net = null;
 		String defaultAddress = "M5.0";
		siemensS7Net = new SiemensS7Net(siemensPLCS);
		siemensS7Net.setIpAddress("192.168.1.99");
		siemensS7Net.setPort(102);
		OperateResult connect = siemensS7Net.ConnectServer();
		if(connect.IsSuccess){
			System.out.println("connect success");
		}else{
			System.out.println("connect error");
		}
//		OperateResultExOne<Float> floatOperateResultExOne = siemensS7Net.ReadFloat(defaultAddress);
//		if(floatOperateResultExOne.IsSuccess){
//			System.out.println("read success");
//			System.out.println(floatOperateResultExOne.Content);
// 		}else{
//			System.out.println(floatOperateResultExOne.Content);
//		}
//		defaultAddress = "M5.0";
//		siemensS7Net.Write(defaultAddress,false);
//		defaultAddress = "VD320";
//		float speed = (float) 5.00;
//		siemensS7Net.Write(defaultAddress,speed);
//		defaultAddress = "VD300";
//		float position = (float) -50.00;//方向
//		siemensS7Net.Write(defaultAddress,position);
//		defaultAddress = "M5.0";
//		siemensS7Net.Write(defaultAddress,true);




		defaultAddress = "M7.0";
		siemensS7Net.Write(defaultAddress,false);
		defaultAddress = "VD420";
		float speed = (float) 5.00;
		siemensS7Net.Write(defaultAddress,speed);
		defaultAddress = "VD400";
		float position = (float) 50.00;//换向
		siemensS7Net.Write(defaultAddress,position);
		defaultAddress = "M7.0";
		siemensS7Net.Write(defaultAddress,true);


	}
}
