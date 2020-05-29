package com.leyou.dao;

import com.leyou.pojo.SpecParam;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpecParamMapper extends Mapper<SpecParam>{

    @Select("select p.* from tb_spec_param p,tb_category y  where p.cid = y.id and y.id=#{cid}")
    List<SpecParam> fingSpecParamByCid(Long cid);
}
