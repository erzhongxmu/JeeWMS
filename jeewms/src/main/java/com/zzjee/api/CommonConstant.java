package com.zzjee.api;

import java.math.BigDecimal;

public interface CommonConstant {

	/**
	 * 正常状态
	 */
	public static final Integer STATUS_NORMAL = 0;

	/**
	 * 禁用状态
	 */
	public static final Integer STATUS_DISABLE = -1;

	/**
	 * 删除标志
	 */
	public static final Integer DEL_FLAG_1 = 1;

	/**
	 * 未删除
	 */
	public static final Integer DEL_FLAG_0 = 0;

	/**
	 * 系统日志类型： 登录
	 */
	public static final int LOG_TYPE_1 = 1;

	/**
	 * 系统日志类型： 操作
	 */
	public static final int LOG_TYPE_2 = 2;

	/**
	 * 操作日志类型： 查询
	 */
	public static final int OPERATE_TYPE_1 = 1;

	/**
	 * 操作日志类型： 添加
	 */
	public static final int OPERATE_TYPE_2 = 2;

	/**
	 * 操作日志类型： 更新
	 */
	public static final int OPERATE_TYPE_3 = 3;

	/**
	 * 操作日志类型： 删除
	 */
	public static final int OPERATE_TYPE_4 = 4;

	/**
	 * 操作日志类型： 倒入
	 */
	public static final int OPERATE_TYPE_5 = 5;

	/**
	 * 操作日志类型： 导出
	 */
	public static final int OPERATE_TYPE_6 = 6;


	/** {@code 500 Server Error} (HTTP/1.0 - RFC 1945) */
    public static final Integer SC_INTERNAL_SERVER_ERROR_500 = 500;
    /** {@code 200 OK} (HTTP/1.0 - RFC 1945) */
    public static final Integer SC_OK_200 = 200;

    /**访问权限认证未通过 510*/
    public static final Integer SC_BASE_NO_AUTHZ=510;

    /** 登录用户Shiro权限缓存KEY前缀 */
    public static String PREFIX_USER_SHIRO_CACHE  = "shiro:cache:com.base.config.shiro.ShiroRealm.authorizationCache:";
    /** 登录用户Token令牌缓存KEY前缀 */
    public static final String PREFIX_USER_TOKEN  = "prefix_user_token_";
    /** Token缓存时间：3600秒即一小时 */
    public static final int  TOKEN_EXPIRE_TIME  = 3600;


    /**
     *  0：一级菜单
     */
    public static final Integer MENU_TYPE_0  = 0;
   /**
    *  1：子菜单
    */
    public static final Integer MENU_TYPE_1  = 1;
    /**
     *  2：按钮权限
     */
    public static final Integer MENU_TYPE_2  = 2;

    /**通告对象类型（USER:指定用户，ALL:全体用户）*/
    public static final String MSG_TYPE_UESR  = "USER";
    public static final String MSG_TYPE_ALL  = "ALL";

    /**发布状态（0未发布，1已发布，2已撤销）*/
    public static final String NO_SEND  = "0";
    public static final String HAS_SEND  = "1";
    public static final String HAS_CANCLE  = "2";

    /**阅读状态（0未读，1已读）*/
    public static final String HAS_READ_FLAG  = "1";
    public static final String NO_READ_FLAG  = "0";

    /**优先级（L低，M中，H高）*/
    public static final String PRIORITY_L  = "L";
    public static final String PRIORITY_M  = "M";
    public static final String PRIORITY_H  = "H";

    /**
     * 短信模板方式  0 .登录模板、1.注册模板、2.忘记密码模板
     */
    public static final String SMS_TPL_TYPE_0  = "0";
    public static final String SMS_TPL_TYPE_1  = "1";
    public static final String SMS_TPL_TYPE_2  = "2";

    /**
     * 状态(0无效1有效)
     */
    public static final String STATUS_0 = "0";
    public static final String STATUS_1 = "1";

    /**
     * 同步工作流引擎1同步0不同步
     */
    public static final Integer ACT_SYNC_1 = 1;
    public static final Integer ACT_SYNC_0 = 0;

