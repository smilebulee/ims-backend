package com.infogen.ims.dept.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infogen.ims.common.vo.DeptVo;
import com.infogen.ims.dept.service.DeptService;

@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping("/ims/dept/findAll")
    public List<DeptVo> findAll(){
        return deptService.findAll();
    }

    @GetMapping("/ims/dept/deptSelect")
    public List<DeptVo> deptSelect(@RequestParam String selectByDeptNm) throws Exception {
        return deptService.deptSelect(selectByDeptNm);
    } 

    @PostMapping("/ims/dept/save")
    public String insertDeptSave(@RequestBody DeptVo vo) throws Exception {
        // data set
        vo.setRegDate(LocalDate.now());
        vo.setChgDate(LocalDate.now());
        vo.setRegEmpNo("test");
        vo.setChgEmpNo("test");
        vo.setRmks(LocalDate.now() + "신규등록");
        return deptService.insertDeptSave(vo) == 1 ? "저장 되었습니다." : "저장에 실패했습니다.";
    }
}
