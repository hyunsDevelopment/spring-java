package com.foresys.app2.sample.service;

import com.foresys.app2.nice.model.body.tr800.VrTR800;
import com.foresys.app2.nice.model.body.tr800.VsTR800;
import com.foresys.app2.nice.model.body.tr800.inner.DATA1;
import com.foresys.app2.nice.model.body.tr800.inner.DATA2;
import com.foresys.app2.nice.model.body.tr800.inner.DATA3;
import com.foresys.app2.nice.service.NiceService;
import com.foresys.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SampleService {

    private final NiceService niceService;

    public VsTR800 tr800() throws Exception {
        VrTR800 vrTR800 = new VrTR800();
        vrTR800.setSvcCode("20");
        vrTR800.setRegNo("7002062999961");
        vrTR800.setApplyReason("84");

        List<String> data1ApplyTypeList = List.of("24", "78");
        List<Map<String, Object>> data2List = List.of(
                Map.of("type", "03", "desc", StringUtil.msg_string("13564", 6, false)+StringUtil.msg_string("경기 성남시 분당구 탄천로 35", 100, false)+StringUtil.msg_string("501-301", 40, false))
                ,Map.of("type", "04", "desc", StringUtil.msg_string("123456", 6, false)+StringUtil.msg_string("", 100, false)+StringUtil.msg_string("", 40, false))
                ,Map.of("type", "13", "desc", "신용상사")
                ,Map.of("type", "14", "desc", "무")
                ,Map.of("type", "16", "desc", StringUtil.msg_string("08506", 6, false)+StringUtil.msg_string("서울 금천구 가산디지털1로 137", 100, false)+StringUtil.msg_string("1101", 40, false))
                ,Map.of("type", "29", "desc", "aaa@chol.com")
        );
        List<String> data3ApplyTypeList = List.of("ML0700_000", "ML1000_001", "ML1300_500", "PD3000_000", "RK0600_000", "IE0400_000");

        List<DATA1> data1 = new ArrayList<>();
        List<DATA2> data2 = new ArrayList<>();
        List<DATA3> data3 = new ArrayList<>();

        for(String applyType : data1ApplyTypeList) {
            DATA1 data = new DATA1();
            data.setType("S1");
            data.setApplyType(applyType);
            if(applyType.equals("24")) data.setApplyDesc("60000");
            data1.add(data);
        }
        for(Map<String, Object> temp : data2List) {
            DATA2 data = new DATA2();
            data.setType("S2");
            data.setApplyType(temp.get("type").toString());
            data.setApplyDesc(temp.get("desc").toString());
            data2.add(data);
        }
        for(String applyType : data3ApplyTypeList) {
            DATA3 data = new DATA3();
            data.setType("S5");
            data.setApplyType(applyType);
            data3.add(data);
        }

        vrTR800.setData1(data1);
        vrTR800.setApplyCnt1(data1.size());
        vrTR800.setData2(data2);
        vrTR800.setApplyCnt2(data2.size());
        vrTR800.setData3(data3);
        vrTR800.setScoreReqCnt(data3.size());

        return (VsTR800) niceService.get(vrTR800, true).getBody();
    }

    /**
     * kafka receive example (kafka listener)
     * @param key
     * @param groupId
     * @param message
     */
    @KafkaListener(topics = "test")
    private void kafkaListener1(@Header(KafkaHeaders.RECEIVED_KEY) String key, @Header(KafkaHeaders.GROUP_ID) String groupId, @Payload String message) {
        log.info("111 received kafka groupId {}", groupId);
        log.info("111 received kafka key {}", key);
        log.info("111 received kafka message: {}", message);
    }

    /**
     * kafka receive example (kafka listener)
     * @param key
     * @param groupId
     * @param message
     */
    @KafkaListener(topics = "test2")
    private void kafkaListener2(@Header(KafkaHeaders.RECEIVED_KEY) String key, @Header(KafkaHeaders.GROUP_ID) String groupId, @Payload Map<String, Object> message) {
        log.info("222 received kafka groupId {}", groupId);
        log.info("222 received kafka key {}", key);
        log.info("222 received kafka message: {}", message);
    }

}
