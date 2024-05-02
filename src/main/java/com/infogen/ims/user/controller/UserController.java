package com.infogen.ims.user.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infogen.ims.user.service.UserService;
import com.infogen.ims.user.vo.Member;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/ims/user/userSelect")
    public List<Member> userInfoSelect(@RequestParam String userNm) throws Exception {
        return userService.userInfoSelect(userNm);
    }

    @GetMapping("/ims/user/getEmpList")
    public List<Member> getEmpList() throws Exception {
        return userService.findAll();
    }

    @PostMapping("/ims/user/insertEmp")
    public int insertEmp(@RequestBody Map<String, Object> member) throws Exception {
        return userService.insertEmp(member);
    }

    @PostMapping("/ims/user/updateEmp")
    public ResponseEntity<String> updateEmp(@RequestBody Member member) throws Exception {
        return userService.updateEmp(member);
    }

    @PostMapping("/ims/user/deleteEmp")
    public ResponseEntity<String> deleteEmp(@RequestBody List<String> userIds) throws Exception {
        try {
            for (String userId : userIds) {
                userService.deleteByUserId(userId);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete employees: " + e.getMessage());
        }
    }
}
