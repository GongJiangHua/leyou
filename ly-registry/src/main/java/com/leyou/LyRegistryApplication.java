package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * author:  jianghua
 * desc:    服务注册中心启动类
 */
@EnableEurekaServer
@SpringBootApplication
public class  LyRegistryApplication{

    public static void main(String[] args){
        SpringApplication.run(LyRegistryApplication.class,args);
    }

}
