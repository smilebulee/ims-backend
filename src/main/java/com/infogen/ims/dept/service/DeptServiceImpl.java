package com.infogen.ims.dept.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infogen.ims.common.vo.DeptVo;
import com.infogen.ims.dept.repository.DeptRepository;
import com.infogen.ims.mapper.DeptMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DeptServiceImpl implements DeptService{
    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private DeptMapper deptMapper;

    @Transactional(readOnly = true)
    @Override
    public List<DeptVo> findAll() {
        return deptMapper.findAll();
    }

    @Override
    public List<DeptVo> deptSelect(String selectByDeptNm){
        return deptMapper.deptSelect(selectByDeptNm);
    }

    @Override
    public int insertDeptSave(DeptVo vo) throws Exception {
        int sabeDept = deptMapper.insertDeptSave(vo);
        return sabeDept;
    }

    @Override
    @Transactional
    public ResponseEntity<String> deptModify(DeptVo vo) {
        try {
            Optional<DeptVo> entity = this.deptRepository.findByDeptCd(vo.getDeptCd());
            if(entity.isPresent()) {
                DeptVo t = entity.get();

                if(vo.getDeptName() != null) {
                    t.setDeptName(vo.getDeptName());
                }
                if(vo.getEmpGm() != null) {
                    t.setEmpGm(vo.getEmpGm());
                }
                if(vo.getEmpPr() != null) {
                    t.setEmpPr(vo.getEmpPr());
                }
                if(vo.getDeptUseYn() != null) {
                    t.setDeptUseYn(vo.getDeptUseYn());
                }
                t.setChgDate(LocalDate.now());
                t.setChgEmpNo("test");
                
                deptRepository.save(t);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dept not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update dept: " + e.getMessage());
        }
    }
}
