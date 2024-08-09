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

    @PostMapping("/api/join")
    public void join(@RequestPart(name = "request") @Validated JoinRequest request,
                     @RequestPart(name = "file") MultipartFile file) {
        memberService.join(request, file);
    }

    @PostMapping("/api/check-id")
    public boolean idCheck(@RequestBody CheckIdRequest request) {
        String memberId = request.getMemberId();
        return memberService.isExist(memberId);
    }
}
