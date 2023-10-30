package com.infogen.ims.report.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.infogen.ims.report.vo.WeeklyReportParam;
import com.infogen.ims.report.vo.WeeklyReportVo;

public interface WeeklyReportService {
    public int weelyReportUpload(List<MultipartFile> files) throws Exception;
    public Page<WeeklyReportVo> weelyReportList(WeeklyReportParam params) throws Exception;
    public int weeklyReportSave(WeeklyReportVo vo) throws Exception;
    public int weeklyReportDelete(List<Integer> seq) throws Exception;
}
