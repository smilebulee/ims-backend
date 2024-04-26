package com.infogen.ims.user.repository;

import org.springframework.stereotype.Repository;
import com.infogen.ims.user.vo.Member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

@Repository
public interface UserRepository extends JpaRepository<Member, String>{
    //public UserDetails loadUserByUsername(String userId);
    Optional<Member> findByUserId(String userId); 

    List<Member> findByUserNm(String userNm); 
}
 