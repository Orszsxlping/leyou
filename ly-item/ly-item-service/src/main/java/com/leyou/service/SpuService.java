package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.SkuMapper;
import com.leyou.dao.SpuDetailMapper;
import com.leyou.dao.SpuMapper;
import com.leyou.dao.StockMapper;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.Stock;
import com.leyou.vo.SpuVo;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SpuService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

    /**
     * 查询商品列表 分页
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    public PageResult<SpuVo> findSpuByPage(String key, Integer saleable,
                                           Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        PageInfo<SpuVo>  list = new PageInfo<SpuVo>(spuMapper.findSpuPage(key,saleable));
        return new PageResult<SpuVo>(list.getTotal(),list.getList());
    }

    /**
     * 保存商品信息
     *
     * @param spuVo
     */
    public void saveSpuDetail(SpuVo spuVo) {

        Date nowDate = new Date();

        /**
         * 1:保存spu
         * 2：保存spu_detail
         * 3:保存sku
         * 4：保存stock
         */

        Spu spu=new Spu();
        spu.setTitle(spuVo.getTitle());
        spu.setSubTitle(spuVo.getSubTitle());
        spu.setBrandId(spuVo.getBrandId());
        spu.setCid1(spuVo.getCid1());
        spu.setCid2(spuVo.getCid2());
        spu.setCid3(spuVo.getCid3());
        spu.setSaleable(false); //默认保存时不上架商品
        spu.setValid(true);
        spu.setCreateTime(nowDate);
        spu.setLastUpdateTime(nowDate);
        spuMapper.insert(spu);

        //保存spu 扩展表
        SpuDetail spuDetail = spuVo.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insert(spuDetail);


        //sku
        List<Sku> skus = spuVo.getSkus();
        skus.forEach(sku ->{
                 sku.setSpuId(spu.getId());
                 sku.setEnable(true);
                 sku.setCreateTime(nowDate);
                 sku.setLastUpdateTime(nowDate);
                 skuMapper.insert(sku);

                 //库存
                Stock stock =new Stock();
                stock.setSkuId(sku.getId());
                stock.setStock(sku.getStock());
                stockMapper.insert(stock);

        });


    }

    /**
     * 根据spuId查询商品集列表
     *
     * @param spuId
     * @return
     */
    public SpuDetail findSpuDetailBySpuId(Long spuId) {
         return spuDetailMapper.selectByPrimaryKey(spuId);
    }

    /**
     *
     * 修改商品信息
     *
     * @param spuVo
     */
    public void updateSpuDetail(SpuVo spuVo) {

        Date nowDate = new Date();

        /**
         * 1:保存spu
         * 2：保存spu_detail
         * 3:保存sku
         * 4：保存stock
         */
        spuVo.setCreateTime(null);
        spuVo.setLastUpdateTime(nowDate);
        spuVo.setSaleable(null);
        spuVo.setValid(null);
        spuMapper.updateByPrimaryKeySelective(spuVo);

        //第二：修改spudetail
        SpuDetail spuDetail = spuVo.getSpuDetail();
        spuDetail.setSpuId(spuVo.getId());
        spuDetailMapper.updateByPrimaryKeySelective(spuDetail);


        //第三：修改sku
        List<Sku> skus = spuVo.getSkus();
        skus.forEach(s ->{
              //删除sku
              s.setEnable(false);
              skuMapper.updateByPrimaryKey(s);
              //库存
              stockMapper.deleteByPrimaryKey(s.getId());
        });

        //sku
        List<Sku> skus1 = spuVo.getSkus();
        skus1.forEach(sku ->{
            sku.setSpuId(spuVo.getId());
            sku.setEnable(true);
            sku.setCreateTime(nowDate);
            sku.setLastUpdateTime(nowDate);
            skuMapper.insert(sku);

            //库存
            Stock stock =new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);

        });
    }

    /**
     * 根据spuId删除spu详情
     *
     * @param spuId
     */
    public void deleteSpuBySpuId(Long spuId) {

        /**
         * 1:保存spu
         * 2：保存spu_detail 脏数据
         * 3:保存sku
         * 4：保存stock
         */

         //删除sku
        List<Sku> skuList = skuMapper.findSkusBySpuId(spuId);
        skuList.forEach(s ->{
            //删除sku
            s.setEnable(false);
            skuMapper.updateByPrimaryKeySelective(s);
            //库存
            stockMapper.deleteByPrimaryKey(s.getId());
        });


        //删除detail
        spuDetailMapper.deleteByPrimaryKey(spuId);

        //删除spu
        spuMapper.deleteByPrimaryKey(spuId);

    }

    /**
     * 操作上下架
     * @param spuId
     */
    public void upOrDown(Long spuId,int saleable) {
        Spu spu =new Spu();
        spu.setId(spuId);
        spu.setSaleable(saleable ==1? true :false);
        spuMapper.updateByPrimaryKeySelective(spu);
    }
}
