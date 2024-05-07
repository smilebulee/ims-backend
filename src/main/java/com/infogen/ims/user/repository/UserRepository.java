package com.infogen.ims.user.repository;

import org.springframework.stereotype.Repository;
import com.infogen.ims.user.vo.Member;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

@Repository
public interface UserRepository extends JpaRepository<Member, String>{
    Optional<Member> findByUserId(String userId); 
}
 