package com.cool.storm.boot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;

@SpringBootApplication
@ComponentScan(value = "com.cool")
@EnableJpaRepositories(basePackages = "com.cool.storm.dao")
@EntityScan(basePackages = "com.cool.storm.dao")
public class SpringStormApplication {

    public synchronized static void run(String ...args) {
        SpringApplication app = new SpringApplication(SpringStormApplication.class);
        //我们并不需要web servlet功能，所以设置为WebApplicationType.NONE
        app.setWebApplicationType(WebApplicationType.NONE);
        //忽略掉banner输出
        app.setBannerMode(Banner.Mode.OFF);
        //忽略Spring启动信息日志
        app.setLogStartupInfo(false);
        app.run(args);
    }
}
