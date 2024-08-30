<script setup>
import '../assets/login.css'
import { RouterLink } from 'vue-router';
</script>
<template>
<div class="align-items-center py-4  container">
  <div class="form-signin w-100 m-auto">
    <form>
      <h1 class="h3 mb-3 fw-normal" style="color: #B40431">Move24</h1>

      <div class="form-floating">
        <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com">
        <label for="floatingInput">아이디</label>
      </div>
      <div class="form-floating">
        <input type="password" class="form-control" id="floatingPassword" placeholder="Password">
        <label for="floatingPassword">비밀번호</label>
      </div>
      <button class="btn btn-outline-danger w-100 py-2" type="submit">로그인</button>
      <RouterLink to="/members/new">
        <button type="button" class="btn btn-danger w-100 py-2 mt-2">회원 가입</button>
      </RouterLink>
    </form>
  </div>
</div>
</template>
<style scoped>
</style>