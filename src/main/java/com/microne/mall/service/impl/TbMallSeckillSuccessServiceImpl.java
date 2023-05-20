package com.microne.mall.service.impl;

import com.microne.mall.entity.TbMallSeckillSuccess;
import com.microne.mall.dao.TbMallSeckillSuccessMapper;
import com.microne.mall.service.TbMallSeckillSuccessService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 秒杀库存表(TbMallSeckillSuccess)表服务实现类
 *
 * @author makejava
 * @since 2023-05-20 16:43:41
 */
@Service("tbMallSeckillSuccessService")
public class TbMallSeckillSuccessServiceImpl implements TbMallSeckillSuccessService {
    @Resource
    private TbMallSeckillSuccessMapper tbMallSeckillSuccessMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param secId 主键
     * @return 实例对象
     */
    @Override
    public TbMallSeckillSuccess queryById(Long secId) {
        return this.tbMallSeckillSuccessMapper.queryById(secId);
    }

    /**
     * 分页查询
     *
     * @param tbMallSeckillSuccess 筛选条件
     * @param pageRequest          分页对象
     * @return 查询结果
     */
    @Override
    public Page<TbMallSeckillSuccess> queryByPage(TbMallSeckillSuccess tbMallSeckillSuccess, PageRequest pageRequest) {
        long total = this.tbMallSeckillSuccessMapper.count(tbMallSeckillSuccess);
        return new PageImpl<>(this.tbMallSeckillSuccessMapper.queryAllByLimit(tbMallSeckillSuccess, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param tbMallSeckillSuccess 实例对象
     * @return 实例对象
     */
    @Override
    public TbMallSeckillSuccess insert(TbMallSeckillSuccess tbMallSeckillSuccess) {
        this.tbMallSeckillSuccessMapper.insert(tbMallSeckillSuccess);
        return tbMallSeckillSuccess;
    }

    /**
     * 修改数据
     *
     * @param tbMallSeckillSuccess 实例对象
     * @return 实例对象
     */
    @Override
    public TbMallSeckillSuccess update(TbMallSeckillSuccess tbMallSeckillSuccess) {
        this.tbMallSeckillSuccessMapper.update(tbMallSeckillSuccess);
        return this.queryById(tbMallSeckillSuccess.getSecId());
    }

    /**
     * 通过主键删除数据
     *
     * @param secId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long secId) {
        return this.tbMallSeckillSuccessMapper.deleteById(secId) > 0;
    }
}
