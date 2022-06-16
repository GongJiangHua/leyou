package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * author:  jianghua
 * desc:    品牌 controller
 */
@RestController
@RequestMapping("brand")
class BrandController {

    @Autowired
    private BrandService mBrandService;

    /**
     * 分类、关键字等查询品牌
     *
     * @param page   当前页码
     * @param rows   每页大小
     * @param sortBy 排序字段
     * @param desc   是否为降序
     * @param key    搜索关键字
     * @return total+items+totalPage
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryCategoryListByPid(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", required = false) Boolean desc,
            @RequestParam(value = "key", required = false) String key) {

        PageResult<Brand> result = mBrandService.queryBrandByPage(page, rows, sortBy, desc, key);
        if (result == null || result.getItems() == null || result.getItems().size() < 1) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 添加品牌
     *
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids") List<Long> cids) {
        mBrandService.saveBrand(brand, cids);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 根据分类id查询分类的集合
     */

    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandsByCid(@PathVariable("cid") Long cid) {
        List<Brand> brands = this.mBrandService.queryBrandByCategory(cid);
        if (CollectionUtils.isEmpty(brands)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brands);
    }


    /**
     * 修改品牌
     *
     * @param brand
     * @param cids
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> changeBrand(Brand brand, @RequestParam("cids") List<Long> cids) {
        mBrandService.changeBrand(brand, cids);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 删除品牌
     *
     * @param map
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteBrand(@RequestBody Map<String, Long> map) {
        Long bid = map.get("bid");
        mBrandService.deleteBrand(bid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id) {
        Brand brand = this.mBrandService.queryBrandById(id);
        if (brand == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brand);
    }

}
