package com.base.modules.biconf.biapi.dto;


/**
 * 通用常量
 *
 * @author Chill
 */
public interface LauncherConstant {

    /**
     * nacos namespace id
     */
    String NACOS_NAMESPACE = "f447a694-519a-4255-95f9-bcbb5a5d6369";

    /**
     * nacos dev 地址
     */
    String NACOS_DEV_ADDR = "127.0.0.1:8848";

    /**
     * nacos prod 地址
     */
    String NACOS_PROD_ADDR = "172.30.0.48:8848";

    /**
     * nacos test 地址
     */
    String NACOS_TEST_ADDR = "172.30.0.48:8848";

    /**
     * sentinel dev 地址
     */
    String SENTINEL_DEV_ADDR = "127.0.0.1:8858";

    /**
     * sentinel prod 地址
     */
    String SENTINEL_PROD_ADDR = "172.30.0.58:8858";

    /**
     * sentinel test 地址
     */
    String SENTINEL_TEST_ADDR = "172.30.0.58:8858";

    /**
     * seata dev 地址
     */
    String SEATA_DEV_ADDR = "127.0.0.1:8091";

    /**
     * seata prod 地址
     */
    String SEATA_PROD_ADDR = "172.30.0.68:8091";

    /**
     * seata test 地址
     */
    String SEATA_TEST_ADDR = "172.30.0.68:8091";

    /**
     * 数据大屏
     */
    String APPLICATION_VISUAL_NAME =   "/bi-visual";

    /**
     * seata file模式
     */
    String FILE_MODE = "file";

    /**
     * seata nacos模式
     */
    String NACOS_MODE = "nacos";

    /**
     * seata default模式
     */
    String DEFAULT_MODE = "default";

    /**
     * seata group后缀
     */
    String GROUP_NAME = "-group";



}

