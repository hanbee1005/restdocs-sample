package com.example.restdocssample.api.controller;

import com.example.restdocssample.common.model.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/common")
public class CommonController {
    @GetMapping
    public CommonResponse common() {
        return CommonResponse.ok();
    }
}
