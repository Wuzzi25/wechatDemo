package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author wuzzi
 */
@Mapper
public interface UserMapper {

    UserEntity queryByUserCode(@Param("userCode") String userCode);

    UserEntity queryByWechatAccount(@Param("wechatAccount") String wechatAccount);

    UserEntity save(@Param("userEntity") UserEntity userEntity);

    UserEntity updateToVip(@Param("userEntity") UserEntity userEntity);

}
