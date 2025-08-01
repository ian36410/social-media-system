<template>
  <div style="max-width:500px; margin:auto; text-align:center; padding:40px;">
    <h1>歡迎來到社群網站！</h1>
    <p>請先登入或註冊以使用完整功能。</p>
    <div style="margin-top:24px;">
      <button @click="goLogin" style="margin-right:10px;">登入</button>
      <button @click="goRegister">註冊</button>
        <button @click="goToPosts">前往貼文牆</button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()

function goLogin() {
  router.push('/login')
}

function goRegister() {
  router.push('/register')
}

function goToPosts() {
  router.push('/posts')
}
</script>