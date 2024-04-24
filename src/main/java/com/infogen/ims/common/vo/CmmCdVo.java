package com.infogen.ims.common.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity(name="tbCmmCd")
@IdClass(CmmCdId.class)
@AllArgsConstructor
@NoArgsConstructor
public class CmmCdVo {
    @Id
    private String cd;
    @Id
    private String cdGrp;
    
    private String cdNm;    
    private int sortOrd;
}
