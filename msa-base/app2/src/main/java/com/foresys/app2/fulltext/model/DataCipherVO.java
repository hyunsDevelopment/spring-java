package com.foresys.app2.fulltext.model;

import com.foresys.app2.fulltext.handler.DataCipher;
import com.foresys.app2.fulltext.type.DataCipherRange;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataCipherVO {

    @NotNull
    private DataCipher dataCipher;

    @NotNull
    private DataCipherRange dataCipherRange;

}
