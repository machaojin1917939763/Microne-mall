
package com.microne.mall.service.impl;

import com.microne.mall.common.ServiceResultEnum;
import com.microne.mall.controller.vo.MicroneMallIndexCarouselVO;
import com.microne.mall.dao.CarouselMapper;
import com.microne.mall.entity.Carousel;
import com.microne.mall.service.MicroneMallCarouselService;
import com.microne.mall.util.BeanUtil;
import com.microne.mall.util.PageQueryUtil;
import com.microne.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MicroneMallCarouselServiceImpl implements MicroneMallCarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    @Cacheable(value = "carousel",keyGenerator = "myKeyGenerator",sync = true)
    public PageResult getCarouselPage(PageQueryUtil pageUtil) {
        List<Carousel> carousels = carouselMapper.findCarouselList(pageUtil);
        int total = carouselMapper.getTotalCarousels(pageUtil);
        PageResult pageResult = new PageResult(carousels, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    @CacheEvict(value = "carousel",keyGenerator = "myKeyGenerator",allEntries = true)
    public String saveCarousel(Carousel carousel) {
        if (carouselMapper.insertSelective(carousel) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    @CacheEvict(value = "carousel",keyGenerator = "myKeyGenerator",allEntries = true)
    public String updateCarousel(Carousel carousel) {
        Carousel temp = carouselMapper.selectByPrimaryKey(carousel.getCarouselId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        temp.setCarouselRank(carousel.getCarouselRank());
        temp.setRedirectUrl(carousel.getRedirectUrl());
        temp.setCarouselUrl(carousel.getCarouselUrl());
        temp.setUpdateTime(new Date());
        if (carouselMapper.updateByPrimaryKeySelective(temp) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    @Cacheable(value = "carousel",keyGenerator = "myKeyGenerator",sync = true)
    public Carousel getCarouselById(Integer id) {
        return carouselMapper.selectByPrimaryKey(id);
    }

    @Override
    @CacheEvict(value = "carousel",keyGenerator = "myKeyGenerator",allEntries = true)
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除数据
        return carouselMapper.deleteBatch(ids) > 0;
    }

    @Override
    @Cacheable(value = "carouse",keyGenerator = "myKeyGenerator",sync = true)
    public List<MicroneMallIndexCarouselVO> getCarouselsForIndex(int number) {
        List<MicroneMallIndexCarouselVO> MicroneMallIndexCarouselVOS = new ArrayList<>(number);
        List<Carousel> carousels = carouselMapper.findCarouselsByNum(number);
        if (!CollectionUtils.isEmpty(carousels)) {
            MicroneMallIndexCarouselVOS = BeanUtil.copyList(carousels, MicroneMallIndexCarouselVO.class);
        }
        return MicroneMallIndexCarouselVOS;
    }
}
