
package com.microne.mall.service.impl;

import com.microne.mall.controller.vo.MicroneMallIndexConfigGoodsVO;
import com.microne.mall.service.MicroneMallIndexConfigService;
import com.microne.mall.common.ServiceResultEnum;
import com.microne.mall.dao.IndexConfigMapper;
import com.microne.mall.dao.MicroneMallGoodsMapper;
import com.microne.mall.entity.IndexConfig;
import com.microne.mall.entity.MicroneMallGoods;
import com.microne.mall.util.BeanUtil;
import com.microne.mall.util.PageQueryUtil;
import com.microne.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MicroneMallIndexConfigServiceImpl implements MicroneMallIndexConfigService {

    @Autowired
    private IndexConfigMapper indexConfigMapper;

    @Autowired
    private MicroneMallGoodsMapper goodsMapper;

    @Override
    @Cacheable(value = "configService",sync = true,keyGenerator = "myKeyGenerator")
    public PageResult getConfigsPage(PageQueryUtil pageUtil) {
        List<IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigList(pageUtil);
        int total = indexConfigMapper.getTotalIndexConfigs(pageUtil);
        PageResult pageResult = new PageResult(indexConfigs, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    @CacheEvict(value = "configService",keyGenerator = "myKeyGenerator",allEntries = true)
    public String saveIndexConfig(IndexConfig indexConfig) {
        if (goodsMapper.selectByPrimaryKey(indexConfig.getGoodsId()) == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        if (indexConfigMapper.selectByTypeAndGoodsId(indexConfig.getConfigType(), indexConfig.getGoodsId()) != null) {
            return ServiceResultEnum.SAME_INDEX_CONFIG_EXIST.getResult();
        }
        if (indexConfigMapper.insertSelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    @CacheEvict(value = "configService",keyGenerator = "myKeyGenerator",allEntries = true)
    public String updateIndexConfig(IndexConfig indexConfig) {
        if (goodsMapper.selectByPrimaryKey(indexConfig.getGoodsId()) == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        IndexConfig temp = indexConfigMapper.selectByPrimaryKey(indexConfig.getConfigId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        IndexConfig temp2 = indexConfigMapper.selectByTypeAndGoodsId(indexConfig.getConfigType(), indexConfig.getGoodsId());
        if (temp2 != null && !temp2.getConfigId().equals(indexConfig.getConfigId())) {
            //goodsId相同且不同id 不能继续修改
            return ServiceResultEnum.SAME_INDEX_CONFIG_EXIST.getResult();
        }
        indexConfig.setUpdateTime(new Date());
        if (indexConfigMapper.updateByPrimaryKeySelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    @Cacheable(value = "configService",sync = true,keyGenerator = "myKeyGenerator")
    public IndexConfig getIndexConfigById(Long id) {
        return null;
    }

    @Override
    @Cacheable(value = "configService",sync = true,keyGenerator = "myKeyGenerator")
    public List<MicroneMallIndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number) {
        List<MicroneMallIndexConfigGoodsVO> MicroneMallIndexConfigGoodsVOS = new ArrayList<>(number);
        List<IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigsByTypeAndNum(configType, number);
        if (!CollectionUtils.isEmpty(indexConfigs)) {
            //取出所有的goodsId
            List<Long> goodsIds = indexConfigs.stream().map(IndexConfig::getGoodsId).collect(Collectors.toList());
            List<MicroneMallGoods> MicroneMallGoods = goodsMapper.selectByPrimaryKeys(goodsIds);
            MicroneMallIndexConfigGoodsVOS = BeanUtil.copyList(MicroneMallGoods, MicroneMallIndexConfigGoodsVO.class);
            for (MicroneMallIndexConfigGoodsVO MicroneMallIndexConfigGoodsVO : MicroneMallIndexConfigGoodsVOS) {
                String goodsName = MicroneMallIndexConfigGoodsVO.getGoodsName();
                String goodsIntro = MicroneMallIndexConfigGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 30) {
                    goodsName = goodsName.substring(0, 30) + "...";
                    MicroneMallIndexConfigGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 22) {
                    goodsIntro = goodsIntro.substring(0, 22) + "...";
                    MicroneMallIndexConfigGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        return MicroneMallIndexConfigGoodsVOS;
    }

    @Override
    @CacheEvict(value = "configService",allEntries = true,keyGenerator = "myKeyGenerator")
    public Boolean deleteBatch(Long[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除数据
        return indexConfigMapper.deleteBatch(ids) > 0;
    }
}
