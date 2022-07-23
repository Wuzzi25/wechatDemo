package com.tencent.wxcloudrun.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wuzzi
 */
@Data
@Builder
public class AccountHistoryVo implements Serializable {

    private static final long serialVersionUID = -8978912802532944604L;

    private String changeType;
    private float change;
    private String status;
    private Date createdAt;

}
