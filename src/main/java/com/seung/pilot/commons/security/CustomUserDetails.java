package com.seung.pilot.commons.security;

import com.seung.pilot.commons.enums.UserRole;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@ToString
public class CustomUserDetails implements UserDetails {

    public final Long userId;
    private final List<UserRole> userRoleList;
    private final List<GrantedAuthority> authorityList;
    private final String userEmail;

    private final String userName;
    private final String userPhoneNumber;

    public CustomUserDetails(Long userId, List<String> userRoleList, String userEmail, String userName, String userPhoneNumber) {
        this.userId = userId;
        this.userRoleList = new ArrayList<>();
        this.authorityList = new ArrayList<>();
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;

        if (userRoleList != null && !userRoleList.isEmpty()) {
            for (String role : userRoleList) {
                this.authorityList.add(new SimpleGrantedAuthority(role));
                this.userRoleList.add(UserRole.of(role));
            }
        }
    }

    public Boolean isMaster(){
        return this.userRoleList.contains(UserRole.ROLE_MASTER);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorityList;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userId.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
