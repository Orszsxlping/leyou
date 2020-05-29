package com.leyou.service;

import com.leyou.dao.SpecGroupMapper;
import com.leyou.dao.SpecParamMapper;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXParseException;

import java.util.List;

@Service
public class SpecGroupService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     *
     * 保存商品规格组
     *
     * @param specGroup
     */
    public void saveSpecGroup(SpecGroup specGroup) {
        specGroupMapper.insert(specGroup);
    }

    /**
     * 根据分类id查询商品规格组列表
     *
     * @param cateGoryId
     * @return
     */
    public List<SpecGroup> findSpecGroupList(Long cateGoryId) {
        SpecGroup specGroup =new SpecGroup();
        specGroup.setCid(cateGoryId);
        return specGroupMapper.select(specGroup);
    }

    /**
     * 根据商品规格组id删除
     *
     * @param id
     */
    public void deleteBySpecGroupId(Long id) {
        specGroupMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改商品规格组
     *
     * @param specGroup
     */
    public void updateSpecGroup(SpecGroup specGroup) {
        specGroupMapper.updateByPrimaryKey(specGroup);
    }

    /**
     * 根据组id查询组参数列表
     *
     * @param gid
     * @return
     */
    public List<SpecParam> findSpecPramByGid(Long gid) {
        SpecParam specParam= new SpecParam();
        specParam.setGroupId(gid);
        return specParamMapper.select(specParam);
    }
}
