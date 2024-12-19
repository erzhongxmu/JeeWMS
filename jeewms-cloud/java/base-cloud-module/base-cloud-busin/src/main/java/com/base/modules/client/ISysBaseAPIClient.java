package com.base.modules.client;

import com.base.common.api.vo.Result;
import com.base.modules.jeeerp.entity.BaseScheduleInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@Component
@FeignClient(value = "base-system")
public interface ISysBaseAPIClient {

    @RequestMapping(value = "/sys/sysbaseapi/sendSysAnnouncement", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    String sendSysAnnouncement(@RequestParam("fromUser") String fromUser,
                               @RequestParam("toUser") String toUser,
                               @RequestParam("title") String title,
                               @RequestParam("msgContent") String msgContent);

    /**
     * 查询入库预约单据
     * @param id
     * @return
     */
    @RequestMapping(value = "/sys/user/list")
    Result<?> queryPageList(@RequestParam(name="id",required=true) String id);


    /**
     * 新增排班计划
     * @param
     * @return
     */
    @RequestMapping(value = "/sys/baseScheduleInfo/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    Result<?> add(@RequestBody BaseScheduleInfo baseScheduleInfo);

    /**
     * 查询排班计划
     * @param
     * @return
     */
    @RequestMapping(value = "/sys/baseScheduleInfo/list")
    Result<?> queryPageList2(@RequestParam(name="attr5",required=true) String attr5,
                                   @RequestParam(name="pageSize", required=true) Integer pageSize );

    /**
     * 刪除排班计划
     * @param
     * @return
     */
    @RequestMapping(value = "/sys/baseScheduleInfo/deleteBatch",method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids);
}


