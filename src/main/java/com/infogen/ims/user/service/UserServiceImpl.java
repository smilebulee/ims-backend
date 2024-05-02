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
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public List<Member> userInfoSelect(String userNm) throws Exception {
        return  userRepository.findByUserNm(userNm);
    }

    public List<Member> findAll() throws Exception {
        return  userRepository.findAll();
    }

    @Transactional
    public int insertEmp(Map<String, Object> member) throws Exception {
        return userMapper.insertEmp(member);
    }

    @Transactional
    public ResponseEntity<String> updateEmp(Member member) {
        try {
            Optional<Member> entity = this.userRepository.findByUserId(member.getUserId());

            if (entity.isPresent()) {
                Member t = entity.get();
                
                if (member.getUserNm() != null) {
                    t.setUserNm(member.getUserNm());
                }
                if (member.getDeptCd() != null) {
                    t.setDeptCd(member.getDeptCd());
                }
                if (member.getUserStatusCd() != null) {
                    t.setUserStatusCd(member.getUserStatusCd());
                }

                userRepository.save(t);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update employee: " + e.getMessage());
        }
    }


    @Transactional
    public int deleteByUserId(String userId){
        return userRepository.deleteByUserId(userId);
    }
}
