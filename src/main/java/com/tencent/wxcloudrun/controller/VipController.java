package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.service.impl.VipServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 和vip相关的交互：充值、提现
 *
 * @author wuzzi
 */
@RestController
public class VipController {

    final Logger logger = LoggerFactory.getLogger(VipController.class);

    @Resource
    private VipServiceImpl vipService;

    /**
     * 充值完成，注册成为会员
     */
    @PostMapping(value = "/vip/beVip")
    ApiResponse beVip(@RequestParam String wechatAccount,
                      @RequestParam String userName,
                      @RequestParam String friendWechatAccount) {
        logger.info("/vip/beVip get request");

        boolean beVip = vipService.beVip(wechatAccount, userName, friendWechatAccount);
        return beVip ? ApiResponse.ok("注册会员成功") : ApiResponse.error("注册会员失败");
    }

    /**
     * 申请提现
     */
    @PostMapping(value = "/vip/doCash")
    ApiResponse doCash(@RequestParam String wechatAccount) {
        logger.info("/vip/doCash get request");

        boolean doCash = vipService.doCash(wechatAccount);
        return doCash ? ApiResponse.ok("申请提现成功，具体进度请到审批记录页面查看") : ApiResponse.error("申请提现失败");
    }

}
