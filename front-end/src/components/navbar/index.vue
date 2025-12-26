<template>
<div class="navbar bg-base-100 shadow-md">
  <div class="flex-1">
    <a class="btn btn-ghost text-xl">基于教考分离的考试系统</a>
  </div>
  <div class="flex-none">
    <ul v-if="userType=='1'" class="menu menu-horizontal px-1 text-base">
      <li><a href="/teacher/question">题库管理</a></li>
      <li><a>试卷管理</a></li>
    </ul>
  </div>
  <div v-if="userType" class="dropdown dropdown-end">
    <div tabindex="0" role="button" class="btn btn-ghost btn-circle avatar">
      <div class="w-10 rounded-full">
        <img
            alt="用户头像"
            :src="userAvatar || defaultAvatar" />
      </div>
    </div>
    <ul
        tabindex="0"
        class="menu menu-sm dropdown-content bg-base-100 rounded-box z-[1] mt-3 w-52 p-2 shadow">
      <li>
        <router-link to="/profile">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
          </svg>
          <span>个人资料</span>
        </router-link>
      </li>
      <li><a>设置</a></li>
      <li><a @click="logout">退出登录</a></li>
    </ul>
  </div>
</div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useMainStore } from "../../stores";
import router from "../../routers";
import { getAvatarAPI, getUserInfoByIdAPI } from "../../apis/Server/userAPI";
import { jwtDecode } from 'jwt-decode';

const userType = ref<string>(localStorage.getItem("userType") || "");
const userAvatar = ref<string | null>(null);
const defaultAvatar = 'https://daisyui.com/images/stock/photo-1534528741775-53994a69daeb.jpg';

const loadUserAvatar = async () => {
  const token = localStorage.getItem('token');
  if (!token) return;
  
  try {
    // 从token中解析用户ID来获取用户信息
    const decoded: any = jwtDecode(token);
    const userId = decoded.id; // 根据您提供的JWT，用户ID存储在id字段中
    
    // 获取用户信息，其中包含头像URL
    const response: any = await getUserInfoByIdAPI(userId, token);
    if (response && response.code === 200 && response.data) {
      const userData = response.data;
      
      // 如果用户有头像URL，直接使用
      if (userData.avatarUrl) {
        // 拼接完整的图片URL（假设后端返回的是相对路径）
        userAvatar.value = `http://localhost:80${userData.avatarUrl}`;
      } else {
        // 如果没有头像，尝试获取头像
        const avatarResponse: any = await getAvatarAPI(token);
        if (avatarResponse && avatarResponse.code === 200 && avatarResponse.data) {
          // 如果返回的是URL字符串，直接使用
          if (typeof avatarResponse.data === 'string') {
            userAvatar.value = `http://localhost:80${avatarResponse.data}`;
          } else {
            // 如果返回的是二进制数据，创建blob URL
            const blob = new Blob([avatarResponse.data], { type: avatarResponse.headers['content-type'] || 'image/jpeg' });
            userAvatar.value = URL.createObjectURL(blob);
          }
        } else {
          userAvatar.value = defaultAvatar;
        }
      }
    } else {
      userAvatar.value = defaultAvatar;
    }
  } catch (error) {
    userAvatar.value = defaultAvatar;
  }
};

onMounted(() => {
  loadUserAvatar();
});

const logout = () => {
  localStorage.removeItem("token")
  localStorage.removeItem("userType")
  useMainStore().useLoginStore().setLogin(false)
  router.push('/login').then(()=>{window.location.reload();})
}
</script>