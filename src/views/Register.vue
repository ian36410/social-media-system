<template>
  <div style="max-width:400px;margin:auto">
    <h2>註冊</h2>
    <form @submit.prevent="register">
      <input v-model="userName" placeholder="手機號碼" maxlength="10" required>
      <input v-model="password" type="password" placeholder="密碼" required>
      <button type="submit">註冊</button>
    </form>
    <div v-if="error" style="color:red;">{{ error }}</div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const userName = ref('')
const password = ref('')
const error = ref('')

const register = async () => {
  try {
    await axios.post('http://localhost:8080/api/users/register', {
      userName: userName.value,
      password: password.value
    })
    alert('註冊成功！請登入')
    // 註冊成功自動跳到登入頁
  } catch (e) {
    error.value = e.response?.data?.message || '註冊失敗'
  }
}
</script>