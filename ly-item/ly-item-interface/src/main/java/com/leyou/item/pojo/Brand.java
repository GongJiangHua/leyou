package com.leyou.item.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * author:  jianghua
 * desc:    商品品牌
 */

@Data
@Table(name="tb_brand")
public class Brand {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    /** 品牌名称 **/
    private String name;
    /** 品牌图片地址 **/
    private String image;
    /** 品牌的首字母 **/
    private Character letter;

}
