package com.example.restdocssample.members.constants;

import com.example.restdocssample.common.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Address implements EnumType {
    HOME("HOME", "집"),
    OFFICE("OFFICE", "회사");

    private final String code;
    private final String name;

    @Override
    public String getDesc() {
        return getName();
    }
}
