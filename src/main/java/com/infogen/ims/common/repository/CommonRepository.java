package com.infogen.ims.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.infogen.ims.common.vo.CmmCdId;
import com.infogen.ims.common.vo.CmmCdVo;

@Repository
public interface CommonRepository extends JpaRepository<CmmCdVo, CmmCdId>, JpaSpecificationExecutor<CmmCdVo> {
    public List<CmmCdVo> findByCdGrpOrderBySortOrdAsc(String cdGrp);
}
