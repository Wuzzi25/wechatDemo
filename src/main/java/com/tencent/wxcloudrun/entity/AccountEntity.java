package com.tencent.wxcloudrun.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 账户余额表
 *
 * @author wuzzi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class AccountEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 3977812699635606274L;

    private String userCode;

    private Float balance;

}
