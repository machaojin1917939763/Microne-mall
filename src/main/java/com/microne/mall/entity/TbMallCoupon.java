package com.microne.mall.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 优惠券信息及规则表(TbMallCoupon)实体类
 *
 * @author makejava
 * @since 2023-05-20 16:43:40
 */
public class TbMallCoupon implements Serializable {
    private static final long serialVersionUID = 759015694673435341L;

    private Long couponId;
    /**
     * 优惠券名称
     */
    private String couponName;
    /**
     * 优惠券介绍，通常是显示优惠券使用限制文字
     */
    private String couponDesc;
    /**
     * 优惠券数量，如果是0，则是无限量
     */
    private Integer couponTotal;
    /**
     * 优惠金额，
     */
    private Integer discount;
    /**
     * 最少消费金额才能使用优惠券。
     */
    private Integer min;
    /**
     * 用户领券限制数量，如果是0，则是不限制；默认是1，限领一张.
     */
    private Integer couponLimit;
    /**
     * 优惠券赠送类型，如果是0则通用券，用户领取；如果是1，则是注册赠券；如果是2，则是优惠券码兑换；
     */
    private Integer couponType;
    /**
     * 优惠券状态，如果是0则是正常可用；如果是1则是过期; 如果是2则是下架。
     */
    private Integer couponStatus;
    /**
     * 商品限制类型，如果0则全商品，如果是1则是类目限制，如果是2则是商品限制。
     */
    private Integer goodsType;
    /**
     * 商品限制值，goods_type如果是0则空集合，如果是1则是类目集合，如果是2则是商品集合。
     */
    private String goodsValue;
    /**
     * 优惠券兑换码
     */
    private String couponCode;
    /**
     * 优惠券开始时间
     */
    private LocalDateTime couponStartTime;
    /**
     * 优惠券结束时间
     */
    private LocalDateTime couponEndTime;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 逻辑删除
     */
    private Integer isDeleted;


    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    public Integer getCouponTotal() {
        return couponTotal;
    }

    public void setCouponTotal(Integer couponTotal) {
        this.couponTotal = couponTotal;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getCouponLimit() {
        return couponLimit;
    }

    public void setCouponLimit(Integer couponLimit) {
        this.couponLimit = couponLimit;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Integer getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(Integer couponStatus) {
        this.couponStatus = couponStatus;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsValue() {
        return goodsValue;
    }

    public void setGoodsValue(String goodsValue) {
        this.goodsValue = goodsValue;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public LocalDateTime getCouponStartTime() {
        return couponStartTime;
    }

    public void setCouponStartTime(LocalDateTime couponStartTime) {
        this.couponStartTime = couponStartTime;
    }

    public LocalDateTime getCouponEndTime() {
        return couponEndTime;
    }

    public void setCouponEndTime(LocalDateTime couponEndTime) {
        this.couponEndTime = couponEndTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

}

