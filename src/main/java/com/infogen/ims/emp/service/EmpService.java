package com.infogen.ims.emp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.infogen.ims.common.vo.DeptVo;
import com.infogen.ims.emp.vo.EmpVo;

@Service
public interface EmpService {
    
    // onload 시 직원 리스트 조회, 상세팝업 띄웠을 시 직원 정보 조회
    public List<EmpVo> findAll();

    // 직원명으로 검색할 시 결과 조회
    public List<EmpVo> findByUserNm(String userNm);

    // 직원정보 삭제
    public String deleteEmployee(String userId);

    // Id 중복확인
    public boolean isUserIdUnique(String userId);

    // 이름 중복확인
    public boolean isUserNmUnique(String userNm);

    // 직원 등록
    public void insertEmp(EmpVo empVo);

    // 직원 수정
    public ResponseEntity<String> modifyEmp(EmpVo empVo);
}