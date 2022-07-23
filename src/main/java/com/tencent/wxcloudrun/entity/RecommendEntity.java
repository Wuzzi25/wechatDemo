package com.tencent.wxcloudrun.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 推荐记录表
 *
 * @author wuzzi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class RecommendEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4901651262803503476L;

    private String userCode;
    private String beRecommendUserCode;
    /**
     * 是否已奖励
     *
     * @see com.tencent.wxcloudrun.constants.CommonConstant.Flag
     */
    private int beenReward;
}
