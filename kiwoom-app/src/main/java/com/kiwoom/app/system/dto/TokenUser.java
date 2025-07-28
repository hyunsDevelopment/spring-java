package com.kiwoom.app.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenUser {

	@Schema(title = "토큰 타입('A', 'R')", example = "A")
	private String type;
	
	@Schema(title = "사용자 ID")
	private String id;
	
}
