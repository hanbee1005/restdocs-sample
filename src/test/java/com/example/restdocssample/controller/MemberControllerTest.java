package com.example.restdocssample.controller;

import com.example.restdocssample.domain.Member;
import com.example.restdocssample.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.restdocssample.constants.Gender.M;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@WebMvcTest(MemberController.class)
@AutoConfigureRestDocs
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("회원 단건 조회")
    public void findOne() {
        // given
        Long memberId = 1L;

        Member member = Member.builder()
                .id(memberId)
                .name("memberA")
                .age(10)
                .gender(M)
                .build();

        given(memberService.findOne(memberId)).willReturn(member);

        // when


        // then
    }
}