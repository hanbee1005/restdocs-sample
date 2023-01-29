package com.example.restdocssample.codes.service.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class CodeQuery {
    private final Map<String, Set<BaseCode>> codeMap;
}
