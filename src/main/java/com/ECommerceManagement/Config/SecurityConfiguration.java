package com.ECommerceManagement.Config;

import com.ECommerceManagement.Filter.JwtValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration  {
  @Bean
  SecurityFilterChain customizedSecurityFilterChain(HttpSecurity http) throws Exception{
      http.csrf(csrf -> csrf.disable())
              .authorizeHttpRequests((requests) -> {
                  requests.requestMatchers("/api/auth/consumer/**").hasAnyAuthority("CONSUMER")
                          .requestMatchers("/api/auth/seller/**").hasAnyAuthority("SELLER")
                          .requestMatchers("/api/public/**").permitAll()
                          .anyRequest().authenticated();
              })
              .sessionManagement(session -> session
                      .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless sessions
              )
//                .addFilterAfter(new JwtGenerationFilter(), BasicAuthenticationFilter.class)
              .addFilterBefore(new JwtValidationFilter(), BasicAuthenticationFilter.class);

//        http.formLogin(Customizer.withDefaults());
//        http.httpBasic(Customizer.withDefaults());
      return (SecurityFilterChain) http.build();
  }
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return  NoOpPasswordEncoder.getInstance();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
