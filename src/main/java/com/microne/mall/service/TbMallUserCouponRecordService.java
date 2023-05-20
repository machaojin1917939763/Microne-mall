package com.microne.mall.service;

import com.microne.mall.entity.TbMallUserCouponRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 优惠券用户使用表(TbMallUserCouponRecord)表服务接口
 *
 * @author makejava
 * @since 2023-05-20 16:43:42
 */
public interface TbMallUserCouponRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param couponUserId 主键
     * @return 实例对象
     */
    TbMallUserCouponRecord queryById(Long couponUserId);

    /**
     * 分页查询
     *
     * @param tbMallUserCouponRecord 筛选条件
     * @param pageRequest            分页对象
     * @return 查询结果
     */
    Page<TbMallUserCouponRecord> queryByPage(TbMallUserCouponRecord tbMallUserCouponRecord, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param tbMallUserCouponRecord 实例对象
     * @return 实例对象
     */
    TbMallUserCouponRecord insert(TbMallUserCouponRecord tbMallUserCouponRecord);

    /**
     * 修改数据
     *
     * @param tbMallUserCouponRecord 实例对象
     * @return 实例对象
     */
    TbMallUserCouponRecord update(TbMallUserCouponRecord tbMallUserCouponRecord);

    /**
     * 通过主键删除数据
     *
     * @param couponUserId 主键
     * @return 是否成功
     */
    boolean deleteById(Long couponUserId);

}
