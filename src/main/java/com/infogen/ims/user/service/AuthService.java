package com.infogen.ims.user.service;

import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infogen.ims.user.jwt.JwtTokenProvider;
import com.infogen.ims.user.repository.UserRepository;
import com.infogen.ims.user.vo.Auth;
import com.infogen.ims.user.vo.AuthRequestDto;
import com.infogen.ims.user.vo.AuthResponse;
import com.infogen.ims.user.vo.AuthUserDetails;
import com.infogen.ims.user.vo.Member;
import com.infogen.ims.user.vo.TokenDto;
import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
   // private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    private final AuthenticationManagerBuilder managerBuilder;

    public TokenDto login(AuthRequestDto requestDto) {
        
        // AuthUserDetails user = userRepository.loadUserByUserId(requestDto.getUserId()).orElseThrow(
        //                 () -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다. username = " + requestDto.getUserId()));
        //         if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
        //             throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. username = " + requestDto.getUserId());
        //         }

       // Optional<Member> optionalUser  = userRepository.loadUserByUserId(requestDto.getUserId());
        // if (optionalUser.isPresent()) {
        //     Member member = optionalUser.get();
        //     if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
        //         throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. username = " + requestDto.getUserId());
        //      }

        //      UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        //      return tokenProvider.generateTokenDto(authenticationToken.getPrincipal(), authenticationToken.getCredentials());
        // } else {
        //    throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다. username = " + requestDto.getUserId());
        // }
        
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        log.info("authenticationToken : {}", authenticationToken);
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        return tokenProvider.generateTokenDto(authentication);
       
    }
    
    // /** 로그인 */
    // @Transactional
    // public AuthResponseDto login(AuthRequestDto param) {
    //     // CHECK USERNAME AND PASSWORD
    //     AuthUserDetails user = this.userRepository.loadUserByUserId(param.getUserId()).orElseThrow(
    //             () -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다. username = " + param.getUserId()));
    //     if (!passwordEncoder.matches(param.getPassword(), user.getPassword())) {
    //         throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. username = " + param.getUserId());
    //     }

    //     // GENERATE ACCESS_TOKEN AND REFRESH_TOKEN
    //     String accessToken = this.jwtTokenProvider.generateAccessToken(
    //             new UsernamePasswordAuthenticationToke(new CustomUserDetails(user), user.getPassword()));
    //     String refreshToken = this.jwtTokenProvider.generateRefreshToken(
    //             new UsernamePasswordAuthenticationToken(new CustomUserDetails(user), user.getPassword()));

    //     // CHECK IF AUTH ENTITY EXISTS, THEN UPDATE TOKEN
    //     if (this.authRepository.existsByUser(user)) {
    //         user.getAuth().updateAccessToken(accessToken);
    //         user.getAuth().updateRefreshToken(refreshToken);
    //         return new AuthResponseDto(user.getAuth());
    //     }

    //     // IF NOT EXISTS AUTH ENTITY, SAVE AUTH ENTITY AND TOKEN
    //     Auth auth = this.authRepository.save(Auth.builder()
    //                     .user(user)
    //                     .tokenType("Bearer")
    //                     .accessToken(accessToken)
    //                     .refreshToken(refreshToken)
    //                     .build());
    //     return new AuthResponseDto(auth);
    // }

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
