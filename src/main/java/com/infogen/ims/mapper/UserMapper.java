package com.infogen.ims.mapper;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insertEmp(Map<String, Object> member);
}
