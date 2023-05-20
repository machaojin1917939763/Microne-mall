package com.microne.mall.service.impl;

import com.microne.mall.controller.vo.MicroneSeckillVO;
import com.microne.mall.entity.MicroneMallGoods;
import com.microne.mall.entity.TbMallSeckill;
import com.microne.mall.dao.TbMallSeckillMapper;
import com.microne.mall.service.MicroneMallGoodsService;
import com.microne.mall.service.TbMallSeckillService;
import com.microne.mall.util.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (TbMallSeckill)表服务实现类
 *
 * @author makejava
 * @since 2023-05-20 16:43:41
 */
@Service("tbMallSeckillService")
public class TbMallSeckillServiceImpl implements TbMallSeckillService {
    @Resource
    private TbMallSeckillMapper tbMallSeckillMapper;


    @Autowired
    private MicroneMallGoodsService goodsService;

    /**
     * 通过ID查询单条数据
     *
     * @param seckillId 主键
     * @return 实例对象
     */
    @Override
    public TbMallSeckill queryById(Long seckillId) {
        return this.tbMallSeckillMapper.queryById(seckillId);
    }

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @Override
    public List<TbMallSeckill> queryByPage() {
        return tbMallSeckillMapper.querySeckillAll();
    }

    /**
     * 新增数据
     *
     * @param tbMallSeckill 实例对象
     * @return 实例对象
     */
    @Override
    public TbMallSeckill insert(TbMallSeckill tbMallSeckill) {
        this.tbMallSeckillMapper.insert(tbMallSeckill);
        return tbMallSeckill;
    }

    /**
     * 修改数据
     *
     * @param tbMallSeckill 实例对象
     * @return 实例对象
     */
    @Override
    public TbMallSeckill update(TbMallSeckill tbMallSeckill) {
        this.tbMallSeckillMapper.update(tbMallSeckill);
        return this.queryById(tbMallSeckill.getSeckillId());
    }

    /**
     * 通过主键删除数据
     *
     * @param seckillId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long seckillId) {
        return this.tbMallSeckillMapper.deleteById(seckillId) > 0;
    }

    @Override
    public Result<List<MicroneSeckillVO>> getListResult() {
        List<TbMallSeckill> tbMallSeckills = this.queryByPage();
        List<MicroneSeckillVO> collect = tbMallSeckills.stream().map((se) -> {

            MicroneMallGoods mallGoodsBy = goodsService.getMicroneMallGoodsById(se.getGoodsId());
            MicroneSeckillVO microneSeckillVO = new MicroneSeckillVO();
            BeanUtils.copyProperties(mallGoodsBy, microneSeckillVO);

            microneSeckillVO.setSeckillBegin(se.getSeckillBegin());
            microneSeckillVO.setSeckillEnd(se.getSeckillEnd());

            microneSeckillVO.setEndDate(se.getSeckillEnd());
            microneSeckillVO.setStartDate(se.getSeckillBegin());

            microneSeckillVO.setSeckillPrice(se.getSeckillPrice());
            microneSeckillVO.setSellingPrice(mallGoodsBy.getOriginalPrice());
            microneSeckillVO.setGoodsId(mallGoodsBy.getGoodsId().intValue());

            microneSeckillVO.setSeckillBeginTime(se.getSeckillBegin());
            microneSeckillVO.setSeckillEndTime(se.getSeckillEnd());
            BeanUtils.copyProperties(se, microneSeckillVO);
            microneSeckillVO.setSeckillId(se.getSeckillId().intValue());
            return microneSeckillVO;
        }).collect(Collectors.toList());
        Result<List<MicroneSeckillVO>> listResult = new Result<>();
        listResult.setResultCode(200);
        listResult.setData(collect);
        listResult.setMessage("SUCCESS");
        return listResult;
    }

    @Override
    public MicroneSeckillVO getSekillGoodsById(String id) {
        MicroneSeckillVO microneSeckillVO = new MicroneSeckillVO();
        TbMallSeckill sekill = tbMallSeckillMapper.queryById(Long.parseLong(id));
        MicroneMallGoods goods = goodsService.getMicroneMallGoodsById(sekill.getGoodsId());

        BeanUtils.copyProperties(sekill, microneSeckillVO);
        BeanUtils.copyProperties(goods, microneSeckillVO);

        microneSeckillVO.setSeckillBeginTime(sekill.getSeckillBegin());
        microneSeckillVO.setSeckillEndTime(sekill.getSeckillEnd());

        microneSeckillVO.setStartDate(sekill.getSeckillBegin());
        microneSeckillVO.setEndDate(sekill.getSeckillEnd());

        return microneSeckillVO;
    }
}
