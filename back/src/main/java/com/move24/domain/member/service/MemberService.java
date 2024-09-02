package com.move24.domain.member.service;

import com.move24.common.utils.ImageUtil;
import com.move24.domain.member.dto.request.MemberJoinServiceRequest;
import com.move24.domain.member.entity.image.Image;
import com.move24.domain.member.entity.member.Member;
import com.move24.domain.member.exception.IdAlreadyExistsException;
import com.move24.domain.member.repository.ImageRepository;
import com.move24.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final ImageUtil imageUtil;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional
    public Member signup(MemberJoinServiceRequest request, MultipartFile imageFile) {
        request.validateBusinessPolicyException();
        if(memberRepository.existsByUserId(request.getUserId())) {
            throw new IdAlreadyExistsException("이미 존재하는 아이디로 회원 가입할 수 없습니다.");
        }

        Image image = Image.create(imageFile);
        imageRepository.save(image);

        Member member = Member.create(request, image);
        Member savedMember = memberRepository.save(member);

        imageUtil.upload(imageFile, image.getFileName(), uploadDir);
        return savedMember;
    }

    public void validateDuplicateId(String userId) {
        if(memberRepository.existsByUserId(userId)) {
            throw new IdAlreadyExistsException("이미 존재하는 아이디입니다.");
        }
    }
}
