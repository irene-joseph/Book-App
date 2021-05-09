package com.stackroute.swagger;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket postApi(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("swagger-api-UST").
                apiInfo(apiInfo()).select().paths(postPaths()).build();
    }

    private Predicate<String> postPaths(){
        return or(regex("/api/.*"),regex("/api/.*"));
    }
    private ApiInfo apiInfo(){
        return  new ApiInfoBuilder().title("Book Application Rest API Documentation")
                .description("Book APP")
                .contact("bookapp@gmail.com")
                .license("stackroute")
                .licenseUrl("www.stackroute.com")
                .termsOfServiceUrl("abc@gmail.com")
                .version("1.0").build();
    }
}
