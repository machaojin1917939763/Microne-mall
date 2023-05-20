package com.microne.mall.controller.mall;

import com.microne.mall.entity.TbMallUserCouponRecord;
import com.microne.mall.service.TbMallUserCouponRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 优惠券用户使用表(TbMallUserCouponRecord)表控制层
 *
 * @author makejava
 * @since 2023-05-20 16:43:42
 */
@RestController
@RequestMapping("userCouponRecord")
public class UserCouponRecordController {
    /**
     * 服务对象
     */
    @Resource
    private TbMallUserCouponRecordService tbMallUserCouponRecordService;

    /**
     * 分页查询
     *
     * @param tbMallUserCouponRecord 筛选条件
     * @param pageRequest            分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<TbMallUserCouponRecord>> queryByPage(TbMallUserCouponRecord tbMallUserCouponRecord, PageRequest pageRequest) {
        return ResponseEntity.ok(this.tbMallUserCouponRecordService.queryByPage(tbMallUserCouponRecord, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<TbMallUserCouponRecord> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.tbMallUserCouponRecordService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param tbMallUserCouponRecord 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<TbMallUserCouponRecord> add(TbMallUserCouponRecord tbMallUserCouponRecord) {
        return ResponseEntity.ok(this.tbMallUserCouponRecordService.insert(tbMallUserCouponRecord));
    }

    /**
     * 编辑数据
     *
     * @param tbMallUserCouponRecord 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<TbMallUserCouponRecord> edit(TbMallUserCouponRecord tbMallUserCouponRecord) {
        return ResponseEntity.ok(this.tbMallUserCouponRecordService.update(tbMallUserCouponRecord));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.tbMallUserCouponRecordService.deleteById(id));
    }

}

