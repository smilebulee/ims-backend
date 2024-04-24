package com.infogen.ims.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.infogen.ims.common.vo.DeptVo;

@Repository
public interface CommonDeptRepository extends JpaRepository<DeptVo, String>, JpaSpecificationExecutor<DeptVo> {
    public List<DeptVo> findBydeptUseYn(String useYn);
}
