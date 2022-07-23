package com.tencent.wxcloudrun.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 账户余额历史记录表
 *
 * @author wuzzi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class AccountHistoryEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -3365676188526350028L;

    private String userCode;

    /**
     * 余额变动
     */
    private float change;

    /**
     * 变动类型
     *
     * @see com.tencent.wxcloudrun.constants.CommonConstant.AccountChangeType
     */
    private int updateType;

    /**
     * 变动类型为推荐奖励时，该字段存储推荐成功的三个好友的userCode
     */
    private String addFrom;

    /**
     * 是否需要审核
     */
    private int needAudit;

    /**
     * 状态
     *
     * @see com.tencent.wxcloudrun.constants.CommonConstant.AccountChangeStatus
     */
    private int status;

}

