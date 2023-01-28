package com.example.restdocssample.members.constants;

import com.example.restdocssample.common.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender implements EnumType {
    M("M", "남자"),
    F("F", "여자");

    private final String code;
    private final String name;

    @Override
    public String getDesc() {
        return getName();
    }
}
