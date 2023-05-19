
package com.microne.mall.controller.admin;

import com.microne.mall.common.Constants;
import com.microne.mall.common.MicroneMallCategoryLevelEnum;
import com.microne.mall.common.MicroneMallException;
import com.microne.mall.common.ServiceResultEnum;
import com.microne.mall.entity.GoodsCategory;
import com.microne.mall.entity.MicroneMallGoods;
import com.microne.mall.util.PageQueryUtil;
import com.microne.mall.util.Result;
import com.microne.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author machaojin
 * 
 * @email 1917939763@qq.com
 * 
 */
@Controller
@RequestMapping("/admin")
public class MicroneMallGoodsController {

    @Resource
    private com.microne.mall.service.MicroneMallGoodsService MicroneMallGoodsService;
    @Resource
    private com.microne.mall.service.MicroneMallCategoryService MicroneMallCategoryService;

    @GetMapping("/goods")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "mall_goods");
        return "admin/mall_goods";
    }

    @GetMapping("/goods/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        //查询所有的一级分类
        List<GoodsCategory> firstLevelCategories = MicroneMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), MicroneMallCategoryLevelEnum.LEVEL_ONE.getLevel());
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            //查询一级分类列表中第一个实体的所有二级分类
            List<GoodsCategory> secondLevelCategories = MicroneMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), MicroneMallCategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<GoodsCategory> thirdLevelCategories = MicroneMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), MicroneMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                request.setAttribute("firstLevelCategories", firstLevelCategories);
                request.setAttribute("secondLevelCategories", secondLevelCategories);
                request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                request.setAttribute("path", "goods-edit");
                return "admin/mall_goods_edit";
            }
        }
        MicroneMallException.fail("分类数据不完善");
        return null;
    }

    @GetMapping("/goods/edit/{goodsId}")
    public String edit(HttpServletRequest request, @PathVariable("goodsId") Long goodsId) {
        request.setAttribute("path", "edit");
        MicroneMallGoods MicroneMallGoods = MicroneMallGoodsService.getMicroneMallGoodsById(goodsId);
        if (MicroneMallGoods.getGoodsCategoryId() > 0) {
            if (MicroneMallGoods.getGoodsCategoryId() != null || MicroneMallGoods.getGoodsCategoryId() > 0) {
                //有分类字段则查询相关分类数据返回给前端以供分类的三级联动显示
                GoodsCategory currentGoodsCategory = MicroneMallCategoryService.getGoodsCategoryById(MicroneMallGoods.getGoodsCategoryId());
                //商品表中存储的分类id字段为三级分类的id，不为三级分类则是错误数据
                if (currentGoodsCategory != null && currentGoodsCategory.getCategoryLevel() == MicroneMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
                    //查询所有的一级分类
                    List<GoodsCategory> firstLevelCategories = MicroneMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), MicroneMallCategoryLevelEnum.LEVEL_ONE.getLevel());
                    //根据parentId查询当前parentId下所有的三级分类
                    List<GoodsCategory> thirdLevelCategories = MicroneMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(currentGoodsCategory.getParentId()), MicroneMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    //查询当前三级分类的父级二级分类
                    GoodsCategory secondCategory = MicroneMallCategoryService.getGoodsCategoryById(currentGoodsCategory.getParentId());
                    if (secondCategory != null) {
                        //根据parentId查询当前parentId下所有的二级分类
                        List<GoodsCategory> secondLevelCategories = MicroneMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondCategory.getParentId()), MicroneMallCategoryLevelEnum.LEVEL_TWO.getLevel());
                        //查询当前二级分类的父级一级分类
                        GoodsCategory firestCategory = MicroneMallCategoryService.getGoodsCategoryById(secondCategory.getParentId());
                        if (firestCategory != null) {
                            //所有分类数据都得到之后放到request对象中供前端读取
                            request.setAttribute("firstLevelCategories", firstLevelCategories);
                            request.setAttribute("secondLevelCategories", secondLevelCategories);
                            request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                            request.setAttribute("firstLevelCategoryId", firestCategory.getCategoryId());
                            request.setAttribute("secondLevelCategoryId", secondCategory.getCategoryId());
                            request.setAttribute("thirdLevelCategoryId", currentGoodsCategory.getCategoryId());
                        }
                    }
                }
            }
        }
        if (MicroneMallGoods.getGoodsCategoryId() == 0) {
            //查询所有的一级分类
            List<GoodsCategory> firstLevelCategories = MicroneMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), MicroneMallCategoryLevelEnum.LEVEL_ONE.getLevel());
            if (!CollectionUtils.isEmpty(firstLevelCategories)) {
                //查询一级分类列表中第一个实体的所有二级分类
                List<GoodsCategory> secondLevelCategories = MicroneMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), MicroneMallCategoryLevelEnum.LEVEL_TWO.getLevel());
                if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                    //查询二级分类列表中第一个实体的所有三级分类
                    List<GoodsCategory> thirdLevelCategories = MicroneMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), MicroneMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    request.setAttribute("firstLevelCategories", firstLevelCategories);
                    request.setAttribute("secondLevelCategories", secondLevelCategories);
                    request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                }
            }
        }
        request.setAttribute("goods", MicroneMallGoods);
        request.setAttribute("path", "goods-edit");
        return "admin/mall_goods_edit";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("page")) || ObjectUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(MicroneMallGoodsService.getMicroneMallGoodsPage(pageUtil));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/goods/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody MicroneMallGoods MicroneMallGoods) {
        if (!StringUtils.hasText(MicroneMallGoods.getGoodsName())
                || !StringUtils.hasText(MicroneMallGoods.getGoodsIntro())
                || !StringUtils.hasText(MicroneMallGoods.getTag())
                || Objects.isNull(MicroneMallGoods.getOriginalPrice())
                || Objects.isNull(MicroneMallGoods.getGoodsCategoryId())
                || Objects.isNull(MicroneMallGoods.getSellingPrice())
                || Objects.isNull(MicroneMallGoods.getStockNum())
                || Objects.isNull(MicroneMallGoods.getGoodsSellStatus())
                || !StringUtils.hasText(MicroneMallGoods.getGoodsCoverImg())
                || !StringUtils.hasText(MicroneMallGoods.getGoodsDetailContent())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = MicroneMallGoodsService.saveMicroneMallGoods(MicroneMallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/goods/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody MicroneMallGoods MicroneMallGoods) {
        if (Objects.isNull(MicroneMallGoods.getGoodsId())
                || !StringUtils.hasText(MicroneMallGoods.getGoodsName())
                || !StringUtils.hasText(MicroneMallGoods.getGoodsIntro())
                || !StringUtils.hasText(MicroneMallGoods.getTag())
                || Objects.isNull(MicroneMallGoods.getOriginalPrice())
                || Objects.isNull(MicroneMallGoods.getSellingPrice())
                || Objects.isNull(MicroneMallGoods.getGoodsCategoryId())
                || Objects.isNull(MicroneMallGoods.getStockNum())
                || Objects.isNull(MicroneMallGoods.getGoodsSellStatus())
                || !StringUtils.hasText(MicroneMallGoods.getGoodsCoverImg())
                || !StringUtils.hasText(MicroneMallGoods.getGoodsDetailContent())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = MicroneMallGoodsService.updateMicroneMallGoods(MicroneMallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/goods/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        MicroneMallGoods goods = MicroneMallGoodsService.getMicroneMallGoodsById(id);
        return ResultGenerator.genSuccessResult(goods);
    }

    /**
     * 批量修改销售状态
     */
    @RequestMapping(value = "/goods/status/{sellStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids, @PathVariable("sellStatus") int sellStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (sellStatus != Constants.SELL_STATUS_UP && sellStatus != Constants.SELL_STATUS_DOWN) {
            return ResultGenerator.genFailResult("状态异常！");
        }
        if (MicroneMallGoodsService.batchUpdateSellStatus(ids, sellStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }

}