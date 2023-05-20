package com.microne.mall.entity;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (TbMallSeckill)实体类
 *
 * @author makejava
 * @since 2023-05-20 16:43:41
 */
@Data
public class TbMallSeckill implements Serializable {
    private static final long serialVersionUID = -15903634823696584L;
    /**
     * 自增ID
     */
    private Long seckillId;
    /**
     * 秒杀商品ID
     */
    private Long goodsId;
    /**
     * 秒杀价格
     */
    private Integer seckillPrice;
    /**
     * 秒杀数量
     */
    private Integer seckillNum;
    /**
     * 秒杀商品状态（0下架，1上架）
     */
    private Integer seckillStatus;
    /**
     * 秒杀开始时间
     */
    private Date seckillBegin;
    /**
     * 秒杀结束时间
     */
    private Date seckillEnd;
    /**
     * 秒杀商品排序
     */
    private Integer seckillRank;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 删除标识字段(0-未删除 1-已删除)
     */
    private Integer isDeleted;

}

