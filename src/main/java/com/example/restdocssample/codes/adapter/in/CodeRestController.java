package com.example.restdocssample.codes.adapter.in;

import com.example.restdocssample.codes.adapter.in.model.CodeQueryResponse;
import com.example.restdocssample.codes.service.CodeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/codes")
@RequiredArgsConstructor
public class CodeRestController {
    private final CodeQueryService codeQueryService;

    @GetMapping
    public ResponseEntity<CodeQueryResponse> findAll() {
        return ResponseEntity.ok(CodeQueryResponse.make(codeQueryService.getCodeQuery()));
    }
}
