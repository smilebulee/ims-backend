package com.infogen.ims.report.vo;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity(name="tbWeeklyReportFiles")
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyReportFilesVo {   

    @Id
    private int idx;

    private String reportDt;
    private int reportSeq;
    private String mailId;
    private String originalFileName;
    private String storedFileName;
    private String storedFilePath;
    private Long fileSize;
}