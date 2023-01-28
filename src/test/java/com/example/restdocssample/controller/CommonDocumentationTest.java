package com.example.restdocssample.controller;

import com.example.restdocssample.api.controller.EnumViewController;
import com.example.restdocssample.common.EnumType;
import com.example.restdocssample.members.constants.Address;
import com.example.restdocssample.members.constants.Gender;
import com.example.restdocssample.utils.CustomResponseFieldsSnippet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnumViewController.class)
@AutoConfigureRestDocs
public class CommonDocumentationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("enum 목록 조회")
    public void enums() throws Exception {
        // given
        this.mockMvc.perform(get("/codes").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("common",
                        customResponseFields("enum-response", beneathPath("genders").withSubsectionId("genders"), // (2)
                                attributes(key("title").value("성별")),
                                enumConvertFieldDescriptor(Gender.values())
                        ),
                        customResponseFields("enum-response", beneathPath("address").withSubsectionId("address"), // (2)
                                attributes(key("title").value("주소")),
                                enumConvertFieldDescriptor(Address.values())
                        )
                ));
    }

    private FieldDescriptor[] enumConvertFieldDescriptor(EnumType[] enumTypes) {
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
