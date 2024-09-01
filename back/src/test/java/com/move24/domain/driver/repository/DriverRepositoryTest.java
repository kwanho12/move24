package com.move24.domain.driver.repository;

import com.move24.IntegrationTestSupport;
import com.move24.domain.driver.dto.request.DriverSearchServiceCondition;
import com.move24.domain.driver.dto.response.DriverOneResponse;
import com.move24.domain.driver.dto.response.DriversResponse;
import com.move24.domain.driver.entity.Driver;
import com.move24.domain.member.entity.image.Image;
import com.move24.domain.member.entity.member.Gender;
import com.move24.domain.member.entity.member.Member;
import com.move24.domain.member.entity.member.MemberDetails;
import com.move24.domain.member.repository.ImageRepository;
import com.move24.domain.member.repository.MemberRepository;
import com.move24.domain.review.entity.Review;
import com.move24.domain.review.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Objects;

import static com.move24.domain.member.entity.member.Gender.FEMALE;
import static com.move24.domain.member.entity.member.Gender.MALE;
import static com.move24.domain.member.entity.member.Role.ROLE_DRIVER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@Slf4j
class DriverRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @DisplayName("회원 객체로 기사를 조회한다.")
    @Test
    void findByMember() {
        // given
        Member member = createMember(
                "test1",
                "김천재",
                MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        memberRepository.save(member);

        Driver driver = createDriver(member, 10, "믿고 맡겨 주십시오.");
        driverRepository.save(driver);

        // when
        Driver findDriver = driverRepository.findByMember(member).orElse(null);

        // then
        assertThat(findDriver).isEqualTo(driver);
    }

    @DisplayName("회원 아이디로 특정한 기사 게시글을 조회한다.")
    @Test
    void getDriverOne() {
        // given
        Member member = createMember(
                "test1",
                "김천재",
                FEMALE,
                "skdltm@naver.com",
                "010-5555-6666"
        );
        memberRepository.save(member);

        Driver driver = createDriver(member, 10, "믿고 맡겨 주십시오.");
        driverRepository.save(driver);

        // when
        DriverOneResponse response = driverRepository.getDriverOne("test1").orElse(null);

        // then
        assertThat(response)
                .extracting(
                        "driverId",
                        "name",
                        "gender",
                        "mail",
                        "phoneNumber",
                        "experienceYear",
                        "content",
                        "averagePoint")
                .containsExactly(
                        "test1",
                        "김천재",
                        FEMALE,
                        "skdltm@naver.com",
                        "010-5555-6666",
                        10,
                        "믿고 맡겨 주십시오.",
                        null
                );
    }

    @DisplayName("특정한 기사 게시글을 조회할 때 기사의 후기가 있다면 후기의 평균 점수가 표시된다.")
    @Test
    void getDriverOneWithAveragePoint() {
        // given
        Member driverMember = createMember(
                "test1",
                "김천재",
                MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        Member reviewer = createMember(
                "test2",
                "김천재",
                MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        memberRepository.saveAll(List.of(driverMember, reviewer));

        Driver driver = createDriver(driverMember, 10, "믿고 맡겨 주십시오.");
        driverRepository.save(driver);

        Review review1 = createReview(reviewer, driver, 3);
        Review review2 = createReview(reviewer, driver, 2);
        Review review3 = createReview(reviewer, driver, 1);
        reviewRepository.saveAll(List.of(review1, review2, review3));

        // when
        DriverOneResponse response = driverRepository.getDriverOne("test1").orElse(null);

        // then
        assertThat(Objects.requireNonNull(response).getAveragePoint()).isEqualTo(2.0);
    }

    @DisplayName("페이지네이션 처리된 여러 개의 기사 게시글을 조회한다.")
    @Test
    void getPagedDrivers() {
        // given
        DriverSearchServiceCondition condition = DriverSearchServiceCondition.builder().build();
        Pageable pageable = PageRequest.of(0, 2);

        Member member1 = createMember(
                "test1",
                "김천재",
                MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        Member member2 = createMember(
                "test2",
                "박천재",
                MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        Member member3 = createMember(
                "test3",
                "최천재",
                MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        memberRepository.saveAll(List.of(member1, member2, member3));

        Driver driver1 = createDriver(member1, 10, "믿고 맡겨 주십시오.");
        Driver driver2 = createDriver(member2, 20, "믿고 맡겨 주십시오.");
        Driver driver3 = createDriver(member3, 30, "믿고 맡겨 주십시오.");
        driverRepository.saveAll(List.of(driver1, driver2, driver3));

        // when
        Page<DriversResponse> drivers = driverRepository.getDrivers(condition, pageable);

        // then
        assertThat(drivers).hasSize(2)
                .extracting("driverId", "name", "experienceYear", "averagePoint")
                .containsExactly(
                        tuple("test1", "김천재", 10, null),
                        tuple("test2", "박천재", 20, null)
                );
    }

    @DisplayName("이름을 검색 조건으로 하여 기사 게시글을 조회한다.")
    @Test
    void getDriversWithOneSearchCondition1() {
        // given
        DriverSearchServiceCondition searchCondition = DriverSearchServiceCondition.builder()
                .name("김천재")
                .build();
        Pageable pageable = PageRequest.of(0, 3);

        Member member1 = createMember(
                "test1",
                "김천재",
                MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        Member member2 = createMember(
                "test2",
                "김천재", MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        Member member3 = createMember(
                "test3",
                "김천재", MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        Member member4 = createMember(
                "test4",
                "박천재", MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        Member member5 = createMember(
                "test5",
                "황천재",
                MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        memberRepository.saveAll(List.of(member1, member2, member3, member4, member5));

        Driver driver1 = createDriver(member1, 10, "믿고 맡겨 주십시오.");
        Driver driver2 = createDriver(member2, 20, "믿고 맡겨 주십시오.");
        Driver driver3 = createDriver(member3, 30, "믿고 맡겨 주십시오.");
        Driver driver4 = createDriver(member4, 40, "믿고 맡겨 주십시오.");
        Driver driver5 = createDriver(member5, 50, "믿고 맡겨 주십시오.");
        driverRepository.saveAll(List.of(driver1, driver2, driver3, driver4, driver5));

        // when
        Page<DriversResponse> drivers = driverRepository.getDrivers(searchCondition, pageable);

        // then
        assertThat(drivers).hasSize(3)
                .extracting("driverId", "name", "experienceYear", "averagePoint")
                .containsExactly(
                        tuple("test1", "김천재", 10, null),
                        tuple("test2", "김천재", 20, null),
                        tuple("test3", "김천재", 30, null)
                );
    }

    @DisplayName("성별을 검색 조건으로 하여 기사 게시글을 조회한다.")
    @Test
    void getDriversWithOneSearchCondition2() {
        // given
        DriverSearchServiceCondition condition = DriverSearchServiceCondition.builder()
                .gender(FEMALE)
                .build();
        Pageable pageable = PageRequest.of(0, 3);

        Member member1 = createMember(
                "test1",
                "김천재", MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        Member member2 = createMember(
                "test2",
                "김천재",
                MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        Member member3 = createMember(
                "test3",
                "김천재", FEMALE,
                "good@naver.com",
                "010-5555-6666"
        );
        Member member4 = createMember(
                "test4",
                "김천재", FEMALE,
                "good@naver.com",
                "010-5555-6666"
        );
        Member member5 = createMember(
                "test5",
                "김천재", FEMALE,
                "good@naver.com",
                "010-5555-6666"
        );
        memberRepository.saveAll(List.of(member1, member2, member3, member4, member5));

        Driver driver1 = createDriver(member1, 10, "믿고 맡겨 주십시오.");
        Driver driver2 = createDriver(member2, 20, "믿고 맡겨 주십시오.");
        Driver driver3 = createDriver(member3, 30, "믿고 맡겨 주십시오.");
        Driver driver4 = createDriver(member4, 40, "믿고 맡겨 주십시오.");
        Driver driver5 = createDriver(member5, 50, "믿고 맡겨 주십시오.");
        driverRepository.saveAll(List.of(driver1, driver2, driver3, driver4, driver5));

        // when
        Page<DriversResponse> drivers = driverRepository.getDrivers(condition, pageable);

        // then
        assertThat(drivers).hasSize(3)
                .extracting("driverId", "name", "experienceYear")
                .containsExactly(
                        tuple("test3", "김천재", 30),
                        tuple("test4", "김천재", 40),
                        tuple("test5", "김천재", 50)
                );
    }

    @DisplayName("이메일을 검색 조건으로 하여 기사 게시글을 조회한다.")
    @Test
    void getDriversWithOneSearchCondition3() {
        // given
        DriverSearchServiceCondition condition = DriverSearchServiceCondition.builder()
                .mail("verygood@naver.com")
                .build();
        Pageable pageable = PageRequest.of(0, 3);

        Member member1 = createMember(
                "test1",
                "김천재",
                MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        Member member2 = createMember(
                "test2",
                "김천재",
                MALE,
                "verygood@naver.com",
                "010-5555-6666"
        );
        Member member3 = createMember(
                "test3",
                "김천재",
                FEMALE,
                "good@naver.com",
                "010-5555-6666"
        );
        Member member4 = createMember(
                "test4",
                "김천재",
                FEMALE,
                "verygood@naver.com",
                "010-5555-6666"
        );
        Member member5 = createMember(
                "test5",
                "김천재",
                FEMALE,
                "good@naver.com",
                "010-5555-6666"
        );
        memberRepository.saveAll(List.of(member1, member2, member3, member4, member5));

        Driver driver1 = createDriver(member1, 10, "믿고 맡겨 주십시오.");
        Driver driver2 = createDriver(member2, 20, "믿고 맡겨 주십시오.");
        Driver driver3 = createDriver(member3, 30, "믿고 맡겨 주십시오.");
        Driver driver4 = createDriver(member4, 40, "믿고 맡겨 주십시오.");
        Driver driver5 = createDriver(member5, 50, "믿고 맡겨 주십시오.");
        driverRepository.saveAll(List.of(driver1, driver2, driver3, driver4, driver5));

        // when
        Page<DriversResponse> drivers = driverRepository.getDrivers(condition, pageable);

        // then
        assertThat(drivers).hasSize(2)
                .extracting("driverId", "name", "experienceYear")
                .containsExactly(
                        tuple("test2", "김천재", 20),
                        tuple("test4", "김천재", 40)
                );
    }

    @DisplayName("휴대폰 번호를 검색 조건으로 하여 기사 게시글을 조회한다.")
    @Test
    void getDriversWithOneSearchCondition4() {
        // given
        DriverSearchServiceCondition condition = DriverSearchServiceCondition.builder()
                .phoneNumber("010-5555-6666")
                .build();
        Pageable pageable = PageRequest.of(0, 3);

        Member member1 = createMember(
                "test1",
                "김천재",
                MALE,
                "good@naver.com",
                "010-5555-7777"
        );
        Member member2 = createMember(
                "test2",
                "김천재",
                MALE,
                "verygood@naver.com",
                "010-5555-6666"
        );
        Member member3 = createMember(
                "test3",
                "김천재",
                FEMALE,
                "good@naver.com",
                "010-5555-7777"
        );
        Member member4 = createMember(
                "test4",
                "김천재",
                FEMALE,
                "verygood@naver.com",
                "010-5555-6666"
        );
        Member member5 = createMember(
                "test5",
                "김천재",
                FEMALE,
                "good@naver.com",
                "010-5555-6666"
        );
        memberRepository.saveAll(List.of(member1, member2, member3, member4, member5));

        Driver driver1 = createDriver(member1, 10, "믿고 맡겨 주십시오.");
        Driver driver2 = createDriver(member2, 20, "믿고 맡겨 주십시오.");
        Driver driver3 = createDriver(member3, 30, "믿고 맡겨 주십시오.");
        Driver driver4 = createDriver(member4, 40, "믿고 맡겨 주십시오.");
        Driver driver5 = createDriver(member5, 50, "믿고 맡겨 주십시오.");
        driverRepository.saveAll(List.of(driver1, driver2, driver3, driver4, driver5));

        // when
        Page<DriversResponse> drivers = driverRepository.getDrivers(condition, pageable);

        // then
        assertThat(drivers).hasSize(3)
                .extracting("driverId", "name", "experienceYear")
                .containsExactly(
                        tuple("test2", "김천재", 20),
                        tuple("test4", "김천재", 40),
                        tuple("test5", "김천재", 50)
                );
    }

    @DisplayName("여러 개의 검색 조건으로 페이지네이션 처리된 기사 게시글을 조회한다.")
    @Test
    void getPagedDriversWithSeveralSearchCondition() {
        // given
        DriverSearchServiceCondition condition = DriverSearchServiceCondition.builder()
                .gender(FEMALE)
                .phoneNumber("010-5555-7777")
                .build();
        Pageable pageable = PageRequest.of(0, 2);

        Member member1 = createMember(
                "test1",
                "김천재",
                MALE,
                "good@naver.com",
                "010-5555-7777"
        );
        Member member2 = createMember(
                "test2",
                "김천재",
                FEMALE,
                "verygood@naver.com",
                "010-5555-7777"
        );
        Member member3 = createMember(
                "test3",
                "김천재",
                FEMALE,
                "good@naver.com",
                "010-5555-6666"
        );
        Member member4 = createMember(
                "test4",
                "김천재",
                FEMALE,
                "verygood@naver.com",
                "010-5555-7777"
        );
        Member member5 = createMember(
                "test5",
                "김천재",
                FEMALE,
                "good@naver.com",
                "010-5555-7777"
        );
        memberRepository.saveAll(List.of(member1, member2, member3, member4, member5));

        Driver driver1 = createDriver(member1, 10, "믿고 맡겨 주십시오.");
        Driver driver2 = createDriver(member2, 20, "믿고 맡겨 주십시오.");
        Driver driver3 = createDriver(member3, 30, "믿고 맡겨 주십시오.");
        Driver driver4 = createDriver(member4, 40, "믿고 맡겨 주십시오.");
        Driver driver5 = createDriver(member5, 50, "믿고 맡겨 주십시오.");
        driverRepository.saveAll(List.of(driver1, driver2, driver3, driver4, driver5));

        // when
        Page<DriversResponse> drivers = driverRepository.getDrivers(condition, pageable);

        // then
        assertThat(drivers).hasSize(2)
                .extracting("driverId", "name", "experienceYear")
                .containsExactly(
                        tuple("test2", "김천재", 20),
                        tuple("test4", "김천재", 40)
                );
    }

    @DisplayName("여러 개의 기사 게시글을 조회할 때 기사의 후기가 있다면 후기의 평균 점수가 표시된다.")
    @Test
    void getPagedDriversWithAveragePoint() {
        // given
        Pageable pageable = PageRequest.of(0, 2);
        DriverSearchServiceCondition condition = DriverSearchServiceCondition.builder().build();

        Member driverMember = createMember(
                "test1",
                "김천재",
                MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        Member reviewer1 = createMember(
                "test2",
                "조천재",
                MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        Member reviewer2 = createMember(
                "test3",
                "황천재",
                MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        Member reviewer3 = createMember(
                "test4",
                "양천재",
                MALE,
                "good@naver.com",
                "010-5555-6666"
        );
        memberRepository.saveAll(List.of(driverMember, reviewer1, reviewer2, reviewer3));

        Driver driver1 = createDriver(driverMember, 10, "믿고 맡겨 주십시오.");
        driverRepository.save(driver1);

        Review review1 = createReview(reviewer1, driver1, 5);
        Review review2 = createReview(reviewer2, driver1, 3);
        Review review3 = createReview(reviewer3, driver1, 1);
        reviewRepository.saveAll(List.of(review1, review2, review3));

        // when
        Page<DriversResponse> drivers = driverRepository.getDrivers(condition, pageable);

        // then
        assertThat(drivers).hasSize(1)
                .extracting("driverId", "name", "experienceYear" ,"averagePoint")
                .containsExactly(
                        tuple("test1", "김천재", 10, 3.0)
                );
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
                                String email, String phoneNumber) {
        Image image = createImage();
        imageRepository.save(image);

        MemberDetails memberDetails = MemberDetails.builder()
                .gender(gender)
                .name(name)
                .mail(email)
                .phoneNumber(phoneNumber)
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

    private Driver createDriver(Member member, int experienceYear, String content) {
        return Driver.builder()
                .member(member)
                .experienceYear(experienceYear)
                .content(content)
                .build();
    }

    private Review createReview(Member reviewer, Driver driver, int point) {
        return Review.builder()
                .member(reviewer)
                .driver(driver)
                .title("리뷰 제목입니다.")
                .content("리뷰 내용입니다.")
                .point(point)
                .build();
    }
}