package com.seung.pilot.business.user.api;

import com.seung.pilot.business.user.service.UserService;
import com.seung.pilot.commons.dto.request.commons.BasicGetListRequest;
import com.seung.pilot.commons.dto.request.user.SignInRequest;
import com.seung.pilot.commons.dto.request.user.SignUpRequest;
import com.seung.pilot.commons.dto.request.user.UpdateUserRequest;
import com.seung.pilot.commons.dto.response.user.GetUserListResponse;
import com.seung.pilot.commons.dto.response.user.SignInResponse;
import com.seung.pilot.commons.dto.response.user.SignUpResponse;
import com.seung.pilot.commons.dto.response.user.UserResponse;
import com.seung.pilot.commons.exception.SignInException;
import com.seung.pilot.commons.utils.ApiUtils.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/sign-in")
    public ApiResult<SignInResponse> signIn(@Valid @RequestBody SignInRequest request) throws SignInException {
        return success(userService.signIn(request));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/{userId}")
    public ApiResult<?> update(@PathVariable("userId") Long userId, @RequestBody UpdateUserRequest request) {
        userService.updateUser(userId, request);
        return success();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{userId}")
    public ApiResult<UserResponse> getUser(@PathVariable("userId") Long userId) {
        return success(userService.getUser(userId));
    }

    @PreAuthorize("hasRole('ROLE_MASTER')")
    @GetMapping
    public ApiResult<Page<GetUserListResponse>> getList(BasicGetListRequest request, Pageable pageable) {
        return success(userService.getUserList(request, pageable));
    }

}
