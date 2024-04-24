package com.infogen.ims.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infogen.ims.common.service.CommonService;

@RestController
public class CommonController{
    @Autowired
    CommonService commonService;

    @GetMapping("/ims/common/getCdList")
    public ResponseEntity<?> getCdList(@RequestParam(required=true) String cdGrps) throws Exception{
        

        return ResponseEntity.ok(commonService.getCmmCd(cdGrps));
    }
}