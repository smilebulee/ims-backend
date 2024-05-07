package com.infogen.ims.user.service;

import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.infogen.ims.user.jwt.JwtTokenProvider;
import com.infogen.ims.user.repository.UserRepository;
import com.infogen.ims.user.vo.AuthRequestDto;
import com.infogen.ims.user.vo.Member;
import com.infogen.ims.user.vo.TokenDto;
import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public Member login(AuthRequestDto requestDto) {
        TokenDto token = generateToken(requestDto);
        Member member = userRepository.findByUserId(requestDto.getUserId()).get();
        member.setAccessToken(token.getAccessToken());
        member.setGrantType(token.getGrantType());
        member.setTokenExpiresIn(token.getTokenExpiresIn());
        return member;
    }

    public TokenDto generateToken(AuthRequestDto requestDto) {

        UserDetails user = userRepository.findByUserId(requestDto.getUserId())
                                         .map(this::createUserDetails)
                                         .orElseThrow(() -> new UsernameNotFoundException(requestDto.getUserId() + " : 해당 유저를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다." );
        }

        return tokenProvider.generateTokenDto(); 
            
    }
    
    private UserDetails createUserDetails(Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthGrpCd().toString());
        return new User(
                String.valueOf(member.getUserId()),
                member.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findByUserId(userId)
        .map(this::createUserDetails)
        .orElseThrow(() -> new UsernameNotFoundException(userId + " 을 DB에서 찾을 수 없습니다"));
    }

}
