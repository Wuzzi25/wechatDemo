package com.tencent.wxcloudrun.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wuzzi
 */
@Data
@Builder
public class UserVo implements Serializable {

    private static final long serialVersionUID = -1195821937318107748L;

    private String userCode;
    private String qrCode;
    private boolean isVip;

}