    /**
     * 消息类型1:通知公告2:系统消息
     */
    public static final String MSG_CATEGORY_1 = "1";
    public static final String MSG_CATEGORY_2 = "2";

    /**
     * 是否配置菜单的数据权限 1是0否
     */
    public static final Integer RULE_FLAG_0 = 0;
    public static final Integer RULE_FLAG_1 = 1;

    /**
     * 是否用户已被冻结 1正常(解冻) 2冻结
     */
    public static final Integer USER_UNFREEZE = 1;
    public static final Integer USER_FREEZE = 2;

    /**字典翻译文本后缀*/
    public static final String DICT_TEXT_SUFFIX = "_dictText";

    /**
     * 表单设计器主表类型
     */
    public static final Integer DESIGN_FORM_TYPE_MAIN = 1;

    /**
     * 表单设计器子表表类型
     */
    public static final Integer DESIGN_FORM_TYPE_SUB = 2;

    /**
     * 表单设计器URL授权通过
     */
    public static final Integer DESIGN_FORM_URL_STATUS_PASSED = 1;

    /**
     * 表单设计器URL授权未通过
     */
    public static final Integer DESIGN_FORM_URL_STATUS_NOT_PASSED = 2;

    /**
     * 表单设计器新增 Flag
     */
    public static final String DESIGN_FORM_URL_TYPE_ADD = "add";
    /**
     * 表单设计器修改 Flag
     */
    public static final String DESIGN_FORM_URL_TYPE_EDIT = "edit";
    /**
     * 表单设计器详情 Flag
     */
    public static final String DESIGN_FORM_URL_TYPE_DETAIL = "detail";
    /**
     * 表单设计器复用数据 Flag
     */
    public static final String DESIGN_FORM_URL_TYPE_REUSE = "reuse";
    /**
     * 表单设计器编辑 Flag （已弃用）
     */
    public static final String DESIGN_FORM_URL_TYPE_VIEW = "view";

    /**
     * online参数值设置（是：Y, 否：N）
     */
    public static final String ONLINE_PARAM_VAL_IS_TURE = "Y";
    public static final String ONLINE_PARAM_VAL_IS_FALSE = "N";

    /**
     * 文件上传类型（本地：local，Minio：minio，阿里云：alioss）
     */
    public static final String UPLOAD_TYPE_LOCAL = "local";
    public static final String UPLOAD_TYPE_MINIO = "minio";
    public static final String UPLOAD_TYPE_OSS = "alioss";

    /**
     * 文档上传自定义桶名称
     */
    public static final String UPLOAD_CUSTOM_BUCKET = "eoafile";
    /**
     * 文档上传自定义路径
     */
    public static final String UPLOAD_CUSTOM_PATH = "eoafile";
    /**
     * 文件外链接有效天数
     */
    public static final Integer UPLOAD_EFFECTIVE_DAYS = 1;

    /**
     * 员工身份 （1:普通员工  2:上级）
     */
    public static final Integer USER_IDENTITY_1 = 1;
    public static final Integer USER_IDENTITY_2 = 2;

    /** sys_user 表 username 唯一键索引 */
    public static final String SQL_INDEX_UNIQ_SYS_USER_USERNAME = "uniq_sys_user_username";
    /** sys_user 表 work_no 唯一键索引 */
    public static final String SQL_INDEX_UNIQ_SYS_USER_WORK_NO = "uniq_sys_user_work_no";
    /** sys_user 表 phone 唯一键索引 */
    public static final String SQL_INDEX_UNIQ_SYS_USER_PHONE = "uniq_sys_user_phone";
    /** sys_user 表 email 唯一键索引 */
    public static final String SQL_INDEX_UNIQ_SYS_USER_EMAIL = "uniq_sys_user_email";
    /** sys_quartz_job 表 job_class_name 唯一键索引 */
    public static final String SQL_INDEX_UNIQ_JOB_CLASS_NAME = "uniq_job_class_name";
    /** sys_position 表 code 唯一键索引 */
    public static final String SQL_INDEX_UNIQ_CODE = "uniq_code";
    /** sys_role 表 code 唯一键索引 */
    public static final String SQL_INDEX_UNIQ_SYS_ROLE_CODE = "uniq_sys_role_role_code";
    /** sys_depart 表 code 唯一键索引 */
    public static final String SQL_INDEX_UNIQ_DEPART_ORG_CODE = "uniq_depart_org_code";
    /**
     * 在线聊天 是否为默认分组
     */
    public static final String IM_DEFAULT_GROUP = "1";
    /**
     * 在线聊天 图片文件保存路径
     */
    public static final String IM_UPLOAD_CUSTOM_PATH = "imfile";
    /**
     * 在线聊天 用户状态
     */
    public static final String IM_STATUS_ONLINE = "online";

