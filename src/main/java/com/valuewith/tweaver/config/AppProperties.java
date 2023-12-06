package com.valuewith.tweaver.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

  private final OAuth2 oAuth2 = new OAuth2();

  @Getter
  public static final class OAuth2 {

    private final List<String> authorizedRedirectUris = new ArrayList<>();
  }
}
