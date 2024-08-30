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
  <div class="container">
    <div class="row">
      <div class="col-lg-6">
        <div class="d-flex justify-content-center">
          <div class="card shadow-lg" style="width: 70%">
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
              <div>평점 : {{ responseData.averagePoint }}</div>
              <div class="btn-group">
                <button type="button" class="btn btn-outline-danger">
                  견적 신청
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-lg-6">
        <h3 class="mt-3">후기</h3>
        <table class="table table-bordered border-danger">
          <colgroup>
            <col width="30%" />
            <col width="70%" />
          </colgroup>
          <tr>
            <th style="text-align: center;">작성자</th>
            <th style="text-align: center;">제목</th>
          </tr>
          <tr v-if="responseData.review">
            <td></td>
            <td></td>
          </tr>
          <tr v-if="!responseData.review">
            <td colspan="2" style="text-align: center">
              작성된 후기가 없습니다.
            </td>
          </tr>
        </table>
        <button type="button" class="btn btn-outline-danger">
          후기 작성하기
        </button>
      </div>
    </div>
  </div>
</template>
