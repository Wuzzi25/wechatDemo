package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.constants.CommonConstant;
import com.tencent.wxcloudrun.dao.AccountMapper;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.entity.AccountEntity;
import com.tencent.wxcloudrun.entity.UserEntity;
import com.tencent.wxcloudrun.model.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;

/**
 * @author wuzzi
 */
@Service
public class UserServiceImpl {

    final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;
    @Resource
    private AccountMapper accountMapper;

    /**
     * 新增用户，授权之后调用
     */
    public UserVo addUser(String wechatAccount, String wechatName, String userPhone) {
        logger.info("addUser_{}_{}_{}", wechatAccount, wechatName, userPhone);

        // 已经存在了，直接返回
        UserEntity userEntity = userMapper.queryByWechatAccount(wechatAccount);
        if (Objects.nonNull(userEntity)) {
            logger.warn("addUser user is exists_{}_{}", wechatAccount, userEntity.getUserCode());
            return convert2Vo(userEntity);
        }

        // 新增一条记录
        // TODO chenjie 调用第三方生成二维码
        String qrCode = "";
        userEntity = UserEntity.builder()
                .userCode(UUID.randomUUID().toString())
                .wechatAccount(wechatAccount)
                .wechatName(wechatName)
                .userName(wechatName)
                .userPhone(userPhone)
                .vipFlag(CommonConstant.VipFlag.NORMAL_USER)
                .qrCode(qrCode)
                .build();
        userMapper.save(userEntity);

        // 初始化一个账户
        AccountEntity accountEntity = AccountEntity.builder()
                .userCode(userEntity.getUserCode())
                .build();
        accountMapper.save(accountEntity);

        return convert2Vo(userEntity);
    }

    /**
     * 查询用户
     */
    public UserVo queryUserByUserCode(String userCode) {
        return convert2Vo(userMapper.queryByUserCode(userCode));
    }

    /**
     * 查询用户
     */
    public UserVo queryUserByWechatAccount(String wechatAccount) {
        return convert2Vo(userMapper.queryByWechatAccount(wechatAccount));
    }

    private static UserVo convert2Vo(UserEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return UserVo.builder()
                .userCode(entity.getUserCode())
                .isVip(isVip(entity.getVipFlag()))
                .qrCode(entity.getQrCode())
                .build();
    }

    private static boolean isVip(int vipFlag) {
        return vipFlag == CommonConstant.VipFlag.VIP || vipFlag == CommonConstant.VipFlag.SUPER_VIP;
    }

}
