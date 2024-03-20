package com.seung.pilot.business.user.api;

import com.seung.pilot.business.user.service.UserService;
import com.seung.pilot.commons.dto.request.user.SignUpRequest;
import com.seung.pilot.commons.dto.response.user.SignUpResponse;
import com.seung.pilot.commons.exception.SignInException;
import com.seung.pilot.commons.utils.ApiUtils.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.seung.pilot.commons.utils.ApiUtils.success;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/sign-up")
    public ApiResult<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) throws SignInException {
        return success(userService.signUp(request));
    }
}
