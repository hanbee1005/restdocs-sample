package com.example.restdocssample.members.constants;

import com.example.restdocssample.common.annotation.CodeClass;
import com.example.restdocssample.common.constants.codes.CommonCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@CodeClass
public enum Gender implements CommonCode {
    M("M", "남자"),
    F("F", "여자");

    private final String code;
    private final String name;

    @Override
    public String getDesc() {
        return getName();
    }
}
