package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.entity.AccountHistoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountHistoryMapper {

    void save(@Param("entity") AccountHistoryEntity entity);

    /**
     * 查询某条提现记录
     */
    AccountHistoryEntity queryById(@Param("id") int id);

    /**
     * 更新审核状态
     */
    void updateById(@Param("id") int id, @Param("status") int status);

    /**
     * TODO chenjie 分页
     * 分页查询余额提现记录
     */
    List<AccountHistoryEntity> queryByUserCode(@Param("userCode") String userCode);

}
