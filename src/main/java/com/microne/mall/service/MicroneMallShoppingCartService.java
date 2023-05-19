
package com.microne.mall.service;

import com.microne.mall.entity.MicroneMallShoppingCartItem;
import com.microne.mall.controller.vo.MicroneMallShoppingCartItemVO;

import java.util.List;

public interface MicroneMallShoppingCartService {

    /**
     * 保存商品至购物车中
     *
     * @param MicroneMallShoppingCartItem
     * @return
     */
    String saveMicroneMallCartItem(MicroneMallShoppingCartItem MicroneMallShoppingCartItem);

    /**
     * 修改购物车中的属性
     *
     * @param MicroneMallShoppingCartItem
     * @return
     */
    String updateMicroneMallCartItem(MicroneMallShoppingCartItem MicroneMallShoppingCartItem);

    /**
     * 获取购物项详情
     *
     * @param MicroneMallShoppingCartItemId
     * @return
     */
    MicroneMallShoppingCartItem getMicroneMallCartItemById(Long MicroneMallShoppingCartItemId);

    /**
     * 删除购物车中的商品
     *
     *
     * @param shoppingCartItemId
     * @param userId
     * @return
     */
    Boolean deleteById(Long shoppingCartItemId, Long userId);

    /**
     * 获取我的购物车中的列表数据
     *
     * @param MicroneMallUserId
     * @return
     */
    List<MicroneMallShoppingCartItemVO> getMyShoppingCartItems(Long MicroneMallUserId);
}
