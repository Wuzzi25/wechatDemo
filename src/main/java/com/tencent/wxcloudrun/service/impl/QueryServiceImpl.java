package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.constants.CommonConstant;
import com.tencent.wxcloudrun.dao.AccountHistoryMapper;
import com.tencent.wxcloudrun.dao.AccountMapper;
import com.tencent.wxcloudrun.dao.RecommendMapper;
import com.tencent.wxcloudrun.entity.AccountEntity;
import com.tencent.wxcloudrun.entity.AccountHistoryEntity;
import com.tencent.wxcloudrun.model.AccountHistoryVo;
import com.tencent.wxcloudrun.model.RecommendVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wuzzi
 */
@Service
public class QueryServiceImpl {

    final Logger logger = LoggerFactory.getLogger(QueryServiceImpl.class);

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private AccountHistoryMapper accountHistoryMapper;

    @Resource
    private RecommendMapper recommendMapper;

    /**
     * 查账号余额
     */
    public float queryAccountBalance(String userCode) {
        AccountEntity accountEntity = accountMapper.queryByUserCode(userCode);
        if (Objects.isNull(accountEntity)) {
            logger.error("queryAccountHistoryList，账号不存在，{}", userCode);
            return 0f;
        }

        return accountEntity.getBalance();
    }

    /**
     * 余额历史记录
     */
    public List<AccountHistoryVo> queryAccountHistoryList(String userCode) {

        List<AccountHistoryEntity> entities = accountHistoryMapper.queryByUserCode(userCode);
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::covert2Vo).collect(Collectors.toList());
    }

    private AccountHistoryVo covert2Vo(AccountHistoryEntity entity) {
        String changeType = entity.getUpdateType() == CommonConstant.AccountChangeType.RECOMMEND_AWARD ? "奖励好友推荐金额" : "提现金额";
        String status = entity.getUpdateType() == CommonConstant.AccountChangeType.RECOMMEND_AWARD ? ""
                : entity.getStatus() == CommonConstant.AccountChangeStatus.GOING ? "审核中"
                : entity.getStatus() == CommonConstant.AccountChangeStatus.VALID ? "已提现"
                : entity.getStatus() == CommonConstant.AccountChangeStatus.INVALID ? "审核不通过" : "";
        return AccountHistoryVo.builder()
                .change(entity.getChange())
                .changeType(changeType)
                .status(status)
                .createdAt(entity.getCreatedAt())
                .build();
    }

    /**
     * 查询推荐记录
     */
    public List<RecommendVo> queryRecommendList(String userCode) {
        return recommendMapper.queryByUserCode(userCode);
    }

}
