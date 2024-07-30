package com.move24.service;

import com.move24.domain.Image;
import com.move24.domain.Member;
import com.move24.domain.MemberDetails;
import com.move24.enums.MemberStatus;
import com.move24.repository.ImageRepository;
import com.move24.repository.MemberRepository;
import com.move24.request.JoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static com.move24.enums.Gender.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public void join(JoinRequest request, MultipartFile file) {
        Image image = new Image(file);
        imageRepository.save(image);

        isValidGender(request.getGender());

        MemberDetails memberDetails = MemberDetails.builder()
                .gender(valueOf(request.getGender()))
                .name(request.getName())
                .mail(request.getMail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .build();

        Member member = Member.builder()
                .id(request.getMemberId())
                .password(request.getPassword())
                .image(image)
                .status(MemberStatus.ACTIVE)
                .details(memberDetails)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        memberRepository.save(member);
        image.upload(file, image.getFileName(), uploadDir);
    }
}
