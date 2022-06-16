package com.leyou.item.service;

import com.leyou.item.pojo.Category;

import java.util.List;

/**
 * 分类service
 */
public interface CategoryService {
    List<Category> queryByParentId(Long pid);

    List<Category> queryBrandByBid(Long brandId);

    List<String> queryNamesByIds(List<Long> cids);

    List<Category> queryAllByCid3(Long id);
}

