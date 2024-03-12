package com.seung.pilot.commons.filter;

import com.seung.pilot.commons.exception.NotAuthorizedException;
import com.seung.pilot.commons.exception.TokenFilterException;
import com.seung.pilot.commons.security.CustomUserDetails;
import com.seung.pilot.commons.security.SecurityService;
import com.seung.pilot.commons.security.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final SecurityService securityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            final String token = tokenService.parseTokenByRequest(request);
            if (token != null) {
                CustomUserDetails userDetails = tokenService.getUserDetailsByToken(token);
                securityService.setAuthentication(userDetails);
            }
        } catch (ExpiredJwtException | NotAuthorizedException e) {
            // jwt만료 등 jwt문제는 에러로그(stack trace)를 남기지 않기 위해서 따로 처리
            throw new TokenFilterException(e);
        } catch (Exception e) {
            throw new NotAuthorizedException("Invalid Token.", e);
        }

        filterChain.doFilter(request, response);
    }
}
