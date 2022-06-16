package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * author:  jianghua
 * desc:    品牌的 mapper
 */
@Repository
public interface BrandMapper extends Mapper<Brand> , SelectByIdListMapper<Brand, Long> {
    //添加品牌
    @Insert("insert INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid})")
    void insertCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    @Update("update tb_category_brand set category_id=#{cid} where brand_id=#{bid}")
    void changeCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    @Delete("delete from tb_category_brand WHERE brand_id=#{bid}")
    void deleteCategoryBrand(@Param("bid") Long bid);

    @Select("select b.* from tb_category_brand cb LEFT join tb_brand b on b.id=cb.brand_id where cb.category_id=#{cid}")
    List<Brand> queryBrandByCid(@Param("cid") Long cid);

}
