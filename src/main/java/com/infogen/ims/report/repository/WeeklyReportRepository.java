package com.infogen.ims.report.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.infogen.ims.report.vo.WeeklyReportId;
import com.infogen.ims.report.vo.WeeklyReportVo;

@Repository
public interface WeeklyReportRepository extends JpaRepository<WeeklyReportVo, WeeklyReportId>, JpaSpecificationExecutor<WeeklyReportVo> {
    public int deleteByReportDtAndMailId(String reportDt, String mailId);
    public Page<WeeklyReportVo> findAll(Specification<WeeklyReportVo> spec, Pageable Pageable);
    public int deleteByMailIdAndSeq(String mailId, int seq);
    public WeeklyReportVo findTopAllByOrderBySeqDesc();
}
