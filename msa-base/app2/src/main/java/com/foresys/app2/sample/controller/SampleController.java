package com.foresys.app2.sample.controller;

import com.foresys.app2.nice.model.body.tr800.VsTR800;
import com.foresys.app2.sample.service.SampleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    @PostMapping("/tr800")
    @Operation(summary = "tr800", description = """
			- tr800 template
			""")
    public VsTR800 tr800() throws Exception {
        return sampleService.tr800();
    }
}
