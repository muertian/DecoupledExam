<template>
  <div class="min-h-screen bg-base-200">
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