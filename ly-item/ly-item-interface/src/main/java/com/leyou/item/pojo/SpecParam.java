package com.leyou.item.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * author:  jianghua
 * desc:    规格参数
 *              - 一个商品分类下有多个规格组
 *              - 一个规格组下有多个规格参数
 */
@Data
@Table(name = "tb_spec_param")
public class SpecParam {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    // 分类id
    private Long cid;
    // 规格组id
    private Long groupId;
    // 规格名称
    private String name;
    @Column(name = "`numeric`")
    private Boolean numeric;
    private String unit;
    private Boolean generic;
    // 是否被搜索
    private Boolean searching;
    private String segments;
}
