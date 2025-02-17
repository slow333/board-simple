package a.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

  @Bean
  public SecurityFilterChain configSecurity(HttpSecurity http) throws Exception {
    http
      .csrf((csrf) -> csrf
              .ignoringRequestMatchers("/h2-console/**"))
      .authorizeHttpRequests(request -> request
          .requestMatchers("/**").permitAll()
          .requestMatchers("/question/**").permitAll()
          .requestMatchers("/board/**").permitAll()
          .requestMatchers(new AntPathRequestMatcher("/error/**")).permitAll()
        )
      .headers(headers -> headers
          .addHeaderWriter(new XFrameOptionsHeaderWriter(
                  XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN
          )))
      .formLogin(login -> login
          .loginPage("/user/login")
          .defaultSuccessUrl("/"))
      .logout(logout -> logout
          .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
          .logoutSuccessUrl("/")
          .invalidateHttpSession(true));
    return http.build();
  }

  @Bean
  PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
  throws Exception {
    return authConfig.getAuthenticationManager();
  }
}
/*
      .headers(header -> header
          .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
 */