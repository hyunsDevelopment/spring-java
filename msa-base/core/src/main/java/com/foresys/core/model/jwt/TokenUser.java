package com.foresys.core.model.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TokenUser {

	@Schema(title = "토큰 타입('A', 'R')", example = "A")
	private String type;
	
	@Schema(title = "사용자 ID")
	private String id;
	
}
