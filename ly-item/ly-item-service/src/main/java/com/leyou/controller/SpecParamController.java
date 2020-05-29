package com.leyou.controller;

import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("specParam")
public class SpecParamController {

    @Autowired
    private SpecParamService specParamService;

    /**
     * 新增规格参数组下的参数
     * @param specParam
     */
    @RequestMapping("param")
    public void saveSpecParam(@RequestBody SpecParam specParam){

        if(specParam.getId()==null){
            specParamService.saveSpecParam(specParam);
        }else{
            specParamService.updateSpecParam(specParam);
        }
    }


    /**
     * 根据id删除参数组
     *
     * @param id
     */
    @RequestMapping("param/{id}")
    public void deleteSpecParamById(@PathVariable("id") Long id){
        specParamService.deleteSpecParamById(id);
    }


    /**
     * 根据分类id查询规格参数集合
     *
     * @param cid
     * @return
     */
    @RequestMapping("params")
    public List<SpecParam> fingSpecParamByCid(@RequestParam("cid") Long cid){
        return specParamService.fingSpecParamByCid(cid);
    }

    /**
     * 根据三级分类id+搜索条件为1的参数查询规格参数集合
     *
     * @param cid
     * @return
     */
    @RequestMapping("paramByCid")
    public List<SpecParam> fingSpecParamByCidAndSearching(@RequestParam("cid") Long cid){
        return specParamService.fingSpecParamByCidAndSearching(cid);
    }

}
