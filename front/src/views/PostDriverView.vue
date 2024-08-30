<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const driverId = ref("sksmss11");
const experienceYear = ref("");
const content = ref("");
const router = useRouter();

const register = async () => {
  const requestData = {
    driverId: driverId.value,
    experienceYear: experienceYear.value,
    content: content.value,
  };

  try {
    const response = await axios.post("api/drivers/new", requestData);
    if (response.status === 200) {
      router.replace("/drivers");
    }
  } catch (error) {
    console.log(
      "post-driver 오류: ",
      error.response ? error.response.data : error.message
    );
  }
};

</script>
<template>
  <form @submit.prevent="register" id="submit">
    <div class="container">
      <div class="row">
        <div class="col-xl-3"></div>
        <div class="col-xl-6">
          <h3 class="mb-3" style="color: #b40431;">기사 홍보 글 작성</h3>
          <h6>경력</h6>
          <div class="d-flex align-items-center">
            <input
              type="number"
              class="mb-3 ml-3 form-control border-danger"
              v-model="experienceYear"
              style="width: 7%"
            />
            <p class="ml-3">년</p>
          </div>
          <h6>설명</h6>
          <textarea class="mb-3 form-control border-danger" v-model="content" style="height: 70%;"></textarea>
          <button type="submit" class="btn btn-outline-danger">작성하기</button>
        </div>
        <div class="col-xl-3"></div>
      </div>
    </div>
  </form>
</template>
<style scoped>
textarea {
  resize: none;
  height: 100px;
}
</style>
