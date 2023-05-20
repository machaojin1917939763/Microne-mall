package com.microne.mall.service.impl;

import com.microne.mall.entity.TbMallCoupon;
import com.microne.mall.dao.CouponMapper;
import com.microne.mall.service.TbMallCouponService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 优惠券信息及规则表(TbMallCoupon)表服务实现类
 *
 * @author makejava
 * @since 2023-05-20 16:43:40
 */
@Service("tbMallCouponService")
public class TbMallCouponServiceImpl implements TbMallCouponService {
    @Resource
    private CouponMapper tbMallCouponMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param couponId 主键
     * @return 实例对象
     */
    @Override
    public TbMallCoupon queryById(Long couponId) {
        return this.tbMallCouponMapper.queryById(couponId);
    }

    /**
     * 分页查询
     *
     * @param tbMallCoupon 筛选条件
     * @param pageRequest  分页对象
     * @return 查询结果
     */
    @Override
    public Page<TbMallCoupon> queryByPage(TbMallCoupon tbMallCoupon, PageRequest pageRequest) {
        long total = this.tbMallCouponMapper.count(tbMallCoupon);
        return new PageImpl<>(this.tbMallCouponMapper.queryAllByLimit(tbMallCoupon, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param tbMallCoupon 实例对象
     * @return 实例对象
     */
    @Override
    public TbMallCoupon insert(TbMallCoupon tbMallCoupon) {
        this.tbMallCouponMapper.insert(tbMallCoupon);
        return tbMallCoupon;
    }

    /**
     * 修改数据
     *
     * @param tbMallCoupon 实例对象
     * @return 实例对象
     */
    @Override
    public TbMallCoupon update(TbMallCoupon tbMallCoupon) {
        this.tbMallCouponMapper.update(tbMallCoupon);
        return this.queryById(tbMallCoupon.getCouponId());
    }

    /**
     * 通过主键删除数据
     *
     * @param couponId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long couponId) {
        return this.tbMallCouponMapper.deleteById(couponId) > 0;
    }
}
