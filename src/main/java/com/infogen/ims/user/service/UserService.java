package com.infogen.ims.user.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.infogen.ims.user.vo.Member;

public interface UserService {
    public List<Member> userInfoSelect(String userNm) throws Exception;
}

