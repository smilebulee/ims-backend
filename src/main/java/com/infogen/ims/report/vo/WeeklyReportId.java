package com.infogen.ims.report.vo;

import java.io.Serializable;


public class WeeklyReportId implements Serializable {
    private String reportDt;
    private String mailId;
    private int seq;

    public WeeklyReportId(){

    }

    public WeeklyReportId(String reportDt, String mailId, int seq){
        this.reportDt = reportDt;
        this.mailId = mailId;
        this.seq = seq;
    }
}
