package com.infogen.ims.emp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.infogen.ims.common.vo.DeptVo;
import com.infogen.ims.emp.repository.EmpRepository;
import com.infogen.ims.emp.service.EmpService;
import com.infogen.ims.emp.vo.EmpVo;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/ims/emp")
public class EmpController {
    
    @Autowired
    private EmpService empService;

    @Autowired
    private EmpRepository empRepository;

    // onload 시 직원 리스트 조회, 상세팝업 띄웠을 시 직원 정보 조회
    @GetMapping("/findAll")
    public List<EmpVo> findAll(){
        return empService.findAll();
    }

    // 직원명으로 검색할 시 결과 조회
    @GetMapping("/{userNm}")
    public List<EmpVo> findByUserNm(@PathVariable(name = "userNm") String userNm) throws Exception{
        return empService.findByUserNm(userNm);
    }

    // 직원 정보 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String userId) throws Exception {
        String result = empService.deleteEmployee(userId);
        if (result.startsWith("Employee with ID")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    // Id 중복확인
    @GetMapping("/check-userId/{userId}")
    public boolean isUserIdUnique(@PathVariable String userId) throws Exception {
        return empService.isUserIdUnique(userId);
    }

    // 이름 중복확인
    @GetMapping("/check-userNm/{userNm}")
    public boolean isUserNmUnique(@PathVariable String userNm) throws Exception {
        return empService.isUserNmUnique(userNm);
    }

    // 직원 등록
    @PostMapping("/insertEmp")
    public ResponseEntity<String> insertEmp(@RequestBody EmpVo empVo) {
        empService.insertEmp(empVo);
        return ResponseEntity.ok("success");
    }

    // 직원 정보 수정
    @PostMapping("/modifyEmp")
    public ResponseEntity<String> modifyEmp(@RequestBody EmpVo empVo) throws Exception {
        return empService.modifyEmp(empVo);
    }
    
}
