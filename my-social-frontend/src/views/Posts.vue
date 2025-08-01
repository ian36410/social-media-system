<template>
  <div>
    <h2>貼文牆</h2>
    <div v-if="!isLogin">
      <p>請先登入才能發文與留言</p>
    </div>
    <div v-else>
      <!-- 發文區 -->
      <input v-model="newPostContent" placeholder="想說些什麼..." />
      <button @click="addPost">發文</button>
    </div>
    <hr>
    <div v-for="post in posts" :key="post.postId" class="post-card">
      <div>{{ post.userName }}：{{ post.content }}</div>
      <div style="margin-left:1em;">
        <b>留言：</b>
        <div v-for="comment in post.comments" :key="comment.commentId">
          {{ comment.userName }}：{{ comment.content }}
        </div>
        <div v-if="isLogin">
          <input v-model="commentInputs[post.postId]" placeholder="留言..." />
          <button @click="addComment(post.postId)">送出</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const posts = ref([])
const newPostContent = ref('')
const commentInputs = ref({})
const isLogin = !!localStorage.getItem('token') // 這邊判斷有無登入

// 取得所有貼文
const fetchPosts = async () => {
  const res = await axios.get('http://localhost:8080/api/posts')
  posts.value = res.data
  // 初始化每個post的留言input
  posts.value.forEach(p => { commentInputs.value[p.postId] = '' })
}

onMounted(fetchPosts)

// 發文
const addPost = async () => {
  await axios.post('http://localhost:8080/api/posts', {
    content: newPostContent.value
  }, {
    headers: { Authorization: 'Bearer ' + localStorage.getItem('token') }
  })
  newPostContent.value = ''
  fetchPosts()
}

// 留言
const addComment = postId => async () => {
  await axios.post('http://localhost:8080/api/comments', {
    postId,
    content: commentInputs.value[postId]
  }, {
    headers: { Authorization: 'Bearer ' + localStorage.getItem('token') }
  })
  commentInputs.value[postId] = ''
  fetchPosts()
}
</script>

<style>
.post-card { border:1px solid #ddd; margin-bottom:1em; padding:1em; border-radius:6px; }
</style>