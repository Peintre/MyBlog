import {createRouter,createWebHashHistory} from 'vue-router'
import store from "@/store";
import { addRoute} from '@/assets/js/menu';
import NProgress from "nprogress";
import "nprogress/nprogress.css";



const routes = [
    {
        path: "/login",
        name: "登录",
        hidden: true,
        component: () => import("@/views/login/Login.vue"),
    },
    {
        path: '/404',
        name: '404',
        component: () => import('@/views/error/404.vue')
    }

]

const router = createRouter({
    routes: routes,
    history: createWebHashHistory()
})


//进度条配置
NProgress.configure({
    easing: "ease", // 动画方式
    speed: 500, // 递增进度条的速度
    showSpinner: false, // 是否显示加载ico
    trickleSpeed: 200, // 自动递增间隔
    minimum: 0.3 // 初始化时的最小百分比
  });


//路由前置守卫
//to：即将要进入的目标路由对象；
//from：当前导航即将要离开的路由对象；
router.beforeEach(async (to, from, next) => {
    NProgress.start();
    console.log('进入路由守卫')
    if (to.path === '/') {
        // 将用户重定向到登录页面
        next({ path: '/login' });
    }else if(to.path === '/login'){
        next();
    }else{ 
        if(store.state.userId != null){   
            if (to.matched.length ===0) {
                addRoute(store.state.userMenuList)
                next({path: to.path})
            }
            else next()
        }else{
            next({ path: '/login' });
        }     
    }
})

router.afterEach(() => {
    NProgress.done();
  });

export function resetRouter() {
    const newRouter = createRouter({
        routes: routes,
        history: createWebHashHistory()
    })
    router.matcher = newRouter.matcher;
}

export default router

//next({ ...to, replace: true })


