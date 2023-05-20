package com.microne.mall.service;

import com.microne.mall.entity.TbMallCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 优惠券信息及规则表(TbMallCoupon)表服务接口
 *
 * @author makejava
 * @since 2023-05-20 16:43:40
 */
public interface TbMallCouponService {

    /**
     * 通过ID查询单条数据
     *
     * @param couponId 主键
     * @return 实例对象
     */
    TbMallCoupon queryById(Long couponId);

    /**
     * 分页查询
     *
     * @param tbMallCoupon 筛选条件
     * @param pageRequest  分页对象
     * @return 查询结果
     */
    Page<TbMallCoupon> queryByPage(TbMallCoupon tbMallCoupon, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param tbMallCoupon 实例对象
     * @return 实例对象
     */
    TbMallCoupon insert(TbMallCoupon tbMallCoupon);

    /**
     * 修改数据
     *
     * @param tbMallCoupon 实例对象
     * @return 实例对象
     */
    TbMallCoupon update(TbMallCoupon tbMallCoupon);

    /**
     * 通过主键删除数据
     *
     * @param couponId 主键
     * @return 是否成功
     */
    boolean deleteById(Long couponId);

}
