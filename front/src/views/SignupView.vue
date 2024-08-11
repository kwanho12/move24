<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRouter } from 'vue-router';

const memberId = ref("");
const password = ref("");
const name = ref("");
const gender = ref("");
const email = ref("");
const address = ref("");
const phoneNumber = ref("");
const role = ref("");
const file = ref(null);
const idCheckMessage = ref("");
const isIdAvailable = ref(false);
const router = useRouter(); 

const onFileChange = (event) => {
  file.value = event.target.files[0];
};

const checkId = async () => {
  try {
    const response = await axios.post("/api/signup/check-id", { memberId: memberId.value });

    if (!response.data) {
      idCheckMessage.value = "사용 가능한 아이디입니다.";
      isIdAvailable.value = true;
    } else {
      idCheckMessage.value = "이미 사용 중인 아이디입니다. 다른 아이디를 입력해주세요.";
      isIdAvailable.value = false;
    }
  } catch (error) {
    console.log('아이디 중복 확인 오류: ', error.response ? error.response.data : error.message);
    idCheckMessage.value = "아이디 확인 중 오류가 발생했습니다.";
    isIdAvailable.value = false;
  }
};

const register = async () => {
  if (!isIdAvailable.value) {
    alert("아이디 중복 확인을 해주세요.");
    return;
  }

  const requestData = {
    memberId: memberId.value,
    password: password.value,
    name: name.value,
    gender: gender.value,
    mail: email.value,
    address: address.value,
    phoneNumber: phoneNumber.value,
    role: role.value,
  };

  const formData = new FormData();
  formData.append("file", file.value);
  formData.append(
    "request",
    new Blob([JSON.stringify(requestData)], {
      type: "application/json",
    })
  );

  try {
    const response = await axios.post("/api/signup", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });

    if (response.status === 200) {
      router.replace("/");
    }
  } catch (error) {
    console.log('회원가입 오류: ', error.response ? error.response.data : error.message);
  }
};
</script>

<template>
  <div class="container">
    <main>
      <div class="row">
        <div class="col-xl-3"></div>
        <div class="col-xl-6">
          <h4 class="mb-3" style="color: #b40431">Move24</h4>
          <form @submit.prevent="register" novalidate>
            <div class="row g-3">
              <div class="col-sm-10">
                <input
                  type="text"
                  class="form-control"
                  placeholder="아이디"
                  v-model="memberId"
                  required
                />
              </div>
              <div class="col-sm-2">
                <button type="button" class="btn btn-danger" @click="checkId">중복 확인</button>
              </div>
              <div class="col-12" v-if="idCheckMessage">
                <p>{{ idCheckMessage }}</p>
              </div>
              <div class="col-12">
                <input
                  type="password"
                  class="form-control"
                  placeholder="비밀번호"
                  v-model="password"
                  required
                />
              </div>
              <div class="col-sm-6">
                <input
                  type="text"
                  class="form-control"
                  v-model="name"
                  placeholder="이름"
                  required
                />
              </div>
              <div class="col-sm-6">
                <select
                  class="form-select"
                  v-model="gender"
                  required
                >
                  <option value="">성별</option>
                  <option value="MALE">남성</option>
                  <option value="FEMALE">여성</option>
                </select>
              </div>

              <div class="col-12">
                <input
                  type="email"
                  class="form-control"
                  v-model="email"
                  placeholder="이메일"
                />
              </div>

              <div class="col-sm-6">
                <input
                  type="text"
                  class="form-control"
                  v-model="address"
                  placeholder="주소"
                  required
                />
              </div>

              <div class="col-sm-6">
                <input
                  type="text"
                  class="form-control"
                  placeholder="주소 상세"
                  required
                />
              </div>

              <div class="col-sm-6">
                <input
                  type="text"
                  class="form-control"
                  v-model="phoneNumber"
                  placeholder="휴대폰 번호"
                />
              </div>

              <div class="col-sm-6">
                <select
                  class="form-select"
                  v-model="role"
                  required
                >
                  <option value="">ROLE</option>
                  <option value="ROLE_USER">사용자</option>
                  <option value="ROLE_DRIVER">기사</option>
                </select>
              </div>

              <div class="col-12">
                <label for="image" class="mb-3">이미지</label>
                <input
                  type="file"
                  class="form-control"
                  placeholder="이미지"
                  @change="onFileChange"
                />
              </div>
            </div>
            <button class="w-100 btn btn-danger btn-lg mb-3 mt-3" type="submit">
              회원가입
            </button>
          </form>
        </div>
        <div class="col-xl-3"></div>
      </div>
    </main>
  </div>
</template>
