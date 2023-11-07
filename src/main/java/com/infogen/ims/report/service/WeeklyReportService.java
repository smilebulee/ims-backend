package com.infogen.ims.report.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.infogen.ims.report.vo.WeeklyReportFilesVo;
import com.infogen.ims.report.vo.WeeklyReportParam;
import com.infogen.ims.report.vo.WeeklyReportVo;

public interface WeeklyReportService {
    public int weelyReportUpload(List<MultipartFile> files, String mailIdString) throws Exception;
    public Page<WeeklyReportVo> weelyReportList(WeeklyReportParam params) throws Exception;
    public int weeklyReportSave(WeeklyReportVo vo) throws Exception;
    public int weeklyReportDelete(Map<String, List<WeeklyReportVo>> data) throws Exception;
    public List<WeeklyReportVo> weeklyReportExcelList(WeeklyReportParam params) throws Exception;
    public WeeklyReportFilesVo weeklyReportFilesInfo(int idx) throws Exception;
}
