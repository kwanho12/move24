package com.move24.controller;

import com.move24.request.CheckIdRequest;
import com.move24.request.JoinRequest;
import com.move24.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/signup")
    public void join(@RequestPart(name = "request") @Validated JoinRequest request,
                     @RequestPart(name = "file") MultipartFile file) {
        memberService.signup(request, file);
    }

    @PostMapping("/api/signup/check-id")
    public void IDDuplicateCheck(@RequestBody CheckIdRequest request) {
        memberService.validateDuplicateId(request.getUserId());
    }
}
