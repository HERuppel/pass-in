package rocketseat.com.passin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import rocketseat.com.passin.middlewares.RequestValidationInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  
  @Autowired
  private RequestValidationInterceptor requestValidationInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(requestValidationInterceptor);
  }
}
