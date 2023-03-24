package com.infogen.ims.report.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity(name="tbWeeklyReport")
@IdClass(WeeklyReportId.class)
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyReportVo {   

    @Id
    private String reportDt;

    @Id
    private String mailId;

    @Id
    private int seq;

    private String empNm;
    private String upDeptNm;
    private String deptNm;
    private String workUnit;
    private String prgsStus;
    private String workDivs;
    private String titlNm;
    private String workPart;
    private String workInfo;
    private String schedStartDt;
    private String schedEndDt;
    private String adjustDt;
    private String adjustRsn;
    private String fnshDt;
    private String prgsHist;
    private String remarks;
}