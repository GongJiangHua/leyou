package com.leyou.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.mapper.GoodsMapper;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.mapper.StockMapper;
import com.leyou.item.pojo.*;
import com.leyou.item.vo.SpuVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

/**
 * author:  jianghua
 * desc:    商品service
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;


    private Logger logger = LoggerFactory.getLogger(GoodsService.class);

    /**
     * 关键字查询商品
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    public PageResult<SpuVo> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();  //创建条件对象
        //动态查询标题
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");  //条件搜索
        }
        //是否过滤上下架
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);   //是否上下架
        }
        //添加分页
        PageHelper.startPage(page, rows);
        //执行查询
        Page<Spu> spuList = (Page<Spu>) goodsMapper.selectByExample(example);
        //通过Stream流的map方法把流中的每一个元素转换成另一个类型
        Stream<SpuVo> spuBoStream = spuList.getResult().stream().map(spu -> {
            SpuVo spubo = new SpuVo();
            BeanUtils.copyProperties(spu, spubo); //把一个对象的属性拷贝到另一个对象
            //获取商品分类名称
            List<String> categoryNames = categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            //获取商品的品牌名称
            String BrandName = brandMapper.selectByPrimaryKey(spu.getBrandId()).getName();
            spubo.setCname(StringUtils.join(categoryNames, "/"));//商品所属分类名
            spubo.setBname(BrandName);//商品的品牌名
            return spubo;
        });
        //把Stream流转换成集合
        List<SpuVo> spuVoList = spuBoStream.collect(Collectors.toList());
        return new PageResult<>(spuList.getTotal(),spuVoList);
    }

    /**
     * 新增商品service
     * @param spu
     */
    @Transactional
    public void saveGoods(SpuVo spu) {
        //保存spu
        spu.setSaleable(true);//设置是否可出售
        spu.setValid(true);//逻辑删除用
        spu.setCreateTime(new Date());//设置创建时间
        spu.setLastUpdateTime(spu.getCreateTime());//设置最后修改时间
        this.spuMapper.insert(spu);

        //保存spuDetail详情
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        this.spuDetailMapper.insert(spuDetail);

        //保存skus和库存
        this.saveSkuAndStock(spu.getSkus(), spu.getId());
        //发送消息通知搜索服务

        this.sendMessage("insert",spu.getId());

    }


    /**
     * 保存skus和库存
     * @param skus
     * @param spuId
     */
    private void saveSkuAndStock(List<Sku> skus, Long spuId) {
        for (Sku sku : skus) {
            //保存sku
            sku.setSpuId(spuId);
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            skuMapper.insert(sku);
            //保存sku 的库存信息
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insert(stock);
        }
    }

    /**
     * 修改商品
     * @param spu
     */
    @Transactional
    public void updateGoods(SpuVo spu) {
        // 查询以前sku
        List<Sku> skus = this.querySkuBySpuId(spu.getId());
        // 如果以前存在，则删除
        if(!CollectionUtils.isEmpty(skus)) {
            List<Long> ids = skus.stream().map(s -> s.getId()).collect(Collectors.toList());
            // 删除以前库存
            Example example = new Example(Stock.class);
            example.createCriteria().andIn("skuId", ids);
            this.stockMapper.deleteByExample(example);

            // 删除以前的sku
            Sku record = new Sku();
            record.setSpuId(spu.getId());
            this.skuMapper.delete(record);

        }
        // 新增sku和库存
        saveSkuAndStock(spu.getSkus(), spu.getId());

        // 更新spu
        spu.setLastUpdateTime(new Date());
        spu.setCreateTime(null);
        spu.setValid(null);
        spu.setSaleable(null);
        this.spuMapper.updateByPrimaryKeySelective(spu);

        // 更新spu详情
        this.spuDetailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());

        //发送消息通知搜索服务
        this.sendMessage("update",spu.getId());
    }


    /**
     * 根据spuId查询Sku
     * @param id
     * @return
     */
    public List<Sku> querySkuBySpuId(Long id) {
        Sku record = new Sku();
        record.setSpuId(id);
        List<Sku> skus = this.skuMapper.select(record);
        //同时查询出Sku的库存信息 库存表Stock的主键就是SkuId
        skus.forEach(sku -> {
            Stock stock = this.stockMapper.selectByPrimaryKey(sku.getId());
            sku.setStock(stock.getStock());
        });
        return skus;
    }

    /**
     * 根据spuId查询spu
     * @param spuId
     * @return
     */
    public Spu querySpuById(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        return spu;
    }

    /**
     * 根据spuId查询spuDetail,spuId是spuDetail的主键
     * @param spuId
     * @return
     */
    public SpuDetail querySpuDetailById(Long spuId) {
        return this.spuDetailMapper.selectByPrimaryKey(spuId);

    }

    /**
     * 发送消息到rabbitmq
     * @param id
     * @param type
     */
    private void sendMessage(String type, Long id) {
        // 发送消息
        try {
            this.amqpTemplate.convertAndSend("item." + type, id);
        } catch (Exception e) {
            logger.error("{}商品消息发送异常，商品id：{}", type, id, e);
        }
    }

}
