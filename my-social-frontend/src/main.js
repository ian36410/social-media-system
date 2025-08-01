import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router'
import axios from 'axios'

axios.defaults.baseURL = 'http://localhost:8080/api'
createApp(App).use(router).mount('#app')

axios.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  error => Promise.reject(error)
);