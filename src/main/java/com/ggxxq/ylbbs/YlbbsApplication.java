package com.ggxxq.ylbbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
//@EnableMongoRepositories(basePackages = "com.ggxxq.ylbbs.controller.repository")
public class  YlbbsApplication {

    public static void main(String[] args) {
        SpringApplication.run(YlbbsApplication.class, args);
    }

}
