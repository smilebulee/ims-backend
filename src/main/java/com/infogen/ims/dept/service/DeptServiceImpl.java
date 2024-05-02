package com.infogen.ims.dept.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infogen.ims.common.vo.DeptVo;
import com.infogen.ims.dept.repository.DeptRepository;
import com.infogen.ims.mapper.DeptMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DeptServiceImpl implements DeptService{
    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private DeptMapper deptMapper;

    @Transactional(readOnly = true)
    @Override
    public List<DeptVo> findAll() {
        return deptMapper.findAll();
    }

    @Override
    public List<DeptVo> deptSelect(String selectByDeptNm){
        return deptMapper.deptSelect(selectByDeptNm);
    }

    @Override
    public int insertDeptSave(DeptVo vo) throws Exception {
        System.out.println("implVo : "+ vo);
        int sabeDept = deptMapper.insertDeptSave(vo);
        System.out.println(sabeDept);
        return sabeDept;
    }
}
