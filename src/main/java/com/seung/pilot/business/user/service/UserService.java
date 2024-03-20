package com.seung.pilot.business.user.service;

import com.seung.pilot.business.user.domain.Users;
import com.seung.pilot.business.user.repo.UserRepo;
import com.seung.pilot.business.user.repo.UserRepoSupport;
import com.seung.pilot.commons.dto.request.user.SignUpRequest;
import com.seung.pilot.commons.dto.response.user.SignUpResponse;
import com.seung.pilot.commons.enums.ResultCode;
import com.seung.pilot.commons.exception.SignInException;
import com.seung.pilot.commons.security.EncryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final UserRepoSupport userRepoSupport;

    public Optional<Users> getUserByEmail(String userEmail) {
        return userRepo.findByUserEmail(userEmail);
    }

    public Users makeUser(SignUpRequest request) {
        request.setLoginPw(passwordEncoder.encode(request.getLoginPw()));
        request.setUserPhoneNumber(EncryptService.encryptPhoneNumber(request.getUserPhoneNumber()));
        Users user = Users.init(request);

        userRepo.save(user);
        return user;
    }

    public SignUpResponse signUp(SignUpRequest request) throws SignInException {
        Optional<Users> getUser = this.getUserByEmail(request.getUserEmail());
        if(!getUser.isEmpty()) {
            throw new SignInException(ResultCode.CONFLICT, "이미 가입된 정보가 존재합니다.");
        }

        Users user = this.makeUser(request);
        return user.convertSignUpResponse();
    }
}
