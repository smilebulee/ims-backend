package com.infogen.ims.user.vo;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Data
@Entity
//(name="tbAuthUser")
@AllArgsConstructor
@Getter
@Setter
//@NoArgsConstructor
public class AuthUserDetails implements UserDetails{

    private final AuthUserDetails authUserVo;

    @Id
    private String userId;

    private String authGroupCd;
    private String deptCd;
    private String userNm;
    private String password;
    private String userStatusCd;
    private String email;
    private String joinDate;
    
    public AuthUserDetails(AuthUserDetails authUserVo){
        this.authUserVo = authUserVo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return Collections.emptyList();
    }
    @Override
    public String getUsername() {
        return this.userNm;
    }
    public String getUserId() {
        return this.userId;
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