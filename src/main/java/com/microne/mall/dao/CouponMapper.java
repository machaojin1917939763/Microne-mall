package com.microne.mall.dao;

import com.microne.mall.entity.TbMallCoupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 优惠券信息及规则表(TbMallCoupon)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-20 16:43:40
 */
public interface CouponMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param couponId 主键
     * @return 实例对象
     */
    TbMallCoupon queryById(Long couponId);

    /**
     * 查询指定行数据
     *
     * @param tbMallCoupon 查询条件
     * @param pageable     分页对象
     * @return 对象列表
     */
    List<TbMallCoupon> queryAllByLimit(TbMallCoupon tbMallCoupon, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param tbMallCoupon 查询条件
     * @return 总行数
     */
    long count(TbMallCoupon tbMallCoupon);

    /**
     * 新增数据
     *
     * @param tbMallCoupon 实例对象
     * @return 影响行数
     */
    int insert(TbMallCoupon tbMallCoupon);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbMallCoupon> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TbMallCoupon> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbMallCoupon> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TbMallCoupon> entities);

    /**
     * 修改数据
     *
     * @param tbMallCoupon 实例对象
     * @return 影响行数
     */
    int update(TbMallCoupon tbMallCoupon);

    /**
     * 通过主键删除数据
     *
     * @param couponId 主键
     * @return 影响行数
     */
    int deleteById(Long couponId);

}

