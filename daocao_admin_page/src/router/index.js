import {createRouter, createWebHistory} from 'vue-router'
import Login from '../views/Login.vue'
import index from "@/views/index.vue";



//创建路由
const constRouter = [
    {
        path: '',
        redirect: '/login',
    },
    //重定向
    {
        path: '/login',
        name: 'login',
        component: Login
    }
]

//创建路由
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    //使用路由规则常量
    routes: constRouter
})

export default router
