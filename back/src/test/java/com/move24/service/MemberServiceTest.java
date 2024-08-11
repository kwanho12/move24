package com.move24.service;

import com.move24.domain.Image;
import com.move24.domain.Member;
import com.move24.exception.exception.IdAlreadyExistsException;
import com.move24.repository.ImageRepository;
import com.move24.repository.MemberRepository;
import com.move24.request.JoinRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private MemberService memberService;

    @Captor
    ArgumentCaptor<Image> imageCaptor;

    @Captor
    ArgumentCaptor<Member> memberCaptor;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(memberService, "uploadDir", "/Users/jkh/git/move24/upload/staff");
    }

    @Test
    @DisplayName("회원가입에 성공한다.")
    void successSignup() {

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        JoinRequest request = JoinRequest.builder()
                .memberId("skdltm12")
                .password("good12#")
                .name("길동")
                .gender("MALE")
                .mail("skdltm12@gmail.com")
                .address("서울시 양산길 12")
                .phoneNumber("010-1234-5678")
                .role("ROLE_USER")
                .build();

        // when
        memberService.signup(request, mockFile);

        // then
        verify(imageRepository, times(1)).save(imageCaptor.capture());
        verify(memberRepository, times(1)).save(memberCaptor.capture());

        Image savedImage = imageCaptor.getValue();
        assertEquals("testFile.jpeg", savedImage.getOriginalName());

        Member savedMember = memberCaptor.getValue();
        assertEquals(request.getMemberId(), savedMember.getId());
        assertEquals(request.getPassword(), savedMember.getPassword());
        assertEquals(savedImage, savedMember.getImage());

    }

    @Test
    @DisplayName("회원가입시 아이디 중복 검사(이미 존재하는 아이디)")
    void idAlreadyExists() {
        // given
        String memberId = "existingId";
        when(memberRepository.existsById(memberId)).thenReturn(true);

        // when
        assertThrows(IdAlreadyExistsException.class, () -> memberService.checkId(memberId));

        // then
        verify(memberRepository).existsById(memberId);
    }

    @Test
    @DisplayName("회원가입시 아이디 중복 검사(사용 가능한 아이디)")
    void idNotExists() {
        // given
        String memberId = "newId";
        when(memberRepository.existsById(memberId)).thenReturn(false);

        // when
        memberService.checkId(memberId);

        // then
        verify(memberRepository).existsById(memberId);
    }

}
