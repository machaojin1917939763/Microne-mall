package com.microne.mall.entity;

import java.io.Serializable;

/**
 * 秒杀库存表(TbMallSeckillSuccess)实体类
 *
 * @author makejava
 * @since 2023-05-20 16:43:41
 */
public class TbMallSeckillSuccess implements Serializable {
    private static final long serialVersionUID = -74615269785401304L;
    /**
     * 自增ID
     */
    private Long secId;
    /**
     * 商品商品id
     */
    private Long seckillId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 状态信息：-1无效，0成功，1已付款，2已发货
     */
    private Integer state;
    /**
     * 创建时间
     */
    private Object createTime;


    public Long getSecId() {
        return secId;
    }

    public void setSecId(Long secId) {
        this.secId = secId;
    }

    public Long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

}

