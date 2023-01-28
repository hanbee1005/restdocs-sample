package com.example.restdocssample.members.service.model;

import com.example.restdocssample.members.constants.Gender;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberResponse {
    private Long id;
    private String name;
    private Gender gender;
    private Integer age;
    private String birthdate;
    private List<String> hobby;
    private List<AddressDto> address;
    private MemberPersonalDto personal;
}
