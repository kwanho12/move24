package com.move24.domain.driver.service;

import com.move24.IntegrationTestSupport;
import com.move24.common.exception.BusinessPolicyValidationException;
import com.move24.domain.driver.dto.request.DriverPostServiceRequest;
import com.move24.domain.driver.dto.request.DriverSearchServiceCondition;
import com.move24.domain.driver.dto.request.UpdateDriverServiceRequest;
import com.move24.domain.driver.dto.response.DriverOneResponse;
import com.move24.domain.driver.dto.response.DriversResponse;
import com.move24.domain.driver.entity.Driver;
import com.move24.domain.driver.repository.DriverRepository;
import com.move24.domain.member.entity.image.Image;
import com.move24.domain.member.entity.member.Gender;
import com.move24.domain.member.entity.member.Member;
import com.move24.domain.member.entity.member.MemberDetails;
import com.move24.domain.member.entity.member.Role;
import com.move24.domain.member.exception.RoleNotValidException;
import com.move24.domain.member.repository.ImageRepository;
import com.move24.domain.member.repository.MemberRepository;
import com.move24.domain.review.repository.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import static com.move24.domain.member.entity.member.Gender.MALE;
import static com.move24.domain.member.entity.member.Role.ROLE_DRIVER;
import static com.move24.domain.member.entity.member.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@Transactional
class DriverServiceTest extends IntegrationTestSupport {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private DriverService driverService;

    @DisplayName("기사 게시글을 올린다.")
    @Test
    void post() {
        // given
        Member member = createMember(
                "test1",
                "김천재",
                MALE,
                "skdltm@naver.com",
                "010-2222-2222",
                ROLE_DRIVER
        );
        memberRepository.save(member);

        DriverPostServiceRequest request = createDriverPostRequest("test1", 10,
                "빠르고 신속한 서비스를 제공합니다.");

        // when
        Driver postedDriver = driverService.post(request);

        // then
        assertThat(postedDriver.getMember()).isEqualTo(member);
    }

    @DisplayName("기사가 아닌 일반 회원은 기사 등록 게시글을 올릴 수 없다.")
    @Test
    void postWithWrongRole() {
        // given
        Member member = createMember(
                "test1",
                "김천재",
                MALE,
                "skdltm@naver.com",
                "010-2222-2222",
                ROLE_USER
        );
        memberRepository.save(member);

        DriverPostServiceRequest request = createDriverPostRequest("test1", 10,
                "빠르고 신속한 서비스를 제공합니다.");

        // when, then
        assertThatThrownBy(() -> driverService.post(request))
                .isInstanceOf(RoleNotValidException.class)
                .hasMessage("기사만 게시글 작성이 가능합니다.");
    }

    @DisplayName("기사 게시글을 올릴 때 경력 연차는 0~100년 까지만 입력 가능하다.")
    @Test
    void postWithCorrectExperienceYear1() {
        // given
        Member member = createMember(
                "test1",
                "김천재",
                MALE,
                "skdltm@naver.com",
                "010-2222-2222",
                ROLE_DRIVER
        );
        memberRepository.save(member);

        DriverPostServiceRequest request = createDriverPostRequest("test1", 0,
                "빠르고 신속한 서비스를 제공합니다.");

        // when
        Driver postedDriver = driverService.post(request);

        // then
        assertThat(postedDriver.getMember()).isEqualTo(member);
    }

    @DisplayName("기사 게시글을 올릴 때 경력 연차는 0~100년 까지만 입력 가능하다.")
    @Test
    void postWithCorrectExperienceYear2() {
        // given
        Member member = createMember(
                "test1",
                "김천재",
                MALE,
                "skdltm@naver.com",
                "010-2222-2222",
                ROLE_DRIVER
        );
        memberRepository.save(member);

        DriverPostServiceRequest request = createDriverPostRequest("test1", 100,
                "빠르고 신속한 서비스를 제공합니다.");

        // when
        Driver postedDriver = driverService.post(request);

        // then
        assertThat(postedDriver.getMember()).isEqualTo(member);
    }

