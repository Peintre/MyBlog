import {createRouter,createWebHashHistory} from 'vue-router'
import NProgress from "nprogress";
import "nprogress/nprogress.css";



const routes = [
    {
        path: "/layout",
        component: () => import("@/layout/index.vue"),
        children: [
            {
                path: '/home',
                component: () => import('@/views/home/Home.vue'),
            },
            {
                path: '/article/:id',
                component: () => import('@/views/article/Article.vue'),
            },
            {
                path: '/archive',
                component: () => import('@/views/archive/Archive.vue'),
            },
            {
                path: '/category',
                component: () => import('@/views/category/Category.vue'),
            },
            {
                path: '/category/:id',
                component: () => import('@/views/article/ArticleList.vue'),
            },
            {
                path: '/tag',
                component: () => import('@/views/tag/Tag.vue'),
            },
            {
                path: '/tag/:id',
                component: () => import('@/views/article/ArticleList.vue'),
            },
            {
                path: '/album',
                component: () => import('@/views/album/Album.vue'),
            },
            {
                path: '/album/:id',
                component: () => import('@/views/album/Photo.vue'),
            },
            {
                path: '/talk',
                component: () => import('@/views/talk/Talk.vue'),
            },
            {
                path: '/talk/:id',
                component: () => import('@/views/talk/TalkInfo.vue'),
            },
            {
                path: '/user',
                component: () => import('@/views/user/User.vue'),
            },
        ]
    }
]

const router = createRouter({
    routes: routes,
    history: createWebHashHistory(),
    scrollBehavior(to, from, savedPosition) {
        // 切换路由时始终滚动到顶部
        if(to.path === from.path){
            return {}
        }
        return { top: 0 }
    },
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
    if (to.path === '/') {
        // 将用户重定向到首页
        next({ path: '/home' });
    }else{ 
        next();    
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



