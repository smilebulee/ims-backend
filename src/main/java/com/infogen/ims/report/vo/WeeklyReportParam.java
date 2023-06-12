package com.infogen.ims.report.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WeeklyReportParam {    
    
    private String empNm;
    private String upDeptNm;
    private String deptNm;
    
    private String prgsStus;
    private String workDivs;

    private String startDt;
    private String endDt;
    
    private Integer page;
    private Integer pageSize = 10;

    private String authCd;
    private String email;
}