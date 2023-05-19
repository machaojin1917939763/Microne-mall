
package com.microne.mall.dao;

import com.microne.mall.entity.MicroneMallOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MicroneMallOrderItemMapper {
    int deleteByPrimaryKey(Long orderItemId);

    int insert(MicroneMallOrderItem record);

    int insertSelective(MicroneMallOrderItem record);

    MicroneMallOrderItem selectByPrimaryKey(Long orderItemId);

    /**
     * 根据订单id获取订单项列表
     *
     * @param orderId
     * @return
     */
    List<MicroneMallOrderItem> selectByOrderId(Long orderId);

    /**
     * 根据订单ids获取订单项列表
     *
     * @param orderIds
     * @return
     */
    List<MicroneMallOrderItem> selectByOrderIds(@Param("orderIds") List<Long> orderIds);

    /**
     * 批量insert订单项数据
     *
     * @param orderItems
     * @return
     */
    int insertBatch(@Param("orderItems") List<MicroneMallOrderItem> orderItems);

    int updateByPrimaryKeySelective(MicroneMallOrderItem record);

    int updateByPrimaryKey(MicroneMallOrderItem record);
}