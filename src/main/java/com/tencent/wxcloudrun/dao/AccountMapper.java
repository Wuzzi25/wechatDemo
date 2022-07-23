package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.entity.AccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {

    AccountEntity queryByUserCode(@Param("userCode") String userCode);

    void save(@Param("accountEntity") AccountEntity accountEntity);

    void update(@Param("accountEntity") AccountEntity accountEntity);

}
