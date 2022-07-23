package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.constants.CommonConstant;
import com.tencent.wxcloudrun.dao.AccountHistoryMapper;
import com.tencent.wxcloudrun.dao.AccountMapper;
import com.tencent.wxcloudrun.dao.RecommendMapper;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.entity.AccountEntity;
import com.tencent.wxcloudrun.entity.AccountHistoryEntity;
import com.tencent.wxcloudrun.entity.RecommendEntity;
import com.tencent.wxcloudrun.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 和vip相关的service：充值、提现
 *
 * @author wuzzi
 */
@Service
public class VipServiceImpl {

    final Logger logger = LoggerFactory.getLogger(VipServiceImpl.class);

    @Resource
    private UserMapper userMapper;
    @Resource
    private RecommendMapper recommendMapper;

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private AccountHistoryMapper accountHistoryMapper;

    /**
     * 充值会员之后成为vip，只有在用户授权成功之后可以调用
     *
     * @param userName 给用户填写的。页面展示以这个为准。
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean beVip(String wechatAccount, String userName, String friendWechatAccount) {
        logger.info("beVip_{}_{}", wechatAccount, friendWechatAccount);

        if (!StringUtils.hasLength(wechatAccount)) {
            logger.error("beVip fail_微信号不能为空");
            return false;
        }

        // step01. 如果用户表没有记录，代表没有授权过，那就不操作了
        UserEntity userEntity = userMapper.queryByWechatAccount(wechatAccount);
        if (Objects.isNull(userEntity)) {
            logger.error("beVip fail_{}_查找不到该微信号对应的记录,好友微信号:{}", wechatAccount, friendWechatAccount);
            return false;
        }

        boolean isBeRecommend = StringUtils.hasLength(friendWechatAccount);
        String friendUserCode = "";
        if (isBeRecommend) {
            UserEntity friendEntity = userMapper.queryByWechatAccount(friendWechatAccount);
            if (Objects.isNull(friendEntity)) {
                logger.error("beVip found friend fail_查找不到推荐源好友微信号{}对应的记录", friendWechatAccount);
                isBeRecommend = false;
            } else {
                friendUserCode = friendEntity.getUserCode();
            }
        }

        // step02. 更新用户为会员
        if (StringUtils.hasLength(userName)) {
            userEntity.setUserName(userName);
        }
        userEntity.setVipFlag(CommonConstant.VipFlag.VIP);
        userEntity.setIsRecommend(isBeRecommend ? CommonConstant.Flag.YES : CommonConstant.Flag.NO);
        userEntity.setRecommendUserCode(friendUserCode);
        userMapper.updateToVip(userEntity);

        String userCode = userEntity.getUserCode();
        logger.info("beVip success_{}_用户成功成为会员,账号{},好友账号:{}", wechatAccount, userCode, friendUserCode);

        // step03. 将推荐成功的记录，留痕
        if (isBeRecommend) {
            recommendMapper.save(RecommendEntity.builder()
                    .userCode(friendUserCode)
                    .beRecommendUserCode(userCode)
                    .build());

            // step04. 如果达到了3个好友，则新增奖励。
            List<RecommendEntity> hasNoRewardRecord = recommendMapper.queryHasNoReward(friendUserCode);
            if (hasNoRewardRecord.size() >= CommonConstant.REWARD_COUNT) {
                hasNoRewardRecord = hasNoRewardRecord.subList(0, 3);
                List<String> rewardFromFriendCodes = hasNoRewardRecord.stream().map(RecommendEntity::getBeRecommendUserCode).collect(Collectors.toList());
                List<Integer> rewardFromIds = hasNoRewardRecord.stream().map(RecommendEntity::getId).collect(Collectors.toList());
                logger.info("用户{}推荐好友成功数达到3个，{}，{}，触发奖励机制", friendUserCode, rewardFromIds.toArray(), rewardFromFriendCodes.toArray());

                // 账户新增一条奖励记录
                accountHistoryMapper.save(buildRewardEntity(userCode, rewardFromFriendCodes));

                // 更新为已奖励状态
                recommendMapper.updateByIds(rewardFromIds);
            }
        }

        return true;
    }

    /**
     * 申请余额提现（默认提全部）
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean doCash(String userCode) {

        AccountEntity accountEntity = accountMapper.queryByUserCode(userCode);
        if (Objects.isNull(accountEntity)) {
            logger.error("doCash fail, 找不到对应的用户账户信息{}", userCode);
            return false;
        }

        float balance = accountEntity.getBalance();
        if (balance == 0) {
            logger.error("doCash fail, 用户账户余额为0{}", userCode);
            return false;
        }

        // 走审批，账户余额先清空
        accountEntity.setBalance(0f);
        accountMapper.update(accountEntity);

        // 新增一条提现记录
        AccountHistoryEntity historyEntity = buildCashEntity(userCode, balance);
        accountHistoryMapper.save(historyEntity);

        return true;
    }

    /**
     * 推荐奖励
     */
    private AccountHistoryEntity buildRewardEntity(String userCode, List<String> friendUserCodes) {
        return AccountHistoryEntity.builder()
                .userCode(userCode)
                .change(CommonConstant.REWARD_MONEY)
                .updateType(CommonConstant.AccountChangeType.RECOMMEND_AWARD)
                .addFrom(String.join(",", friendUserCodes))
                .needAudit(CommonConstant.Flag.NO)
                .status(CommonConstant.AccountChangeStatus.VALID)
                .build();
    }

    /**
     * 提现
     */
    private AccountHistoryEntity buildCashEntity(String userCode, float cashNumber) {
        return AccountHistoryEntity.builder()
                .userCode(userCode)
                .change(cashNumber)
                .updateType(CommonConstant.AccountChangeType.CASH)
                .addFrom("")
                .needAudit(CommonConstant.Flag.YES)
                .status(CommonConstant.AccountChangeStatus.GOING)
                .build();
    }

}
