package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * author:  niceyoo
 * blog:    https://cnblogs.com/niceyoo
 * desc:    分布式文件上传启动类
 */
@EnableDiscoveryClient
@SpringBootApplication
public class LyUploadApplication {

    public static void main(String[] args){
        SpringApplication.run(LyUploadApplication.class);
    }

}
