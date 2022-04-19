package com.example.maktabapi_exam;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MaktabApiExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaktabApiExamApplication.class, args);
    }

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(apiInfo());
    }


    public Info apiInfo(){
        Info info = new Info();
        info.title("Live api code")
                .description("Live code system  swagger Open Api ")
                .version("v1.0.0");
        return info;
    }
}
