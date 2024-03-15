package com.valuewith.tweaver.config;

import com.valuewith.tweaver.auth.CustomAuthenticationPrincipalArgumentResolver;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
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
  public static final String CHAT_TAG = "채팅 API";
  public static final String TRIP_GROUP_TAG = "여행 그룹 API";
  public static final String GROUP_MEMBER_APPLICATION_TAG = "그룹 참여 신청 API";
  public static final String GROUP_MEMBER_LIST_TAG = "그룹원 리스트 API";
  public static final String MEMBER_TAG = "멤버 API";
  public static final String ALERT_TAG = "알림 API";
  public static final String RECOMMEND_ROUTE_TAG = "길 추천 API";
  public static final String LOCATION_IMAGE_TAG = "이미지 API";
  public static final String CALENDAR_TAG = "달력 API";
  public static final String BOOKMARK_TAG = "북마크 API";

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
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .paths(PathSelectors.any())
        .build()
        .tags(
            new Tag(AUTH_TAG, "회원 인증, 토큰 재발급을 담당"),
            new Tag(TRIP_LIST_TAG, "여행 그룹 리스트 조회 담당"),
            new Tag(CHAT_TAG, "채팅 조회 담당"),
            new Tag(TRIP_GROUP_TAG, "여행 그룹 조회 담당"),
            new Tag(GROUP_MEMBER_APPLICATION_TAG, "그룹 참여 신청 담당"),
            new Tag(GROUP_MEMBER_LIST_TAG, "그룹원 리스트 담당"),
            new Tag(MEMBER_TAG, "회원 개인정보 담당"),
            new Tag(ALERT_TAG, "알림 담당"),
            new Tag(RECOMMEND_ROUTE_TAG, "길 추천 알고리즘 호출 담당"),
            new Tag(LOCATION_IMAGE_TAG, "이미지 담당"),
            new Tag(CALENDAR_TAG, "달력 조회 담당"),
            new Tag(BOOKMARK_TAG, "북마크 담당")
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

  // CustomAuthPrincipal 어노테이션 관련 메서드
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new CustomAuthenticationPrincipalArgumentResolver());
  }
}
