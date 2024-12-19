package com.base.modules.jeewms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.base.modules.jeewms.entity.WmDayCostConf;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 计费日期
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
public interface WmDayCostConfMapper extends BaseMapper<WmDayCostConf> {

    //删除计费数据
    public boolean deleteBydate(@Param("costdate") String costdate);
    //费用10
    public List<Map<String, Object>> getcost10(@Param("costdate") String costdate);

    public List<Map<String, Object>> getcostconf(
            @Param("costcode") String costcode,
            @Param("cuscode") String cuscode,
            @Param("costdate") String costdate
    );
    //费用20
    public List<Map<String, Object>> getcost20(@Param("costdate") String costdate);

    //费用30
    public List<Map<String, Object>> getcost30(@Param("costdate") String costdate);

    //费用40
    public List<Map<String, Object>> getcost40(@Param("costdate") String costdate);

    //费用50
    public List<Map<String, Object>> getcost50(@Param("costdate") String costdate);

    //费用60
    public List<Map<String, Object>> getcost60(@Param("costdate") String costdate);

    //费用70
    public List<Map<String, Object>> getcost70(@Param("costdate") String costdate);

    //费用80
    public List<Map<String, Object>> getcost80(@Param("costdate") String costdate);

    //账单
    public List<Map<String, Object>> resultbill(@Param("costjs")String costjs,
                                            @Param("cuscode") String cuscode,
                                            @Param("begindate")String begindate,
                                            @Param("enddate")String enddate);
    //账单
    public List<Map<String, Object>> resultmx(@Param("costjs")String costjs,
                                              @Param("cuscode") String cuscode,
                                              @Param("begindate")String begindate,
                                              @Param("enddate")String enddate);
    //账单
    public List<Map<String, Object>> resultsum(@Param("costjs")String costjs,
                                               @Param("cuscode") String cuscode,
                                               @Param("begindate")String begindate,
                                               @Param("enddate")String enddate);
    //账单
    public List<Map<String, Object>> resultcountsum(@Param("costjs")String costjs,
                                                    @Param("cuscode") String cuscode,
                                                    @Param("begindate")String begindate,
                                                    @Param("enddate")String enddate);

}
