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

    void save(@Param("userEntity") UserEntity userEntity);

    void updateToVip(@Param("userEntity") UserEntity userEntity);

}
