package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.BrandMapper;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.apache.catalina.LifecycleState;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    BrandMapper brandMapper;

    /**
     * 1: pageHelper
     * 2: 手写sql 去做分页
     * 3:通用mapper Excempe cri
     *
     *
     *
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    public PageResult<Brand> findBrand(String key, Integer page, Integer rows, String sortBy, boolean desc) {
        //pageHelper

        PageHelper.startPage(page,rows);

        List<Brand> brandList =brandMapper.findBrand(key,sortBy,desc);

        PageInfo<Brand> pageInfo = new PageInfo<Brand>(brandList);

        return new PageResult<Brand>(pageInfo.getTotal(),pageInfo.getList());

    }


    /**
     * 2:手写sql 去做分页
     *
     */
    public PageResult<Brand> findBrandByLimit(String key, Integer page, Integer rows, String sortBy, boolean desc) {
        //pageHelper

        //PageHelper.startPage(page,rows);
        /**
         * limit 0,5
         *       5,5
         *       10,5
         *
         *       page:页码   1  2  3
         *       rows：条数  5  5  5
         *     (page-1)*rows , 5   limit  0，5
         *                5，，5    limit  5，5
         *                         limit  10，5
         *
         *       （当前页码 -1）* 条数  ==limit  ？，？
         *
         *
         */

        //查询出来的结果
        List<Brand> brandList =brandMapper.findBrandLimit(key,(page-1)*rows,rows,sortBy,desc);

        //查询总条数

        Long brandCount = brandMapper.findBrandCount(key,sortBy,desc);

        //PageInfo<Brand> pageInfo = new PageInfo<Brand>(brandList);



        return new PageResult<Brand>(brandCount,brandList);

    }


    public PageResult<Brand> queryBrandsbyPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {

        //1初始化example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        //2添加模糊查询,或者首字母精确查询
        /*  判断Key是否为空*/
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name","%"+key+"%")
                    .orEqualTo("letter",key);

        }

        //3添加分页
        PageHelper.startPage(page,rows);

        //4添加排序
        /* 判断sortBy是否为空*/
        if (StringUtils.isNotBlank(sortBy)) {
            System.out.println("***********"+desc+"*********");
            //判断desc是否为true,ture为降序，三目判断
            example.setOrderByClause(sortBy+(desc ?  "  desc":"  asc"));

        }

        //5找到合适通用Mapper方法
        List<Brand> brands = brandMapper.selectByExample(example);
        /*封装分页*/
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);

        return  new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    public void brandCategorySave(Brand brand, List<String> cids) {

        //1:保存brand
        brandMapper.insert(brand);

        //2：保存tb_category_brand
        cids.forEach(id ->{
            brandMapper.addBrandAndCategory(brand.getId(),Long.parseLong(id));
        });


    }

    public void deleteById(Long id) {

        //第一删除brand
        Brand brand =new Brand();
        brand.setId(id);
        brandMapper.deleteByPrimaryKey(brand);

        //第二删除关系表tb_category_brand

        brandMapper.deleteBrandAndCategory(id);
    }


    public List<Category> findCategoryByBrandId(Long pid) {

        return brandMapper.findCategoryByBrandId(pid);
    }

    public void updateBrand(Brand brand, List<String> cids) {

        /*
         * 1:修改品牌表
         * 2：修改品牌和分类的关系表
         *   修改前 ：1，3，5
         *   修改后 ：3   先删掉当前品牌下所有分类，再重新添加分类，
         */
        brandMapper.updateByPrimaryKey(brand);

        brandMapper.deleteBrandAndCategory(brand.getId());

        cids.forEach(cid ->{
            brandMapper.addBrandAndCategory(brand.getId(),Long.parseLong(cid));
        });

        /*for(Long ccid :cids){
            brandMapper.addBrandAndCategory(brand.getId(),ccid);
        }*/

    }

    public List<Brand> findBrandBycid(Long cid) {
        return brandMapper.findBrandBycid(cid);
    }
}
