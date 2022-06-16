package com.leyou.search.client;

import com.google.gson.JsonObject;
import com.leyou.common.utils.JsonUtils;
import com.leyou.common.vo.PageResult;
import com.leyou.item.vo.SpuVo;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class GoodsClientTest {
    @Autowired
    private GoodsClient goodsClient;
    @Test
    public void testSpu(){
        PageResult<SpuVo> result = this.goodsClient.querySpuByPage(null, true, 1, 5);
        result.getItems().forEach(System.out::println);
    }

}