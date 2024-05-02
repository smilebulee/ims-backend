package com.infogen.ims.common.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

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
    private String rmks;
    private String deptUseYn;
    @CreatedDate
    private LocalDate regDate;
    private String regEmpNo;
    @LastModifiedDate
    private LocalDate chgDate;
    private String chgEmpNo;
}
