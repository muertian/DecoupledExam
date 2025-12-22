import { createApp } from 'vue'
import App from './App.vue'
import router from "./routers";
import pinia from './stores/createPinia';
import "tailwindcss/tailwind.css";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App); // 创建app实例，其中使用了"App"根组件

for (const [key, component] of Object.entries(ElementPlusIconsVue)){
    app.component(key, component)
}

app.use(router);                  // app实例使用router实例
app.use(pinia);
app.use(ElementPlus)
app.mount("#app");                // 全局挂载app实例
