package com.base.modules.system.controller;

import com.base.common.api.dto.message.MessageDTO;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/sysbaseapi")
public class SysBaseApiController {

    @Autowired
    private ISysBaseAPI sysBaseAPI;

    @GetMapping("/sendSysAnnouncement")
    public String sendSysAnnouncement(@RequestParam("fromUser") String fromUser,
                                      @RequestParam("toUser") String toUser,
                                      @RequestParam("title") String title,
                                      @RequestParam("msgContent") String msgContent) {
        MessageDTO dto = new MessageDTO();
        dto.setFromUser(fromUser);
        dto.setToUser(toUser);
        dto.setTitle(title);
        dto.setContent(msgContent);
        sysBaseAPI.sendSysAnnouncement(dto);
        return "{\"msg\":\"发送成功\"}";
    }
}
