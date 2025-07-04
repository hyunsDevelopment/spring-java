package com.foresys.app1.sample.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class KafkaTestDTO {

    @Schema(name = "topic", title = "토픽", example = "test")
    @NotEmpty(message = "토픽을 입력해주세요.")
    String topic;

    @Schema(name = "key", title = "키값")
    @NotEmpty(message = "키를 입력해주세요.")
    String key;

    @Schema(name = "message", title = "메시지")
    @NotEmpty(message = "메시지를 입력해주세요.")
    String message;

}
