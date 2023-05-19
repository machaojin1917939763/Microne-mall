
package com.microne.mall.service.impl;

import com.microne.mall.controller.vo.MicroneMallSearchGoodsVO;
import com.microne.mall.service.MicroneMallGoodsService;
import com.microne.mall.common.MicroneMallCategoryLevelEnum;
import com.microne.mall.common.MicroneMallException;
import com.microne.mall.common.ServiceResultEnum;
import com.microne.mall.dao.GoodsCategoryMapper;
import com.microne.mall.dao.MicroneMallGoodsMapper;
import com.microne.mall.entity.GoodsCategory;
import com.microne.mall.entity.MicroneMallGoods;
import com.microne.mall.util.BeanUtil;
import com.microne.mall.util.MicroneMallUtils;
import com.microne.mall.util.PageQueryUtil;
import com.microne.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MicroneMallGoodsServiceImpl implements MicroneMallGoodsService {

    @Autowired
    private MicroneMallGoodsMapper goodsMapper;
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public PageResult getMicroneMallGoodsPage(PageQueryUtil pageUtil) {
        List<MicroneMallGoods> goodsList = goodsMapper.findMicroneMallGoodsList(pageUtil);
        int total = goodsMapper.getTotalMicroneMallGoods(pageUtil);
        PageResult pageResult = new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveMicroneMallGoods(MicroneMallGoods goods) {
        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(goods.getGoodsCategoryId());
        // 分类不存在或者不是三级分类，则该参数字段异常
        if (goodsCategory == null || goodsCategory.getCategoryLevel().intValue() != MicroneMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        if (goodsMapper.selectByCategoryIdAndName(goods.getGoodsName(), goods.getGoodsCategoryId()) != null) {
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        goods.setGoodsName(MicroneMallUtils.cleanString(goods.getGoodsName()));
        goods.setGoodsIntro(MicroneMallUtils.cleanString(goods.getGoodsIntro()));
        goods.setTag(MicroneMallUtils.cleanString(goods.getTag()));
        if (goodsMapper.insertSelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public void batchSaveMicroneMallGoods(List<MicroneMallGoods> MicroneMallGoodsList) {
        if (!CollectionUtils.isEmpty(MicroneMallGoodsList)) {
            goodsMapper.batchInsert(MicroneMallGoodsList);
        }
    }

    @Override
    public String updateMicroneMallGoods(MicroneMallGoods goods) {
        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(goods.getGoodsCategoryId());
        // 分类不存在或者不是三级分类，则该参数字段异常
        if (goodsCategory == null || goodsCategory.getCategoryLevel().intValue() != MicroneMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        MicroneMallGoods temp = goodsMapper.selectByPrimaryKey(goods.getGoodsId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        MicroneMallGoods temp2 = goodsMapper.selectByCategoryIdAndName(goods.getGoodsName(), goods.getGoodsCategoryId());
        if (temp2 != null && !temp2.getGoodsId().equals(goods.getGoodsId())) {
            //name和分类id相同且不同id 不能继续修改
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        goods.setGoodsName(MicroneMallUtils.cleanString(goods.getGoodsName()));
        goods.setGoodsIntro(MicroneMallUtils.cleanString(goods.getGoodsIntro()));
        goods.setTag(MicroneMallUtils.cleanString(goods.getTag()));
        goods.setUpdateTime(new Date());
        if (goodsMapper.updateByPrimaryKeySelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public MicroneMallGoods getMicroneMallGoodsById(Long id) {
        MicroneMallGoods MicroneMallGoods = goodsMapper.selectByPrimaryKey(id);
        if (MicroneMallGoods == null) {
            MicroneMallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        return MicroneMallGoods;
    }

    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return goodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }

    @Override
    public PageResult searchMicroneMallGoods(PageQueryUtil pageUtil) {
        List<MicroneMallGoods> goodsList = goodsMapper.findMicroneMallGoodsListBySearch(pageUtil);
        int total = goodsMapper.getTotalMicroneMallGoodsBySearch(pageUtil);
        List<MicroneMallSearchGoodsVO> MicroneMallSearchGoodsVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            MicroneMallSearchGoodsVOS = BeanUtil.copyList(goodsList, MicroneMallSearchGoodsVO.class);
            for (MicroneMallSearchGoodsVO MicroneMallSearchGoodsVO : MicroneMallSearchGoodsVOS) {
                String goodsName = MicroneMallSearchGoodsVO.getGoodsName();
                String goodsIntro = MicroneMallSearchGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 28) {
                    goodsName = goodsName.substring(0, 28) + "...";
                    MicroneMallSearchGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 30) {
                    goodsIntro = goodsIntro.substring(0, 30) + "...";
                    MicroneMallSearchGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        PageResult pageResult = new PageResult(MicroneMallSearchGoodsVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
