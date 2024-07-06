// 导入router/index.js
import router from "@/router/index.js";
//导入 stores/menu.js
import {useMenuStore} from '@/api/stores/menu.js'
// 设置路由白名单
const whiteRouter = ['/', '/login', '/error', '/404'];
// 引入Layout
import Layout from '@/api/Layout/index.vue'
// 全局前置路由守卫
router.beforeEach((to, from, next) => {
    console.log("from", from);
    console.log("to", to);
    // 使用pinia
    const menuStore = useMenuStore();
    // 判断是否是路由白名单
    if (whiteRouter.indexOf(to.path) === -1) {
        // 判断routerList中是否有动态路由的数据
        if (menuStore.routerList.length === 0) {
            //设置动态路由数据结构，并且添加到路由中
            menuStore.generateRouter().then(() => {
                //添加动态路由
                const routerList = menuStore.routerList;
                // 所有的页面都是加载到layout/Main组件的RouterView中
                // 相当于所有的路由都是Layout的子路由
                router.addRoute(
                    {
                        component: Layout,
                        path: '/',
                        redirect: 'index',
                        children: routerList
                    }
                )
                // 跳转页面
                next({...to, replace: true})
            })
        } else {
            // 已经有动态路由了
            // 情况1：路由的路径是合法的
            if (to.matched.length !== 0) {
                next();
            } else {
                // 情况2：路由的页面并没有
                next('/404');
            }
        }
    } else {
        //直接放行
        next();
    }
})