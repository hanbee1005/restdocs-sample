package com.example.restdocssample.members.adapter.in;

import com.example.restdocssample.common.model.CommonResponse;
import com.example.restdocssample.members.service.MemberService;
import com.example.restdocssample.members.service.model.MemberResponse;
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
    public CommonResponse<MemberResponse> findOne(@PathVariable Long memberId) {
        return CommonResponse.ok(memberService.findOne(memberId));
    }
}
