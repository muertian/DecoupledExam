<template>
  <div class="min-h-screen bg-base-200">
    <!-- 顶部导航栏 -->
    <div class="navbar bg-base-100 shadow-md">
      <div class="flex-1">
        <a class="btn btn-ghost text-xl">管理员面板</a>
      </div>
      <div class="flex-none">
        <div class="dropdown dropdown-end">
          <div tabindex="0" role="button" class="btn btn-ghost btn-circle avatar">
            <div class="w-10 rounded-full">
              <img alt="头像" src="https://daisyui.com/images/stock/photo-1534528741775-53994a69daeb.jpg" />
            </div>
          </div>
          <ul tabindex="0" class="mt-3 z-[1] p-2 shadow menu menu-sm dropdown-content bg-base-100 rounded-box w-52">
            <li>
              <router-link to="/admin/profile">
                <span>个人资料</span>
              </router-link>
            </li>
            <li>
              <a @click="logout">退出登录</a>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <!-- 主内容区域 -->
    <div class="p-6">
      <div class="bg-base-100 rounded-lg shadow min-h-[calc(100vh-140px)]">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
import { useMainStore } from '../../stores';
import { storeToRefs } from 'pinia';
import router from '../../routers';

const logout = () => {
  // 清除登录状态
  const store = useMainStore();
  const { useLoginStore } = store;
  useLoginStore().setLogin(false);
  
  // 清除本地存储的token
  localStorage.removeItem('token');
  
  // 跳转到登录页
  router.push('/login');
};

onMounted(() => {
  // 检查用户是否已登录
  const store = useMainStore();
  const { useLoginStore } = store;
  const { loginSession } = storeToRefs(useLoginStore());

  if (!loginSession.value) {
    router.push('/login');
  }
});
</script>

<style scoped>
.router-link-active {
  background-color: var(--fallback-p, oklch(0.78039 0.19907 274.37));
  color: white;
}
</style>