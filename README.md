
JEEWMSQQ交流群：335607153 1群已满  请加2群  313930553  有任何问题可以加群交流  
 **智能制造产品JEEMES已经开源**  (https://gitee.com/erzhongxmu/jeemes) 欢迎大家STAR
 
测试系统地址 http://47.113.229.131:9080/jeewms  用户名：admin 密码：llg123 本地启动的用户名：admin 密码：llg123
大家看到了，star一下，谢谢，本团队会持续更新，一直开源！

开源不易，希望大家多多支持，有仓储硬件需求（PDA，RFID，AGV等）的可以找我们代买，保证最优惠的价格，官网全新上线，欢迎访问  [公司官网](http://www.huayi-tec.com/)。

一、项目简介：
     
    JeeWMS是基于java的智能仓储管理系统，在经过多家公司上线运行后，为了降低物流仓储企业的信息化成本，决定全面开源
    此产品。针对有特殊信息化需求的企业，提供高性价比的实施服务。

    产品特点：
    1、适用范围：第三方物流仓储企业，冷链仓库，工厂仓储，海外仓等。
    2、技术特点：基于JAVA的WEB后台，基于UNI-APP开发的智能仓储管理系统手持操作端。
    3、功能特点：涵盖订单管理系统（OMS），仓库管理系统（WMS），计费管理系统（BMS），现场作业系统（RF），RFID模块
    4，AGV模拟程序：加入了基于PLC的模拟程序，大家可以基于此思路研究AGV的调度。
   

二、业务介绍：    

    1、主要功能
        计费配置、仓库配置、基础配置、计费管理、基础资料、仓库管理、月台管理、进货管理、出货管理、退货管理、库内管理、盘点管理、
        库存查询、PDA功能、分析报表、分析图表、域验证。
    2、主要流程
        客户下单流程，收货流程，上架流程，移货作业、拣货流程：批量拣货，按单拣货、盘点流程、计费流程。
    3、硬件对接
        对接自主研发基于LORA物联网技术的电子货架标签模块，满足快速退货分拣，波次拣货；对接自动化立体库系统，对接AGV，RFID。
    4、计费管理：通过在线SQL，动态完成费用的计算，满足3PL仓费用复杂多变的需求。

三、安装说明：
  
    1，开发环境：
       开发工具：
		IDEA（版本不限）；
		JDK1.8
		Maven
		Mysql5.7以上（注意设置大小写不敏感，关闭only_full_group_by规则  
               # 设置sql_mode，去掉了ONLY_FULL_GROUP_BY
               sql_mode='STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION'），mysql8.0不行
		运行环境：CENTOS6.5以上或windows server 2008、tomcat7以上，JDK1.8， MYSQL5.7
    2，按照mvn方式导入
    3，数据库还原：
        安装完数据库执行下 GRANT ALL PRIVILEGES ON *.* TO 'root'@'%'IDENTIFIED BY '你的密码' WITH GRANT OPTION;
                          FLUSH PRIVILEGES;     
        步骤 
        1：还原数据库，
        2:修改 dbconfig.properties
        3: sql导入方式建议 登录MYSQL服务器上用source命令还原,或用Mysqlworkbench importing
    4，IDEA：mvn tomcat7:run   输入用户名和密码：admin llg123
    5、主要技术
        开发语言：JAVA。
    6、技术架构
	基础架构基于jeecg。技术架构为SpringMVC+Hibernat+Minidao(类Mybatis)+Easyui(UI库)+ Jquery + Bootstrap + Ehcache + Redis + Ztree等基础架构
	
四、技术支持：

    演示环境：  http://47.113.229.131:9080/jeewms     用户： admin   密码：llg123
    QQ群：335607153    
   
五、开源及商务合作说明：

    1、开源说明：本项目完全遵循GPL V3协议。
        如果你喜欢我们的产品, 可以放心从我们的开源版开始使用, 在产品商业化的支持下同时我们会尽最大的努力维护开源版的稳定。
        后期在不影响公司业务的前提下, 会逐步开放更多的信息化平台。在此期间,如果你发现产品有任何Bug, 请在Issue模块向我们反馈，谢谢。
    2、商务合作：本项目同时支持开源版实施和定制开发服务，商务合作请 统一联系 QQ：290813851

# 问题&BUG&建议：请在此提交https://gitee.com/erzhongxmu/jeewms/issues

![AGV调度模拟]![输入图片说明](1111.png)
![主页](https://images.gitee.com/uploads/images/2018/1014/235739_2c29bbbf_544004.png "wmshome.png")
![仓位使用率](https://images.gitee.com/uploads/images/2018/1015/000747_4eafc335_544004.png "cwsyl.png")
![仓位图]![输入图片说明](%E4%BB%93%E4%BD%8D%E5%9B%BE.png)
![输入图片说明](https://images.gitee.com/uploads/images/2018/1015/000813_bab8d35c_544004.png "ccsl.png")
![效期预警](https://images.gitee.com/uploads/images/2018/1015/000823_c5a982fe_544004.png "xqyj.png")

![输入图片说明](https://images.gitee.com/uploads/images/2018/1016/070614_dad8bb5b_544004.png "ysd.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1016/070625_7ab42b77_544004.png "货品ID.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1016/070641_343c5b31_544004.png "拣货单.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1015/000843_92d44144_544004.png "Screenshot_2018-10-15-00-07-03-907_com.jeewms.www.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1015/000857_ad7d16dc_544004.png "Screenshot_2018-10-15-00-03-11-011_com.jeewms.www.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1015/000906_e439bf3f_544004.png "Screenshot_2018-10-15-00-03-34-019_com.jeewms.www.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1015/000914_a6078e90_544004.png "Screenshot_2018-10-15-00-04-17-626_com.jeewms.www.png")
