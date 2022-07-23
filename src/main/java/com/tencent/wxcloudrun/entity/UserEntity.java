package com.tencent.wxcloudrun.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * -- 用戶信息表，用於存儲用户基础信息
 *
 * @author wuzzi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class UserEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -2544845270459063662L;

    /**
     * uuid
     */
    private String userCode;
    private String wechatName;
    private String wechatAccount;

    /**
     * 姓名，用户输入
     */
    private String userName;
    /**
     * 手机号
     */
    private String userPhone;

    /**
     * 用户身份标识
     *
     * @see com.tencent.wxcloudrun.constants.CommonConstant.VipFlag
     */
    private int vipFlag;
    /**
     * 二维码，当是会员时才展示
     */
    private String qrCode;
    /**
     * 成为会员时间
     */
    private Date beVipAt;

    /**
     * 是否被推荐
     *
     * @see com.tencent.wxcloudrun.constants.CommonConstant.Flag
     */
    private int isRecommend;
    /**
     * 推荐人账号
     */
    private String recommendUserCode;

}
