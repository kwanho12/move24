package com.move24.controller;

import com.move24.request.JoinRequest;
import com.move24.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
}
