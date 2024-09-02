package com.move24.domain.member.controller;

import com.move24.domain.member.dto.request.MemberJoinRequest;
import com.move24.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/members/new")
    public void join(@RequestPart(name = "request") @Validated MemberJoinRequest request,
                     @RequestPart(name = "file", required = false) MultipartFile file) {
        memberService.signup(request.toServiceRequest(), file);
    }

    @GetMapping("/api/members/new/id-duplicate-check")
    public void IdDuplicateCheck(String userId) {
        memberService.validateDuplicateId(userId);
    }
}
