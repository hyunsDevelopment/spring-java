package com.kiwoom.app.sample.controller;

import com.github.pagehelper.PageInfo;
import com.kiwoom.app.fsb.model.FsbApiDTO;
import com.kiwoom.app.mois.model.SearchJusoReqDTO;
import com.kiwoom.app.mois.model.SearchJusoResDTO;
import com.kiwoom.app.sample.model.SamplePagingDTO;
import com.kiwoom.app.sample.service.SampleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sample")
public class SampleController {

    private final SampleService sampleService;

    @Operation(summary = "api test request")
    @PostMapping("/api")
    public String api(@RequestBody String params) {
        return params;
    }

    @Operation(summary = "Fsb Api execute")
    @PostMapping("/api/fsb")
    @SuppressWarnings("rawtypes")
    public HashMap fsbApi(@RequestBody @Valid FsbApiDTO params) throws Exception {
        return sampleService.fsbApi(params);
    }

    @Operation(summary = "Jasypt encoding request")
    @PostMapping("/jasypt/encoding")
    public String jasyptEncoding(@RequestBody String params) {
        return sampleService.jasyptEncoding(params);
    }

    @Operation(summary = "Jasypt decoding request")
    @PostMapping("/jasypt/decoding")
    public String jasyptDecoding(@RequestBody String params) {
        return sampleService.jasyptDecoding(params);
    }

    @Operation(summary = "database select request")
    @PostMapping("/select")
    public List<Map<String, Object>> select() {
        return sampleService.select();
    }

    @Operation(summary = "database select with paging request")
    @PostMapping("/select/page")
    public PageInfo<Map<String, Object>> selectPage(@RequestBody SamplePagingDTO params) {
        return sampleService.selectPage(params);
    }

    @Operation(summary = "inquiry address by mois request")
    @PostMapping("/addr/mois")
    public SearchJusoResDTO addrMois(@RequestBody @Valid SearchJusoReqDTO params) throws Exception {
        return sampleService.addrMois(params);
    }
}
