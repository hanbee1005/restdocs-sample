package com.example.restdocssample.controller;

import com.example.restdocssample.api.controller.CommonController;
import com.example.restdocssample.codes.adapter.in.CodeRestController;
import com.example.restdocssample.codes.service.CodeQueryService;
import com.example.restdocssample.codes.service.model.BaseCode;
import com.example.restdocssample.common.ApiDocumentationTest;
import com.example.restdocssample.members.adapter.in.MemberController;
import com.example.restdocssample.utils.CustomResponseFieldsSnippet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CommonController.class, CodeRestController.class})
public class CommonDocumentationTest extends ApiDocumentationTest {
    @MockBean
    private CodeQueryService codeQueryService;

    private final CodeQueryService _codeQueryService = new CodeQueryService();

    @Test
    @DisplayName("공통 응답 조회")
    public void common() throws Exception {
        // given
        String url = API_V1 + "/common";
        String documentPath = "common";

        // when
        ResultActions resultActions = mockMvc.perform(get(url));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document(documentPath,
                        customResponseFields("common-response", null,
                                attributes(key("title").value("공통 응답")),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지"),
                                subsectionWithPath("data").description("데이터"))))
                .andDo(print());
    }

    @Test
    @DisplayName("enum 목록 조회")
    public void enums() throws Exception {
        // given
        String url = API_V1 + "/codes";
        String documentPath = "code/getCodes";
        given(codeQueryService.getCodeQuery()).willReturn(_codeQueryService.getCodeQuery());

        // when
        ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andDo(document(documentPath,
                        _codeQueryService.getCodeQuery().getCodeMap().keySet().stream().map((k) -> customResponseFields("enum-response", beneathPath("codeMap." + k).withSubsectionId(k), // (2)
                                attributes(key("title").value(k)),
                                enumConvertFieldDescriptor(_codeQueryService.getCodeQuery().getCodeMap().get(k).toArray(BaseCode[]::new))
                        )).toArray(Snippet[]::new)
                ));
    }

    private FieldDescriptor[] enumConvertFieldDescriptor(BaseCode[] enumTypes) {
        return Arrays.stream(enumTypes)
                .map(enumType -> fieldWithPath(enumType.getCode()).description(enumType.getDesc()))
                .toArray(FieldDescriptor[]::new);
    }

    public static CustomResponseFieldsSnippet customResponseFields(String type,
                                                                   PayloadSubsectionExtractor<?> subsectionExtractor,
                                                                   Map<String, Object> attributes, FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(type, subsectionExtractor, Arrays.asList(descriptors), attributes
                , true);
    }
}
