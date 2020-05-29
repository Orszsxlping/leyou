package com.leyou.dao;

import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {


    List<Brand> findBrand(@Param("key") String key,
                          @Param("sortBy") String sortBy,
                          @Param("desc") boolean desc);


    List<Brand> findBrandLimit(@Param("key") String key,
                               @Param("page")int page,
                               @Param("rows")Integer rows,
                               @Param("sortBy")  String sortBy,
                               @Param("desc")  boolean desc);

    Long findBrandCount(@Param("key") String key,
                        @Param("sortBy") String sortBy,
                        @Param("desc") boolean desc);

    @Insert("insert into tb_category_brand values(#{cid},#{bid})")
    void addBrandAndCategory(Long bid, Long cid);

    @Delete("delete from tb_category_brand  where brand_id=#{id}")
    void deleteBrandAndCategory(Long id);


    @Select("select y.* from tb_category_brand t,tb_category y where t.category_id=y.id and t.brand_id=#{pid}")
    List<Category> findCategoryByBrandId(Long pid);

    @Select("select d.* from tb_brand d,tb_category_brand b where d.id=b.brand_id and b.category_id=#{cid}")
    List<Brand> findBrandBycid(Long cid);
}
