import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'

export default createStore({  
    state: {
        collapse: false,
        userId: null,
        roleList: null,
        avatar: null,
        nickname: null,
        intro: null,
        webSite: null,
        userMenuList:[],  //菜单
        existMenuList:false,
        tabList: [{ name: "首页", path: "/home" }],
    },  
    getters:{
        existMenuList(state) {
            return state.existMenuList  
        }
    },
    mutations: {
        login(state, user) {
            state.userId = user.userInfoId
            state.roleList = user.roleList
            state.avatar = user.avatar
            state.nickname = user.nickname
            state.intro = user.intro
            state.webSite = user.webSite
        },   
        saveUserMenuList(state,userMenuList) {
            state.userMenuList = userMenuList
            state.existMenuList = true
        },
        existMenu(state) {
            state.existMenuList = true
        },
        trigger(state) {
            state.collapse = !state.collapse
        },
        saveTab(state, tab) {
            if (state.tabList.findIndex(item => item.path === tab.path) == -1) {
              state.tabList.push({ name: tab.name, path: tab.path })
            }
            
        },
        resetTab(state) {
            state.tabList = [{ name: "首页", path: "/home" }]
        },
        removeTab(state, tab) {
            var index = state.tabList.findIndex(item => item.name === tab.name)
            state.tabList.splice(index, 1)
        },
        logout(state) {
            state.userId = null
            state.roleList = null
            state.avatar = null
            state.nickname = null
            state.intro = null
            state.webSite = null
            state.userMenuList = []
        },
        updateUserInfo(state,user){
            state.nickname = user.nickname
            state.intro = user.intro
            state.webSite = user.webSite
        },
        updateAvatar(state,avatar){
            state.avatar = avatar
        }
    },  
    actions: {  },  
    modules: {  },  
    plugins: [    
        // 把vuex的数据存储到sessionStorage    
        createPersistedState({      
            storage: window.sessionStorage,    
        }),  
    ],
})

