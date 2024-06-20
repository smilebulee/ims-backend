package com.infogen.ims.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.infogen.ims.emp.vo.EmpVo;


@Mapper
public interface EmpMapper {

    // onload 시 직원 리스트 조회, 상세팝업 띄웠을 시 직원 정보 조회
    List<EmpVo> findAll(); 

    // 직원명으로 검색할 시 결과 조회
    public List<EmpVo> findByUserNm(String userNm);

    // 직원 등록
    void insertEmp(EmpVo empVo);
}
