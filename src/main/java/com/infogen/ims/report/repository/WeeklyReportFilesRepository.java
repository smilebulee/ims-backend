package com.infogen.ims.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.infogen.ims.report.vo.WeeklyReportFilesVo;

@Repository
public interface WeeklyReportFilesRepository extends JpaRepository<WeeklyReportFilesVo, Integer> {
    public WeeklyReportFilesVo findByIdx(int idx);
}
