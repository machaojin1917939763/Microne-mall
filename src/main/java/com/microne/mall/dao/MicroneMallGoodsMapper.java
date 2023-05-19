
package com.microne.mall.dao;

import com.microne.mall.util.PageQueryUtil;
import com.microne.mall.entity.MicroneMallGoods;
import com.microne.mall.entity.StockNumDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MicroneMallGoodsMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(MicroneMallGoods record);

    int insertSelective(MicroneMallGoods record);

    MicroneMallGoods selectByPrimaryKey(Long goodsId);

    MicroneMallGoods selectByCategoryIdAndName(@Param("goodsName") String goodsName, @Param("goodsCategoryId") Long goodsCategoryId);

    int updateByPrimaryKeySelective(MicroneMallGoods record);

    int updateByPrimaryKeyWithBLOBs(MicroneMallGoods record);

    int updateByPrimaryKey(MicroneMallGoods record);

    List<MicroneMallGoods> findMicroneMallGoodsList(PageQueryUtil pageUtil);

    int getTotalMicroneMallGoods(PageQueryUtil pageUtil);

    List<MicroneMallGoods> selectByPrimaryKeys(List<Long> goodsIds);

    List<MicroneMallGoods> findMicroneMallGoodsListBySearch(PageQueryUtil pageUtil);

    int getTotalMicroneMallGoodsBySearch(PageQueryUtil pageUtil);

    int batchInsert(@Param("MicroneMallGoodsList") List<MicroneMallGoods> MicroneMallGoodsList);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int recoverStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);

}