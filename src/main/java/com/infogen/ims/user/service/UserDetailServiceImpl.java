package com.infogen.ims.user.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.infogen.ims.mapper.ReportMapper;
import com.infogen.ims.user.repository.UserRepository;
import com.infogen.ims.user.vo.AuthUserDetails;
import com.infogen.ims.user.vo.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
  
    private final ReportMapper map;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        System.out.println(">>loadUserByUsername"+ String.valueOf(userRepository.findByUserId(userId).get()));
      //  return userRepository.findByUserId(userId)
        return userRepository.findByUserId(userId)
        .map(this::createUserDetails)
        .orElseThrow(() -> new UsernameNotFoundException(userId + " 을 DB에서 찾을 수 없습니다"));
        // Optional<Member> member = userRepository.findById(userId);
        // System.out.println(member);
    }

    // @Override
    // public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    //        System.out.println(">"+userRepository.findById(userId).toString());
    //     return userRepository.findById(userId)
    //             .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    // }

    private UserDetails createUserDetails(Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthGrpCd().toString());
        return new User(
                String.valueOf(member.getUserId()),
                member.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }

    public UserDetails loadUserByUserId(String userId) throws IllegalArgumentException {
        // AuthUserDetails user = userRepository.findById(userId).orElseThrow(
        //         () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. user_id = " + userId));
        //return new AuthUserVo(user);
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId + " 을 DB에서 찾을 수 없습니다"));
    }
}
