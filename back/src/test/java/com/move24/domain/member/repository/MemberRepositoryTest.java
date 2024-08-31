package com.move24.domain.member.repository;

import com.move24.IntegrationTestSupport;
import com.move24.domain.member.entity.image.Image;
import com.move24.domain.member.entity.member.Member;
import com.move24.domain.member.entity.member.MemberDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import static com.move24.domain.member.entity.member.Gender.MALE;
import static com.move24.domain.member.entity.member.Role.ROLE_DRIVER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

class MemberRepositoryTest extends IntegrationTestSupport {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ImageRepository imageRepository;

    @DisplayName("회원 아이디로 회원을 조회한다.")
    @Test
    void findByUserId() {
        // given
        Member member = createMember("test1");
        memberRepository.save(member);

        // when
        Member findMember = memberRepository.findByUserId("test1").orElse(null);

        // then
        assertThat(findMember).isEqualTo(member);
    }

    @DisplayName("회원가입할 때 이미 존재하는 아이디를 입력하면 true를 return한다.")
    @Test
    void existsByUserId() {
        // given
        Member member = createMember("test1");
        memberRepository.save(member);

        // when
        boolean result = memberRepository.existsByUserId("test1");

        // then
        assertThat(result).isTrue();

    }

    private Image createImage() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        return Image.builder()
                .imageFile(file)
                .build();
    }

    private Member createMember(String userId) {
        Image image = createImage();
        imageRepository.save(image);

        MemberDetails memberDetails = MemberDetails.builder()
                .gender(MALE)
                .name("김천재")
                .mail("skdltm@naver.com")
                .phoneNumber("010-1234-5678")
                .address("서울시 양산길 21 305호")
                .role(ROLE_DRIVER)
                .build();

        return Member.builder()
                .userId(userId)
                .password("rkskek12#")
                .image(image)
                .details(memberDetails)
                .build();
    }

}