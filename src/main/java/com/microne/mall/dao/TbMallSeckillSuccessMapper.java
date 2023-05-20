package com.microne.mall.dao;

import com.microne.mall.entity.TbMallSeckillSuccess;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 秒杀库存表(TbMallSeckillSuccess)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-20 16:43:41
 */
public interface TbMallSeckillSuccessMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param secId 主键
     * @return 实例对象
     */
    TbMallSeckillSuccess queryById(Long secId);

    /**
     * 查询指定行数据
     *
     * @param tbMallSeckillSuccess 查询条件
     * @param pageable             分页对象
     * @return 对象列表
     */
    List<TbMallSeckillSuccess> queryAllByLimit(TbMallSeckillSuccess tbMallSeckillSuccess, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param tbMallSeckillSuccess 查询条件
     * @return 总行数
     */
    long count(TbMallSeckillSuccess tbMallSeckillSuccess);

    /**
     * 新增数据
     *
     * @param tbMallSeckillSuccess 实例对象
     * @return 影响行数
     */
    int insert(TbMallSeckillSuccess tbMallSeckillSuccess);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbMallSeckillSuccess> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TbMallSeckillSuccess> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbMallSeckillSuccess> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TbMallSeckillSuccess> entities);

    /**
     * 修改数据
     *
     * @param tbMallSeckillSuccess 实例对象
     * @return 影响行数
     */
    int update(TbMallSeckillSuccess tbMallSeckillSuccess);

    /**
     * 通过主键删除数据
     *
     * @param secId 主键
     * @return 影响行数
     */
    int deleteById(Long secId);

}

