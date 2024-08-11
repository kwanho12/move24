<script setup>
import { RouterLink } from 'vue-router';
</script>
<template>
<main>
    <div class="container">
        <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
            <div class="col-md-3 mb-2 mb-md-0">
                <a href="/" class="d-inline-flex link-body-emphasis text-decoration-none">
                    <svg class="bi" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"/></svg>
                </a>
            </div>

            <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
                <RouterLink to="/home">
                    <li><a href="#" class="nav-link px-2">Home</a></li>
                </RouterLink>
                <RouterLink to="/drivers">
                    <li><a href="#" class="nav-link px-2">기사</a></li>
                </RouterLink>
                <li><a href="#" class="nav-link px-2">후기</a></li>
            </ul>

            <div class="col-md-3 text-end">
                <RouterLink to="/login">
                    <button type="button" class="btn btn-outline-danger me-2">로그인</button>
                </RouterLink>
                <RouterLink to="/signup">
                    <button type="button" class="btn btn-danger">회원가입</button>
                </RouterLink>
            </div>
        </header>
    </div>
</main>
</template>
<style scoped>
</style>