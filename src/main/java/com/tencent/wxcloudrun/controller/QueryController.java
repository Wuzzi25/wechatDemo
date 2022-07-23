package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.service.impl.QueryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * 查询列表相关，全部用get了
 * @author wuzzi
 */
@Controller
public class QueryController {

    final Logger logger = LoggerFactory.getLogger(QueryController.class);

    @Resource
    private QueryServiceImpl queryService;

    /**
     * 查询账号余额记录
     */
    @GetMapping(value = "/query/account/number")
    ApiResponse queryAccountNumber(@RequestParam String userCode) {
        logger.info("/query/account/number get request");
        return ApiResponse.ok(queryService.queryAccountBalance(userCode));
    }

    /**
     * 账号余额列表
     */
    @GetMapping(value = "/query/account/list")
    ApiResponse queryAccountList(@RequestParam String userCode) {
        logger.info("/query/account/list get request");
        return ApiResponse.ok(queryService.queryAccountHistoryList(userCode));
    }

    /**
     * 推荐列表
     */
    @GetMapping(value = "/query/recommend/list")
    ApiResponse queryRecommendList(@RequestParam String userCode) {
        logger.info("/query/recommend/list get request");
        return ApiResponse.ok(queryService.queryRecommendList(userCode));
    }

}
