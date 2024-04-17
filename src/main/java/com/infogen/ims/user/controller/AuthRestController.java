package com.infogen.ims.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.infogen.ims.mapper.ReportMapper;
import com.infogen.ims.user.service.AuthService;
import com.infogen.ims.user.vo.AuthRequestDto;
import com.infogen.ims.user.vo.AuthResponseDto;
import com.infogen.ims.user.vo.TokenDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthRestController {
    private final AuthService authService;

    @PostMapping("/ims/auth/login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthRequestDto requestDto) {  
        System.out.println(requestDto);
        System.out.println("login" + authService.login(requestDto).toString());
        return ResponseEntity.ok(authService.login(requestDto));
    }

    private final ReportMapper map;

    @GetMapping("/ims/auth/test")
    public String test() {
        return "a";
    }





    // /** 토큰갱신 API */
    // @GetMapping("/ims/auth/refresh")
    // public ResponseEntity<?> refreshToken(@RequestHeader("REFRESH_TOKEN") String refreshToken) {
    //     String newAccessToken = this.authService.refreshToken(refreshToken);
    //     return ResponseEntity.status(HttpStatus.OK).body(newAccessToken);
    // }
}
