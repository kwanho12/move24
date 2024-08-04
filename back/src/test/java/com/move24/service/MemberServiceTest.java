package com.move24.service;

import com.move24.domain.Image;
import com.move24.domain.Member;
import com.move24.enums.Gender;
import com.move24.repository.ImageRepository;
import com.move24.repository.MemberRepository;
import com.move24.request.JoinRequest;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Test
    void joinTest() {

        // given
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        JoinRequest request = new JoinRequest();
        request.setMemberId("user1");
        request.setPassword("1234");
        request.setName("관호");
        request.setGender("MALE");
        request.setMail("sksmss123@gmail.com");
        request.setPhoneNumber("01012345678");
        request.setAddress("서울시 양산길 33");

        // when
        memberService.join(request, file);
        Member savedMember = memberRepository.findById(request.getMemberId()).orElse(null);
        Image savedImage = savedMember != null ? imageRepository.findById(savedMember.getImage().getId()).orElse(null) : null;

        // then
        assertNotNull(savedMember);
        assertNotNull(savedImage);

        assertEquals(request.getMemberId(), savedMember.getId());
        assertEquals(request.getName(), savedMember.getDetails().getName());
        assertEquals(request.getMail(), savedMember.getDetails().getMail());
        assertEquals(request.getPhoneNumber(), savedMember.getDetails().getPhoneNumber());
        assertEquals(request.getAddress(), savedMember.getDetails().getAddress());
        assertEquals(Gender.valueOf(request.getGender()), savedMember.getDetails().getGender());
        assertEquals(savedMember.getImage().getFileName(), savedImage.getFileName());
    }
}