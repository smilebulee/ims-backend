package com.infogen.ims.user.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.infogen.ims.mapper.UserMapper;
import com.infogen.ims.user.repository.UserRepository;
import com.infogen.ims.user.vo.Member;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public List<Member> userInfoSelect(String userNm) throws Exception {
        return  userMapper.userInfoSelect(userNm);
    }

}
