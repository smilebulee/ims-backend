package com.infogen.ims.emp.repository;

import com.infogen.ims.emp.vo.EmpVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmpRepository extends JpaRepository<EmpVo, String>{

    Optional<EmpVo> findByUserId(String userId);

    Optional<EmpVo> findByUserNm(String userNm);


    // Id 중복확인
    boolean existsByUserId(String userId);

    // 이름 중복확인
    boolean existsByUserNm(String userNm);
}
