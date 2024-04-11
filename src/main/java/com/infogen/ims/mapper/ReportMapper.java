package com.infogen.ims.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportMapper {
    List<Map<String, Object>> findAll();
}
