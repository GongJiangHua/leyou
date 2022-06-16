package com.leyou.item.api;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("spec")
public interface SpecApi {

    /**
     * 规格参数主体中的详细属性
     * @param gid 组id
     * @param cid 分类id
     * @param searching
     * @param generic
     * @return
     */
    @GetMapping("params")//params?gid=1
    public List<SpecParam> querySpecParam(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching", required = false) Boolean searching,
            @RequestParam(value = "generic", required = false) Boolean generic
    );

    @GetMapping("group/param/{cid}")
    public List<SpecGroup> queryGroupWithParam(@PathVariable("cid")Long cid);
}
