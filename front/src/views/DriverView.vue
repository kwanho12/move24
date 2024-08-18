<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import axios from "axios";

const route = useRoute();
const driverId = ref(route.params.driverId);
const responseData = ref("");

const getImageUrl = (fileName) => {
  return "/member/" + fileName;
};

const fetchPost = async () => {
  try {
    const response = await axios.get(`/api/drivers/${driverId.value}`);
    responseData.value = response.data;
    console.log(response.data);
  } catch (error) {
    console.error(error.response ? error.response.data : error.message);
  }
};

onMounted(() => {
  fetchPost();
});
</script>
<template>
  <div class="d-flex justify-content-center">
    <div class="card shadow-lg" style="width: 25%">
      <div class="card-header">{{ responseData.name }} 기사님</div>
      <img
        :src="getImageUrl(responseData.fileName)"
        class="card-img-top"
        alt="..."
      />
      <div class="card-body">
        <h6 class="card-title">소개</h6>
        {{ responseData.content }}
      </div>
      <ul class="list-group list-group-flush">
        <li class="list-group-item">
          경력 : {{ responseData.experienceYear }}년
        </li>
        <li class="list-group-item">성별 : {{ responseData.gender }}</li>
        <li class="list-group-item">이메일 : {{ responseData.mail }}</li>
        <li class="list-group-item">
          휴대폰 번호 : {{ responseData.phoneNumber }}
        </li>
      </ul>
      <div
        class="card-footer d-flex justify-content-between align-items-center"
      >
        <div>{{ responseData.likeCount }} like</div>
        <div class="btn-group">
          <button type="button" class="btn btn-danger">좋아요</button>
        </div>
      </div>
    </div>
  </div>
</template>
