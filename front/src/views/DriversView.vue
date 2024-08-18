<script setup>
import { RouterLink } from "vue-router";
import axios from "axios";
import { ref, onMounted } from "vue";

const page = ref(null);
const size = ref(null);
const responseData = ref("");

const getImageUrl = (fileName) => {
  return "/member/" + fileName;
};

const getDriverUrl = (driverId) => {
  return "/drivers/" + driverId;
};

const fetchPosts = async () => {
  try {
    const response = await axios.get("/api/drivers", {
      params: {
        page: page.value,
        size: size.value,
      },
    });
    console.log(response.data);
    responseData.value = response.data.content;
  } catch (error) {
    console.error(error.response ? error.response.data : error.message);
  }
};

onMounted(() => {
  fetchPosts();
});
</script>
<template>
  <main>
    <div class="album py-5 bg-body-tertiary">
      <div class="container">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

          <div v-for="data in responseData" class="col">
            <div class="card shadow-lg">
              <div class="card-header">{{ data.name }} 기사님</div>
              <img
                class="bd-placeholder-img card-img-top"
                width="100%"
                height="300"
                :src="getImageUrl(data.fileName)"
              />
              <ul class="list-group list-group-flush">
                <li class="list-group-item">
                  경력 : {{ data.experienceYear }}년
                </li>
                <li class="list-group-item">
                  <div
                    class="d-flex justify-content-between align-items-center"
                  >
                    <div class="btn-group">
                      <RouterLink :to="getDriverUrl(data.driverId)">
                        <button
                          type="button"
                          class="btn btn-sm btn-outline-secondary"
                        >
                          상세 정보 & 후기
                        </button>
                      </RouterLink>
                    </div>
                    <small class="text-body-secondary"
                      >{{ data.likeCount }} like
                    </small>
                  </div>
                </li>
              </ul>
            </div>
          </div>

        </div>
        <div class="text-end">
          <RouterLink to="/post-driver">
            <button class="btn btn-danger mt-3 mb-3" type="button">기사 등록</button>
          </RouterLink>
        </div>
        <div class="d-flex justify-content-center">
          <nav>
            <ul class="pagination">
              <li class="page-item">
                <a class="page-link" href="#" aria-label="Previous">
                  <span aria-hidden="true">&laquo;</span>
                </a>
              </li>
              <li class="page-item">
                <a class="page-link" href="#">이전</a>
              </li>
              <li class="page-item"><a class="page-link" href="#">1</a></li>
              <li class="page-item"><a class="page-link" href="#">2</a></li>
              <li class="page-item"><a class="page-link" href="#">3</a></li>
              <li class="page-item"><a class="page-link" href="#">4</a></li>
              <li class="page-item"><a class="page-link" href="#">5</a></li>
              <li class="page-item"><a class="page-link" href="#">6</a></li>
              <li class="page-item"><a class="page-link" href="#">7</a></li>
              <li class="page-item"><a class="page-link" href="#">8</a></li>
              <li class="page-item"><a class="page-link" href="#">9</a></li>
              <li class="page-item"><a class="page-link" href="#">10</a></li>
              <li class="page-item"><a class="page-link" href="#">다음</a></li>
              <li class="page-item">
                <a class="page-link" href="#" aria-label="Next">
                  <span aria-hidden="true">&raquo;</span>
                </a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  </main>
</template>
<style scoped>
.bd-placeholder-img {
  font-size: 1.125rem;
  text-anchor: middle;
  -webkit-user-select: none;
  -moz-user-select: none;
  user-select: none;
}

@media (min-width: 768px) {
  .bd-placeholder-img-lg {
    font-size: 3.5rem;
  }
}

.b-example-divider {
  width: 100%;
  height: 3rem;
  background-color: rgba(0, 0, 0, 0.1);
  border: solid rgba(0, 0, 0, 0.15);
  border-width: 1px 0;
  box-shadow: inset 0 0.5em 1.5em rgba(0, 0, 0, 0.1),
    inset 0 0.125em 0.5em rgba(0, 0, 0, 0.15);
}

.b-example-vr {
  flex-shrink: 0;
  width: 1.5rem;
  height: 100vh;
}

.bi {
  vertical-align: -0.125em;
  fill: currentColor;
}

.nav-scroller {
  position: relative;
  z-index: 2;
  height: 2.75rem;
  overflow-y: hidden;
}

.nav-scroller .nav {
  display: flex;
  flex-wrap: nowrap;
  padding-bottom: 1rem;
  margin-top: -1px;
  overflow-x: auto;
  text-align: center;
  white-space: nowrap;
  -webkit-overflow-scrolling: touch;
}

.btn-bd-primary {
  --bd-violet-bg: #712cf9;
  --bd-violet-rgb: 112.520718, 44.062154, 249.437846;

  --bs-btn-font-weight: 600;
  --bs-btn-color: var(--bs-white);
  --bs-btn-bg: var(--bd-violet-bg);
  --bs-btn-border-color: var(--bd-violet-bg);
  --bs-btn-hover-color: var(--bs-white);
  --bs-btn-hover-bg: #6528e0;
  --bs-btn-hover-border-color: #6528e0;
  --bs-btn-focus-shadow-rgb: var(--bd-violet-rgb);
  --bs-btn-active-color: var(--bs-btn-hover-color);
  --bs-btn-active-bg: #5a23c8;
  --bs-btn-active-border-color: #5a23c8;
}

.bd-mode-toggle {
  z-index: 1500;
}

.bd-mode-toggle .dropdown-menu .active .bi {
  display: block !important;
}

.page-link {
  color: #b40431;
}
</style>
