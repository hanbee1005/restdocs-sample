package com.example.restdocssample.members.service.model;

import com.example.restdocssample.members.constants.Address;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressDto {
    private Address type;
    private String sido;
    private String sigungu;
    private String road;
}
