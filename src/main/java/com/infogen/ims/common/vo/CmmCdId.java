package com.infogen.ims.common.vo;

import java.io.Serializable;

public class CmmCdId implements Serializable {
    private String cdGrp;
    private String cd;    

    public CmmCdId(){

    }

    public CmmCdId(String cdGrp, String cd){
        this.cdGrp = cdGrp;
        this.cd = cd;        
    }
    
}
