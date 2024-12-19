第一步 导入数据库
1.启动nacos前请创建mysql数据库库名为base-nacos,将db文件下面base-nacos.sql文件通过navicat导入表和数据
2.创建mysql数据库base-boot,将db文件下面base-boot.sql文件通过navicat导入表和数据
3.检查nacos数据库连接 可在c:\windows\system32\drivers\etc\hosts查看链接路径，如图：

4.最先启动nacos,启动后访问nacos地址进行程序数据库链接配置。

5.启动gateway网关
6.程序依次启动即可

代码所在位置描述

1.业务代码存在base-cloud-module下面的base-cloud-busin业务模块中
2.入库、理货、上架、下单、库存、波次、拣货、复核、称重、发货存在base-cloud-busin\src\main\java\com\base\modules\**
3.图片解析
（1）入库base-cloud-busin\src\main\java\com\base\modules\jeewms\controller

(2)出库base-cloud-busin\src\main\java\com\base\modules\jeewms\controller

(3)库存、理货任务在报表中


找到项目下的.idea/workspace.xml，在标签<component name="PropertiesComponent">里添加一行属性：<property name="dynamic.classpath" value="true" />
<component name="PropertiesComponent">
 其它属性不改
 <property name="dynamic.classpath" value="true" />
</component>

nacos，gateway，xxljob,bussi，system

Zzerp123.


