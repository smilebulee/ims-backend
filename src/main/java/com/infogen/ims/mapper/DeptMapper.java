package com.infogen.ims.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.infogen.ims.common.vo.DeptVo;

@Mapper
public interface DeptMapper {

    int insertDeptSave(DeptVo vo);

    List<DeptVo> deptSelect(String selectByDeptNm);

    List<DeptVo> findAll();
    
}
