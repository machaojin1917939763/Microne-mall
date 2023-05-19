
package com.microne.mall.service;

import com.microne.mall.controller.vo.MicroneMallIndexCarouselVO;
import com.microne.mall.entity.Carousel;
import com.microne.mall.util.PageQueryUtil;
import com.microne.mall.util.PageResult;

import java.util.List;

public interface MicroneMallCarouselService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getCarouselPage(PageQueryUtil pageUtil);

    String saveCarousel(Carousel carousel);

    String updateCarousel(Carousel carousel);

    Carousel getCarouselById(Integer id);

    Boolean deleteBatch(Integer[] ids);

    /**
     * 返回固定数量的轮播图对象(首页调用)
     *
     * @param number
     * @return
     */
    List<MicroneMallIndexCarouselVO> getCarouselsForIndex(int number);
}
