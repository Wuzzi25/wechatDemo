package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.entity.RecommendEntity;
import com.tencent.wxcloudrun.model.RecommendVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecommendMapper {

    /**
     * TODO 改成分页
     * 分页查询推荐记录
     */
    List<RecommendVo> queryByUserCode(@Param("userCode") String userCode);

    /**
     * 查还没奖励的推荐列表
     */
    List<RecommendEntity> queryHasNoReward(@Param("userCode") String userCode);

    /**
     * 更新为已奖励
     */
    void updateByIds(@Param("ids") List<Integer> ids);

    /**
     * 新增一条推荐成功的记录
     */
    void save(@Param("entity")RecommendEntity entity);
}
