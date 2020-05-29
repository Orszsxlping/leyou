package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.service.SpuService;
import com.leyou.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    /**
     * 查询商品列表
     *
     * @param key
     * @param page
     * @param rows
     * @param saleable
     * @return
     */
    @RequestMapping("page")
    public PageResult<SpuVo> findSpuByPage(@RequestParam("key") String key,
                                           @RequestParam("page") Integer page,
                                           @RequestParam("rows") Integer rows,
                                           @RequestParam("saleable") Integer saleable){
        System.out.println();

        return spuService.findSpuByPage(key,saleable,page,rows);
    }


    /**
     * 保存商品信息
     *
     * @param spuVo
     */
    @RequestMapping("savaOrUpdateGoods")
    public void saveSpuDetail(@RequestBody SpuVo spuVo){
        if(spuVo.getId() !=null){
            spuService.updateSpuDetail(spuVo);
        }else{
            spuService.saveSpuDetail(spuVo);
        }
    }


    /**
     * 根据spuId查询商品集列表
     *
     * @param spuId
     * @return
     */
    @RequestMapping("detail/{spuId}")
    public SpuDetail findSpuDetailBySpuId(@PathVariable("spuId") Long spuId){
        return spuService.findSpuDetailBySpuId(spuId);
    }


    /**
     * 根据spuId删除spu详情
     *
     * @param spuId
     */
    @RequestMapping("deleteById/{spuId}")
    public void deleteSpuBySpuId(@PathVariable("spuId") Long spuId){
        spuService.deleteSpuBySpuId(spuId);
    }

    /**
     * 操作上下架
     * @param spuId
     */
    @RequestMapping("upOrDown")
    public void upOrDown(@RequestParam("spuId") Long spuId,@RequestParam("saleable") int saleable){
        System.out.println();
        spuService.upOrDown(spuId,saleable);
    }



}
