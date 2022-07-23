package com.tencent.wxcloudrun.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wuzzi
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 8142534583437615534L;

    private int id;

    private Date createAt;

    private Date updateAt;
}
