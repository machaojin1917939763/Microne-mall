
package com.microne.mall.controller.mall;

import com.microne.mall.controller.vo.MicroneMallShoppingCartItemVO;
import com.microne.mall.controller.vo.MicroneMallUserVO;
import com.microne.mall.common.Constants;
import com.microne.mall.common.MicroneMallException;
import com.microne.mall.common.MicroneMallOrderStatusEnum;
import com.microne.mall.common.ServiceResultEnum;
import com.microne.mall.controller.vo.MicroneMallOrderDetailVO;
import com.microne.mall.entity.MicroneMallOrder;
import com.microne.mall.util.PageQueryUtil;
import com.microne.mall.util.Result;
import com.microne.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Resource
    private com.microne.mall.service.MicroneMallShoppingCartService MicroneMallShoppingCartService;
    @Resource
    private com.microne.mall.service.MicroneMallOrderService MicroneMallOrderService;

    @GetMapping("/orders/{orderNo}")
    public String orderDetailPage(HttpServletRequest request, @PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        MicroneMallUserVO user = (MicroneMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        MicroneMallOrderDetailVO orderDetailVO = MicroneMallOrderService.getOrderDetailByOrderNo(orderNo, user.getUserId());
        request.setAttribute("orderDetailVO", orderDetailVO);
        return "mall/order-detail";
    }

    @GetMapping("/orders")
    public String orderListPage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        MicroneMallUserVO user = (MicroneMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        params.put("userId", user.getUserId());
        if (ObjectUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
        //封装我的订单数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("orderPageResult", MicroneMallOrderService.getMyOrders(pageUtil));
        request.setAttribute("path", "orders");
        return "mall/my-orders";
    }

    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession httpSession) {
        MicroneMallUserVO user = (MicroneMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        List<MicroneMallShoppingCartItemVO> myShoppingCartItems = MicroneMallShoppingCartService.getMyShoppingCartItems(user.getUserId());
        if (!StringUtils.hasText(user.getAddress().trim())) {
            //无收货地址
            MicroneMallException.fail(ServiceResultEnum.NULL_ADDRESS_ERROR.getResult());
        }
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物车中无数据则跳转至错误页
            MicroneMallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        }
        //保存订单并返回订单号
        String saveOrderResult = MicroneMallOrderService.saveOrder(user, myShoppingCartItems);
        //跳转到订单详情页
        return "redirect:/orders/" + saveOrderResult;
    }

    @PutMapping("/orders/{orderNo}/cancel")
    @ResponseBody
    public Result cancelOrder(@PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        MicroneMallUserVO user = (MicroneMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        String cancelOrderResult = MicroneMallOrderService.cancelOrder(orderNo, user.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(cancelOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(cancelOrderResult);
        }
    }

    @PutMapping("/orders/{orderNo}/finish")
    @ResponseBody
    public Result finishOrder(@PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        MicroneMallUserVO user = (MicroneMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        String finishOrderResult = MicroneMallOrderService.finishOrder(orderNo, user.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(finishOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(finishOrderResult);
        }
    }

    @GetMapping("/selectPayType")
    public String selectPayType(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession) {
        MicroneMallUserVO user = (MicroneMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        MicroneMallOrder MicroneMallOrder = MicroneMallOrderService.getMicroneMallOrderByOrderNo(orderNo);
        //判断订单userId
        if (!user.getUserId().equals(MicroneMallOrder.getUserId())) {
            MicroneMallException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
        }
        //判断订单状态
        if (MicroneMallOrder.getOrderStatus().intValue() != MicroneMallOrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
            MicroneMallException.fail(ServiceResultEnum.ORDER_STATUS_ERROR.getResult());
        }
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", MicroneMallOrder.getTotalPrice());
        return "mall/pay-select";
    }

    @GetMapping("/payPage")
    public String payOrder(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession, @RequestParam("payType") int payType) {
        MicroneMallUserVO user = (MicroneMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        MicroneMallOrder MicroneMallOrder = MicroneMallOrderService.getMicroneMallOrderByOrderNo(orderNo);
        //判断订单userId
        if (!user.getUserId().equals(MicroneMallOrder.getUserId())) {
            MicroneMallException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
        }
        //判断订单状态
        if (MicroneMallOrder.getOrderStatus().intValue() != MicroneMallOrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
            MicroneMallException.fail(ServiceResultEnum.ORDER_STATUS_ERROR.getResult());
        }
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", MicroneMallOrder.getTotalPrice());
        if (payType == 1) {
            return "mall/alipay";
        } else {
            return "mall/wxpay";
        }
    }

    @GetMapping("/paySuccess")
    @ResponseBody
    public Result paySuccess(@RequestParam("orderNo") String orderNo, @RequestParam("payType") int payType) {
        String payResult = MicroneMallOrderService.paySuccess(orderNo, payType);
        if (ServiceResultEnum.SUCCESS.getResult().equals(payResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(payResult);
        }
    }

}
