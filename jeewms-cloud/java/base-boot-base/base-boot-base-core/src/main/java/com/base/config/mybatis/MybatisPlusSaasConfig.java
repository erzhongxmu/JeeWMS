package com.base.config.mybatis;

import java.util.ArrayList;
import java.util.List;

import com.base.common.util.oConvertUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

/**
 * 单数据源配置（base.datasource.open = false时生效）
 * @Author zhoujf
 *
 */
@Configuration
@MapperScan(value={"org.jeecg.modules.**.mapper*","com.base.modules.**.mapper*"})
public class MybatisPlusSaasConfig {
    /**
     * tenant_id 字段名
     */
    private static final String TENANT_FIELD_NAME = "tenant_id";
    /**
     * 哪些表需要做多租户 表需要添加一个字段 tenant_id
     */
    private static final List<String> tenantTable = new ArrayList<String>();
    static {
       /* tenantTable.add("md_cus");
        tenantTable.add("wm_his_stock");
        tenantTable.add("wm_his_stock_data");
        tenantTable.add("wm_his_stock_ku");
        tenantTable.add("wm_im_notice_h");
        tenantTable.add("wm_im_notice_i");
        tenantTable.add("wm_in_qm_h");
        tenantTable.add("wm_in_qm_i");
        tenantTable.add("wm_om_notice_h");
        tenantTable.add("wm_om_notice_i");
        tenantTable.add("wm_om_qm_i");
        tenantTable.add("wm_plat_io");
        tenantTable.add("wm_stt_in_goods");
        tenantTable.add("wm_to_down_goods");
        tenantTable.add("wm_to_move_goods");
        tenantTable.add("wm_to_up");
        tenantTable.add("wm_to_up_goods");

        tenantTable.add("ba_store");
        tenantTable.add("ba_store_area");
        tenantTable.add("md_bin");
        tenantTable.add("md_cus_other");
        tenantTable.add("md_goods");
        tenantTable.add("md_goods_item");
        tenantTable.add("ba_part_type");
        tenantTable.add("md_sup");
        tenantTable.add("ba_order_type");
        tenantTable.add("ba_buss_type");
        tenantTable.add("ba_act_type");
        tenantTable.add("ba_oper_step");
        tenantTable.add("ba_lad_mode");
        tenantTable.add("ba_del_mode");
        tenantTable.add("ba_bin_type");
        tenantTable.add("ba_deg_type");*/
    }



    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                String tenant_id = oConvertUtils.getString(TenantContext.getTenant(),"0");
                return new LongValue(tenant_id);
            }

            @Override
            public String getTenantIdColumn(){
                return TENANT_FIELD_NAME;
            }

            // 返回 true 表示不走租户逻辑
            @Override
            public boolean ignoreTable(String tableName) {
                for(String temp: tenantTable){
                    if(temp.equalsIgnoreCase(tableName)){
                        return false;
                    }
                }
                return true;
            }
        }));
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }


//    /**
//     * 下个版本会删除，现在为了避免缓存出现问题不得不配置
//     * @return
//     */
//    @Bean
//    public ConfigurationCustomizer configurationCustomizer() {
//        return configuration -> configuration.setUseDeprecatedExecutor(false);
//    }
//    /**
//     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
//     */
//    @Bean
//    public PerformanceInterceptor performanceInterceptor() {
//        return new PerformanceInterceptor();
//    }

}
