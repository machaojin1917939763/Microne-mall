
package com.microne.mall.service;

import com.microne.mall.controller.vo.MicroneMallOrderDetailVO;
import com.microne.mall.controller.vo.MicroneMallOrderItemVO;
import com.microne.mall.controller.vo.MicroneMallShoppingCartItemVO;
import com.microne.mall.controller.vo.MicroneMallUserVO;
import com.microne.mall.entity.MicroneMallOrder;
import com.microne.mall.util.PageQueryUtil;
import com.microne.mall.util.PageResult;

import java.util.List;

public interface MicroneMallOrderService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getMicroneMallOrdersPage(PageQueryUtil pageUtil);

    /**
     * 订单信息修改
     *
     * @param MicroneMallOrder
     * @return
     */
    String updateOrderInfo(MicroneMallOrder MicroneMallOrder);

    /**
     * 配货
     *
     * @param ids
     * @return
     */
    String checkDone(Long[] ids);

    /**
     * 出库
     *
     * @param ids
     * @return
     */
    String checkOut(Long[] ids);

    /**
     * 关闭订单
     *
     * @param ids
     * @return
     */
    String closeOrder(Long[] ids);

    /**
     * 保存订单
     *
     * @param user
     * @param myShoppingCartItems
     * @return
     */
    String saveOrder(MicroneMallUserVO user, List<MicroneMallShoppingCartItemVO> myShoppingCartItems);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @param userId
     * @return
     */
    MicroneMallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @return
     */
    MicroneMallOrder getMicroneMallOrderByOrderNo(String orderNo);

    /**
     * 我的订单列表
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyOrders(PageQueryUtil pageUtil);

    /**
     * 手动取消订单
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String cancelOrder(String orderNo, Long userId);

    /**
     * 确认收货
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String finishOrder(String orderNo, Long userId);

    String paySuccess(String orderNo, int payType);

    List<MicroneMallOrderItemVO> getOrderItems(Long id);
}
