package com.infogen.ims.common.service;

import java.util.List;
import java.util.Map;

import com.infogen.ims.common.vo.CmmCdVo;

public interface CommonService {
    public Map<String, Object> getCmmCd(String cdGrps) throws Exception;
}
