import { defineStore } from 'pinia';
import { ref } from "vue";
// 第一个参数是应用程序中 store 的唯一 id
const loginStore = defineStore(
    'login',     //该 store 的唯一 id
    ()=>{
        const loginSession = ref(false);   // 定义了变量，设置了初始值
        const setLogin = (loginNewSession: boolean) =>{   // 改变变量的函数
            loginSession.value=loginNewSession;
        };
        return{        // 将变量与函数返回
            loginSession,
            setLogin,
        };
    },
    {
        persist:true
    }
);
export default loginStore;