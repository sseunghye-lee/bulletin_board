package com.seung.pilot.commons.dto.response.user;

import com.seung.pilot.commons.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SignInResponse {
    private String accessToken;
    private LocalDateTime expired;
    private String refreshToken;
    private List<UserRole> userRoles;
    private Me me;

    @Data
    @Builder
    @AllArgsConstructor
    public static class Me {
        private Long userId;
        private String userEmail;
        private String userName;
        private String userPhoneNumber;
        private String nickName;
    }
}
