package org.jeecg.common.system.api;

import com.alibaba.fastjson.JSONObject;
import com.base.common.system.vo.LoginUser;
import org.jeecg.common.api.vo.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @author: scott
 * @date: 2020年05月21日 14:32
 */
@Component
//@FeignClient(contextId = "sysBaseRemoteApi", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = SysBaseRemoteApiFallbackFactory.class)
public interface SysBaseRemoteApi {

    @GetMapping("/sys/user/info/{username}")
    Result<LoginUser> getUserByName(@PathVariable("username") String username);

    /**
     * 保存日志
     * @param jsonObject
     */
    @PostMapping("/sys/log/save")
    void saveSysLog(@RequestBody JSONObject jsonObject);

    /**
     * 通过编码和存储的value查询字典text、
     * @return
     */
    @GetMapping("/sys/user/queryDictTextByKey")
    String queryDictTextByKey(@RequestParam("code") String code, @RequestParam("key") String key);

    /**
     * 通过编码和存储的value查询表字典的text
     * @param table 表名
     * @param text  表字段
     * @param code  表字段
     * @param key   表字段code的值
     * @return
     */
    @GetMapping("/sys/user/queryTableDictTextByKey")
    String queryTableDictTextByKey(@RequestParam("table") String table, @RequestParam("text") String text, @RequestParam("code") String code, @RequestParam("key") String key);


}
