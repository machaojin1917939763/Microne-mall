
package com.microne.mall.service.impl;

import com.microne.mall.common.*;
import com.microne.mall.controller.vo.*;
import com.microne.mall.service.MicroneMallOrderService;
import com.microne.mall.dao.MicroneMallGoodsMapper;
import com.microne.mall.dao.MicroneMallOrderItemMapper;
import com.microne.mall.dao.MicroneMallOrderMapper;
import com.microne.mall.dao.MicroneMallShoppingCartItemMapper;
import com.microne.mall.entity.MicroneMallGoods;
import com.microne.mall.entity.MicroneMallOrder;
import com.microne.mall.entity.MicroneMallOrderItem;
import com.microne.mall.entity.StockNumDTO;
import com.microne.mall.util.BeanUtil;
import com.microne.mall.util.NumberUtil;
import com.microne.mall.util.PageQueryUtil;
import com.microne.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@Transactional
public class MicroneMallOrderServiceImpl implements MicroneMallOrderService {

    @Autowired
    private MicroneMallOrderMapper MicroneMallOrderMapper;
    @Autowired
    private MicroneMallOrderItemMapper MicroneMallOrderItemMapper;
    @Autowired
    private MicroneMallShoppingCartItemMapper MicroneMallShoppingCartItemMapper;
    @Autowired
    private MicroneMallGoodsMapper MicroneMallGoodsMapper;

