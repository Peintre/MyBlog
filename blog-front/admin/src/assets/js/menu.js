import axios from "axios";
import router from "@/router";
import store from "@/store";


// vite动态导入
////**为通配符,vite不支持require导入方式,故用import.meta.glob(vite动态导入)
const vite_modules =import.meta.glob(`@/views/**/*.vue`);



// 获取用户菜单数据
export function generaMenu(path) {
    // 使用axios查询当前用户所拥有的菜单列表
    axios.get("/api/menu/getUserMenus").then(({ data }) => {
        if(data.flag){
            store.commit('saveUserMenuList',data.data) 
            if(path) {
                var userMenuList = data.data  
                addRoute(userMenuList)
                router.push({path:path})
            }      
        }else{
            //this.$message.error(data.message)
            router.push({ path: "/login" })
        }
    })
}

//加载路由
export function addRoute(userMenuList,isAdd){
    if(!isAdd){
        router.addRoute({   
            path: '/index',
            name: 'index',
            component: () => import("@/layout/index.vue"),
        })
        isAdd=true
    }
    userMenuList.forEach(item => {
        if(item.parentName){
            router.addRoute(item.parentName,{   
                path: item.path,
                name: item.name,
                component: vite_modules[`../../views${item.component}`],
            })
        }else{
            router.addRoute({   
                path: item.path,
                name: item.name,
                component: () => import("@/layout/index.vue"),
            })
        } 
        if(item.childMenuList){
            addRoute(item.childMenuList,isAdd);
        }  
    })
}

