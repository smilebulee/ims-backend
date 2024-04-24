package com.infogen.ims.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogen.ims.common.repository.CommonDeptRepository;
import com.infogen.ims.common.repository.CommonRepository;
import com.infogen.ims.common.vo.CmmCdVo;

@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    CommonRepository commmonRepo;

    @Autowired
    CommonDeptRepository commmonDeptRepo;

    @Override
    public Map<String, Object> getCmmCd(String cdGrps) throws Exception{
        String[] cdGrpArr = cdGrps.split(",");
        Map<String, Object> ret = new HashMap<>();

        for(String cdGrp: cdGrpArr){
            if(cdGrp.equals("DEPT")) ret.put(cdGrp, commmonDeptRepo.findBydeptUseYnOrderByDeptNameAsc("Y"));
            else ret.put(cdGrp, commmonRepo.findByCdGrpOrderBySortOrdAsc(cdGrp));
        }
        
        return ret;
    }
}
