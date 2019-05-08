package com.leyou.web;

import com.leyou.service.UploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * author:  niceyoo
 * blog:    https://cnblogs.com/niceyoo
 * desc:
 */

@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传图片功能
     * @param file
     * @return
     */
    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//        String url = this.uploadService.upload(file);
//        if (StringUtils.isBlank(url)) {
//            // url为空，证明上传失败
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        // 返回200，并且携带url路径
//        return ResponseEntity.ok(url);
        return ResponseEntity.ok("");
    }

}
