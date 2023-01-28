package com.example.restdocssample.members.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Address {
    HOME("HOME", "집"),
    OFFICE("OFFICE", "회사");

    private final String code;
    private final String name;
}
