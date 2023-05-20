package com.microne.mall.controller.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class MicroneSeckillVO implements Serializable {

    private int seckillId;
    private int goodsId;
    private String goodsName;
    private String goodsIntro;
    private String goodsDetailContent;
    private String goodsCoverImg;
    private int sellingPrice;
    private int seckillPrice;
    private Date seckillBegin;
    private Date seckillEnd;
    private Date seckillBeginTime;
    private Date seckillEndTime;
    private Date startDate;
    private Date endDate;

}

