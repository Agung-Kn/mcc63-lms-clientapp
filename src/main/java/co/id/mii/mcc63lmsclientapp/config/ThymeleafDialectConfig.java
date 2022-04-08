package co.id.mii.mcc63lmsclientapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

@Configuration
public class ThymeleafDialectConfig {

  @Bean
  public LayoutDialect layoutDialect() {
    return new LayoutDialect();
  }
}
