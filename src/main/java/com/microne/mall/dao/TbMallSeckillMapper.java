package com.microne.mall.dao;

import com.microne.mall.entity.TbMallSeckill;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (TbMallSeckill)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-20 16:43:41
 */
public interface TbMallSeckillMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param seckillId 主键
     * @return 实例对象
     */
    TbMallSeckill queryById(Long seckillId);

    /**
     * 查询全部数据
     *
     * @return 实例对象
     */
    List<TbMallSeckill> querySeckillAll();

    /**
     * 查询指定行数据
     *
     * @param tbMallSeckill 查询条件
     * @param pageable      分页对象
     * @return 对象列表
     */
    List<TbMallSeckill> queryAllByLimit(TbMallSeckill tbMallSeckill, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param tbMallSeckill 查询条件
     * @return 总行数
     */
    long count(TbMallSeckill tbMallSeckill);

    /**
     * 新增数据
     *
     * @param tbMallSeckill 实例对象
     * @return 影响行数
     */
    int insert(TbMallSeckill tbMallSeckill);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbMallSeckill> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TbMallSeckill> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbMallSeckill> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TbMallSeckill> entities);

    /**
     * 修改数据
     *
     * @param tbMallSeckill 实例对象
     * @return 影响行数
     */
    int update(TbMallSeckill tbMallSeckill);

    /**
     * 通过主键删除数据
     *
     * @param seckillId 主键
     * @return 影响行数
     */
    int deleteById(Long seckillId);

}

