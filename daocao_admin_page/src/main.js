import {createApp} from 'vue'
import {createPinia} from 'pinia'

// 导入icon
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import App from './App.vue'
// 导入路由规则
import router from './router/index.js'
// 导入路由守卫
import '@/router/permission.js'


//引入pinia持久化
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate' //引入持久化插件
const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
// 使用持久化
const pinia = createPinia() //创建pinia实例
pinia.use(piniaPluginPersistedstate) //将插件添加到 pinia 实例上
// 自注册全局组件
app.use(pinia)
app.use(router)
app.mount('#app')
