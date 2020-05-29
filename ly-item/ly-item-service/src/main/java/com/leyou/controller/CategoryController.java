package com.leyou.controller;

import com.leyou.pojo.Category;
import com.leyou.service.CategoryService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    /**
     * 根据节点id查询所有
     *
     * @param pid
     * @return
     */
    @RequestMapping("list")
    public List<Category> list(@RequestParam("pid") Long pid){
        Category category =new Category();
        category.setParentId(pid);
        return categoryService.findCategory(category);
    }

    @RequestMapping("id")
    public Object findCate(){
        return categoryService.findCate(6);
    }

    /**
     * 添加商品分类
     *
     * @param category
     * @return
     */
    @RequestMapping("add")
    public String  add(@RequestBody Category category){
        String result = "SUCC";
        try {
            categoryService.cateGoryAdd(category);
        }catch (Exception e){
            System.out.println("添加商品分类异常");
            result = "FAIL";
        }
        return  result;
    }

    /**
     * 修改商品分类
     *
     * @param category
     * @return
     */
    @RequestMapping("update")
    public String  update(@RequestBody Category category){
        String result = "SUCC";
        try {
            categoryService.cateGoryUpdate(category);
        }catch (Exception e){
            System.out.println("修改商品分类异常");
            result = "FAIL";
        }
        return  result;
    }

    /**
     * 根据节点id进行删除
     *
     * @param id
     * @return
     */
    @RequestMapping("deleteById")
    public String deleteById(@RequestParam("id") Long id){
        String result = "SUCC";
        try {
            categoryService.deleteById(id);
        }catch (Exception e){
            System.out.println("修改商品分类异常");
            result = "FAIL";
        }
        return  result;
    }

}
