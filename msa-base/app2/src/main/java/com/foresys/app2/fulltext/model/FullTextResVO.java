package com.foresys.app2.fulltext.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FullTextResVO {

    private Object head;

    private Object body;

    private String rsltCd;

}
