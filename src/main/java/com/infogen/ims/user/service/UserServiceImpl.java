package com.infogen.ims.user.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogen.ims.user.repository.UserRepository;
import com.infogen.ims.user.vo.Member;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public List<Member> userInfoSelect(String userNm) throws Exception {
        return  userRepository.findByUserNm(userNm);
    }
    
}
