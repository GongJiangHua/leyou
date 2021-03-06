package com.leyou.item.vo;

import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;

import java.util.List;

import javax.persistence.Transient;

import lombok.Data;

@Data
public class SpuVo extends Spu {
    @Transient
    private String cname;// 商品分类名称
    @Transient
    private String bname;// 品牌名称
    @Transient
    private SpuDetail spuDetail;// 商品详情
    @Transient
    private List<Sku> skus;// sku列表
}

