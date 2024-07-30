package com.move24.controller;

import com.move24.request.JoinRequest;
import com.move24.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public void join(@RequestPart @Validated JoinRequest request,
                     @RequestPart MultipartFile file) {

        memberService.join(request, file);
    }

}
