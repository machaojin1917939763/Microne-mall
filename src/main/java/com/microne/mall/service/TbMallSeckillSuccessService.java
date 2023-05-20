package com.microne.mall.service;

import com.microne.mall.entity.TbMallSeckillSuccess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 秒杀库存表(TbMallSeckillSuccess)表服务接口
 *
 * @author makejava
 * @since 2023-05-20 16:43:41
 */
public interface TbMallSeckillSuccessService {

    /**
     * 通过ID查询单条数据
     *
     * @param secId 主键
     * @return 实例对象
     */
    TbMallSeckillSuccess queryById(Long secId);

    /**
     * 分页查询
     *
     * @param tbMallSeckillSuccess 筛选条件
     * @param pageRequest          分页对象
     * @return 查询结果
     */
    Page<TbMallSeckillSuccess> queryByPage(TbMallSeckillSuccess tbMallSeckillSuccess, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param tbMallSeckillSuccess 实例对象
     * @return 实例对象
     */
    TbMallSeckillSuccess insert(TbMallSeckillSuccess tbMallSeckillSuccess);

    /**
     * 修改数据
     *
     * @param tbMallSeckillSuccess 实例对象
     * @return 实例对象
     */
    TbMallSeckillSuccess update(TbMallSeckillSuccess tbMallSeckillSuccess);

    /**
     * 通过主键删除数据
     *
     * @param secId 主键
     * @return 是否成功
     */
    boolean deleteById(Long secId);

}
