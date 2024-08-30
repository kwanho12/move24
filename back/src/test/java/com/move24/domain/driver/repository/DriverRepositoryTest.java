package com.move24.domain.driver.repository;

import com.move24.IntegrationTestSupport;
import com.move24.domain.driver.dto.request.DriverSearchServiceCondition;
import com.move24.domain.driver.dto.response.DriverOneResponse;
import com.move24.domain.driver.dto.response.DriversResponse;
import com.move24.domain.driver.entity.Driver;
import com.move24.domain.member.entity.image.Image;
import com.move24.domain.member.entity.member.Member;
import com.move24.domain.member.entity.member.MemberDetails;
import com.move24.domain.member.repository.ImageRepository;
import com.move24.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static com.move24.domain.member.entity.member.Gender.MALE;
import static com.move24.domain.member.entity.member.Role.ROLE_DRIVER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@Slf4j
class DriverRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 객체로 기사 정보를 조회한다.")
    @Test
    void findByMember() {
        // given
        Member member = createMember("test1", "김천재");
        memberRepository.save(member);

        Driver driver = createDriver(member, 10, "믿고 맡겨 주십시오.");
        driverRepository.save(driver);

        // when
        Driver findDriver = driverRepository.findByMember(member).orElse(null);

        // then
        assertThat(findDriver).isEqualTo(driver);
    }

    @DisplayName("회원 아이디로 기사 정보를 조회한다.")
    @Test
    void getDriverOne() {
        // given
        Member member = createMember("test1", "김천재");
        memberRepository.save(member);

        Driver driver = createDriver(member, 10, "믿고 맡겨 주십시오.");
        driverRepository.save(driver);

        // when
        DriverOneResponse response = driverRepository.getDriverOne("test1").orElse(null);

        // then
        assertThat(response)
                .extracting("driverId", "name", "content")
                .containsExactlyInAnyOrder(
                    "test1", "김천재", "믿고 맡겨 주십시오."
                );
    }

    @DisplayName("전체 기사 정보를 조회한다.")
    @Test
    void getDrivers() {
        // given
        Member member1 = createMember("test1", "김천재");
        Member member2 = createMember("test2", "박천재");
        Member member3 = createMember("test3", "최천재");
        memberRepository.saveAll(List.of(member1, member2, member3));

        Driver driver1 = createDriver(member1, 10, "믿고 맡겨 주십시오.");
        Driver driver2 = createDriver(member2, 10, "열심히 할게요.");
        Driver driver3 = createDriver(member3, 10, "저렴하게 특급 배송.");
        driverRepository.saveAll(List.of(driver1, driver2, driver3));

        Pageable pageable = PageRequest.of(0, 3);

        // when
        DriverSearchServiceCondition condition = DriverSearchServiceCondition.builder().build();
        Page<DriversResponse> drivers = driverRepository.getDrivers(condition, pageable);

        log.info("drivers : " + drivers);

        // then
//        assertThat(drivers).hasSize(1)

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


    private Member createMember(String userId, String name) {
        Image image = createImage();
        imageRepository.save(image);

        MemberDetails memberDetails = MemberDetails.builder()
                .gender(MALE)
                .name(name)
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

    private Driver createDriver(Member member, int experienceYear, String content) {
        return Driver.builder()
                .member(member)
                .experienceYear(experienceYear)
                .content(content)
                .build();
    }

}