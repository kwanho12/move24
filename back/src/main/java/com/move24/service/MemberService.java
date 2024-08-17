package com.move24.service;

import com.move24.domain.*;
import com.move24.enums.MemberStatus;
import com.move24.enums.Role;
import com.move24.exception.exception.IdAlreadyExistsException;
import com.move24.repository.ImageRepository;
import com.move24.repository.MemberRepository;
import com.move24.request.JoinRequest;
import com.move24.response.DriversResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.move24.enums.Gender.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional
    public void signup(JoinRequest request, MultipartFile file) {

        boolean isExist = memberRepository.existsByUserId(request.getUserId());
        if(isExist) {
            throw new IdAlreadyExistsException("이미 존재하는 아이디입니다.");
        }

        Image image = new Image(file);
        imageRepository.save(image);

        MemberDetails memberDetails = MemberDetails.builder()
                .gender(valueOf(request.getGender()))
                .name(request.getName())
                .mail(request.getMail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .role(Role.valueOf(request.getRole()))
                .build();

        Member member = Member.builder()
                .userId(request.getUserId())
                .password(request.getPassword())
                .image(image)
                .status(MemberStatus.ACTIVE)
                .details(memberDetails)
                .loginDate(LocalDateTime.now())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        memberRepository.save(member);
        image.upload(file, image.getFileName(), uploadDir);
    }
    
    public void validateDuplicateId(String userId) {
        boolean isExist = memberRepository.existsByUserId(userId);
        if(isExist) {
            throw new IdAlreadyExistsException("이미 존재하는 아이디입니다.");
        }
    }

}
