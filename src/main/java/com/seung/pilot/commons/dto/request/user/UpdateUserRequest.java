package com.seung.pilot.commons.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    private String nickName;
    private String userEmail;
    private String loginPw;
    private String userPhoneNumber;
}
