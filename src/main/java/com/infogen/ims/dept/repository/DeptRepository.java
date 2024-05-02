package com.infogen.ims.dept.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.infogen.ims.common.vo.DeptVo;

@Repository
public interface DeptRepository extends JpaRepository<DeptVo, String>, JpaSpecificationExecutor<DeptVo> {    

}
