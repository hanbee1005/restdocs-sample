package com.example.restdocssample.controller;

import com.example.restdocssample.members.domain.Member;
import com.example.restdocssample.members.adapter.in.MemberController;
import com.example.restdocssample.members.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.restdocssample.members.constants.Gender.M;
import static com.example.restdocssample.utils.ApiDocumentUtils.getDocumentRequest;
import static com.example.restdocssample.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@AutoConfigureRestDocs
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("회원 단건 조회")
    public void findOne() throws Exception {
        // given
        Long memberId = 1L;

        Member member = Member.builder()
                .id(memberId)
                .name("memberA")
                .age(10)
                .gender(M)
                .build();

        given(memberService.findOne(any())).willReturn(member);

        // when
        ResultActions resultActions = mockMvc.perform(get("/v1/members/{memberId}", memberId));

        System.out.println(resultActions.andReturn().getResponse().getContentAsString());

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("members/find-by-id",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("memberId").description("회원 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이")
                        )
                ))
                .andDo(print());
    }
}