package com.zbf.mapper;

import com.zbf.core.page.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShiJuanMapper {
    public int addShiJuanInfo(Map<String, Object> map);

    public List<Map<String, Object>> getShijuanList(Page<Map<String, Object>> page);

    public int deleteShijuan(Map<String, Object> map);

    public int updateShijuan(Map<String,Object> map);
}
