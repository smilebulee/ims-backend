package com.infogen.ims.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.infogen.ims.user.vo.Member;


@Mapper
public interface UserMapper {
    List<Member> userInfoSelect(String userNm);
    int insertEmp(Map<String, Object> member);
}

