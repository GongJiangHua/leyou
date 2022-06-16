package com.leyou.item.web;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.ConnectException;
import java.util.List;

/**
 * author:  jianghua
 * desc:    商品分类 controller
 */
@RestController
@RequestMapping("category")
class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分类列表
     * 根据父类目的id查询所有子节点
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryByParentId(@RequestParam(value = "pid", defaultValue = "0") Long pid) {
        // 默认查询一级分类
        //响应400
        if (pid == null || pid < 0){
            return ResponseEntity.badRequest().build();
        }
        //执行查询，获取结果集
        List<Category> categoryList = categoryService.queryByParentId(pid);
        if (categoryList == null || categoryList.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        //响应200
        return ResponseEntity.ok(categoryList);
    }

    /**
     * 品牌回显
     */
    @GetMapping("bid/{brandId}")
    public ResponseEntity<List<Category>> queryBrandByBid(@PathVariable("brandId") Long brandId) {
        List<Category> categories = categoryService.queryBrandByBid(brandId);
        if (categories == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(categories);
    }

    /**
     * 根据三级分类id查询分类名
     * @param ids
     * @return
     */
    @GetMapping("names")
    public ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids") List<Long> ids) {
        List<String> list = categoryService.queryNamesByIds(ids);

        if (list == null || list.size() == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 根据3级分类id，查询1~3级的分类
     *
     * @param id
     * @return
     */
    @GetMapping("all/level")
    public ResponseEntity<List<Category>> queryAllByCid3(@RequestParam("id") Long id) {
        List<Category> list = this.categoryService.queryAllByCid3(id);
        if (list == null || list.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

}
