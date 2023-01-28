package com.example.restdocssample.members.adapter.in;

import com.example.restdocssample.members.domain.Member;
import com.example.restdocssample.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Member> findOne(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.findOne(memberId));
    }
}
