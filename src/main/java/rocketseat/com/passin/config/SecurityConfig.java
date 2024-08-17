package rocketseat.com.passin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.config.exceptions.CustomAccessDeniedHandler;
import rocketseat.com.passin.config.exceptions.CustomAuthenticationEntryPoint;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  @Autowired
  SecurityFilter securityFilter;
  @Autowired
  private final CustomAccessDeniedHandler customAccessDeniedHandler;
  @Autowired
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
    return http
            .csrf(csfr -> csfr.disable())
            .authorizeHttpRequests(auth -> {
              auth.requestMatchers(HttpMethod.POST, "/auth/signin").permitAll();
              auth.requestMatchers(HttpMethod.POST, "/auth/signup").permitAll();
              auth.requestMatchers(HttpMethod.POST, "/auth/confirm-account").permitAll();
              auth.requestMatchers(HttpMethod.GET, "/user").hasAnyRole("USER", "ADMIN");
              auth.requestMatchers(HttpMethod.POST, "/events/**").hasRole("ADMIN");
              auth.anyRequest().authenticated();
            })
            .exceptionHandling(exceptionHandling -> 
              exceptionHandling
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint)  
              )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
