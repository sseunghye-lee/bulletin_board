package com.seung.pilot.commons.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpResponse {
    private String email;
    private String userName;
}
