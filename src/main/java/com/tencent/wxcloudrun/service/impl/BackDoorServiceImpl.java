package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.constants.CommonConstant;
import com.tencent.wxcloudrun.dao.AccountHistoryMapper;
import com.tencent.wxcloudrun.dao.AccountMapper;
import com.tencent.wxcloudrun.entity.AccountEntity;
import com.tencent.wxcloudrun.entity.AccountHistoryEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author wuzzi
 */
@Service
public class BackDoorServiceImpl {

    final Logger logger = LoggerFactory.getLogger(BackDoorServiceImpl.class);

    @Resource
    private AccountMapper accountMapper;
    @Resource
    private AccountHistoryMapper accountHistoryMapper;

    /**
     * 提现审核完成，只有需要审核的记录会走这一条
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean finishCashAudit(int id, int status) {

        AccountHistoryEntity accountHistoryEntity = accountHistoryMapper.queryById(id);
        if (Objects.isNull(accountHistoryEntity)) {
            logger.error("finishCashAudit fail_{}_找不到对应的提现记录", id);
            return false;
        }

        // 更新审核状态
        accountHistoryMapper.updateById(id, status);

        // 审核通过，更新账户余额，减去提现的金额
        if (status == CommonConstant.AccountChangeStatus.VALID) {

            String userCode = accountHistoryEntity.getUserCode();
            AccountEntity accountEntity = accountMapper.queryByUserCode(userCode);
            if (Objects.isNull(accountEntity)) {
                logger.error("finishCashAudit fail_{}_找不到对应的账户", userCode);
                return false;
            }

            float afterChange = accountEntity.getBalance() - accountHistoryEntity.getChange();
            if (afterChange < 0) {
                logger.warn("finishCashAudit，账户余额少于0！！！！");
                afterChange = 0f;
            }

            accountEntity.setBalance(afterChange);
            accountMapper.update(accountEntity);
        }

        return true;
    }

}
