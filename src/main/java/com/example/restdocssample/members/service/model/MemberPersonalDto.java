package com.example.restdocssample.members.service.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberPersonalDto {
    private String phoneNumber;
    private String email;
}
