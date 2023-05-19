
package com.microne.mall.service;

import com.microne.mall.controller.vo.MicroneMallIndexConfigGoodsVO;
import com.microne.mall.entity.IndexConfig;
import com.microne.mall.util.PageQueryUtil;
import com.microne.mall.util.PageResult;

import java.util.List;

public interface MicroneMallIndexConfigService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getConfigsPage(PageQueryUtil pageUtil);

    String saveIndexConfig(IndexConfig indexConfig);

    String updateIndexConfig(IndexConfig indexConfig);

    IndexConfig getIndexConfigById(Long id);

    /**
     * 返回固定数量的首页配置商品对象(首页调用)
     *
     * @param number
     * @return
     */
    List<MicroneMallIndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number);

    Boolean deleteBatch(Long[] ids);
}
