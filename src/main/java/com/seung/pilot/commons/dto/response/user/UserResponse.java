package com.seung.pilot.commons.dto.response.user;

import com.seung.pilot.commons.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long userId;
    private String birth;
    private Gender gender;
    private String nickName;
    private String userEmail;
    private String userName;
    private String userPhoneNumber;
}
