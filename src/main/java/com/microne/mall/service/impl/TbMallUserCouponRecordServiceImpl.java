package com.microne.mall.service.impl;

import com.microne.mall.entity.TbMallUserCouponRecord;
import com.microne.mall.dao.TbMallUserCouponRecordMapper;
import com.microne.mall.service.TbMallUserCouponRecordService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 优惠券用户使用表(TbMallUserCouponRecord)表服务实现类
 *
 * @author makejava
 * @since 2023-05-20 16:43:42
 */
@Service("tbMallUserCouponRecordService")
public class TbMallUserCouponRecordServiceImpl implements TbMallUserCouponRecordService {
    @Resource
    private TbMallUserCouponRecordMapper tbMallUserCouponRecordMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param couponUserId 主键
     * @return 实例对象
     */
    @Override
    public TbMallUserCouponRecord queryById(Long couponUserId) {
        return this.tbMallUserCouponRecordMapper.queryById(couponUserId);
    }

    /**
     * 分页查询
     *
     * @param tbMallUserCouponRecord 筛选条件
     * @param pageRequest            分页对象
     * @return 查询结果
     */
    @Override
    public Page<TbMallUserCouponRecord> queryByPage(TbMallUserCouponRecord tbMallUserCouponRecord, PageRequest pageRequest) {
        long total = this.tbMallUserCouponRecordMapper.count(tbMallUserCouponRecord);
        return new PageImpl<>(this.tbMallUserCouponRecordMapper.queryAllByLimit(tbMallUserCouponRecord, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param tbMallUserCouponRecord 实例对象
     * @return 实例对象
     */
    @Override
    public TbMallUserCouponRecord insert(TbMallUserCouponRecord tbMallUserCouponRecord) {
        this.tbMallUserCouponRecordMapper.insert(tbMallUserCouponRecord);
        return tbMallUserCouponRecord;
    }

    /**
     * 修改数据
     *
     * @param tbMallUserCouponRecord 实例对象
     * @return 实例对象
     */
    @Override
    public TbMallUserCouponRecord update(TbMallUserCouponRecord tbMallUserCouponRecord) {
        this.tbMallUserCouponRecordMapper.update(tbMallUserCouponRecord);
        return this.queryById(tbMallUserCouponRecord.getCouponUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param couponUserId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long couponUserId) {
        return this.tbMallUserCouponRecordMapper.deleteById(couponUserId) > 0;
    }
}