    /**
     * 在线聊天 SOCKET消息类型
     */
    public static final String IM_SOCKET_TYPE = "chatMessage";

    /**
     * 在线聊天 是否开启默认添加好友 1是 0否
     */
    public static final String IM_DEFAULT_ADD_FRIEND = "1";

    /**
     * 在线聊天 用户好友缓存前缀
     */
    public static final String IM_PREFIX_USER_FRIEND_CACHE = "sys:cache:im:im_prefix_user_friend_";

    /**
     * 考勤补卡业务状态 （1：同意  2：不同意）
     */
    public static final String SIGN_PATCH_BIZ_STATUS_1 = "1";
    public static final String SIGN_PATCH_BIZ_STATUS_2 = "2";

    /**
     * 公文文档上传自定义路径
     */
    public static final String UPLOAD_CUSTOM_PATH_OFFICIAL = "officialdoc";
     /**
     * 公文文档下载自定义路径
     */
    public static final String DOWNLOAD_CUSTOM_PATH_OFFICIAL = "officaldown";

    /**
     * WPS存储值类别(1 code文号 2 text（WPS模板还是公文发文模板）)
     */
    public static final String WPS_TYPE_1="1";
    public static final String WPS_TYPE_2="2";


    public final static String X_ACCESS_TOKEN = "X-Access-Token";
    public final static String X_SIGN = "X-Sign";
    public final static String X_TIMESTAMP = "X-TIMESTAMP";

    /**
     * 多租户 请求头
     */
    public final static String TENANT_ID = "tenant-id";

    /**
     * 微服务读取配置文件属性 服务地址
     */
    public final static String CLOUD_SERVER_KEY = "spring.cloud.nacos.discovery.server-addr";

    /**
     * 第三方登录 验证密码/创建用户 都需要设置一个操作码 防止被恶意调用
     */
    public final static String THIRD_LOGIN_CODE = "third_login_code";

    /**
     * 第三方APP同步方向：本地 --> 第三方APP
     */
    String THIRD_SYNC_TO_APP = "SYNC_TO_APP";
    /**
     * 第三方APP同步方向：第三方APP --> 本地
     */
    String THIRD_SYNC_TO_LOCAL = "SYNC_TO_LOCAL";

    /** 系统通告消息状态：0=未发布 */
    String ANNOUNCEMENT_SEND_STATUS_0 = "0";
    /** 系统通告消息状态：1=已发布 */
    String ANNOUNCEMENT_SEND_STATUS_1 = "1";
    /** 系统通告消息状态：2=已撤销 */
    String ANNOUNCEMENT_SEND_STATUS_2 = "2";

    /** 任务配置缓存前缀 */
    public static final String PREFIX_TASK_CONF  = "prefix_task_conf_";
    public static final String DATE_UNIT_DD  = "dd";
    public static final String DATE_UNIT_MM  = "mm";
    public static final String ONE_HOUR  = 60 * 60 * 1000 + "";
    public static final String ONE_MINUTE  = 60 * 1000 + "";

    String PREFIX_REDIS_CONF = "prefix_redis_conf_";

    /** 任务类型*/

    String TASK_TYPE_NARU = "naru";

    String TASK_TYPE_RUKU = "ruku";

