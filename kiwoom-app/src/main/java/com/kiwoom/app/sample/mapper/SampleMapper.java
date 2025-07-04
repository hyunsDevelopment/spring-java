package com.kiwoom.app.sample.mapper;

import com.kiwoom.app.system.datasource.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SampleMapper {

    List<Map<String, Object>> getMemberList();
}
