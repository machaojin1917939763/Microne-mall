
package com.microne.mall.controller.mall;

import com.microne.mall.controller.vo.MicroneMallIndexCarouselVO;
import com.microne.mall.controller.vo.MicroneMallIndexCategoryVO;
import com.microne.mall.controller.vo.MicroneMallIndexConfigGoodsVO;
import com.microne.mall.common.Constants;
import com.microne.mall.common.IndexConfigTypeEnum;
import com.microne.mall.common.MicroneMallException;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Resource
    private com.microne.mall.service.MicroneMallCarouselService MicroneMallCarouselService;

    @Resource
    private com.microne.mall.service.MicroneMallIndexConfigService MicroneMallIndexConfigService;

    @Resource
    private com.microne.mall.service.MicroneMallCategoryService MicroneMallCategoryService;

    @GetMapping({"/index", "/", "/index.html"})
    public String indexPage(HttpServletRequest request) {
        List<MicroneMallIndexCategoryVO> categories = MicroneMallCategoryService.getCategoriesForIndex();
        if (CollectionUtils.isEmpty(categories)) {
            MicroneMallException.fail("分类数据不完善");
        }
        List<MicroneMallIndexCarouselVO> carousels = MicroneMallCarouselService.getCarouselsForIndex(Constants.INDEX_CAROUSEL_NUMBER);
        List<MicroneMallIndexConfigGoodsVO> hotGoodses = MicroneMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType(), Constants.INDEX_GOODS_HOT_NUMBER);
        List<MicroneMallIndexConfigGoodsVO> newGoodses = MicroneMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_NEW.getType(), Constants.INDEX_GOODS_NEW_NUMBER);
        List<MicroneMallIndexConfigGoodsVO> recommendGoodses = MicroneMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_RECOMMOND.getType(), Constants.INDEX_GOODS_RECOMMOND_NUMBER);
        request.setAttribute("categories", categories);//分类数据
        request.setAttribute("carousels", carousels);//轮播图
        request.setAttribute("hotGoodses", hotGoodses);//热销商品
        request.setAttribute("newGoodses", newGoodses);//新品
        request.setAttribute("recommendGoodses", recommendGoodses);//推荐商品
        return "mall/index";
    }
}
