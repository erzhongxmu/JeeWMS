package org.jeecgframework.test.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import HslCommunication.Core.Types.OperateResult;
import HslCommunication.Core.Types.OperateResultExOne;
import HslCommunication.Profinet.Omron.OmronHostLinkOverTcp;
import org.jeecgframework.AbstractUnitTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;


public class ControllerTestDemo  extends AbstractUnitTest{

	private MockMvc mockMvc;
	private MockHttpSession session; //为模拟登录时，所有请求使用同一个session
	@Before
	public void setup() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = post("/");
		MockHttpServletRequest request = requestBuilder.buildRequest(this.wac.getServletContext());
		session = (MockHttpSession) request.getSession();
		this.mockMvc = webAppContextSetup(this.wac).alwaysExpect(status().isOk()).build();
		this.testLogin(); //先调用登录
	}
	//测试登录
	@Test
	public void testLogin() throws Exception {

		OmronHostLinkOverTcp omronHostLinkOverTcp = new OmronHostLinkOverTcp();
		omronHostLinkOverTcp.setIpAddress("172.17.140.51");
		omronHostLinkOverTcp.setPort(9600);
		OperateResult operateResult = omronHostLinkOverTcp.ConnectServer();
		final OperateResultExOne<byte[]> d1111 = omronHostLinkOverTcp.Read("D1111", (short) 1);
		omronHostLinkOverTcp.Write("D1111",1);
//        yyUtil.getProduct();
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("formDate","2017-01-01");
//		paramMap.put("lastUpdateTime","2017-01-01");
//		paramMap.put("pi_class","采购验收单");
//		uasUtil.getProduct(paramMap);
//
//		uasUtil.getCustomer(paramMap);
//
//		uasUtil.getVendor(paramMap);
//
//		uasUtil.getWarehouse(paramMap);
//
//		uasUtil.getBil(paramMap);
 //		session.setAttribute("randCode", "1234"); //设置登录验证码
//		this.mockMvc.perform(post("/loginController.do?checkuser=")
//				.param("userName","admin")
//				.param("password", "c44b01947c9e6e3f")
//				.param("randCode", "1234")
//				.header("USER-AGENT", "")  // 设置USER-AGENT： 浏览器
//				.session(session))  //关键 每个测试都要设置session 。以保证是使用的同一个session
//				.andDo(print())
//				.andExpect(jsonPath("$.success").value(Matchers.equalTo(true)));
	}
	//验证返回view 是否正确
	@Test
	public void testAorudemo() throws Exception {
//		MockHttpServletRequestBuilder requestBuilder = post("/demoController.do?aorudemo=")
//			.param("type","table")
//			.header("USER-AGENT", "")  // 设置USER-AGENT： 浏览器
//			.session(session);
//
//		this.mockMvc.perform(requestBuilder)
//				.andDo(print()) //打印报文
//				.andExpect(view().name(containsString("jeecg/demo/base/tabledemo"))); //验证返回view 是否不正确
	}

	//使用jsonPath 验证返回json 的属性
	@Test
	public void testPDemoList() throws Exception{

//		MockHttpServletRequestBuilder requestBuilder = post("/userController.do?datagrid=")
//	    .param("field", "id")
//		.header("USER-AGENT", "")  // 设置USER-AGENT： 浏览器
//		.session(session);
//
//		this.mockMvc.perform(requestBuilder)
//		.andDo(print()) //打印报文
//		.andExpect(jsonPath("$.rows[0].id").exists()); // 验证id 属性是否存在


	}
}
