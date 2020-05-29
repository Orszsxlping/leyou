package com.leyou.dao;

import com.leyou.pojo.Sku;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SkuMapper extends Mapper<Sku> {

    @Select("select s.*,k.stock from tb_sku s,tb_stock k  " +
            "where s.id=k.sku_id and  spu_id =#{id} and enable=1")
    List<Sku> findSkusBySpuId(Long id);
}
