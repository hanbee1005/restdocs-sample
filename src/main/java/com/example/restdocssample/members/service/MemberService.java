package com.example.restdocssample.members.service;

import com.example.restdocssample.members.constants.Address;
import com.example.restdocssample.members.domain.Member;
import com.example.restdocssample.members.service.model.AddressDto;
import com.example.restdocssample.members.service.model.MemberPersonalDto;
import com.example.restdocssample.members.service.model.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.restdocssample.members.constants.Gender.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    public List<Member> findAll() {
        return makeDummyMember();
    }

    public MemberResponse findOne(Long memberId) {
        return MemberResponse.builder()
                .id(memberId)
                .name("memberA")
                .age(10)
                .gender(M)
                .birthdate("1995-10-05")
                .hobby(List.of("photo", "bike"))
                .address(List.of(
                        AddressDto.builder().type(Address.HOME).sido("서울").sigungu("강서구").road("화곡로").build(),
                        AddressDto.builder().type(Address.OFFICE).sido("서울").sigungu("강남구").road("태해란로").build()))
                .personal(MemberPersonalDto.builder().phoneNumber("01012345678").email("hello@gmail.com").build())
                .build();
    }

    private List<Member> makeDummyMember() {
        Member memberA = Member.builder()
                .id(1L)
                .name("memberA")
                .age(10)
                .gender(M)
                .build();

        Member memberB = Member.builder()
                .id(2L)
                .name("memberAB")
                .age(20)
                .gender(F)
                .build();

        Member memberC = Member.builder()
                .id(3L)
                .name("memberC")
                .age(12)
                .gender(M)
                .build();

        return List.of(memberA, memberB, memberC);
    }
}
