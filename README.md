
QQ交流群：335607153
测试系统地址http://218.5.177.99/wmstest   用户名：test  密码：123456
android app也已经开源，近期更新会比较频繁 app开源地址（https://gitee.com/erzhongxmu/jeewmsapp）。
大家看到了，star一下，谢谢，本团队会持续更新，一直开源！
欢迎喜欢的朋友一起来优化功能。

一、项目简介：
     
    Ideer-wms是由灵鹿谷科技主导的开源项目，WMS在经过多家公司上线运行后，为了降低物流仓储企业的信息化成本，决定全面开源
    此产品。针对有特殊信息化需求的企业，提供高性价比的实施服务。

    产品特点：
    1、适用范围：第三方物流仓储企业，自营仓储等。
    2、技术特点：基于JAVA的WEB后台，基于ANDROID开发的PDA系统。
    3、功能特点：涵盖订单管理系统（OMS），仓储管理系统（WMS），计费管理系统（BMS），现场作业系统（RF），第三方接口模块
    4、接口支持：已经对接：SAP ECC，SAP HANA 数据库，用友U8，百胜E3，UAS。

二、业务介绍：    

    1、主要功能
        计费配置、仓库配置、基础配置、计费管理、基础资料、仓库管理、月台管理、进货管理、出货管理、退货管理、库内管理、盘点管理、
        库存查询、PDA功能、分析报表、分析图表、域验证。
    2、主要流程
        客户下单流程，收货流程，上架流程，移货作业、拣货流程：批量拣货，按单拣货、盘点流程、计费流程。

三、安装说明：
  
    1，开发环境：
       开发工具：
		IDEA（强烈建议用IDEA，也可以Eclipse）；ANDROID STUDIO
		JDK1.8
		Maven
		Mysql5.6以上（linux 注意设置大小写不敏感）
		运行环境：CENTOS6.5以上或windows server 2008、tomcat7以上，JDK1.8， MYSQL5.7
    2，按照mvn方式导入
    3，数据库还原：步骤 1：还原数据库，2，修改 dbconfig.properties
    4，IDEA：tomcat7:tun   输入用户名和密码：admin llg123
    5、主要技术
        开发语言：JAVA。
    6、技术架构
	SpringMVC+Hibernat+Minidao(类Mybatis)+Easyui(UI库)+ Jquery + Boostrap + Ehcache + Redis + Ztree等基础架构
	
四、技术支持：

    演示环境：http://218.5.177.99/wmstest     用户： test   密码：123456
    QQ群：335607153    

五、版本管理及更新说明：


	此版本为定版1.0
	每半月发布一个小版本 例如1.1。
	每半年升级大版本 例如2.0

六、开发计划：

    1，支持Mysql的同时支持多数据库。
    2，去除触发器。
    3，电商系统选型和对接
    4，升级基础框架
   
七、开源及商务合作说明：

    1、开源说明：本项目完全遵循GPL V3协议。
        如果你喜欢我们的产品, 可以放心从我们的开源版开始使用, 在产品商业化的支持下同时我们会尽最大的努力维护开源版的稳定。
        后期在不影响公司业务的前提下, 会逐步开放更多的信息化平台。在此期间,如果你发现产品有任何Bug, 请在Issue模块向我们反馈，谢谢。
    2、商务合作：本项目同时支持开源版实施和定制开发服务，商务合作请 统一联系 QQ：22663889

# 问题&BUG&建议：请在此提交https://gitee.com/erzhongxmu/jeewms/issues
![主页](https://images.gitee.com/uploads/images/2018/1014/235739_2c29bbbf_544004.png "wmshome.png")
![仓位使用率](https://images.gitee.com/uploads/images/2018/1015/000747_4eafc335_544004.png "cwsyl.png")
![仓位图](https://images.gitee.com/uploads/images/2018/1015/000802_e438ced8_544004.png "cwt.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1015/000813_bab8d35c_544004.png "ccsl.png")
![效期预警](https://images.gitee.com/uploads/images/2018/1015/000823_c5a982fe_544004.png "xqyj.png")

![输入图片说明](https://images.gitee.com/uploads/images/2018/1016/070614_dad8bb5b_544004.png "ysd.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1016/070625_7ab42b77_544004.png "货品ID.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1016/070641_343c5b31_544004.png "拣货单.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1015/000843_92d44144_544004.png "Screenshot_2018-10-15-00-07-03-907_com.jeewms.www.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1015/000857_ad7d16dc_544004.png "Screenshot_2018-10-15-00-03-11-011_com.jeewms.www.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1015/000906_e439bf3f_544004.png "Screenshot_2018-10-15-00-03-34-019_com.jeewms.www.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1015/000914_a6078e90_544004.png "Screenshot_2018-10-15-00-04-17-626_com.jeewms.www.png")
