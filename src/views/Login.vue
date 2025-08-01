<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()
const userName = ref('')
const password = ref('')
const error = ref('')

const login = async () => {
  try {
    const res = await axios.post('http://localhost:8080/api/users/login', {
      userName: userName.value,
      password: password.value
    })
    // 儲存 token（假設後端回傳 res.data.token）
    localStorage.setItem('token', res.data.token)
    // 跳轉到貼文牆
    router.push('/posts')
  } catch (e) {
    error.value = e.response?.data?.message || '登入失敗'
  }
}
</script>
