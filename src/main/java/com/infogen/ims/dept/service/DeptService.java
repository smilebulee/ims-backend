package com.infogen.ims.dept.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.infogen.ims.common.vo.DeptVo;


public interface DeptService {

    public List<DeptVo> findAll();
    public int insertDeptSave(DeptVo vo) throws Exception;
    public List<DeptVo> deptSelect(String selectByDeptNm);
    public ResponseEntity<String> deptModify(DeptVo vo);
    
}
