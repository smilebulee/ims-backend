package com.infogen.ims.user.vo;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name="tbAuthUser")
public class Member implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    private String authGrpCd;
    private String deptCd;
    private String userNm;
    private String password;
    private String userStatusCd;
    private String email;
    private String joinDate;
    

    @Builder
    public Member(String userId, String password/*, Authority authority*/) {
        this.userId = userId;
        this.password = password; 
  
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

    @Override
    public String toString() {
        return "Member{" +
                "userId='" + userId + '\'' +
                ", authGrpCd='" + authGrpCd + '\'' +
                ", userNm='" + userNm + '\'' +
                ", password='" + password + '\'' +
                ", userStatusCd='" + userStatusCd + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}