    @Override
    public PageResult getMicroneMallOrdersPage(PageQueryUtil pageUtil) {
        List<MicroneMallOrder> MicroneMallOrders = MicroneMallOrderMapper.findMicroneMallOrderList(pageUtil);
        int total = MicroneMallOrderMapper.getTotalMicroneMallOrders(pageUtil);
        PageResult pageResult = new PageResult(MicroneMallOrders, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String updateOrderInfo(MicroneMallOrder MicroneMallOrder) {
        MicroneMallOrder temp = MicroneMallOrderMapper.selectByPrimaryKey(MicroneMallOrder.getOrderId());
        //不为空且orderStatus>=0且状态为出库之前可以修改部分信息
        if (temp != null && temp.getOrderStatus() >= 0 && temp.getOrderStatus() < 3) {
            temp.setTotalPrice(MicroneMallOrder.getTotalPrice());
            temp.setUserAddress(MicroneMallOrder.getUserAddress());
            temp.setUpdateTime(new Date());
            if (MicroneMallOrderMapper.updateByPrimaryKeySelective(temp) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            }
            return ServiceResultEnum.DB_ERROR.getResult();
        }
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    public String checkDone(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<MicroneMallOrder> orders = MicroneMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (MicroneMallOrder MicroneMallOrder : orders) {
                if (MicroneMallOrder.getIsDeleted() == 1) {
                    errorOrderNos += MicroneMallOrder.getOrderNo() + " ";
                    continue;
                }
                if (MicroneMallOrder.getOrderStatus() != 1) {
                    errorOrderNos += MicroneMallOrder.getOrderNo() + " ";
                }
            }
            if (!StringUtils.hasText(errorOrderNos)) {
                //订单状态正常 可以执行配货完成操作 修改订单状态和更新时间
                if (MicroneMallOrderMapper.checkDone(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行出库操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单的状态不是支付成功无法执行出库操作";
                } else {
                    return "你选择了太多状态不是支付成功的订单，无法执行配货完成操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    public String checkOut(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<MicroneMallOrder> orders = MicroneMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (MicroneMallOrder MicroneMallOrder : orders) {
                if (MicroneMallOrder.getIsDeleted() == 1) {
                    errorOrderNos += MicroneMallOrder.getOrderNo() + " ";
                    continue;
                }
                if (MicroneMallOrder.getOrderStatus() != 1 && MicroneMallOrder.getOrderStatus() != 2) {
                    errorOrderNos += MicroneMallOrder.getOrderNo() + " ";
                }
            }
            if (!StringUtils.hasText(errorOrderNos)) {
                //订单状态正常 可以执行出库操作 修改订单状态和更新时间
                if (MicroneMallOrderMapper.checkOut(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行出库操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单的状态不是支付成功或配货完成无法执行出库操作";
                } else {
                    return "你选择了太多状态不是支付成功或配货完成的订单，无法执行出库操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    public String closeOrder(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<MicroneMallOrder> orders = MicroneMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (MicroneMallOrder MicroneMallOrder : orders) {
                // isDeleted=1 一定为已关闭订单
                if (MicroneMallOrder.getIsDeleted() == 1) {
                    errorOrderNos += MicroneMallOrder.getOrderNo() + " ";
                    continue;
                }
                //已关闭或者已完成无法关闭订单
                if (MicroneMallOrder.getOrderStatus() == 4 || MicroneMallOrder.getOrderStatus() < 0) {
                    errorOrderNos += MicroneMallOrder.getOrderNo() + " ";
                }
            }
            if (!StringUtils.hasText(errorOrderNos)) {
                //订单状态正常 可以执行关闭操作 修改订单状态和更新时间&&恢复库存
                if (MicroneMallOrderMapper.closeOrder(Arrays.asList(ids), MicroneMallOrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) > 0 && recoverStockNum(Arrays.asList(ids))) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行关闭操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单不能执行关闭操作";
                } else {
                    return "你选择的订单不能执行关闭操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    public String saveOrder(MicroneMallUserVO user, List<MicroneMallShoppingCartItemVO> myShoppingCartItems) {
        List<Long> itemIdList = myShoppingCartItems.stream().map(MicroneMallShoppingCartItemVO::getCartItemId).collect(Collectors.toList());
        List<Long> goodsIds = myShoppingCartItems.stream().map(MicroneMallShoppingCartItemVO::getGoodsId).collect(Collectors.toList());
        List<MicroneMallGoods> microneMallGoods = MicroneMallGoodsMapper.selectByPrimaryKeys(goodsIds);
        //检查是否包含已下架商品
        List<MicroneMallGoods> goodsListNotSelling = microneMallGoods.stream()
                .filter(MicroneMallGoodsTemp -> MicroneMallGoodsTemp.getGoodsSellStatus() != Constants.SELL_STATUS_UP)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(goodsListNotSelling)) {
            //goodsListNotSelling 对象非空则表示有下架商品
            MicroneMallException.fail(goodsListNotSelling.get(0).getGoodsName() + "已下架，无法生成订单");
        }
        Map<Long, MicroneMallGoods> MicroneMallGoodsMap = microneMallGoods.stream().collect(Collectors.toMap(MicroneMallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
        //判断商品库存
        for (MicroneMallShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems) {
            //查出的商品中不存在购物车中的这条关联商品数据，直接返回错误提醒
            if (!MicroneMallGoodsMap.containsKey(shoppingCartItemVO.getGoodsId())) {
                MicroneMallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
            }
            //存在数量大于库存的情况，直接返回错误提醒
            if (shoppingCartItemVO.getGoodsCount() > MicroneMallGoodsMap.get(shoppingCartItemVO.getGoodsId()).getStockNum()) {
                MicroneMallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
            }
        }
        //删除购物项
        if (!CollectionUtils.isEmpty(itemIdList) && !CollectionUtils.isEmpty(goodsIds) && !CollectionUtils.isEmpty(microneMallGoods)) {
            if (MicroneMallShoppingCartItemMapper.deleteBatch(itemIdList) > 0) {
                List<StockNumDTO> stockNumDTOS = BeanUtil.copyList(myShoppingCartItems, StockNumDTO.class);
                int updateStockNumResult = MicroneMallGoodsMapper.updateStockNum(stockNumDTOS);
                if (updateStockNumResult < 1) {
                    MicroneMallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
                }
                //生成订单号
                String orderNo = NumberUtil.genOrderNo();
                int priceTotal = 0;
                //保存订单
                MicroneMallOrder MicroneMallOrder = new MicroneMallOrder();
                MicroneMallOrder.setOrderNo(orderNo);
                MicroneMallOrder.setUserId(user.getUserId());
                MicroneMallOrder.setUserAddress(user.getAddress());
                //总价
                for (MicroneMallShoppingCartItemVO MicroneMallShoppingCartItemVO : myShoppingCartItems) {
                    priceTotal += MicroneMallShoppingCartItemVO.getGoodsCount() * MicroneMallShoppingCartItemVO.getSellingPrice();
                }
                if (priceTotal < 1) {
                    MicroneMallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                MicroneMallOrder.setTotalPrice(priceTotal);
                //订单body字段，用来作为生成支付单描述信息，暂时未接入第三方支付接口，故该字段暂时设为空字符串
                String extraInfo = "";
                MicroneMallOrder.setExtraInfo(extraInfo);
                //生成订单项并保存订单项纪录
                if (MicroneMallOrderMapper.insertSelective(MicroneMallOrder) > 0) {
                    //生成所有的订单项快照，并保存至数据库
                    List<MicroneMallOrderItem> MicroneMallOrderItems = new ArrayList<>();
                    for (MicroneMallShoppingCartItemVO MicroneMallShoppingCartItemVO : myShoppingCartItems) {
                        MicroneMallOrderItem MicroneMallOrderItem = new MicroneMallOrderItem();
                        //使用BeanUtil工具类将MicroneMallShoppingCartItemVO中的属性复制到MicroneMallOrderItem对象中
                        BeanUtil.copyProperties(MicroneMallShoppingCartItemVO, MicroneMallOrderItem);
                        //MicroneMallOrderMapper文件insert()方法中使用了useGeneratedKeys因此orderId可以获取到
                        MicroneMallOrderItem.setOrderId(MicroneMallOrder.getOrderId());
                        MicroneMallOrderItems.add(MicroneMallOrderItem);
                    }
                    //保存至数据库
                    if (MicroneMallOrderItemMapper.insertBatch(MicroneMallOrderItems) > 0) {
                        //所有操作成功后，将订单号返回，以供Controller方法跳转到订单详情
                        return orderNo;
                    }
                    MicroneMallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                MicroneMallException.fail(ServiceResultEnum.DB_ERROR.getResult());
            }
            MicroneMallException.fail(ServiceResultEnum.DB_ERROR.getResult());
        }
        MicroneMallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        return ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult();
    }

    @Override
    public MicroneMallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId) {
        MicroneMallOrder MicroneMallOrder = MicroneMallOrderMapper.selectByOrderNo(orderNo);
        if (MicroneMallOrder == null) {
            MicroneMallException.fail(ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult());
        }
        //验证是否是当前userId下的订单，否则报错
        if (!userId.equals(MicroneMallOrder.getUserId())) {
            MicroneMallException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
        }
        List<MicroneMallOrderItem> orderItems = MicroneMallOrderItemMapper.selectByOrderId(MicroneMallOrder.getOrderId());
        //获取订单项数据
        if (CollectionUtils.isEmpty(orderItems)) {
            MicroneMallException.fail(ServiceResultEnum.ORDER_ITEM_NOT_EXIST_ERROR.getResult());
        }
        List<MicroneMallOrderItemVO> MicroneMallOrderItemVOS = BeanUtil.copyList(orderItems, MicroneMallOrderItemVO.class);
        MicroneMallOrderDetailVO MicroneMallOrderDetailVO = new MicroneMallOrderDetailVO();
        BeanUtil.copyProperties(MicroneMallOrder, MicroneMallOrderDetailVO);
        MicroneMallOrderDetailVO.setOrderStatusString(MicroneMallOrderStatusEnum.getMicroneMallOrderStatusEnumByStatus(MicroneMallOrderDetailVO.getOrderStatus()).getName());
        MicroneMallOrderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(MicroneMallOrderDetailVO.getPayType()).getName());
        MicroneMallOrderDetailVO.setMicroneMallOrderItemVOS(MicroneMallOrderItemVOS);
        return MicroneMallOrderDetailVO;
    }

    @Override
    public MicroneMallOrder getMicroneMallOrderByOrderNo(String orderNo) {
        return MicroneMallOrderMapper.selectByOrderNo(orderNo);
    }

    @Override
    public PageResult getMyOrders(PageQueryUtil pageUtil) {
        int total = MicroneMallOrderMapper.getTotalMicroneMallOrders(pageUtil);
        List<MicroneMallOrder> MicroneMallOrders = MicroneMallOrderMapper.findMicroneMallOrderList(pageUtil);
        List<MicroneMallOrderListVO> orderListVOS = new ArrayList<>();
        if (total > 0) {
            //数据转换 将实体类转成vo
            orderListVOS = BeanUtil.copyList(MicroneMallOrders, MicroneMallOrderListVO.class);
            //设置订单状态中文显示值
            for (MicroneMallOrderListVO MicroneMallOrderListVO : orderListVOS) {
                MicroneMallOrderListVO.setOrderStatusString(MicroneMallOrderStatusEnum.getMicroneMallOrderStatusEnumByStatus(MicroneMallOrderListVO.getOrderStatus()).getName());
            }
            List<Long> orderIds = MicroneMallOrders.stream().map(MicroneMallOrder::getOrderId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(orderIds)) {
                List<MicroneMallOrderItem> orderItems = MicroneMallOrderItemMapper.selectByOrderIds(orderIds);
                Map<Long, List<MicroneMallOrderItem>> itemByOrderIdMap = orderItems.stream().collect(groupingBy(MicroneMallOrderItem::getOrderId));
                for (MicroneMallOrderListVO MicroneMallOrderListVO : orderListVOS) {
                    //封装每个订单列表对象的订单项数据
                    if (itemByOrderIdMap.containsKey(MicroneMallOrderListVO.getOrderId())) {
                        List<MicroneMallOrderItem> orderItemListTemp = itemByOrderIdMap.get(MicroneMallOrderListVO.getOrderId());
                        //将MicroneMallOrderItem对象列表转换成MicroneMallOrderItemVO对象列表
                        List<MicroneMallOrderItemVO> MicroneMallOrderItemVOS = BeanUtil.copyList(orderItemListTemp, MicroneMallOrderItemVO.class);
                        MicroneMallOrderListVO.setMicroneMallOrderItemVOS(MicroneMallOrderItemVOS);
                    }
                }
            }
        }
        PageResult pageResult = new PageResult(orderListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String cancelOrder(String orderNo, Long userId) {
        MicroneMallOrder MicroneMallOrder = MicroneMallOrderMapper.selectByOrderNo(orderNo);
        if (MicroneMallOrder != null) {
            //验证是否是当前userId下的订单，否则报错
            if (!userId.equals(MicroneMallOrder.getUserId())) {
                MicroneMallException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
            }
            //订单状态判断
            if (MicroneMallOrder.getOrderStatus().intValue() == MicroneMallOrderStatusEnum.ORDER_SUCCESS.getOrderStatus()
                    || MicroneMallOrder.getOrderStatus().intValue() == MicroneMallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()
                    || MicroneMallOrder.getOrderStatus().intValue() == MicroneMallOrderStatusEnum.ORDER_CLOSED_BY_EXPIRED.getOrderStatus()
                    || MicroneMallOrder.getOrderStatus().intValue() == MicroneMallOrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            //修改订单状态&&恢复库存
            if (MicroneMallOrderMapper.closeOrder(Collections.singletonList(MicroneMallOrder.getOrderId()), MicroneMallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()) > 0 && recoverStockNum(Collections.singletonList(MicroneMallOrder.getOrderId()))) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String finishOrder(String orderNo, Long userId) {
        MicroneMallOrder MicroneMallOrder = MicroneMallOrderMapper.selectByOrderNo(orderNo);
        if (MicroneMallOrder != null) {
            //验证是否是当前userId下的订单，否则报错
            if (!userId.equals(MicroneMallOrder.getUserId())) {
                return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
            }
            //订单状态判断 非出库状态下不进行修改操作
            if (MicroneMallOrder.getOrderStatus().intValue() != MicroneMallOrderStatusEnum.ORDER_EXPRESS.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            MicroneMallOrder.setOrderStatus((byte) MicroneMallOrderStatusEnum.ORDER_SUCCESS.getOrderStatus());
            MicroneMallOrder.setUpdateTime(new Date());
            if (MicroneMallOrderMapper.updateByPrimaryKeySelective(MicroneMallOrder) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String paySuccess(String orderNo, int payType) {
        MicroneMallOrder MicroneMallOrder = MicroneMallOrderMapper.selectByOrderNo(orderNo);
        if (MicroneMallOrder != null) {
            //订单状态判断 非待支付状态下不进行修改操作
            if (MicroneMallOrder.getOrderStatus().intValue() != MicroneMallOrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            MicroneMallOrder.setOrderStatus((byte) MicroneMallOrderStatusEnum.ORDER_PAID.getOrderStatus());
            MicroneMallOrder.setPayType((byte) payType);
            MicroneMallOrder.setPayStatus((byte) PayStatusEnum.PAY_SUCCESS.getPayStatus());
            MicroneMallOrder.setPayTime(new Date());
            MicroneMallOrder.setUpdateTime(new Date());
            if (MicroneMallOrderMapper.updateByPrimaryKeySelective(MicroneMallOrder) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public List<MicroneMallOrderItemVO> getOrderItems(Long id) {
        MicroneMallOrder MicroneMallOrder = MicroneMallOrderMapper.selectByPrimaryKey(id);
        if (MicroneMallOrder != null) {
            List<MicroneMallOrderItem> orderItems = MicroneMallOrderItemMapper.selectByOrderId(MicroneMallOrder.getOrderId());
            //获取订单项数据
            if (!CollectionUtils.isEmpty(orderItems)) {
                List<MicroneMallOrderItemVO> MicroneMallOrderItemVOS = BeanUtil.copyList(orderItems, MicroneMallOrderItemVO.class);
                return MicroneMallOrderItemVOS;
            }
        }
        return null;
    }

    /**
     * 恢复库存
     * @param orderIds
     * @return
     */
    public Boolean recoverStockNum(List<Long> orderIds) {
        //查询对应的订单项
        List<MicroneMallOrderItem> MicroneMallOrderItems = MicroneMallOrderItemMapper.selectByOrderIds(orderIds);
        //获取对应的商品id和商品数量并赋值到StockNumDTO对象中
        List<StockNumDTO> stockNumDTOS = BeanUtil.copyList(MicroneMallOrderItems, StockNumDTO.class);
        //执行恢复库存的操作
        int updateStockNumResult = MicroneMallGoodsMapper.recoverStockNum(stockNumDTOS);
        if (updateStockNumResult < 1) {
            MicroneMallException.fail(ServiceResultEnum.CLOSE_ORDER_ERROR.getResult());
            return false;
        } else {
            return true;
        }
    }
}