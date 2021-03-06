package com.leyou.item.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import tk.mybatis.mapper.entity.Example;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        PageResult pageResult = new PageResult<Brand>();

        // 初始化一个example
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(key)) {
            criteria.andLike("letter", "%" + key + "%");
        }
        // 开始分页 - 分页插件
        PageHelper.startPage(page, rows);
       /* if (desc) {
            example.orderBy(sortBy).desc();
        } else {
            example.orderBy(sortBy).asc();
        }*/
        // 排序条件
        if (StringUtils.isNotEmpty(sortBy)) {
            String sort = desc ? "DESC" : "ASC";
            example.setOrderByClause(sortBy + " " + sort);
        }
        // 查询 - 返回结果
        Page<Brand> pages = (Page<Brand>) brandMapper.selectByExample(example);
        pageResult.setTotal(pages.getTotal());
        pageResult.setItems(pages);
        pageResult.setTotalPage(rows);
        return pageResult;
    }


    /**
     * 添加brand
     * @param brand
     * @param cids
     */
    @Override
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        brandMapper.insert(brand);
        cids.forEach((cid) -> {
            Long bid = brand.getId();
            brandMapper.insertCategoryBrand(cid, bid);
        });
    }

    /**
     * 修改brand
     * @param brand
     * @param cids
     */
    @Override
    @Transactional
    public void changeBrand(Brand brand, List<Long> cids) {

        //tb_brand表修改brand信息
        brandMapper.updateByPrimaryKey(brand);
        Long bid = brand.getId();
        //删除tb_category_brand表的brand分类信息然后重新添加
        brandMapper.deleteCategoryBrand(bid);
        cids.forEach((cid) -> {
            brandMapper.insertCategoryBrand(cid, bid);
        });
    }

    /**
     * 删除品牌
     * @param bid
     */
    @Override
    @Transactional
    public void deleteBrand(Long bid) {
        brandMapper.deleteByPrimaryKey(bid);
        brandMapper.deleteCategoryBrand(bid);
    }

    /**
     * 根据分类查询品牌
     * @param cid
     * @return
     */
    @Override
    public List<Brand> queryBrandByCategory(Long cid) {
        return brandMapper.queryBrandByCid(cid);
    }

    /**
     * 根据brandId集合查询brand集合
     * @param bids
     * @return
     */
    @Override
    public List<Brand> queryBrandByIds(List<Long> bids) {
        return brandMapper.selectByIdList(bids);
    }

    @Override
    public Brand queryBrandById(Long id){
        return brandMapper.selectByPrimaryKey(id);
    }
}
