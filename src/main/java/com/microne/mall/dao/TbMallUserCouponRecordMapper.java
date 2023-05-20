package com.microne.mall.dao;

import com.microne.mall.entity.TbMallUserCouponRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 优惠券用户使用表(TbMallUserCouponRecord)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-20 16:43:42
 */
public interface TbMallUserCouponRecordMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param couponUserId 主键
     * @return 实例对象
     */
    TbMallUserCouponRecord queryById(Long couponUserId);

    /**
     * 查询指定行数据
     *
     * @param tbMallUserCouponRecord 查询条件
     * @param pageable               分页对象
     * @return 对象列表
     */
    List<TbMallUserCouponRecord> queryAllByLimit(TbMallUserCouponRecord tbMallUserCouponRecord, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param tbMallUserCouponRecord 查询条件
     * @return 总行数
     */
    long count(TbMallUserCouponRecord tbMallUserCouponRecord);

    /**
     * 新增数据
     *
     * @param tbMallUserCouponRecord 实例对象
     * @return 影响行数
     */
    int insert(TbMallUserCouponRecord tbMallUserCouponRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbMallUserCouponRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TbMallUserCouponRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbMallUserCouponRecord> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TbMallUserCouponRecord> entities);

    /**
     * 修改数据
     *
     * @param tbMallUserCouponRecord 实例对象
     * @return 影响行数
     */
    int update(TbMallUserCouponRecord tbMallUserCouponRecord);

    /**
     * 通过主键删除数据
     *
     * @param couponUserId 主键
     * @return 影响行数
     */
    int deleteById(Long couponUserId);

}

