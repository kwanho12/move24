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
const file = ref(null);
const router = useRouter(); 

const onFileChange = (event) => {
  file.value = event.target.files[0];
};

const register = async () => {
  const requestData = {
    memberId: memberId.value,
    password: password.value,
    name: name.value,
    gender: gender.value,
    mail: email.value,
    address: address.value,
    phoneNumber: phoneNumber.value,
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
    const response = await axios.post("/api/join", formData, {
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
              <div class="col-12">
                <input
                  type="text"
                  class="form-control"
                  id="memberId"
                  placeholder="아이디"
                  v-model="memberId"
                  required
                />
              </div>
              <div class="col-12">
                <input
                  type="password"
                  class="form-control"
                  id="password"
                  placeholder="비밀번호"
                  v-model="password"
                  required
                />
              </div>
              <div class="col-sm-6">
                <input
                  type="text"
                  class="form-control"
                  id="name"
                  v-model="name"
                  placeholder="이름"
                  required
                />
              </div>
              <div class="col-sm-6">
                <select
                  class="form-select"
                  id="gender"
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
                  id="email"
                  v-model="email"
                  placeholder="이메일"
                />
              </div>

              <div class="col-sm-6">
                <input
                  type="text"
                  class="form-control"
                  id="address"
                  v-model="address"
                  placeholder="주소"
                  required
                />
              </div>

              <div class="col-sm-6">
                <input
                  type="text"
                  class="form-control"
                  id="addressDetail"
                  placeholder="주소 상세"
                  required
                />
              </div>

              <div class="col-12">
                <input
                  type="text"
                  class="form-control"
                  id="phoneNumber"
                  v-model="phoneNumber"
                  placeholder="휴대폰 번호"
                />
              </div>

              <div class="col-12">
                <label for="image" class="mb-3">이미지</label>
                <input
                  type="file"
                  class="form-control"
                  id="image"
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
