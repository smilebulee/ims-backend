package com.infogen.ims.dept.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        return deptService.insertDeptSave(vo) == 1 ? "저장 되었습니다." : "저장에 실패했습니다.";
    }

    @PostMapping("/ims/dept/deptModify")
    public ResponseEntity<String> deptModify(@RequestBody DeptVo vo) throws Exception {
        System.out.println("VO : " + vo);
        return deptService.deptModify(vo);
    }
}
