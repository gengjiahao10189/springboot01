package com.kuangstudy.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

//访问地址：http://ip:端口/swagger-ui/index.html
@Configuration
@EnableOpenApi // 开启swagger3的支持
@EnableWebMvc
public class Swagger3Configuration {


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                // 扫包的方式
                //.apis(RequestHandlerSelectors.basePackage("com.kuangstudy.contorller"))
                // 推荐下面这个
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("学相伴旅游项目实战接口文档")
                .description("学相伴旅游项目实战接口文档")
                .contact(new Contact("aa,bb,cc,dd", "https://www.itbooking.net", "10086@163.com"))
                .version("1.0")
                .build();
    }

}
