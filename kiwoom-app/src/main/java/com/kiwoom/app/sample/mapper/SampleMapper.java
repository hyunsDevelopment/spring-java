package com.kiwoom.app.sample.mapper;

import com.kiwoom.app.system.config.datasource.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SampleMapper {

    Map<String, Object> getMember(String userId);

    List<Map<String, Object>> getMemberList(String userStcd);
}