    String TASK_TYPE_NARU_FULFIL = "naru_fulfil";
    /** 任务类型*/
    String TASK_TYPE_PEISONG = "peisong";
    /** 任务类型*/
    String TASK_TYPE_SHANGJIA = "shangjia";
    /** 任务类型*/
    String TASK_TYPE_FALIAO = "faliao";
 /** 任务类型*/
 String TASK_TYPE_RECHECH = "JHRW";
    /** 任务状态*/
    String TASK_STATUS_YWC = "ywc";
    /** 任务状态*/
    String TASK_STATUS_WWC = "wwc";
    /** 库存类型*/
    String STOCK_STATUS_ZT = "zt";
    /** 库存类型*/
    String STOCK_STATUS_ZW = "zw";
    /** 库存类型*/
    String STOCK_STATUS_LP = "lp";
    String STOCK_STATUS_BLP = "blp";
    /** 库存类型*/
    String STOCK_STATUS_JY = "jy";
    /** 库存类型*/
    String STOCK_API_STATUS_ZY = "zy";  //正在使用
    /** 库存类型*/
    String STOCK_API_STATUS_WL = "wl";//未来计划需求
    /** 库存类型*/
    String STOCK_API_STATUS_ZK = "zk";//在库库存
    String STOCK_API_STATUS_ZT = "zt";//在途库存


    /** 资源状态*/
    String RESOURCE_STATUS_OCCUPY = "occupy";
    String RESOURCE_STATUS_FETCH = "fetch";
    String TASK_TYPE_JIANYAN = "jianyan";
    String TASK_TYPE_JIANYAN_FULFIL = "jianyan_fulfil";
    String TASK_TYPE_FALIAO_ONE = "faliao_one";
    String TASK_TYPE_FALIAO_FULFIL = "faliao_fulfil";
    String TASK_TYPE_JIANYAN_SJ_FULFIL = "jianyan_sj_fulfil";
    BigDecimal STOCK_FLAG = new BigDecimal(-1);
    String GET_CONF_CODE = "get_conf_code";
    String TABLE_NAME_BUSI_IM = "busi_im";
    String TABLE_NAME_BMS_BILL = "bms_bill";
    String TABLE_NAME_BMS_INVOICE = "bms_invoice";
    String TABLE_NAME_BMS_PAY = "bms_pay";
    String TABLE_NAME_BMS_BILL_STATUS_0 = "0";//已生成
    String TABLE_NAME_BMS_BILL_STATUS_1 = "1";//已审核
    String TABLE_NAME_BMS_BILL_STATUS_2 = "2";//已发送
    String TABLE_NAME_BMS_BILL_STATUS_3 = "3";//已确认
    String TABLE_NAME_BMS_BILL_STATUS_4 = "4";//已完成
    String TABLE_NAME_BMS_BILL_STATUS_5 = "5";//已开票
    String TABLE_NAME_BASE_BOM = "BASE_BOM";
    String TABLE_NAME_BUSI_OM = "busi_om";
    String TABLE_NAME_BUSI_PLAN = "busi_plan";
    String TABLE_NAME_BUSI_WORK = "busi_work";
    String TABLE_NAME_BUSI_TASK_IM = "busi_task_im";
    String TASK_TYPE_JIANYAN_ONE = "jianyan_one";

    String TASK_TYPE_FANBAO_STACK_ADD = "fanbao_stackAdd";
    String TASK_TYPE_FANBAO_STACK_FULFIL = "fanbao_fulfil";
    String TASK_TYPE_JIPEI_STACK_FULFIL = "jipei_fulfil";
    String TASK_TYPE_JIPEI_STACK_ONE = "jipei_one";
    String TASK_TYPE_JIPEI = "jipei";
    String TASK_TYPE_SHANGXIAN_STACK_ONE = "shangxian_one";
    String TASK_TYPE_SHANGXIAN_STACK_FULFIL = "shangxian_fulfil";
    String TASK_TYPE_SHANGXIAN = "shangxian";
    String STOCK_STATUS_KY = "ky";
    String PLAN_TYPE_CANCEL = "cancel";
 String STOCK_STATUS_ZY = "zy";
    String TABLE_NAME_OT_LY_SCRAP = "ot_ly_scrap";
    String TABLE_NAME_OT_LY_NG_REASON = "ot_ly_ng_reason";
    String TABLE_NAME_OT_LY_OPER = "ot_ly_oper";
    String TABLE_NAME_OT_LY_RELDEP = "ot_ly_reldep";
    String TABLE_NAME_BUSI_BUSI_TASK_YIKU = "busi_task_yiku";
    String TABLE_NAME_BUSI_UNNORMAL = "busi_unnormal";
    String TASK_TYPE_YUEKU = "yueku";
    String FALIAO_IS_FULFILL_JH = "整托拣货";
    String TASK_TYPE_YIKU = "yiku";
    String TABLE_NAME_BUSI_DAY_PLAN = "busi_day_plan";
    String BASE_STORETYPE_XIANBIAN = "1000";

