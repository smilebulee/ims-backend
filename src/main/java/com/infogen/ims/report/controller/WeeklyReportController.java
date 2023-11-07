package com.infogen.ims.report.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URLEncoder;

import org.apache.commons.io.FileUtils;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.infogen.ims.report.service.WeeklyReportService;
import com.infogen.ims.report.vo.WeeklyReportFilesVo;
import com.infogen.ims.report.vo.WeeklyReportParam;
import com.infogen.ims.report.vo.WeeklyReportVo;

@RestController
public class WeeklyReportController {
    @Autowired
    private WeeklyReportService wReportService;

    @PostMapping("/ims/report/weekly/upload")
    public ResponseEntity<?> weelyReportUpload(List<MultipartFile> files, String mailId) throws Exception{
        wReportService.weelyReportUpload(files, mailId);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return ResponseEntity.ok(resultMap);
    }

    @GetMapping("/ims/report/weekly/list")
    public ResponseEntity<?> weelyReportList(@RequestParam(required=false) String upDeptNm 
                                            , @RequestParam(required=false) String deptNm
                                            , @RequestParam(required=false) String empNm
                                            , @RequestParam(required=false) String prgsStus  
                                            , @RequestParam(required=false) String workDivs
                                            , @RequestParam(required=false) String startDt
                                            , @RequestParam(required=false) String endDt 
                                            , @RequestParam(required=false, defaultValue="1") int page 
                                            , @RequestParam(required=false, defaultValue="10") int pageSize
                                            , @RequestParam(required=true) String authCd
                                            , @RequestParam(required=true) String email) throws Exception{
        WeeklyReportParam param = WeeklyReportParam.builder()
            .upDeptNm(upDeptNm)
            .deptNm(deptNm)
            .empNm(empNm)
            .prgsStus(prgsStus)
            .workDivs(workDivs)
            .startDt(startDt)
            .endDt(endDt)
            .page(page)
            .pageSize(pageSize)
            .authCd(authCd)
            .email(email).build();
        
        System.out.println(param.toString());        

        return ResponseEntity.ok(wReportService.weelyReportList(param));
    }

    @PostMapping("/ims/report/weekly/save")
    public String weeklyReportSave(@RequestBody WeeklyReportVo vo) throws Exception {
        return wReportService.weeklyReportSave(vo) == 0 ? "저장 되었습니다." : "저장에 실패했습니다.";
    }

    @PostMapping("/ims/report/weekly/delete")
    public String weeklyReportDelete(@RequestBody Map<String, List<WeeklyReportVo>> data) throws Exception {
        return wReportService.weeklyReportDelete(data) == 0 ? "삭제 되었습니다." : "삭제에 실패했습니다.";
    }

    @GetMapping("/ims/report/weekly/excelList")
    public List<WeeklyReportVo> weelyReportExcelList(@RequestParam(required=false) String upDeptNm 
                                            , @RequestParam(required=false) String deptNm
                                            , @RequestParam(required=false) String empNm
                                            , @RequestParam(required=false) String prgsStus  
                                            , @RequestParam(required=false) String workDivs
                                            , @RequestParam(required=false) String startDt
                                            , @RequestParam(required=false) String endDt 
                                            , @RequestParam(required=false, defaultValue="1") int page 
                                            , @RequestParam(required=false, defaultValue="10") int pageSize
                                            , @RequestParam(required=true) String authCd
                                            , @RequestParam(required=true) String email) throws Exception{
        WeeklyReportParam param = WeeklyReportParam.builder()
            .upDeptNm(upDeptNm)
            .deptNm(deptNm)
            .empNm(empNm)
            .prgsStus(prgsStus)
            .workDivs(workDivs)
            .startDt(startDt)
            .endDt(endDt)
            .page(page)
            .pageSize(pageSize)
            .authCd(authCd)
            .email(email).build();     

        return wReportService.weeklyReportExcelList(param);
    }

    @GetMapping("/ims/report/weekly/download")
    public void getMethodName(int idx, HttpServletResponse response) throws Exception {
        // Map<String, Object> fileInfo;

        WeeklyReportFilesVo info = wReportService.weeklyReportFilesInfo(idx);

        String storedFullPath = info.getStoredFilePath() + info.getStoredFileName();
        String originalFileName = info.getOriginalFileName();

        byte fileByte[] = FileUtils.readFileToByteArray(new File(storedFullPath));

        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);

        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName, "UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();

    }
    
}
