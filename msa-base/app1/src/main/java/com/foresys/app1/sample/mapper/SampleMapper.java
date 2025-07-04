package com.foresys.app1.sample.mapper;

import com.foresys.app1.system.db.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SampleMapper {

    List<Map<String, Object>> getMemberList();

}
