package com.infogen.ims.user.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infogen.ims.user.service.UserService;
import com.infogen.ims.user.vo.Member;

import lombok.RequiredArgsConstructor;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/ims/user/userSelect")
    public List<Member> userInfoSelect(@RequestParam String userNm) throws Exception {
        return userService.userInfoSelect(userNm);
    }
}
