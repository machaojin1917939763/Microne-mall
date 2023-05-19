
package com.microne.mall.dao;

import com.microne.mall.entity.MicroneMallShoppingCartItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MicroneMallShoppingCartItemMapper {
    int deleteByPrimaryKey(Long cartItemId);

    int insert(MicroneMallShoppingCartItem record);

    int insertSelective(MicroneMallShoppingCartItem record);

    MicroneMallShoppingCartItem selectByPrimaryKey(Long cartItemId);

    MicroneMallShoppingCartItem selectByUserIdAndGoodsId(@Param("MicroneMallUserId") Long MicroneMallUserId, @Param("goodsId") Long goodsId);

    List<MicroneMallShoppingCartItem> selectByUserId(@Param("MicroneMallUserId") Long MicroneMallUserId, @Param("number") int number);

    int selectCountByUserId(Long MicroneMallUserId);

    int updateByPrimaryKeySelective(MicroneMallShoppingCartItem record);

    int updateByPrimaryKey(MicroneMallShoppingCartItem record);

    int deleteBatch(List<Long> ids);
}