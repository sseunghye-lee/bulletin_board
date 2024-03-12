package com.seung.pilot.commons.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seung.pilot.commons.enums.ResultCode;
import com.seung.pilot.commons.exception.NotAuthorizedException;
import com.seung.pilot.commons.exception.TokenFilterException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.seung.pilot.commons.utils.ApiUtils.newResponse;


@CommonsLog
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (TokenFilterException e) {
            log.warn("token filter exception.");

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            setHeader(response);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(
                newResponse(ResultCode.NONE, e.getMessage(), HttpStatus.UNAUTHORIZED));
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (NotAuthorizedException e) {
            log.error("Not Authorized.", e);

//            ApiError apiError = ApiError.builder().message(e.getMessage()).build();

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            setHeader(response);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(
                newResponse(ResultCode.NONE, e.getMessage(), HttpStatus.UNAUTHORIZED));
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (ClientAbortException e) {
            // 클라이언트가 서버응답을 받기 전에 연결을 끊는 경우에 발생. 보통 소켓통신 연결이 끊어진 경우 발생.
            log.warn("ClientAbortException", e);
        } catch (Exception e) {
            log.error("Internal Server Error.", e);
//            ApiError apiError = ApiError.builder().message(e.getMessage()).build();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

            setHeader(response);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(
                newResponse(ResultCode.NONE, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    private void setHeader(HttpServletResponse response) {

        response.setHeader("content-type", "application/json;charset=UTF-8");
        response.setHeader("cache-control", "no-cache, no-store, max-age=0, must-revalidate");
        response.setHeader("expires", "0");
        response.setHeader("pragma", "no-cache'");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");

    }
}
