package com.seung.pilot.config;

import com.seung.pilot.commons.utils.EncryptUtil;
import com.seung.pilot.commons.utils.JwtUtil;
import com.seung.pilot.commons.utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.UnsupportedEncodingException;

@Configuration
@RequiredArgsConstructor
public class InitBeanConfig {

    @Value("${util.encrypt.secretKey}")
    private String encSecretKey;

    @Value("${util.jwt.secretKey}")
    private String jwtSecretKey;

    @Value("${util.jwt.refreshKey}")
    private String jwtReFreshKey;

    private final Environment env;

    @Bean
    public EncryptUtil encryptUtil() throws UnsupportedEncodingException {
        return new EncryptUtil(encSecretKey);
    }

    @Bean
    public JwtUtil jwtUtil() throws UnsupportedEncodingException {
        return new JwtUtil(jwtSecretKey, jwtReFreshKey);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mm = new ModelMapper();
        mm.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true);
        return mm;
    }

    @Bean
    public ModelMapperUtil modelMapperUtil() {
        return new ModelMapperUtil(modelMapper());
    }
}
