package com.valuewith.tweaver.config;

import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

  public static final String AUTH_TAG = "인증 API";
  public static final String TRIP_LIST_TAG = "그룹 리스트 API";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  @Bean
  public Docket publicApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .consumes(getConsumeContentTypes())
        .produces(getProduceContentTypes())
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build()
        .tags(
            new Tag(AUTH_TAG, "회원 인증, 토큰 재발급을 담당"),
            new Tag(TRIP_LIST_TAG, "여행 그룹 리스트 조회 담당")
        )
        .useDefaultResponseMessages(false)
        .apiInfo(apiInfo());
  }

  private Set<String> getProduceContentTypes() {
    Set<String> produces = new HashSet<>();
    produces.add("application/json;charset=UTF-8");
    return produces;
  }

  private Set<String> getConsumeContentTypes() {
    Set<String> consumes = new HashSet<>();
    consumes.add("application/json;charset=UTF-8");
    consumes.add("application/x-www-form-urlencoded");
    return consumes;
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Tweaver API")
        .description("Tweaver API 명세서입니다.")
        .version("v0.1")
        .build();
  }
}
