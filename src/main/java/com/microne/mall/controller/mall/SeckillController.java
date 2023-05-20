package com.microne.mall.controller.mall;

import com.microne.mall.controller.vo.MicroneSeckillVO;
import com.microne.mall.entity.MicroneMallGoods;
import com.microne.mall.entity.TbMallSeckill;
import com.microne.mall.service.MicroneMallGoodsService;
import com.microne.mall.service.TbMallSeckillService;
import com.microne.mall.util.Result;
import com.microne.mall.util.ResultGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (TbMallSeckill)表控制层
 *
 * @author makejava
 * @since 2023-05-20 16:43:40
 */
@Controller
@RequestMapping("seckill")
public class SeckillController {
    /**
     * 服务对象
     */
    @Resource
    private TbMallSeckillService tbMallSeckillService;

    @Autowired
    private MicroneMallGoodsService goodsService;

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @GetMapping
    public String toPage() {
        return "mall/miaosha";
    }

    ///seckill/time/now
    @ResponseBody
    @GetMapping("/time/now")
    public Result getTimeNow() {
        return ResultGenerator.genSuccessResult(System.currentTimeMillis());
    }

    /**
     * 判断秒杀商品虚拟库存是否足够
     *
     * @param seckillId 秒杀ID
     * @return result
     */
    @ResponseBody
    @PostMapping("{seckillId}/checkStock")
    public Result seckillCheckStock(@PathVariable Long seckillId) {
        // redis虚拟库存大于等于0时，可以执行秒杀
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 获取秒杀链接
     *
     * @param seckillId 秒杀商品ID
     * @return result
     */
    @ResponseBody
    @PostMapping("{seckillId}/exposer")
    public Result exposerUrl(@PathVariable Long seckillId) {
        return ResultGenerator.genSuccessResult();
    }




    @GetMapping("/info/{id}")
    public String toDetailPage(@PathVariable String id, HttpServletRequest request) {
        request.setAttribute("seckillId", id);
        return "mall/miaosha-detail";
    }

    @GetMapping("{id}")
    @ResponseBody
    public Result<MicroneSeckillVO> toDetail(@PathVariable String id) {
        MicroneSeckillVO microneSeckillVO = tbMallSeckillService.getSekillGoodsById(id);
        Result<MicroneSeckillVO> result = new Result<>();
        result.setMessage("SUCCESS");
        result.setData(microneSeckillVO);
        result.setResultCode(200);
        return result;
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<List<MicroneSeckillVO>> queryByPage() {
        return tbMallSeckillService.getListResult();
    }


    /**
     * 新增数据
     *
     * @param tbMallSeckill 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<TbMallSeckill> add(TbMallSeckill tbMallSeckill) {
        return ResponseEntity.ok(this.tbMallSeckillService.insert(tbMallSeckill));
    }

    /**
     * 编辑数据
     *
     * @param tbMallSeckill 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<TbMallSeckill> edit(TbMallSeckill tbMallSeckill) {
        return ResponseEntity.ok(this.tbMallSeckillService.update(tbMallSeckill));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.tbMallSeckillService.deleteById(id));
    }

}

