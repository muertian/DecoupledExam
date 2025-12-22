import { createPinia } from "pinia";   // 导入模块
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
const pinia = createPinia();           // 创建实例
pinia.use(piniaPluginPersistedstate)
export default pinia;                  // 默认导出