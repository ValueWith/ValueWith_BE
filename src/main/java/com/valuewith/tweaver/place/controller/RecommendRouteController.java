package com.valuewith.tweaver.place.controller;

import com.valuewith.tweaver.config.SwaggerConfig;
import com.valuewith.tweaver.place.dto.RecommendRouteDto;
import com.valuewith.tweaver.place.service.RecommendRouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {SwaggerConfig.RECOMMEND_ROUTE_TAG})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend/route")
public class RecommendRouteController {
  private final RecommendRouteService recommendRouteService;

  @ApiOperation(value = "장소 기반 길 추천 API")
  @PostMapping
  public ResponseEntity<List<RecommendRouteDto>> recommendRoute(@RequestBody List<RecommendRouteDto> places) {
    return ResponseEntity.ok(recommendRouteService.recommendRoute(places));
  }
}
