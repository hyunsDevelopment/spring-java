package com.kiwoom.app.sample.controller;

import com.kiwoom.app.fsb.dto.FsbApiDto;
import com.kiwoom.app.mois.dto.SearchJusoReqDto;
import com.kiwoom.app.mois.dto.SearchJusoResDto;
import com.kiwoom.app.sample.dto.SamplePagingDto;
import com.kiwoom.app.sample.service.SampleService;
import com.kiwoom.app.session.dto.CustomUserDetails;
import com.kiwoom.app.system.dto.PageReq;
import com.kiwoom.app.system.dto.PageRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public HashMap fsbApi(@RequestBody @Valid FsbApiDto params) throws Exception {
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
    @GetMapping("/select/{userId}")
    public Map<String, Object> select(
            @Parameter(
                    name = "userId",
                    description = "사용자 ID (번호)",
                    example = "12312355",
                    required = true
            ) @PathVariable("userId") String userId) {
        return sampleService.select(userId);
    }

    @Operation(summary = "mybatis - database select with paging request")
    @PostMapping("/mybatis/select/page")
    public PageRes<?> selectPage(@RequestBody @Valid PageReq<SamplePagingDto> params) {
        return sampleService.selectPage(params);
    }

    @Operation(summary = "jpa - database select with paging request")
    @PostMapping("/jpa/select/page")
    public PageRes<?> selectPageWithJpa(@RequestBody @Valid PageReq<SamplePagingDto> params) {
        return sampleService.selectPageWithJpa(params);
    }

    @Operation(summary = "로그인 시 내 정보 가져오기")
    @GetMapping("/me")
    public CustomUserDetails getMe() {
        return sampleService.getMe();
    }

    @Operation(summary = "inquiry address by mois request")
    @PostMapping("/addr/mois")
    public SearchJusoResDto addrMois(@RequestBody @Valid SearchJusoReqDto params) throws Exception {
        return sampleService.addrMois(params);
    }
}
