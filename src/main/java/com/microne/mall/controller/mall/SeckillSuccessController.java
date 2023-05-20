package com.microne.mall.controller.mall;

import com.microne.mall.entity.TbMallSeckillSuccess;
import com.microne.mall.service.TbMallSeckillSuccessService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 秒杀库存表(TbMallSeckillSuccess)表控制层
 *
 * @author makejava
 * @since 2023-05-20 16:43:41
 */
@RestController
@RequestMapping("seckillSuccess")
public class SeckillSuccessController {
    /**
     * 服务对象
     */
    @Resource
    private TbMallSeckillSuccessService tbMallSeckillSuccessService;

    /**
     * 分页查询
     *
     * @param tbMallSeckillSuccess 筛选条件
     * @param pageRequest          分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<TbMallSeckillSuccess>> queryByPage(TbMallSeckillSuccess tbMallSeckillSuccess, PageRequest pageRequest) {
        return ResponseEntity.ok(this.tbMallSeckillSuccessService.queryByPage(tbMallSeckillSuccess, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<TbMallSeckillSuccess> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.tbMallSeckillSuccessService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param tbMallSeckillSuccess 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<TbMallSeckillSuccess> add(TbMallSeckillSuccess tbMallSeckillSuccess) {
        return ResponseEntity.ok(this.tbMallSeckillSuccessService.insert(tbMallSeckillSuccess));
    }

    /**
     * 编辑数据
     *
     * @param tbMallSeckillSuccess 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<TbMallSeckillSuccess> edit(TbMallSeckillSuccess tbMallSeckillSuccess) {
        return ResponseEntity.ok(this.tbMallSeckillSuccessService.update(tbMallSeckillSuccess));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.tbMallSeckillSuccessService.deleteById(id));
    }

}

