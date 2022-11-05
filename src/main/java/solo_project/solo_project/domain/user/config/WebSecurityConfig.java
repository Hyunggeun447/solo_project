package solo_project.solo_project.domain.user.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import solo_project.solo_project.domain.user.security.JwtAccessDeniedHandler;
import solo_project.solo_project.domain.user.security.JwtAuthenticationFilter;
import solo_project.solo_project.domain.user.security.JwtEntryPoint;
import solo_project.solo_project.domain.user.service.CustomUserDetailService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtEntryPoint jwtEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final CustomUserDetailService customUserDetailService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .httpBasic()
        .disable()

        .csrf()
        .disable()

        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()

        .headers()
        .frameOptions()
        .sameOrigin()
        .and()

        .authorizeRequests()

        .anyRequest().permitAll()
        .and()

        .exceptionHandling()
        .authenticationEntryPoint(jwtEntryPoint)
        .accessDeniedHandler(jwtAccessDeniedHandler)
        .and()

        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
  }
}
