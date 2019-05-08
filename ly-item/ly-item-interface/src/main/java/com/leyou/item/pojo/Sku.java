package com.leyou.item.pojo;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * author:  niceyoo
 * blog:    https://cnblogs.com/niceyoo
 * desc:
 */
@Table(name = "tb_sku")
@Data
public class Sku {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;// sku id
    private Long spuId;// spu id
    private String title;// 商品标题
    private String images;// 商品的图片，多个图片以‘,’分割
    private Double price;// 销售价格，单位为分
    private String ownSpec;// 商品特殊规格的键值对
    private String indexes;// 商品特殊规格的下标
    private Boolean enable;// 是否有效，逻辑删除用
    private Date createTime;// 创建时间
    private Date lastUpdateTime;// 最后修改时间
    @Transient
    private Integer stock;// 库存
}
