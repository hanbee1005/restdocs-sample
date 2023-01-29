package com.example.restdocssample.codes.adapter.in.model;

import com.example.restdocssample.codes.service.model.CodeQuery;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeQueryResponse {
    private final Map<String, Map<String, String>> codeMap;

    public static CodeQueryResponse make(CodeQuery codes) {
        Map<String, Map<String, String>> codeMap = new HashMap<>();
        codes.getCodeMap().forEach((k, v) -> {
            Map<String, String> values = new HashMap<>();
            v.forEach(baseCode -> values.put(baseCode.getCode(), baseCode.getDesc()));
            codeMap.put(k, values);
        });

        return new CodeQueryResponse(codeMap);
    }
}
