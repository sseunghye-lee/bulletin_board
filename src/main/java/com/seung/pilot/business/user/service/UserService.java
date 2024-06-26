package com.seung.pilot.business.user.service;

import com.seung.pilot.business.user.domain.Users;
import com.seung.pilot.business.user.repo.UserRepo;
import com.seung.pilot.business.user.repo.UserRepoSupport;
import com.seung.pilot.commons.dto.request.commons.BasicGetListRequest;
import com.seung.pilot.commons.dto.request.user.SignInRequest;
import com.seung.pilot.commons.dto.request.user.SignUpRequest;
import com.seung.pilot.commons.dto.request.user.UpdateUserRequest;
import com.seung.pilot.commons.dto.response.user.GetUserListResponse;
import com.seung.pilot.commons.dto.response.user.SignInResponse;
import com.seung.pilot.commons.dto.response.user.SignUpResponse;
import com.seung.pilot.commons.dto.response.user.UserResponse;
import com.seung.pilot.commons.enums.ResultCode;
import com.seung.pilot.commons.exception.SignInException;
import com.seung.pilot.commons.security.EncryptService;
import com.seung.pilot.commons.utils.ModelMapperUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public Users getOne(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("not Exists " + userId));
    }

    public UserResponse getUser(Long userId) {
        Users user = this.getOne(userId);
        UserResponse response = ModelMapperUtil.get().map(user, UserResponse.class);
        response.setUserPhoneNumber(EncryptService.encryptPhoneNumber(response.getUserPhoneNumber()));
        return response;
    }

    public Users getUserByEmailAndPw(String email, String password) throws SignInException {
        Users user = getUserByEmail(email).orElseThrow(() ->
                new SignInException(ResultCode.NOT_EXISTS, String.format("%s(email: %s", "Not Exists User.", email)));

        if(passwordEncoder.matches(password, user.getLoginPw())) {
            return user;
        }

        throw new SignInException(ResultCode.NOT_EXISTS, String.format("%s(email: %s, %s)", "Not Exists User.", email, password));
    }

    public SignInResponse signIn(SignInRequest request) throws SignInException {
        Users user = this.getUserByEmailAndPw(request.getEmail(), request.getPassword());

        SignInResponse signInResponse = user.signIn(request.getRemember());

        return signInResponse;
    }

    public void updateUser(Long userId, UpdateUserRequest request) {
        Users user = this.getOne(userId);
        request.setLoginPw(passwordEncoder.encode(request.getLoginPw()));
        request.setUserPhoneNumber(EncryptService.encryptPhoneNumber(request.getUserPhoneNumber()));
        user.updateUser(request);
    }

    public Page<GetUserListResponse> getUserList(BasicGetListRequest request, Pageable pageable) {
        List<GetUserListResponse> list = userRepoSupport.getUserList(request, pageable);
        Long total = userRepoSupport.getUserCount(request);

        pageable = pageable == null ? PageRequest.of(0, 10) : pageable;

        return new PageImpl<>(list, pageable, total);
    }

    public List<GetUserListResponse> findByUserName(String userName) {
        return userRepo.findByUserNameStartsWith(userName).stream().map(Users::convertListDto)
                .collect(Collectors.toList());
    }

    public Map<Long, GetUserListResponse> getUsers(Set<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return Map.of();
        }

        return userRepo.findByUserIdIn(userIds).stream().map(Users::convertListDto)
                .collect(Collectors.toMap(GetUserListResponse::getUserId, Function.identity()));
    }

    private GetUserListResponse convertToGetUserListResponse(Users user) {
        GetUserListResponse userListResponse = new GetUserListResponse();
        userListResponse.setUserId(user.getUserId());
        userListResponse.setUserName(user.getUserName());
        userListResponse.setNickName(user.getNickName());
        userListResponse.setGender(user.getGender());
        userListResponse.setCreatedDate(user.getCreatedDate());
        return userListResponse;
    }


}
