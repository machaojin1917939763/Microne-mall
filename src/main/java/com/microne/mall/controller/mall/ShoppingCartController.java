
package com.microne.mall.controller.mall;

import com.microne.mall.controller.vo.MicroneMallShoppingCartItemVO;
import com.microne.mall.entity.MicroneMallShoppingCartItem;
import com.microne.mall.common.Constants;
import com.microne.mall.common.MicroneMallException;
import com.microne.mall.common.ServiceResultEnum;
import com.microne.mall.controller.vo.MicroneMallUserVO;
import com.microne.mall.util.Result;
import com.microne.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ShoppingCartController {

    @Resource
    private com.microne.mall.service.MicroneMallShoppingCartService MicroneMallShoppingCartService;

    @GetMapping("/shop-cart")
    public String cartListPage(HttpServletRequest request,
                               HttpSession httpSession) {
        MicroneMallUserVO user = (MicroneMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        int itemsTotal = 0;
        int priceTotal = 0;
        List<MicroneMallShoppingCartItemVO> myShoppingCartItems = MicroneMallShoppingCartService.getMyShoppingCartItems(user.getUserId());
        if (!CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物项总数
            itemsTotal = myShoppingCartItems.stream().mapToInt(MicroneMallShoppingCartItemVO::getGoodsCount).sum();
            if (itemsTotal < 1) {
                MicroneMallException.fail("购物项不能为空");
            }
            //总价
            for (MicroneMallShoppingCartItemVO MicroneMallShoppingCartItemVO : myShoppingCartItems) {
                priceTotal += MicroneMallShoppingCartItemVO.getGoodsCount() * MicroneMallShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                MicroneMallException.fail("购物项价格异常");
            }
        }
        request.setAttribute("itemsTotal", itemsTotal);
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myShoppingCartItems", myShoppingCartItems);
        return "mall/cart";
    }

    @PostMapping("/shop-cart")
    @ResponseBody
    public Result saveMicroneMallShoppingCartItem(@RequestBody MicroneMallShoppingCartItem MicroneMallShoppingCartItem,
                                                 HttpSession httpSession) {
        MicroneMallUserVO user = (MicroneMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        MicroneMallShoppingCartItem.setUserId(user.getUserId());
        String saveResult = MicroneMallShoppingCartService.saveMicroneMallCartItem(MicroneMallShoppingCartItem);
        //添加成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //添加失败
        return ResultGenerator.genFailResult(saveResult);
    }

    @PutMapping("/shop-cart")
    @ResponseBody
    public Result updateMicroneMallShoppingCartItem(@RequestBody MicroneMallShoppingCartItem MicroneMallShoppingCartItem,
                                                   HttpSession httpSession) {
        MicroneMallUserVO user = (MicroneMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        MicroneMallShoppingCartItem.setUserId(user.getUserId());
        String updateResult = MicroneMallShoppingCartService.updateMicroneMallCartItem(MicroneMallShoppingCartItem);
        //修改成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(updateResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //修改失败
        return ResultGenerator.genFailResult(updateResult);
    }

    @DeleteMapping("/shop-cart/{MicroneMallShoppingCartItemId}")
    @ResponseBody
    public Result updateMicroneMallShoppingCartItem(@PathVariable("MicroneMallShoppingCartItemId") Long MicroneMallShoppingCartItemId,
                                                   HttpSession httpSession) {
        MicroneMallUserVO user = (MicroneMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        Boolean deleteResult = MicroneMallShoppingCartService.deleteById(MicroneMallShoppingCartItemId,user.getUserId());
        //删除成功
        if (deleteResult) {
            return ResultGenerator.genSuccessResult();
        }
        //删除失败
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }

    @GetMapping("/shop-cart/settle")
    public String settlePage(HttpServletRequest request,
                             HttpSession httpSession) {
        int priceTotal = 0;
        MicroneMallUserVO user = (MicroneMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        List<MicroneMallShoppingCartItemVO> myShoppingCartItems = MicroneMallShoppingCartService.getMyShoppingCartItems(user.getUserId());
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            //无数据则不跳转至结算页
            return "/shop-cart";
        } else {
            //总价
            for (MicroneMallShoppingCartItemVO MicroneMallShoppingCartItemVO : myShoppingCartItems) {
                priceTotal += MicroneMallShoppingCartItemVO.getGoodsCount() * MicroneMallShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                MicroneMallException.fail("购物项价格异常");
            }
        }
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myShoppingCartItems", myShoppingCartItems);
        return "mall/order-settle";
    }
}
