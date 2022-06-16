package com.leyou.item.api;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("category")
public interface CategoryApi {
    /**
     * 根据三级分类id查询分类名
     * @param ids
     * @return
     */
    @GetMapping("names")
    public List<String> queryNameByIds(@RequestParam("ids") List<Long> ids);

}
