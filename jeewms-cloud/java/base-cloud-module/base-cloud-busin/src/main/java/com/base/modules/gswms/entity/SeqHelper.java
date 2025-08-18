package com.base.modules.gswms.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Demo class
 *
 * @author admin
 * @date 2021-05-18
 */
public class SeqHelper {

	static long seq=0;
	static int mod=10000;
	/**
	 * ��ȡһ��ID
	 * @return
	 */
	public static long genSeq() {
		return ++seq;
	}
	/**
	 * ����һ������
	 * @return
	 */
	public static String genNumber(String pre) {
		SimpleDateFormat sdf=new  SimpleDateFormat("yyyyMMHHmmss");
		String id=sdf.format(new Date());
		if(pre==null) {
			return id + (genSeq() +  mod);
		} else {
			return pre+id + (genSeq() +  mod);
		}
	}
}
