package com.infogen.ims.report.service;

import java.io.InputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.net.URLEncoder;

import org.apache.commons.io.FileUtils;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.infogen.ims.report.repository.WeeklyReportFilesRepository;
import com.infogen.ims.report.repository.WeeklyReportRepository;
import com.infogen.ims.report.vo.WeeklyReportFilesVo;
import com.infogen.ims.report.vo.WeeklyReportParam;
import com.infogen.ims.report.vo.WeeklyReportVo;

@Service
public class WeeklyReportServiceImpl implements WeeklyReportService {
    @Autowired
    private WeeklyReportRepository wrRepository;

    @Autowired
    private WeeklyReportFilesRepository wrfRepository;

    @Transactional
    @Override
    public int weelyReportUpload(List<MultipartFile> files, String mailId) throws Exception{
        List<String> deletedList = new ArrayList<String>();
        InputStream is = null;
        for(MultipartFile file : files){
            try { 
                is = file.getInputStream();               
                XSSFWorkbook workbook = new XSSFWorkbook(is);
                XSSFSheet sheet = workbook.getSheetAt(0);
                int seq = wrRepository.findTopAllByOrderBySeqDesc().getSeq() + 1;

                for(Row row : sheet){                         
                    if(row.getRowNum() == 0) continue;   // 헤더 row skip
                    Cell firstCell = row.getCell(0);
                    
                    if(getStringValue(firstCell) == null || "".equals(getStringValue(firstCell))){
                        continue;
                    }

                    WeeklyReportVo reportVo = WeeklyReportVo.builder().build();                   
                    Iterator<Cell> cellIterator = row.cellIterator();                    
                    reportVo.setMailId(mailId);
                    reportVo.setUploadYn("Y");  
                    reportVo.setSeq(seq++);
                    
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch(cell.getColumnIndex()) {
                            case 0:
                                reportVo.setReportDt(getStringValue(cell));
                                break;
                            case 1:
                                reportVo.setEmpNm(getStringValue(cell));
                                break;
                            case 2:
                                reportVo.setUpDeptNm(getStringValue(cell));
                                break;
                            case 3:
                                reportVo.setDeptNm(getStringValue(cell));
                                break;
                            case 4:
                                reportVo.setWorkUnit(getStringValue(cell));
                                break;
                            case 5:
                                reportVo.setPrgsStus(getStringValue(cell));
                                break;
                            case 6:
                                reportVo.setWorkDivs(getStringValue(cell));
                                break;
                            case 7:
                                reportVo.setTitlNm(getStringValue(cell));
                                break;
                            case 8:
                                reportVo.setWorkPlan(getStringValue(cell));
                                break;
                            case 9:
                                reportVo.setWorkInfo(getStringValue(cell));
                                break;
                            case 10:
                                reportVo.setSchedStartDt(getStringValue(cell));
                                break;
                            case 11:
                                reportVo.setSchedEndDt(getStringValue(cell));
                                break;
                            case 12:
                                reportVo.setAdjustDt(getStringValue(cell));
                                break;
                            case 13:
                                reportVo.setAdjustRsn(getStringValue(cell));
                                break;
                            case 14:
                                reportVo.setFnshDt(getStringValue(cell));
                                break;
                            case 15:
                                reportVo.setPrgsHist(getStringValue(cell));
                                break;
                            case 16:
                                reportVo.setRemarks(getStringValue(cell));
                                break;
                        }
                        // switch(cell.getCellType()){
                        //     case NUMERIC:
                        //         System.out.print(cell.getColumnIndex() + " : " + cell.getNumericCellValue() + "\t");
                        //         break;
                        //     case STRING:
                        //         System.out.print(cell.getColumnIndex() + " : " + cell.getStringCellValue() + "\t");
                        //         break;
                        // }
                    }
                    System.out.println(reportVo.toString());
                    
                    if(!deletedList.contains(reportVo.getReportDt() + "_" + reportVo.getMailId())){
                        deletedList.add(reportVo.getReportDt() + "_" + reportVo.getMailId());
                        wrRepository.deleteByReportDtAndMailId(reportVo.getReportDt(), reportVo.getMailId());
                    }
                    
                    wrRepository.save(reportVo);
                }
                is.close();

            } catch (Exception e) {
                if(is != null) is.close();
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return 0;
    }

    @Override
    public Page<WeeklyReportVo> weelyReportList(WeeklyReportParam params) throws Exception {
        Sort sort = Sort.by(
            Sort.Order.desc("reportDt"),
            Sort.Order.asc("mailId"),
            Sort.Order.asc("seq")
        );

        PageRequest pReq = PageRequest.of(params.getPage() - 1, params.getPageSize(), sort);

        return wrRepository.findAll(getSpec(params), pReq);
    }

    private String getStringValue(Cell cell){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String retVal = "";

        if(cell == null || cell.getCellType() == CellType.BLANK) return retVal;

        switch(cell.getCellType()){
            case NUMERIC:                
                if(DateUtil.isCellDateFormatted(cell)){
                    retVal = sdf.format(cell.getDateCellValue());
                }else{
                    retVal = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case STRING:
                retVal = cell.getStringCellValue();
                break;
        }

        return retVal;
    }

    private Specification<WeeklyReportVo> getSpec(WeeklyReportParam params) throws Exception{
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();
            if(params.getUpDeptNm() != null && !"".equals(params.getUpDeptNm())){
                predicates.add(criteriaBuilder.equal(root.get("upDeptNm"), params.getUpDeptNm()));
            }

            if(params.getDeptNm() != null && !"".equals(params.getDeptNm())){
                predicates.add(criteriaBuilder.equal(root.get("deptNm"), params.getDeptNm()));
            }

            if(params.getEmpNm() != null && !"".equals(params.getEmpNm())){
                predicates.add(criteriaBuilder.equal(root.get("empNm"), params.getEmpNm()));
            }

            if(params.getPrgsStus() != null && !"".equals(params.getPrgsStus())){
                predicates.add(criteriaBuilder.equal(root.get("prgsStus"), params.getPrgsStus()));
            }

            if(params.getWorkDivs() != null && !"".equals(params.getWorkDivs())){
                predicates.add(criteriaBuilder.equal(root.get("workDivs"), params.getWorkDivs()));
            }

            if(params.getStartDt() != null && !"".equals(params.getStartDt()) && params.getEndDt() != null && !"".equals(params.getEndDt())){
                predicates.add(criteriaBuilder.between(root.get("reportDt"), params.getStartDt(), params.getEndDt()));
            }

            if(params.getAuthCd().equals("USER")){
                predicates.add(criteriaBuilder.equal(root.get("mailId"), params.getEmail()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
           
    }

    @Transactional
    @Override
    public int weeklyReportSave(WeeklyReportVo vo, MultipartFile file) throws Exception {
        int reportSeq = vo.getSeq() == 0 ? wrRepository.findTopAllByOrderBySeqDesc().getSeq() + 1 : vo.getSeq();
        vo.setSeq(reportSeq);

        wrRepository.save(vo);
        
        if(file != null) {
            WeeklyReportFilesVo savedFile = wrfRepository.findByReportSeqAndReportDtAndMailId(vo.getSeq(), vo.getReportDt(), vo.getMailId());

            String path = "/var/lib/jenkins/workspace/IMS-BACKEND/src/main/files/";
            String fileName = UUID.randomUUID().toString() + "_" + vo.getReportDt() + "_" + file.getOriginalFilename();
            String filePath = path + fileName;

            if(savedFile != null) { // db에 저장된 데이터가 있는 경우 
                if(!savedFile.getOriginalFileName().equals(file.getOriginalFilename())) {   // 저장된 데이터의 파일이름과 저장할 파일 이름이 다를 경우
                    wrfRepository.deleteByReportSeqAndReportDtAndMailId(vo.getSeq(), vo.getReportDt(), vo.getMailId());

                    file.transferTo(new File(filePath));

                    wrfRepository.save(new WeeklyReportFilesVo(0, vo.getReportDt(), reportSeq, vo.getMailId(), file.getOriginalFilename(), fileName, path, file.getSize()));
                }
            }
            else {
                file.transferTo(new File(filePath));

                wrfRepository.save(new WeeklyReportFilesVo(0, vo.getReportDt(), reportSeq, vo.getMailId(), file.getOriginalFilename(), fileName, path, file.getSize()));
            }
        }

        return 0;
    }

    @Transactional
    @Override
    public int weeklyReportDelete(Map<String, List<WeeklyReportVo>> data) throws Exception {
        for(WeeklyReportVo vo : data.get("row")) {
            wrRepository.deleteByMailIdAndSeq(vo.getMailId(), vo.getSeq());

            WeeklyReportFilesVo fileInfo = wrfRepository.findByReportSeqAndReportDtAndMailId(vo.getSeq(), vo.getReportDt(), vo.getMailId());
            if(fileInfo != null) {
                File file = new File(fileInfo.getStoredFilePath() + fileInfo.getStoredFileName());

                if(file.exists()) {
                    file.delete();
                }

                wrfRepository.deleteByReportSeqAndReportDtAndMailId(vo.getSeq(), vo.getReportDt(), vo.getMailId());
            }
        }


        return 0;
    }

    @Override
    public List<WeeklyReportVo> weeklyReportExcelList(WeeklyReportParam params) throws Exception {
        return wrRepository.findAll(getSpec(params));
    }

    @Override
    public WeeklyReportFilesVo weeklyReportAttachList(int seq, String reportDt, String mailId) throws Exception {
        return wrfRepository.findByReportSeqAndReportDtAndMailId(seq, reportDt, mailId);
    }

    @Transactional
    @Override
    public int weeklyReportDeleteAttach(int seq, String reportDt, String mailId) throws Exception {

        WeeklyReportFilesVo fileInfo = wrfRepository.findByReportSeqAndReportDtAndMailId(seq, reportDt, mailId);

        File file = new File(fileInfo.getStoredFilePath() + fileInfo.getStoredFileName());

        if(file.exists()) {
            file.delete();
        }
        
        wrfRepository.deleteByReportSeqAndReportDtAndMailId(seq, reportDt,mailId);
        
        return 0;
    }

    // @Override
    // public WeeklyReportFilesVo weeklyReportFilesInfo(int idx) throws Exception {
    //     return wrfRepository.findByIdx(idx);
    // }

    @Override
    public void weeklyReportDownloadFile(int seq, String reportDt, String mailId, HttpServletResponse response) throws Exception {
        // Map<String, Object> fileInfo;

        WeeklyReportFilesVo info = wrfRepository.findByReportSeqAndReportDtAndMailId(seq, reportDt, mailId);

        String storedFullPath = info.getStoredFilePath() + info.getStoredFileName();
        String originalFileName = info.getOriginalFileName();

        byte fileByte[] = FileUtils.readFileToByteArray(new File(storedFullPath));

        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);

        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(originalFileName, "UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
