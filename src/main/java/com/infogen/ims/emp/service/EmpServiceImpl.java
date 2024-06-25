package com.infogen.ims.emp.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.infogen.ims.emp.repository.EmpRepository;
import com.infogen.ims.emp.vo.EmpVo;
import com.infogen.ims.mapper.EmpMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService{
    
    @Autowired
    private EmpRepository empRepository;

    @Autowired
    private EmpMapper empMapper;

    // onload 시 직원 리스트 조회, 상세팝업 띄웠을 시 직원 정보 조회
    @Override
    public List<EmpVo> findAll() {
        return empMapper.findAll();
    }

    // 직원명으로 검색할 시 결과 조회
    @Override
    public List<EmpVo> findByUserNm(String userNm) {
        return empMapper.findByUserNm(userNm);
    }

    // 직원정보 삭제
    @Override
    public String deleteEmployee(String userId) {
        Optional<EmpVo> employeeOptional = empRepository.findByUserId(userId);
        if (employeeOptional.isPresent()) {
            empRepository.deleteById(userId);
            return "Employee with ID " + userId + " deleted successfully.";
        } else {
            return "Employee with ID " + userId + " not found.";
        }
    }

    // Id 중복확인
    @Override
    public boolean isUserIdUnique(String userId) {
        return empRepository.existsByUserId(userId) ? true : false;
    }

    // 이름 중복확인
    @Override
    public boolean isUserNmUnique(String userNm) {
        return empRepository.existsByUserNm(userNm) ? true : false;
    }

    // 직원 등록
    @Override
    public void insertEmp(EmpVo empVo) {
        empMapper.insertEmp(empVo);
    }

    // 직원 수정
    @Override
    public ResponseEntity<String> modifyEmp(EmpVo empVo) {
        try {
            Optional<EmpVo> entity = empRepository.findById(empVo.getUserId());
            
            if(entity.isPresent()) {
                EmpVo emp = entity.get();

                // 직원ID, 이메일
                if(empVo.getUserId() != null) {
                    //emp.setUserId(empVo.getUserId());
                    emp.setEmail(empVo.getUserId());;
                }

                // 직원 PW
                if(empVo.getPassword() != null) {
                    emp.setPassword(empVo.getPassword());
                }

                // 직원 이름
                if(empVo.getUserNm() != null) {
                    emp.setUserNm(empVo.getUserNm());
                }

                // 근무 시작 시각
                if(empVo.getJobStrtTm() != null) {
                    emp.setJobStrtTm(empVo.getJobStrtTm());
                }

                // 근무 종료 시각
                if(empVo.getJobEndTm() != null) {
                    emp.setJobEndTm(empVo.getJobEndTm());
                }

                // 권한 그룹 코드
                if(empVo.getAuthGrpCd() != null) {
                    emp.setAuthGrpCd(empVo.getAuthGrpCd());
                }

                // 부서코드
                if(empVo.getDeptCd() != null) {
                    String deptCd = empVo.getDeptCd(); // 부서코드
                    
                    emp.setDeptCd(deptCd);
                    emp.setEmpGm(empMapper.findEmpGm(deptCd)); // 사업부장
                    emp.setEmpPr(empMapper.findEmpPr(deptCd)); // 현장대리인
                }

                // 셀프승인
                if(empVo.getSelfPrYn() != null) {
                    emp.setSelfPrYn(empVo.getSelfPrYn());
                }

                // 재직/퇴직
                if(empVo.getUserStatusCd() != null) {
                    emp.setUserStatusCd(empVo.getUserStatusCd());
                }

                // 재직여부를 'N'으로 변경
                if ("1".equals(empVo.getUserStatusCd()) && empVo.getUserStatusCd() != null) {
                    emp.setUserStatusCd(empVo.getUserStatusCd());
                    emp.setRtrDate(LocalDate.now()); // 퇴사일자는 재직여부를 N으로 변경한 날짜로 저장
                }

                // 수정날짜, 직원은 수정 시점의 시간과 사용자 id 가지고 오기
                emp.setUpdDate(LocalDate.now()); // 수정날짜
                emp.setUpdUser("test1"); // 수정직원 > 일단 하드코딩

                empRepository.save(emp);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("success");

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Emp is not found");
            }
       } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Emp: " + e.getMessage());
        }
    }
     

}
