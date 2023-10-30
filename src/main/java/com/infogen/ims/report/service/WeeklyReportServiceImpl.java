package com.infogen.ims.report.service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.Iterator;

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

import com.infogen.ims.report.vo.WeeklyReportParam;
import com.infogen.ims.report.vo.WeeklyReportVo;
import com.infogen.ims.report.repository.WeeklyReportRepository;

@Service
public class WeeklyReportServiceImpl implements WeeklyReportService {
    @Autowired
    private WeeklyReportRepository wrRepository;

    @Transactional
    @Override
    public int weelyReportUpload(List<MultipartFile> files) throws Exception{
        List<String> deletedList = new ArrayList<String>();
        InputStream is = null;
        for(MultipartFile file : files){
            try { 
                is = file.getInputStream();               
                XSSFWorkbook workbook = new XSSFWorkbook(is);
                XSSFSheet sheet = workbook.getSheetAt(0);

                for(Row row : sheet){                         
                    if(row.getRowNum() == 0) continue;   // 헤더 row skip
                    Cell firstCell = row.getCell(0);
                    
                    if(getStringValue(firstCell) == null || "".equals(getStringValue(firstCell))){
                        continue;
                    }

                    WeeklyReportVo reportVo = WeeklyReportVo.builder().build();                   
                    Iterator<Cell> cellIterator = row.cellIterator();                    
                    
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        
                        switch(cell.getColumnIndex()){
                            case 0:
                                reportVo.setReportDt(getStringValue(cell));
                                break;
                            case 1:
                                reportVo.setUpDeptNm(getStringValue(cell));
                                break;
                            case 2:
                                reportVo.setDeptNm(getStringValue(cell));
                                break;
                            case 3:
                                reportVo.setMailId(getStringValue(cell));
                                break;
                            case 4:
                                reportVo.setEmpNm(getStringValue(cell));
                                break;
                            case 5:
                                reportVo.setSeq(row.getRowNum());
                                break;
                            case 6:
                                reportVo.setWorkUnit(getStringValue(cell));
                                break;
                            case 7:
                                reportVo.setPrgsStus(getStringValue(cell));
                                break;
                            case 8:
                                reportVo.setWorkDivs(getStringValue(cell));
                                break;
                            case 9:
                                reportVo.setTitlNm(getStringValue(cell));
                                break;
                            case 10:
                                reportVo.setWorkPart(getStringValue(cell));
                                break;
                            case 11:
                                reportVo.setWorkInfo(getStringValue(cell));
                                break;
                            case 12:
                                reportVo.setSchedStartDt(getStringValue(cell));
                                break;
                            case 13:
                                reportVo.setSchedEndDt(getStringValue(cell));
                                break;
                            case 14:
                                reportVo.setAdjustDt(getStringValue(cell));
                                break;
                            case 15:
                                reportVo.setAdjustRsn(getStringValue(cell));
                                break;
                            case 16:
                                reportVo.setFnshDt(getStringValue(cell));
                                break;
                            case 17:
                                reportVo.setPrgsHist(getStringValue(cell));
                                break;
                            case 18:
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

    @Override
    public int weeklyReportSave(WeeklyReportVo vo) throws Exception {
        vo.setReportDt(vo.getReportDt());
        vo.setMailId(vo.getMailId());
       
        if(vo.getSeq() == 0) {
            vo.setSeq(wrRepository.findTopAllByOrderBySeqDesc().getSeq() + 1);
        }

        wrRepository.save(vo);
        
        return 0;
    }

    @Transactional
    @Override
    public int weeklyReportDelete(List<Integer> seq) throws Exception {
        // 로그인 시 메일ID 하드코딩
        String mailId = "test@test.com";
        
        for(Integer s : seq) {
            wrRepository.deleteByMailIdAndSeq(mailId, s);
        }


        return 0;
    }

}
