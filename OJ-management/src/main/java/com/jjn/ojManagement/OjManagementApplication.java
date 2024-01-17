package com.jjn.ojManagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jjn.ojManagement.mapper")
public class OjManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(OjManagementApplication.class, args);
    }

}
