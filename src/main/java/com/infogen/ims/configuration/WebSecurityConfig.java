package com.infogen.ims.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.infogen.ims.user.jwt.JwtAccessDeniedHandler;
import com.infogen.ims.user.jwt.JwtAuthenticationEntryPoint;
import com.infogen.ims.user.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return  PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //return new Pbkdf2PasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .httpBasic().disable()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .accessDeniedHandler(jwtAccessDeniedHandler)

        .and()
        .authorizeRequests()
        .antMatchers("/ims/auth/**").permitAll()
     //   .antMatchers("/ims/**").authenticated()
      //  .anyRequest().authenticated()

        .and()
        .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }


    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    //     return httpSecurity.csrf(csrf ->csrf.disable())
    //             .authorizeRequests(requests ->requests
    //             // 관리자 관련 모든 요청에 대해 승인된 사용자 중 ADMIN 권한이 있는 사용자만 허용
    //             .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
    //             .antMatchers("/api/v2/admin/**").hasRole("ADMIN")
    //             // 회원가입 및 로그인 관련 모든 요청에 대해 아무나 승인
    //             .antMatchers("/api/v1/auth/**").permitAll()
    //             .antMatchers("/api/v2/auth/**").permitAll()
    //             // 중복체크 관련 모든 요청에 대해 아무나 허용
    //             .antMatchers("/api/v1/user/check/**").permitAll()
    //             .antMatchers("/api/v2/user/check/**").permitAll()
    //             // 유저정보 관련 모든 요청에 대해 승인된 사용자만 허용
    //             .antMatchers("/api/v1/user/**").authenticated()
    //             .antMatchers("/api/v2/user/**").authenticated()
    //             // 첨부파일 관련 GET 요청에 대해 아무나 승인
    //             .antMatchers(HttpMethod.GET, "/api/v1/attachment/**").permitAll()
    //             .antMatchers(HttpMethod.GET, "/api/v2/attachment/**").permitAll()
    //             // 댓글 관련 GET 요청에 대해 아무나 승인
    //             .antMatchers(HttpMethod.GET, "/api/v1/comment/**").permitAll()
    //             .antMatchers(HttpMethod.GET, "/api/v2/comment/**").permitAll()
    //             // 게시글 관련 GET 요청에 대해 아무나 승인
    //             .antMatchers(HttpMethod.GET, "/api/v1/post/**").permitAll()
    //             .antMatchers(HttpMethod.GET, "/api/v2/post/**").permitAll()
    //             // 기타 모든 요청에 대해 승인된 사용자만 허용
    //             .antMatchers("/api/v1/**").authenticated()
    //             .antMatchers("/api/v2/**").authenticated())
    //             .sessionManagement()
    //             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    //             .and()
    //             .build();
    // }

}
