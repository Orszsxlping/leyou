package com.leyou.service;

import com.leyou.dao.SpecParamMapper;
import com.leyou.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecParamService {

    @Autowired
    private SpecParamMapper specParamMapper;


    /**
     * 新增规格参数组下的参数
     * @param specParam
     */
    public void saveSpecParam(SpecParam specParam) {

        specParamMapper.insert(specParam);

    }

    /**
     * 修改规格参数组下的参数
     * @param specParam
     */
    public void updateSpecParam(SpecParam specParam) {
        specParamMapper.updateByPrimaryKey(specParam);
    }

    /**
     * 根据id删除参数组
     *
     * @param id
     */
    public void deleteSpecParamById(Long id) {
        specParamMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据分类id查询规格参数集合
     *
     * @param cid
     * @return
     */
    public List<SpecParam> fingSpecParamByCid(Long cid) {

        return specParamMapper.fingSpecParamByCid(cid);
    }

    /**
     * 根据三级分类id+搜索条件为1的参数查询规格参数集合
     *
     * @param cid
     * @return
     */
    public List<SpecParam> fingSpecParamByCidAndSearching(Long cid) {
        SpecParam specParam =new SpecParam();
        specParam.setCid(cid);
        specParam.setSearching(true);
        return specParamMapper.select(specParam);
    }
}
