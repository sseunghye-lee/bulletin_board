package com.seung.pilot.commons.security;

import com.seung.pilot.business.user.domain.Users;
import com.seung.pilot.commons.enums.ResultCode;
import com.seung.pilot.commons.exception.NotAuthorizedException;
import com.seung.pilot.commons.exception.SignInException;
import com.seung.pilot.commons.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CommonsLog
@Service
@RequiredArgsConstructor
public class TokenService {

    private static Long defaultExpirationMinutes;

    @Value("${util.jwt.defaultExpirationMinutes}")
    public void setDefaultExpirationMinutes(Long defaultExpirationMinutes) {
        TokenService.defaultExpirationMinutes = defaultExpirationMinutes;
    }

    public String parseTokenByRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader("authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    public static String parseReFreshTokenByRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader("refresh-token");
        return authHeader;
    }

    public static Map<String, Object> generateDefaultClaims(Users user, Long plusExpMinutes) {
        LocalDateTime now = LocalDateTime.now();

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "pilot-access-token");
        claims.put("iss", "pilot");
        claims.put("iat", Timestamp.valueOf(now));
        claims.put("exp", Timestamp.valueOf(now.plusMinutes(plusExpMinutes)).getTime() / 1000);
        claims.put("userId", user.getUserId());
        claims.put("userName", user.getUserName());
        claims.put("userEmail", user.getUserEmail());
        claims.put("created",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        claims.put("userRoles", user.getUserRoleForString());

        return claims;
    }

    public static Map<String, Object> generateReFreshClaims(Users user, Long plusExpMinutes) {
        LocalDateTime now = LocalDateTime.now();

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "pilot-refresh-token");
        claims.put("iss", "pilot");
        claims.put("userId", user.getUserId());
        claims.put("iat", Timestamp.valueOf(now));
        claims.put("exp", Timestamp.valueOf(now.plusMinutes(plusExpMinutes)).getTime() / 1000);
        claims.put("created",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

        return claims;
    }

    public static String generateToken(Users user, Boolean remember) {
        Long expMin = remember ? defaultExpirationMinutes * 24 * 7 : defaultExpirationMinutes;
        Map<String, Object> claims = generateDefaultClaims(user, expMin);

        return JwtUtil.generateToken(claims);
    }

    public static String generateReFreshToken(Users user, Boolean remember) {
        Long expMin = remember ? defaultExpirationMinutes * 24 * 30 : defaultExpirationMinutes * 4;
        Map<String, Object> claims = generateReFreshClaims(user, expMin);

        return JwtUtil.generateRefreshToken(claims);
    }

    public static String generateToken(Users user, Long plusExpMinutes) {
        Map<String, Object> claims = generateDefaultClaims(user, plusExpMinutes);

        return JwtUtil.generateToken(claims);
    }

    public CustomUserDetails getUserDetailsByToken(String token) {
        Map<String, Object> claims = JwtUtil.getClaims(token);
        if (claims == null) {
            throw new NotAuthorizedException("Invalid token (" + token + ")");
        }

        // token정보 이외에 추가로 정보가 필요해서 DB의 데이터를 조회해서 userDetails정보를 만드려면 UserDetailsService 인터페이스를 캐시서비스 또는 유저서비스에서 구현해서 userDetails를 리턴하게 하면 된다.
        // 꼭 UserDetailsService 인터페이스를 구현하지 않아도 되고 DB를 조회해서 userDetails에 데이터를 넣어서 리턴해주게 만들면된다.
        Long userId = Long.parseLong(claims.get("userId").toString());
        String userEmail = claims.get("userEmail").toString();
        String userName = claims.get("userName").toString();
        String phoneNumber = (String) claims.get("phoneNumber");
        List<String> userRoles = null;
        if (claims.containsKey("userRoles")) {
            userRoles = (List<String>) claims.get("userRoles");
        }

        return new CustomUserDetails(userId, userRoles, userEmail, userName, phoneNumber);
    }

    public static CustomUserDetails getUserDetailsByRefreshToken(String token)
        throws SignInException {
        Map<String, Object> claims = null;
        try {
            claims = JwtUtil.getClaimsForReFresh(token);
        } catch (Exception e) {
            throw new SignInException(ResultCode.EXPIRED_REFRESH_TOKEN,
                "Invalid token (" + token + ")");
        }

        if (claims == null) {
            throw new SignInException(ResultCode.EXPIRED_REFRESH_TOKEN,
                "Invalid token (" + token + ")");
        }

        Long userId = Long.parseLong(claims.get("userId").toString());

        return new CustomUserDetails(userId, null, null, null, null);
    }

}
