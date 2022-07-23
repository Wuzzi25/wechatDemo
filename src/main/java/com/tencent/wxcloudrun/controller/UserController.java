package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.UserVo;
import com.tencent.wxcloudrun.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 进入小程序的整体交互、个人中心页面
 *
 * @author wuzzi
 */
@Controller
public class UserController {

    final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserServiceImpl userService;

    /**
     * 拿到用户的授权之后，新增一个用户
     */
    @PostMapping(value = "/user/add")
    ApiResponse addUser(@RequestParam String wechatAccount,
                        @RequestParam String wechatName,
                        @RequestParam String userPhone) {
        logger.info("/user/add get request");

        Optional<UserVo> userVo = Optional.ofNullable(userService.addUser(wechatAccount, wechatName, userPhone));
        return userVo.isPresent() ? ApiResponse.ok(userVo) : ApiResponse.error("创建用户信息失败");
    }

    /**
     * 根据微信号获取用户信息
     *
     * @param wechatAccount 微信号
     */
    @GetMapping(value = "/user/query/v1")
    ApiResponse queryUserV1(@RequestParam String wechatAccount) {
        logger.info("/user/query/v1 get request");

        Optional<UserVo> userVo = Optional.ofNullable(userService.queryUserByWechatAccount(wechatAccount));
        return userVo.isPresent() ? ApiResponse.ok(userVo) : ApiResponse.error("用户不存在");
    }

    /**
     * 根据用户编码获取用户信息
     *
     * @param userCode 用户编码
     */
    @GetMapping(value = "/user/query/v2")
    ApiResponse queryUserV2(@RequestParam String userCode) {
        logger.info("/user/query/v2 get request");

        Optional<UserVo> userVo = Optional.ofNullable(userService.queryUserByUserCode(userCode));
        return userVo.isPresent() ? ApiResponse.ok(userVo) : ApiResponse.error("用户不存在");
    }

}
