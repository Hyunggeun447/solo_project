package solo_project.solo_project.domain.user.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import solo_project.solo_project.domain.user.security.JwtAuthenticationFilter;
import solo_project.solo_project.domain.user.security.JwtEntryPoint;
import solo_project.solo_project.domain.user.service.CustomUserDetailService;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtEntryPoint jwtEntryPoint;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final CustomUserDetailService customUserDetailService;

  @Bean
  public PasswordEncoder passwordEncoder(){
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

        .authorizeRequests()
        // TODO: 2022/10/25
        .antMatchers(HttpMethod.GET, "/").hasAnyRole("USER")

        .anyRequest().permitAll()
        .and()

        .exceptionHandling()
        .authenticationEntryPoint(jwtEntryPoint)
        .and()

        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
  }
}
