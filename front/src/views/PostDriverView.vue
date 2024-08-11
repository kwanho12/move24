<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const driverId = ref("");
const experienceYear = ref("");
const content = ref("");
const router = useRouter(); 

const register = async () => {
  const requestData = {
    experienceYear: experienceYear.value,
    content: content.value,
  };

  try {
    const response = await axios.post("api/post-driver", requestData);

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
  <div class="container">
    <div class="row">
      <h6>경력</h6>
      <div class="d-flex">
        <input type="number" class="mb-3" v-model="experienceYear" />년
      </div>
      <h6>설명</h6>
      <textarea class="mb-3" v-model="content"></textarea>
      <button type="submit" class="btn btn-outline-danger" @click="register">
        작성하기
      </button>
    </div>
  </div>
</template>
<style scoped>
textarea {
  resize: none;
  height: 100px;
}
</style>
