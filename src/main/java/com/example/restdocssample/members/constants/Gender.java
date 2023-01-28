package com.example.restdocssample.members.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    M("M", "남자"),
    F("F", "여자");

    private final String code;
    private final String name;
}
