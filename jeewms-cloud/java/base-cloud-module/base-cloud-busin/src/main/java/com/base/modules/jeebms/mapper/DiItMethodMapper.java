package com.base.modules.jeebms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.base.modules.jeebms.entity.DiItMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Description: di_it_method
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
public interface DiItMethodMapper extends BaseMapper<DiItMethod> {
    /**
     * 传递sql执行
     * @param sql
     * @return
     */
    @Select("${sql}")
    List<Map<String, Object>> creatDatacoll(@Param("sql") String sql);

    @Select("select count(*) from ${tableName} where id = #{id}")
    int getByTableNameId(@Param("tableName")String tableName,@Param("id") String id);
}
