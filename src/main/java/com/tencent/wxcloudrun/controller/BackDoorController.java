package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.service.impl.BackDoorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuzzi
 */
@RestController
public class BackDoorController {

    final Logger logger = LoggerFactory.getLogger(BackDoorController.class);

    @Resource
    private BackDoorServiceImpl backDoorService;

    /**
     * 审核提现记录
     */
    @PostMapping(value = "/doCash/audit")
    ApiResponse addUser(@RequestParam Integer recordId,
                        @RequestParam Integer status) {
        logger.info("/doCash/audit get request");

        boolean finishCashAudit = backDoorService.finishCashAudit(recordId, status);
        return finishCashAudit ? ApiResponse.ok("审核完成") : ApiResponse.error("审核失败");
    }

}