    String BC_WDH = "wdh";
    String BC_MERGE = "merge";
    String PANDIAN_TYPE_DONGTAIMINGPAN = "dongtaimingpan";
    String PANDIAN_TYPE_DONGTAIANPAN = "dongtaianpan";
    String PANDIAN_TYPE_JINGTAIMINGPAN = "jingtaimingpan";
    String PANDIAN_TYPE_JINGTAIANPAN = "jingtaianpan";
    String ACT_LC_KEY_ZHPD = "zhpd";
    String ACT_LC_KEY_SBWX = "sbwx";
    String ACT_LC_KEY_ZDGL = "zdgl";
    String ACT_LC_KEY_KCDJ = "kcdj";
    String ACT_LC_KEY_YCRW = "ycrw";
    String ACT_LC_KEY_HTGL = "htgl";
    String TABLE_NAME_BUSI_PANDIAN_CMPRE = "busi_pandian_cmpre";
    String TABLE_NAME_OT_MAC_TASK = "ot_mac_task";
    String TABLE_NAME_BMS_COST_DETAIL = "bms_cost_detail";
    String TABLE_NAME_BUSI_STOCK_FREEZE = "busi_stock_freeze";
    String TABLE_NAME_CONT_MAIN = "cont_main";
    String TABLE_NAME_BUSI_TASK_PANDIAN = "busi_task_pandian";
    String TASK_TYPE_MINGPAN = "mingpan";
    String TASK_TYPE_ANPAN = "anpan";
    String TASK_TYPE_FUPAN = "fupan";
    String TASK_TYPE_ZIZHUPAN = "zizhupan";
    String USER_CONF_TYPE_DATA_AUTH = "dataAuth";
    String USER_DATA_AUTH_ADMIN = "admin";
    String ACT_SP_STATUS = "审批通过";
    String ACT_CONTMAIN_STATUS = "审批中";
    String ACT_LC_KEY_HTYJ = "htyj";
    String ACT_LC_KEY_SQRGL = "sqrgl";
    String TABLE_NAME_BASE_AUTH_MANA = "base_auth_mana";
    String ACT_LC_KEY_GYSFJ = "gysfj";
    String TABLE_NAME_BASE_SUPPLY_FILE = "base_supply_file";
    String PREFIX_BOX_XH = "XH";
    String PREFIX_STACK_TP = "TP";
    String ACT_LC_KEY_JDKC = "jdkc";
    String STOCK_STATUS_TH = "th";
    String TASK_STATUS_YRC = "yrc";
    String TASK_STATUS_DSX = "dsx";
    String TASK_TYPE_OM = "om";
    String TABLE_NAME_BUSI_TASK_SHENGJU = "busi_task_shengju";
    String TASK_TYPE_HUIKONG = "huikong";
    String TASK_TYPE_HUIKONG_FULFIL = "huikong_fulfil";
    String RESOURCE_STATUS_RELEASE = "release";
    String IM_TYPE_FXCK = "fxck";
    String IM_TYPE_GYSTH = "gysth";
    String TABLE_NAME_BUSI_STOCK_TRIM_LOG = "busi_stock_trim_log";
    String ACT_LC_KEY_TZSP = "tzsp";
    String UNNORMAL_NAME = "帐实不符";
    String TABLE_NAME_BUSI_STOCK = "busi_stock";
    String STOCK_UNIQUE_NO = "unique_no";
}
