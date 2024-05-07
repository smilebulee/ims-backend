package com.infogen.ims.dept.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.infogen.ims.common.vo.DeptVo;

@Repository
public interface DeptRepository extends JpaRepository<DeptVo, String>, JpaSpecificationExecutor<DeptVo> {

    Optional<DeptVo> findByDeptCd(String deptCd);    

}
