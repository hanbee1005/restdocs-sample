package com.example.restdocssample.controller;

import com.example.restdocssample.common.ApiDocumentationTest;
import com.example.restdocssample.members.constants.Address;
import com.example.restdocssample.members.adapter.in.MemberController;
import com.example.restdocssample.members.service.MemberService;
import com.example.restdocssample.members.service.model.AddressDto;
import com.example.restdocssample.members.service.model.MemberPersonalDto;
import com.example.restdocssample.members.service.model.MemberResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.example.restdocssample.members.constants.Gender.M;
import static com.example.restdocssample.utils.ApiDocumentUtils.getDocumentRequest;
import static com.example.restdocssample.utils.ApiDocumentUtils.getDocumentResponse;
import static com.example.restdocssample.utils.DocumentFormatGenerator.getDateFormat;
import static com.example.restdocssample.utils.DocumentLinkGenerator.DocUrl.ADDRESS;
import static com.example.restdocssample.utils.DocumentLinkGenerator.DocUrl.GENDER;
import static com.example.restdocssample.utils.DocumentLinkGenerator.generateLinkCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest extends ApiDocumentationTest {
    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("?????? ?????? ??????")
    public void findOne() throws Exception {
        // given
        String url = API_V1 + "/members/{memberId}";
        Long memberId = 1L;

        MemberResponse member = MemberResponse.builder()
                .id(memberId)
                .name("memberA")
                .age(10)
                .gender(M)
                .birthdate("1995-10-05")
                .hobby(List.of("photo", "bike"))
                .address(List.of(
                        AddressDto.builder().type(Address.HOME).sido("??????").sigungu("?????????").road("?????????").build(),
                        AddressDto.builder().type(Address.OFFICE).sido("??????").sigungu("?????????").road("????????????").build()))
                .personal(MemberPersonalDto.builder().phoneNumber("01012345678").email("hello@gmail.com").build())
                .build();

        given(memberService.findOne(any())).willReturn(member);

        // when
        ResultActions resultActions = mockMvc.perform(get(url, memberId));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("members/find-by-id",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("memberId").description("?????? ?????????")
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description(generateLinkCode(GENDER)),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("??????").optional(),
                                fieldWithPath("birthdate").type(JsonFieldType.STRING).description("????????????").attributes(getDateFormat()),
                                fieldWithPath("hobby").type(JsonFieldType.ARRAY).description("??????"),
                                fieldWithPath("address").type(JsonFieldType.ARRAY).description(generateLinkCode(ADDRESS)),
                                fieldWithPath("address[].type").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("address[].sido").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("address[].sigungu").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("address[].road").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("personal").type(JsonFieldType.OBJECT).description("????????????").optional(),
                                fieldWithPath("personal.phoneNumber").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("personal.email").type(JsonFieldType.STRING).description("?????????")
                        )
                ))
                .andDo(print());
    }
}