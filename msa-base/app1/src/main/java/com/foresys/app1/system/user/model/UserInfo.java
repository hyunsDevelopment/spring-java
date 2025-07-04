package com.foresys.app1.system.user.model;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserInfo {

    @Schema(description = "access token")
    @Hidden
    private String aToken;

    @Schema(description = "refresh token")
    @Hidden
    private String rToken;

    @Schema(description = "사용자 ID")
    @Hidden
    private String userId;

}
