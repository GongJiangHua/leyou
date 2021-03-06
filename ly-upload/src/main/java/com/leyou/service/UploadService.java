package com.leyou.service;

import com.leyou.web.UploadController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * desc:
 */
@Service
public class UploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    /**
     * 支持的文件类型
     */
    private static final List<String> CONTENT_TYPE = Arrays.asList("image/png", "image/jpeg");

    /**
     * 上传
     * @param file
     * @return
     */
    public String upload(MultipartFile file){

        try{
            // 1、图片信息校验
            // 1)校验文件类型
            String type = file.getContentType();
            if (!CONTENT_TYPE.contains(type)) {
                LOGGER.info("上传失败，文件类型不匹配：{}", type);
                return null;
            }
            // 2)校验图片内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                LOGGER.info("上传失败，文件内容不符合要求");
                return null;
            }
            // 2、保存图片
            // 2.1、生成保存目录
            File dir = new File("D:\\ProjectImage\\leyouImages");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 2.2、保存图片
            file.transferTo(new File(dir, file.getOriginalFilename()));

            // 2.3、拼接图片地址
            String url = "http://image.leyou.com/" + file.getOriginalFilename();

            return url;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}