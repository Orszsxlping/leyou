package com.leyou.dao;

import com.leyou.pojo.Category;
import tk.mybatis.mapper.common.Mapper;

public interface CategoryMapper  extends Mapper<Category> {
    
    public Category findCate(int id);
}
