package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 推荐记录列表
 *
 * @author wuzzi
 */
@Data
public class RecommendVo implements Serializable {

    private static final long serialVersionUID = 7390959016791800801L;

    private String userCode;
    private String userName;
    private int beenReward;
    private Date recommendAt;
}
