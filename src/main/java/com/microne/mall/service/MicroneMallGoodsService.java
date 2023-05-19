
package com.microne.mall.service;

import com.microne.mall.entity.MicroneMallGoods;
import com.microne.mall.util.PageQueryUtil;
import com.microne.mall.util.PageResult;

import java.util.List;

public interface MicroneMallGoodsService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getMicroneMallGoodsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    String saveMicroneMallGoods(MicroneMallGoods goods);

    /**
     * 批量新增商品数据
     *
     * @param MicroneMallGoodsList
     * @return
     */
    void batchSaveMicroneMallGoods(List<MicroneMallGoods> MicroneMallGoodsList);

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    String updateMicroneMallGoods(MicroneMallGoods goods);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    MicroneMallGoods getMicroneMallGoodsById(Long id);

    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */
    Boolean batchUpdateSellStatus(Long[] ids,int sellStatus);

    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */
    PageResult searchMicroneMallGoods(PageQueryUtil pageUtil);
}
