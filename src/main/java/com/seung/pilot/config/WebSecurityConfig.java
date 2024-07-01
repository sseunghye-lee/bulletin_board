package com.seung.pilot.config;

import com.seung.pilot.business.user.service.UserService;
import com.seung.pilot.commons.filter.EncodingFilter;
import com.seung.pilot.commons.filter.ExceptionHandlerFilter;
import com.seung.pilot.commons.filter.TokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final UserService userService;

    @Value("${util.jwt.secretKey}")
    private String secretKey;

    private final TokenFilter tokenFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final EncodingFilter encodingFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(encodingFilter, CsrfFilter.class)
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, TokenFilter.class)
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests.anyRequest().permitAll())
                .build();
    }
}
