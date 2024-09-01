package com.move24.domain.member.service;

import com.move24.IntegrationTestSupport;
import com.move24.common.exception.BusinessPolicyValidationException;
import com.move24.domain.member.dto.request.MemberJoinServiceRequest;
import com.move24.domain.member.entity.image.Image;
import com.move24.domain.member.entity.member.Gender;
import com.move24.domain.member.entity.member.Member;
import com.move24.domain.member.entity.member.MemberDetails;
import com.move24.domain.member.entity.member.Role;
import com.move24.domain.member.exception.IdAlreadyExistsException;
import com.move24.domain.member.repository.ImageRepository;
import com.move24.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.move24.domain.member.entity.member.Gender.MALE;
import static com.move24.domain.member.entity.member.Role.ROLE_DRIVER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.doNothing;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@Transactional
class MemberServiceTest extends IntegrationTestSupport {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @DisplayName("신규 회원을 등록한다.")
    @Test
    void signup() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rkskek12#",
                "김천재",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        doNothing().when(imageUtil).upload(any(MultipartFile.class), any(String.class), any(String.class));

        // when
        Member savedMember = memberService.signup(request, imageFile);

        // then
        assertThat(savedMember).extracting(
                "userId",
                "password",
                "details.name",
                "details.gender",
                "details.mail",
                "details.address",
                "details.phoneNumber",
                "details.role"
        ).containsExactly(
                "skdltm12",
                "rkskek12#",
                "김천재",
                Gender.valueOf("FEMALE"),
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                Role.valueOf("ROLE_USER")
        );
    }

    @DisplayName("이미 존재하는 아이디로 회원가입할 수 없다.")
    @Test
    void signupWithAlreadyExistingID() {
        // given
        Member member = createMember("skdltm12");
        memberRepository.save(member);

        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rkskek12#",
                "김천재",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        // when, then
        assertThatThrownBy(() -> memberService.signup(request, imageFile))
                .isInstanceOf(IdAlreadyExistsException.class)
                .hasMessage("이미 존재하는 아이디로 회원 가입할 수 없습니다.");
    }

    @DisplayName("이미 존재하는 아이디를 입력하고 중복 체크하면 예외가 발생한다.")
    @Test
    void CheckDuplicatesWithAlreadyExistingID() {
        // given
        Member member = createMember("skdltm12");
        memberRepository.save(member);

        // when, then
        assertThatThrownBy(() -> memberService.validateDuplicateId("skdltm12"))
                .isInstanceOf(IdAlreadyExistsException.class)
                .hasMessage("이미 존재하는 아이디입니다.");
    }

    @DisplayName("회원가입 시 ID는 6~15글자의 영문 소문자와 숫자만 입력 가능한다.")
    @Test
    void validationSuccessWithUserId1() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm",
                "rkskek12#",
                "김천재",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        doNothing().when(imageUtil).upload(any(MultipartFile.class), any(String.class), any(String.class));

        // when
        Member savedMember = memberService.signup(request, imageFile);

        // then
        assertThat(savedMember).extracting(
                "userId",
                "password",
                "details.name",
                "details.gender",
                "details.mail",
                "details.address",
                "details.phoneNumber",
                "details.role"
        ).containsExactly(
                "skdltm",
                "rkskek12#",
                "김천재",
                Gender.valueOf("FEMALE"),
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                Role.valueOf("ROLE_USER")
        );
    }

    @DisplayName("회원가입 시 ID는 6~15글자의 영문 소문자와 숫자만 입력 가능한다.")
    @Test
    void validationSuccessWithUserId2() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltmskdltmskd",
                "rkskek12#",
                "김천재",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        doNothing().when(imageUtil).upload(any(MultipartFile.class), any(String.class), any(String.class));

        // when
        Member savedMember = memberService.signup(request, imageFile);

        // then
        assertThat(savedMember).extracting(
                "userId",
                "password",
                "details.name",
                "details.gender",
                "details.mail",
                "details.address",
                "details.phoneNumber",
                "details.role"
        ).containsExactly(
                "skdltmskdltmskd",
                "rkskek12#",
                "김천재",
                Gender.valueOf("FEMALE"),
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                Role.valueOf("ROLE_USER")
        );
    }

    @DisplayName("회원가입 시 ID는 6~15글자의 영문 소문자와 숫자만 입력 가능한다.")
    @Test
    void validationFailWithUserId1() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "Skdltm12",
                "rkskek12#",
                "김천재",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        // when, then
        assertThatThrownBy(() -> memberService.signup(request, imageFile))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("ID는 6~15글자의 영문 소문자와 숫자만 입력 가능합니다.");
    }

    @DisplayName("회원가입 시 ID는 6~15글자의 영문 소문자와 숫자만 입력 가능한다.")
    @Test
    void validationFailWithUserId2() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdlt",
                "rkskek12#",
                "김천재",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        // when, then
        assertThatThrownBy(() -> memberService.signup(request, imageFile))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("ID는 6~15글자의 영문 소문자와 숫자만 입력 가능합니다.");
    }

    @DisplayName("회원가입 시 ID는 6~15글자의 영문 소문자와 숫자만 입력 가능한다.")
    @Test
    void validationFailWithUserId3() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltmskdltm1234",
                "rkskek12#",
                "김천재",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        // when, then
        assertThatThrownBy(() -> memberService.signup(request, imageFile))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("ID는 6~15글자의 영문 소문자와 숫자만 입력 가능합니다.");
    }

    @DisplayName("회원가입 시 비밀번호는 6~15자로 영문, 숫자, 특수기호를 혼용해서 입력해야 한다.")
    @Test
    void validationSuccessWithPassword1() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm",
                "rksk1#",
                "김천재",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        doNothing().when(imageUtil).upload(any(MultipartFile.class), any(String.class), any(String.class));

        // when
        Member savedMember = memberService.signup(request, imageFile);

        // then
        assertThat(savedMember).extracting(
                "userId",
                "password",
                "details.name",
                "details.gender",
                "details.mail",
                "details.address",
                "details.phoneNumber",
                "details.role"
        ).containsExactly(
                "skdltm",
                "rksk1#",
                "김천재",
                Gender.valueOf("FEMALE"),
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                Role.valueOf("ROLE_USER")
        );
    }

    @DisplayName("회원가입 시 비밀번호는 6~15자로 영문, 숫자, 특수기호를 혼용해서 입력해야 한다.")
    @Test
    void validationSuccessWithPassword2() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm",
                "rkskekrkskek12#",
                "김천재",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        doNothing().when(imageUtil).upload(any(MultipartFile.class), any(String.class), any(String.class));

        // when
        Member savedMember = memberService.signup(request, imageFile);

        // then
        assertThat(savedMember).extracting(
                "userId",
                "password",
                "details.name",
                "details.gender",
                "details.mail",
                "details.address",
                "details.phoneNumber",
                "details.role"
        ).containsExactly(
                "skdltm",
                "rkskekrkskek12#",
                "김천재",
                Gender.valueOf("FEMALE"),
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                Role.valueOf("ROLE_USER")
        );
    }

    @DisplayName("회원가입 시 비밀번호는 6~15자로 영문, 숫자, 특수기호를 혼용해서 입력해야 한다.")
    @Test
    void validationFailWithPassword1() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rkskek12",
                "김천재",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        // when, then
        assertThatThrownBy(() -> memberService.signup(request, imageFile))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("비밀번호는 6~15자로 영문, 숫자, 특수기호를 혼용해서 입력해 주세요.");
    }

    @DisplayName("회원가입 시 비밀번호는 6~15자로 영문, 숫자, 특수기호를 혼용해서 입력해야 한다.")
    @Test
    void validationFailWithPassword2() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rksk#",
                "김천재",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        // when, then
        assertThatThrownBy(() -> memberService.signup(request, imageFile))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("비밀번호는 6~15자로 영문, 숫자, 특수기호를 혼용해서 입력해 주세요.");
    }

    @DisplayName("회원가입 시 비밀번호는 6~15자로 영문, 숫자, 특수기호를 혼용해서 입력해야 한다.")
    @Test
    void validationFailWithPassword3() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rkskekrkskekrks#",
                "김천재",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        // when, then
        assertThatThrownBy(() -> memberService.signup(request, imageFile))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("비밀번호는 6~15자로 영문, 숫자, 특수기호를 혼용해서 입력해 주세요.");
    }

    @DisplayName("회원가입 시 올바른 이메일 형식을 입력해야 한다.")
    @Test
    void validationFailWithEmail1() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rkskek12#",
                "김천재",
                "FEMALE",
                "testMember",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        // when, then
        assertThatThrownBy(() -> memberService.signup(request, imageFile))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("이메일 형식이 올바르지 않습니다.");
    }

    @DisplayName("회원가입 시 올바른 이메일 형식을 입력해야 한다.")
    @Test
    void validationFailWithEmail2() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rkskek12#",
                "김천재",
                "FEMALE",
                "testMember@naver",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        // when, then
        assertThatThrownBy(() -> memberService.signup(request, imageFile))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("이메일 형식이 올바르지 않습니다.");
    }

    @DisplayName("회원가입 시 올바른 이메일 형식을 입력해야 한다.")
    @Test
    void validationFailWithEmail3() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rkskek12#",
                "김천재",
                "FEMALE",
                "testMember@.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        // when, then
        assertThatThrownBy(() -> memberService.signup(request, imageFile))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("이메일 형식이 올바르지 않습니다.");
    }

    @DisplayName("회원가입 시 올바른 휴대폰 번호 형식을 입력해야 한다.")
    @Test
    void validationFailWithPhoneNumber1() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rkskek12#",
                "김천재",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "01022222222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        // when, then
        assertThatThrownBy(() -> memberService.signup(request, imageFile))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("휴대폰 번호의 형식이 올바르지 않습니다.");
    }

    @DisplayName("회원가입 시 올바른 휴대폰 번호 형식을 입력해야 한다.")
    @Test
    void validationFailWithPhoneNumber2() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rkskek12#",
                "김천재",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        // when, then
        assertThatThrownBy(() -> memberService.signup(request, imageFile))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("휴대폰 번호의 형식이 올바르지 않습니다.");
    }

    @DisplayName("회원가입 시 이름은 2~15자로 입력해야 한다.")
    @Test
    void validationSuccessWithName1() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rkskek12#",
                "김천",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        doNothing().when(imageUtil).upload(any(MultipartFile.class), any(String.class), any(String.class));

        // when
        Member savedMember = memberService.signup(request, imageFile);

        // then
        assertThat(savedMember).extracting(
                "userId",
                "password",
                "details.name",
                "details.gender",
                "details.mail",
                "details.address",
                "details.phoneNumber",
                "details.role"
        ).containsExactly(
                "skdltm12",
                "rkskek12#",
                "김천",
                Gender.valueOf("FEMALE"),
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                Role.valueOf("ROLE_USER")
        );
    }

    @DisplayName("회원가입 시 이름은 2~15자로 입력해야 한다.")
    @Test
    void validationSuccessWithName2() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rkskek12#",
                "김천재김천재김천재김천재김천재",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        doNothing().when(imageUtil).upload(any(MultipartFile.class), any(String.class), any(String.class));

        // when
        Member savedMember = memberService.signup(request, imageFile);

        // then
        assertThat(savedMember).extracting(
                "userId",
                "password",
                "details.name",
                "details.gender",
                "details.mail",
                "details.address",
                "details.phoneNumber",
                "details.role"
        ).containsExactly(
                "skdltm12",
                "rkskek12#",
                "김천재김천재김천재김천재김천재",
                Gender.valueOf("FEMALE"),
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                Role.valueOf("ROLE_USER")
        );
    }

    @DisplayName("회원가입 시 이름은 2~15자로 입력해야 한다.")
    @Test
    void validationFailWithName1() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rkskek12#",
                "김",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        // when, then
        assertThatThrownBy(() -> memberService.signup(request, imageFile))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("이름은 2~15자 입력 가능합니다.");
    }

    @DisplayName("회원가입 시 이름은 2~15자로 입력해야 한다.")
    @Test
    void validationFailWithName2() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rkskek12#",
                "김천재김천재김천재김천재김천재김",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양산길 21 107호",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        // when, then
        assertThatThrownBy(() -> memberService.signup(request, imageFile))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("이름은 2~15자 입력 가능합니다.");
    }

    @DisplayName("회원가입 시 주소는 5~200자로 입력해야 한다.")
    @Test
    void validationSuccessWithAddress1() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rkskek12#",
                "김천",
                "FEMALE",
                "testMember@naver.com",
                "서울시 양",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        doNothing().when(imageUtil).upload(any(MultipartFile.class), any(String.class), any(String.class));

        // when
        Member savedMember = memberService.signup(request, imageFile);

        // then
        assertThat(savedMember).extracting(
                "userId",
                "password",
                "details.name",
                "details.gender",
                "details.mail",
                "details.address",
                "details.phoneNumber",
                "details.role"
        ).containsExactly(
                "skdltm12",
                "rkskek12#",
                "김천",
                Gender.valueOf("FEMALE"),
                "testMember@naver.com",
                "서울시 양",
                "010-2222-2222",
                Role.valueOf("ROLE_USER")
        );
    }

    @DisplayName("회원가입 시 주소는 5~200자로 입력해야 한다.")
    @Test
    void validationSuccessWithAddress2() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rkskek12#",
                "김천",
                "FEMALE",
                "testMember@naver.com",
                "서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울" +
                        "서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울" +
                        "서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울" +
                        "서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        doNothing().when(imageUtil).upload(any(MultipartFile.class), any(String.class), any(String.class));

        // when
        Member savedMember = memberService.signup(request, imageFile);

        // then
        assertThat(savedMember).extracting(
                "userId",
                "password",
                "details.name",
                "details.gender",
                "details.mail",
                "details.address",
                "details.phoneNumber",
                "details.role"
        ).containsExactly(
                "skdltm12",
                "rkskek12#",
                "김천",
                Gender.valueOf("FEMALE"),
                "testMember@naver.com",
                "서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울" +
                        "서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울" +
                        "서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울" +
                        "서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울",
                "010-2222-2222",
                Role.valueOf("ROLE_USER")
        );
    }

    @DisplayName("회원가입 시 주소는 5~200자로 입력해야 한다.")
    @Test
    void validationFailWithAddress1() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rkskek12#",
                "김천재",
                "FEMALE",
                "testMember@naver.com",
                "서울시동",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        // when, then
        assertThatThrownBy(() -> memberService.signup(request, imageFile))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("주소는 5~200자 입력 가능합니다.");
    }

    @DisplayName("회원가입 시 주소는 5~200자로 입력해야 한다.")
    @Test
    void validationFailWithAddress2() {
        // given
        MemberJoinServiceRequest request = createJoinRequest(
                "skdltm12",
                "rkskek12#",
                "김천재",
                "FEMALE",
                "testMember@naver.com",
                "서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울" +
                        "서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울" +
                        "서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울" +
                        "서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서울서",
                "010-2222-2222",
                "ROLE_USER"
        );
        MultipartFile imageFile = createImageFile();

        // when, then
        assertThatThrownBy(() -> memberService.signup(request, imageFile))
                .isInstanceOf(BusinessPolicyValidationException.class)
                .hasMessage("주소는 5~200자 입력 가능합니다.");
    }


    private MemberJoinServiceRequest createJoinRequest(String userId, String password, String name,
                                                       String gender, String email, String address,
                                                       String phoneNumber, String role) {
        return MemberJoinServiceRequest.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .gender(gender)
                .mail(email)
                .address(address)
                .phoneNumber(phoneNumber)
                .role(role)
                .build();
    }

    private MultipartFile createImageFile() {
        return new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
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