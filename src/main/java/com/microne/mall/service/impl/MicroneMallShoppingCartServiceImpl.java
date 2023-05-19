
package com.microne.mall.service.impl;

import com.microne.mall.common.Constants;
import com.microne.mall.common.ServiceResultEnum;
import com.microne.mall.controller.vo.MicroneMallShoppingCartItemVO;
import com.microne.mall.dao.MicroneMallGoodsMapper;
import com.microne.mall.dao.MicroneMallShoppingCartItemMapper;
import com.microne.mall.entity.MicroneMallGoods;
import com.microne.mall.entity.MicroneMallShoppingCartItem;
import com.microne.mall.service.MicroneMallShoppingCartService;
import com.microne.mall.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MicroneMallShoppingCartServiceImpl implements MicroneMallShoppingCartService {

    @Autowired
    private MicroneMallShoppingCartItemMapper MicroneMallShoppingCartItemMapper;

    @Autowired
    private MicroneMallGoodsMapper MicroneMallGoodsMapper;

    @Override
    public String saveMicroneMallCartItem(MicroneMallShoppingCartItem MicroneMallShoppingCartItem) {
        MicroneMallShoppingCartItem temp = MicroneMallShoppingCartItemMapper.selectByUserIdAndGoodsId(MicroneMallShoppingCartItem.getUserId(), MicroneMallShoppingCartItem.getGoodsId());
        if (temp != null) {
            //已存在则修改该记录
            temp.setGoodsCount(MicroneMallShoppingCartItem.getGoodsCount());
            return updateMicroneMallCartItem(temp);
        }
        MicroneMallGoods MicroneMallGoods = MicroneMallGoodsMapper.selectByPrimaryKey(MicroneMallShoppingCartItem.getGoodsId());
        //商品为空
        if (MicroneMallGoods == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        int totalItem = MicroneMallShoppingCartItemMapper.selectCountByUserId(MicroneMallShoppingCartItem.getUserId()) + 1;
        //超出单个商品的最大数量
        if (MicroneMallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //超出最大数量
        if (totalItem > Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR.getResult();
        }
        //保存记录
        if (MicroneMallShoppingCartItemMapper.insertSelective(MicroneMallShoppingCartItem) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateMicroneMallCartItem(MicroneMallShoppingCartItem MicroneMallShoppingCartItem) {
        MicroneMallShoppingCartItem MicroneMallShoppingCartItemUpdate = MicroneMallShoppingCartItemMapper.selectByPrimaryKey(MicroneMallShoppingCartItem.getCartItemId());
        if (MicroneMallShoppingCartItemUpdate == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        //超出单个商品的最大数量
        if (MicroneMallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //当前登录账号的userId与待修改的cartItem中userId不同，返回错误
        if (!MicroneMallShoppingCartItemUpdate.getUserId().equals(MicroneMallShoppingCartItem.getUserId())) {
            return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
        }
        //数值相同，则不执行数据操作
        if (MicroneMallShoppingCartItem.getGoodsCount().equals(MicroneMallShoppingCartItemUpdate.getGoodsCount())) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        MicroneMallShoppingCartItemUpdate.setGoodsCount(MicroneMallShoppingCartItem.getGoodsCount());
        MicroneMallShoppingCartItemUpdate.setUpdateTime(new Date());
        //修改记录
        if (MicroneMallShoppingCartItemMapper.updateByPrimaryKeySelective(MicroneMallShoppingCartItemUpdate) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public MicroneMallShoppingCartItem getMicroneMallCartItemById(Long MicroneMallShoppingCartItemId) {
        return MicroneMallShoppingCartItemMapper.selectByPrimaryKey(MicroneMallShoppingCartItemId);
    }

    @Override
    public Boolean deleteById(Long shoppingCartItemId, Long userId) {
        MicroneMallShoppingCartItem MicroneMallShoppingCartItem = MicroneMallShoppingCartItemMapper.selectByPrimaryKey(shoppingCartItemId);
        if (MicroneMallShoppingCartItem == null) {
            return false;
        }
        //userId不同不能删除
        if (!userId.equals(MicroneMallShoppingCartItem.getUserId())) {
            return false;
        }
        return MicroneMallShoppingCartItemMapper.deleteByPrimaryKey(shoppingCartItemId) > 0;
    }

    @Override
    public List<MicroneMallShoppingCartItemVO> getMyShoppingCartItems(Long MicroneMallUserId) {
        List<MicroneMallShoppingCartItemVO> MicroneMallShoppingCartItemVOS = new ArrayList<>();
        List<MicroneMallShoppingCartItem> MicroneMallShoppingCartItems = MicroneMallShoppingCartItemMapper.selectByUserId(MicroneMallUserId, Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER);
        if (!CollectionUtils.isEmpty(MicroneMallShoppingCartItems)) {
            //查询商品信息并做数据转换
            List<Long> MicroneMallGoodsIds = MicroneMallShoppingCartItems.stream().map(MicroneMallShoppingCartItem::getGoodsId).collect(Collectors.toList());
            List<MicroneMallGoods> microneMallGoods = MicroneMallGoodsMapper.selectByPrimaryKeys(MicroneMallGoodsIds);
            Map<Long, MicroneMallGoods> microneMallGoodsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(microneMallGoods)) {
                microneMallGoodsMap = microneMallGoods.stream().collect(Collectors.toMap(MicroneMallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
            }
            for (MicroneMallShoppingCartItem microneMallShoppingCartItem : MicroneMallShoppingCartItems) {
                MicroneMallShoppingCartItemVO MicroneMallShoppingCartItemVO = new MicroneMallShoppingCartItemVO();
                BeanUtil.copyProperties(microneMallShoppingCartItem, MicroneMallShoppingCartItemVO);
                if (microneMallGoodsMap.containsKey(microneMallShoppingCartItem.getGoodsId())) {
                    MicroneMallGoods MicroneMallGoodsTemp = microneMallGoodsMap.get(microneMallShoppingCartItem.getGoodsId());
                    MicroneMallShoppingCartItemVO.setGoodsCoverImg(MicroneMallGoodsTemp.getGoodsCoverImg());
                    String goodsName = MicroneMallGoodsTemp.getGoodsName();
                    // 字符串过长导致文字超出的问题
                    if (goodsName.length() > 28) {
                        goodsName = goodsName.substring(0, 28) + "...";
                    }
                    MicroneMallShoppingCartItemVO.setGoodsName(goodsName);
                    MicroneMallShoppingCartItemVO.setSellingPrice(MicroneMallGoodsTemp.getSellingPrice());
                    MicroneMallShoppingCartItemVOS.add(MicroneMallShoppingCartItemVO);
                }
            }
        }
        return MicroneMallShoppingCartItemVOS;
    }
}
