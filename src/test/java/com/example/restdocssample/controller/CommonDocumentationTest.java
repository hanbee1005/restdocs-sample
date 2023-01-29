package com.example.restdocssample.controller;

import com.example.restdocssample.codes.adapter.in.CodeRestController;
import com.example.restdocssample.codes.service.CodeQueryService;
import com.example.restdocssample.codes.service.model.BaseCode;
import com.example.restdocssample.utils.CustomResponseFieldsSnippet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CodeRestController.class)
@AutoConfigureRestDocs
public class CommonDocumentationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CodeQueryService codeQueryService;

    private final CodeQueryService _codeQueryService = new CodeQueryService();

    @Test
    @DisplayName("enum 목록 조회")
    public void enums() throws Exception {
        // given
        String documentPath = "code/getCodes";
        given(codeQueryService.getCodeQuery()).willReturn(_codeQueryService.getCodeQuery());

        // when
        ResultActions resultActions = this.mockMvc.perform(get("/v1/codes").accept(MediaType.APPLICATION_JSON));

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