    @DisplayName("기사 게시글을 올릴 때 경력 연차는 0~100년 까지만 입력 가능하다.")
    @Test
    void postWithWrongExperienceYear1() {
        // given
        Member member = createMember(
                "test1",
                "김천재",
                MALE,
                "skdltm@naver.com",
                "010-2222-2222",
                ROLE_USER
        );
        memberRepository.save(member);

        DriverPostServiceRequest request = createDriverPostRequest("test1", -1,
                "빠르고 신속한 서비스를 제공합니다.");

        // when, then
        assertThatThrownBy(() -> driverService.post(request))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("경력은 0~100년까지 입력 가능합니다.");
    }

    @DisplayName("기사 게시글을 올릴 때 경력 연차는 0~100년 까지만 입력 가능하다.")
    @Test
    void postWithWrongExperienceYear2() {
        // given
        Member member = createMember(
                "test1",
                "김천재",
                MALE,
                "skdltm@naver.com",
                "010-2222-2222",
                ROLE_USER
        );
        memberRepository.save(member);

        DriverPostServiceRequest request = createDriverPostRequest("test1", 101,
                "빠르고 신속한 서비스를 제공합니다.");

        // when, then
        assertThatThrownBy(() -> driverService.post(request))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("경력은 0~100년까지 입력 가능합니다.");
    }

    @DisplayName("기사 게시글 1개를 조회할 때 기사의 후기가 없다면 후기 점수는 0.0점이다.")
    @Test
    void getOneWithoutReview() {
        // given
        Member member = createMember(
                "test1",
                "김천재",
                MALE,
                "skdltm@naver.com",
                "010-2222-2222",
                ROLE_DRIVER
        );
        memberRepository.save(member);

        Driver driver = createDriver(member, 10, "확실한 서비스를 보증합니다.");
        driverRepository.save(driver);

        // when
        DriverOneResponse response = driverService.getOne("test1");

        // then
        assertThat(response)
                .extracting("driverId",
                        "name",
                        "gender",
                        "mail",
                        "phoneNumber",
                        "experienceYear",
                        "content",
                        "averagePoint"
                ).containsExactly(
                        "test1",
                        "김천재",
                        MALE,
                        "skdltm@naver.com",
                        "010-2222-2222",
                        10,
                        "확실한 서비스를 보증합니다.",
                        0.0
                );
    }

    @DisplayName("여러 개의 기사 게시글을 조회할 때 기사의 후기가 없다면 후기 점수는 0.0점이다.")
    @Test
    void getListWithoutReview() {
        // given
        Member member = createMember(
                "test1",
                "김천재",
                MALE,
                "skdltm@naver.com",
                "010-2222-2222",
                ROLE_DRIVER
        );
        memberRepository.save(member);

        Driver driver = createDriver(member, 10, "확실한 서비스를 보증합니다.");
        driverRepository.save(driver);

        DriverSearchServiceCondition condition = DriverSearchServiceCondition.builder().build();
        PageRequest pageRequest = PageRequest.of(0, 3);

        // when
        Page<DriversResponse> response = driverService.getList(condition, pageRequest);

        // then
        assertThat(response.getContent().get(0).getAveragePoint()).isEqualTo(0.0);
    }

    @DisplayName("기사 게시글을 수정한다.")
    @Test
    void updateDriver() {
        // given
        Member member = createMember(
                "test1",
                "김천재",
                MALE,
                "skdltm@naver.com",
                "010-2222-2222",
                ROLE_DRIVER
        );
        memberRepository.save(member);

        Driver driver = createDriver(member, 10, "확실한 서비스를 보증합니다.");
        driverRepository.save(driver);

        UpdateDriverServiceRequest request = updateDriver("test1", 20, "열심히 하겠습니다.");

        // when
        driverService.update(request);

        // then
        Driver findDriver = driverRepository.findByMember(member).orElse(null);
        Assertions.assertThat(findDriver)
                .extracting("experienceYear", "content")
                .containsExactly(20, "열심히 하겠습니다.");
    }

