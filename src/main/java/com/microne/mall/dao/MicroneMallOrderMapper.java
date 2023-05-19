
package com.microne.mall.dao;

import com.microne.mall.util.PageQueryUtil;
import com.microne.mall.entity.MicroneMallOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MicroneMallOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(MicroneMallOrder record);

    int insertSelective(MicroneMallOrder record);

    MicroneMallOrder selectByPrimaryKey(Long orderId);

    MicroneMallOrder selectByOrderNo(String orderNo);

    int updateByPrimaryKeySelective(MicroneMallOrder record);

    int updateByPrimaryKey(MicroneMallOrder record);

    List<MicroneMallOrder> findMicroneMallOrderList(PageQueryUtil pageUtil);

    int getTotalMicroneMallOrders(PageQueryUtil pageUtil);

    List<MicroneMallOrder> selectByPrimaryKeys(@Param("orderIds") List<Long> orderIds);

    int checkOut(@Param("orderIds") List<Long> orderIds);

    int closeOrder(@Param("orderIds") List<Long> orderIds, @Param("orderStatus") int orderStatus);

    int checkDone(@Param("orderIds") List<Long> asList);
}