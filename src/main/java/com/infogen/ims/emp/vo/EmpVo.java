package com.infogen.ims.emp.vo;


import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity(name="tb_auth_user")
public class EmpVo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private String userId; // 사용자ID

    @Column(nullable = false)
    private String authGrpCd; // 권한그룹코드

    @Column(nullable = false)
    private String deptCd; // 부서코드

    @Column(nullable = false)
    private String userNm; // 사용자명

    @Column(nullable = false)
    private String password; // 사용자비밀번호

    @Column(nullable = false)
    private String userStatusCd; // 재직/퇴직여부

    private String email; // 사용자이메일

    @CreatedDate
    private LocalDate joinDate = LocalDate.now(); // 입사일자

    @CreatedDate
    private LocalDate rtrDate; // 퇴사일자

    private String empGm; // 사업부장
    private String empPr; // 현장대리인

    @CreatedDate
    private LocalDate regDate = LocalDate.now(); // 등록날짜
    private String regUser; // 등록직원

    @CreatedDate
    private LocalDate updDate = LocalDate.now() ; // 수정날짜
    private String updUser; // 수정직원

    private LocalTime jobStrtTm; // 정규업무시작시간
    private LocalTime jobEndTm; // 정규업무종료시간

    private String selfPrYn; // 셀프승인여부

    //private String deptName; // 부서명

}