    @DisplayName("기사 게시글을 수정할 때 경력 연차는 0~100년 까지만 입력 가능하다.")
    @Test
    void updateDriverWithCorrectExperienceYear1() {
        // given
        Member member = createMember(
                "test1",
                "김천재",
                MALE,
                "skdltm@naver.com",
                "010-2222-2222",
                ROLE_DRIVER
        );
        memberRepository.save(member);

        Driver driver = createDriver(member, 10, "확실한 서비스를 보증합니다.");
        driverRepository.save(driver);

        UpdateDriverServiceRequest request = updateDriver("test1", 0, "열심히 하겠습니다.");

        // when
        driverService.update(request);

        // then
        Driver findDriver = driverRepository.findByMember(member).orElse(null);
        Assertions.assertThat(findDriver)
                .extracting("experienceYear", "content")
                .containsExactly(0, "열심히 하겠습니다.");
    }

    @DisplayName("기사 게시글을 수정할 때 경력 연차는 0~100년 까지만 입력 가능하다.")
    @Test
    void updateDriverWithCorrectExperienceYear2() {
        // given
        Member member = createMember(
                "test1",
                "김천재",
                MALE,
                "skdltm@naver.com",
                "010-2222-2222",
                ROLE_DRIVER
        );
        memberRepository.save(member);

        Driver driver = createDriver(member, 10, "확실한 서비스를 보증합니다.");
        driverRepository.save(driver);

        UpdateDriverServiceRequest request = updateDriver("test1", 100, "열심히 하겠습니다.");

        // when
        driverService.update(request);

        // then
        Driver findDriver = driverRepository.findByMember(member).orElse(null);
        Assertions.assertThat(findDriver)
                .extracting("experienceYear", "content")
                .containsExactly(100, "열심히 하겠습니다.");
    }

    @DisplayName("기사 게시글을 수정할 때 경력 연차는 0~100년 까지만 입력 가능하다.")
    @Test
    void updateDriverWithWrongExperienceYear1() {
        // given
        Member member = createMember(
                "test1",
                "김천재",
                MALE,
                "skdltm@naver.com",
                "010-2222-2222",
                ROLE_DRIVER
        );
        memberRepository.save(member);

        Driver driver = createDriver(member, 10, "확실한 서비스를 보증합니다.");
        driverRepository.save(driver);

        UpdateDriverServiceRequest request = updateDriver("test1", -1, "열심히 하겠습니다.");

        // when, then
        assertThatThrownBy(() -> driverService.update(request))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("경력은 0~100년까지 입력 가능합니다.");
    }

    @DisplayName("기사 게시글을 수정할 때 경력 연차는 0~100년 까지만 입력 가능하다.")
    @Test
    void updateDriverWithWrongExperienceYear2() {
        // given
        Member member = createMember(
                "test1",
                "김천재",
                MALE,
                "skdltm@naver.com",
                "010-2222-2222",
                ROLE_DRIVER
        );
        memberRepository.save(member);

        Driver driver = createDriver(member, 10, "확실한 서비스를 보증합니다.");
        driverRepository.save(driver);

        UpdateDriverServiceRequest request = updateDriver("test1", 101, "열심히 하겠습니다.");

        // when, then
        assertThatThrownBy(() -> driverService.update(request))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("경력은 0~100년까지 입력 가능합니다.");
    }

    private DriverPostServiceRequest createDriverPostRequest(String driverId, Integer experienceYear,
                                                             String content) {
        return DriverPostServiceRequest.builder()
                .driverId(driverId)
                .experienceYear(experienceYear)
                .content(content)
                .build();
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

    private Member createMember(String userId, String name, Gender gender,
                                String email, String phoneNumber, Role role) {
        Image image = createImage();
        imageRepository.save(image);

        MemberDetails memberDetails = MemberDetails.builder()
                .gender(gender)
                .name(name)
                .mail(email)
                .phoneNumber(phoneNumber)
                .address("서울시 양산길 21 305호")
                .role(role)
                .build();

        return Member.builder()
                .userId(userId)
                .password("rkskek12#")
                .image(image)
                .details(memberDetails)
                .build();
    }

    private Driver createDriver(Member member, int experienceYear, String content) {
        return Driver.builder()
                .member(member)
                .experienceYear(experienceYear)
                .content(content)
                .build();
    }

    private UpdateDriverServiceRequest updateDriver(String driverId, int experienceYear, String content) {
        return UpdateDriverServiceRequest.builder()
                .driverId(driverId)
                .experienceYear(experienceYear)
                .content(content)
                .build();
    }
}

