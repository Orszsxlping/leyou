package com.leyou;

import com.leyou.client.SpuClient;
import com.leyou.common.PageResult;
import com.leyou.item.Goods;
import com.leyou.repository.GoodsRepository;
import com.leyou.service.GoodService;
import com.leyou.vo.SpuVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GoodsTest {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    GoodService goodService;
    @Autowired
    SpuClient spuClient;

    @Test
    public void goodsTest(){

        elasticsearchTemplate.createIndex(Goods.class);
        elasticsearchTemplate.putMapping(Goods.class);
        PageResult<SpuVo> spuVoList = spuClient.findSpuByPage("", 1, 200,2);
        spuVoList.getItems().forEach(spu->{
            System.out.println(spu.getId());
            try {
                Goods goods = goodService.convert(spu);
                goodsRepository.save(goods);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
