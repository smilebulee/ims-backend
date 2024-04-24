package com.infogen.ims.common.vo;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity(name="tbDept")
@AllArgsConstructor
@NoArgsConstructor
public class DeptVo {
    @Id
    private String deptCd;
    
    private String deptName;    
    private String empPr;    
    private String empGm;
    private String deptUseYn;
}
