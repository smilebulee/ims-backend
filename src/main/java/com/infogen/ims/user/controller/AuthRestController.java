package com.infogen.ims.user.controller;


import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.infogen.ims.user.service.AuthService;
import com.infogen.ims.user.vo.AuthRequestDto;
import com.infogen.ims.user.vo.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthRestController {
    private final AuthService authService;

    @PostMapping("/ims/auth/login")
    public ResponseEntity<Member> login(@RequestBody AuthRequestDto requestDto) {  
        return ResponseEntity.ok(authService.login(requestDto));
    }

}
