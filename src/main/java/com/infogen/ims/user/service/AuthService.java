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
    private final AuthenticationManagerBuilder managerBuilder;

    public TokenDto login(AuthRequestDto requestDto) {

        UserDetails user = userRepository.findByUserId(requestDto.getUserId())
                                         .map(this::createUserDetails)
                                         .orElseThrow(() -> new UsernameNotFoundException(requestDto.getUserId() + " : 해당 유저를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다." );
        }

        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        log.info("authenticationToken : {}", authenticationToken);
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        return tokenProvider.generateTokenDto(authentication);
       
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

        System.out.println(">>loadUserByUsername"+ String.valueOf(userRepository.findByUserId(userId).get()));
        return userRepository.findByUserId(userId)
        .map(this::createUserDetails)
        .orElseThrow(() -> new UsernameNotFoundException(userId + " 을 DB에서 찾을 수 없습니다"));
    }

    // /** Token 갱신 */
    // @Transactional
    // public String refreshToken(String refreshToken) {
    //     // CHECK IF REFRESH_TOKEN EXPIRATION AVAILABLE, UPDATE ACCESS_TOKEN AND RETURN
    //     if (this.jwtTokenProvider.validateToken(refreshToken)) {
    //         Auth auth = this.authRepository.findByRefreshToken(refreshToken).orElseThrow(
    //                 () -> new IllegalArgumentException("해당 REFRESH_TOKEN 을 찾을 수 없습니다.\nREFRESH_TOKEN = " + refreshToken));

    //         String newAccessToken = this.jwtTokenProvider.generateAccessToken(
    //                 new UsernamePasswordAuthenticationToken(
    //                         new CustomUserDetails(auth.getUser()), auth.getUser().getPassword()));
    //         auth.updateAccessToken(newAccessToken);
    //         return newAccessToken;
    //     }

    //     // IF NOT AVAILABLE REFRESH_TOKEN EXPIRATION, REGENERATE ACCESS_TOKEN AND REFRESH_TOKEN
    //     // IN THIS CASE, USER HAVE TO LOGIN AGAIN, SO REGENERATE IS NOT APPROPRIATE
    //     return null;
    // }
}
