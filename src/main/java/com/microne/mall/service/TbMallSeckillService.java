package com.microne.mall.service;

import com.microne.mall.controller.vo.MicroneSeckillVO;
import com.microne.mall.entity.TbMallSeckill;
import com.microne.mall.util.Result;

import java.util.List;

/**
 * (TbMallSeckill)表服务接口
 *
 * @author makejava
 * @since 2023-05-20 16:43:41
 */
public interface TbMallSeckillService {

    /**
     * 通过ID查询单条数据
     *
     * @param seckillId 主键
     * @return 实例对象
     */
    TbMallSeckill queryById(Long seckillId);

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    List<TbMallSeckill> queryByPage();

    /**
     * 新增数据
     *
     * @param tbMallSeckill 实例对象
     * @return 实例对象
     */
    TbMallSeckill insert(TbMallSeckill tbMallSeckill);

    /**
     * 修改数据
     *
     * @param tbMallSeckill 实例对象
     * @return 实例对象
     */
    TbMallSeckill update(TbMallSeckill tbMallSeckill);

    /**
     * 通过主键删除数据
     *
     * @param seckillId 主键
     * @return 是否成功
     */
    boolean deleteById(Long seckillId);

    public Result<List<MicroneSeckillVO>> getListResult();

    MicroneSeckillVO getSekillGoodsById(String id);
}
