package com.microne.mall.controller.mall;

import com.microne.mall.entity.TbMallCoupon;
import com.microne.mall.service.TbMallCouponService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 优惠券信息及规则表(TbMallCoupon)表控制层
 *
 * @author makejava
 * @since 2023-05-20 16:43:39
 */
@RestController
@RequestMapping("coupon")
public class CouponController {
    /**
     * 服务对象
     */
    @Resource
    private TbMallCouponService tbMallCouponService;

    /**
     * 分页查询
     *
     * @param tbMallCoupon 筛选条件
     * @param pageRequest  分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<TbMallCoupon>> queryByPage(TbMallCoupon tbMallCoupon, PageRequest pageRequest) {
        return ResponseEntity.ok(this.tbMallCouponService.queryByPage(tbMallCoupon, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<TbMallCoupon> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.tbMallCouponService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param tbMallCoupon 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<TbMallCoupon> add(TbMallCoupon tbMallCoupon) {
        return ResponseEntity.ok(this.tbMallCouponService.insert(tbMallCoupon));
    }

    /**
     * 编辑数据
     *
     * @param tbMallCoupon 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<TbMallCoupon> edit(TbMallCoupon tbMallCoupon) {
        return ResponseEntity.ok(this.tbMallCouponService.update(tbMallCoupon));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.tbMallCouponService.deleteById(id));
    }

}

