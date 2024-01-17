package com.jjn.ojManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * knife4j配置类
 *
 * @author 焦久宁
 * @date 2023/11/21
 */
@Configuration
@EnableSwagger2
public class Knife4jConfig {

    @Bean
    public Docket defaultApi2() {
        return new Docket(DocumentationType.OAS_30).apiInfo(new ApiInfoBuilder()
                        .title("OJ接口文档")
                        .description("OJ")
                        .version("1.0")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jjn.ojManagement.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
