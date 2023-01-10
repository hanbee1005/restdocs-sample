package com.example.restdocssample.controller;

import com.example.restdocssample.domain.Member;
import com.example.restdocssample.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public Member findOne(@PathVariable Long memberId) {
        return memberService.findOne(memberId);
    }
}
