package com.example.restdocssample.service;

import com.example.restdocssample.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.restdocssample.constants.Gender.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    public List<Member> findAll() {
        return makeDummyMember();
    }

    public Member findOne(Long memberId) {
        return Member.builder()
                .name("memberA")
                .age(10)
                .gender(M)
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
