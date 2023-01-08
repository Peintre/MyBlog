import { createStore } from 'vuex'

// 创建一个新的 store 实例
const store = createStore({
  state () {
    return {
      mbMenu: false,
      count: 0,
      modelFlag: false,
      theme: 'light',
      userInfo:{
        userId: null,
        avatar: null,
        nickname: null,
        intro: null,
        webSite: null,
        loginType: null,
        email: null,
        articleLikeSet: [],
        commentLikeSet: [],
        talkLikeSet: []
      }
    }
  },
  getters: {
    getTheme (state) {
      let theme = localStorage.getItem('blog_theme')
      if(theme){
        state.theme = theme
      }
      return state.theme
    },
    getUserInfo(state){
      let userInfo = localStorage.getItem('blog_userInfo')
      if(userInfo){
        state.userInfo = JSON.parse(userInfo)
      }
      return state.userInfo
    }
  },
  mutations: {
    increment (state) {
      state.count++
    },
    openMbMenu(state,mbMenu) {
      state.mbMenu = mbMenu
    },
    setModelFlag(state,modelFlag){
      state.modelFlag = modelFlag
    },
    changeTheme(state,theme){
      localStorage.setItem('blog_theme', theme)
      state.theme = theme
    },
    setUserInfo(state,userInfo){
      state.userInfo.userId = userInfo.userInfoId
      state.userInfo.avatar = userInfo.avatar
      state.userInfo.nickname = userInfo.nickname
      state.userInfo.intro = userInfo.intro
      state.userInfo.webSite = userInfo.webSite
      state.userInfo.articleLikeSet = userInfo.articleLikeSet ? userInfo.articleLikeSet : []
      state.userInfo.commentLikeSet = userInfo.commentLikeSet ? userInfo.commentLikeSet : []
      state.userInfo.talkLikeSet = userInfo.talkLikeSet ? userInfo.talkLikeSet : []
      state.userInfo.email = userInfo.email
      state.userInfo.loginType = userInfo.loginType
      localStorage.setItem('blog_userInfo', JSON.stringify(state.userInfo))
    },
    resetUserInfo(state) {
      state.userInfo.userId = null
      state.userInfo.avatar = null
      state.userInfo.nickname = null
      state.userInfo.intro = null
      state.userInfo.webSite = null
      state.userInfo.articleLikeSet = []
      state.userInfo.commentLikeSet = []
      state.userInfo.talkLikeSet = []
      state.userInfo.email = null
      state.userInfo.loginType = null
      localStorage.removeItem('blog_userInfo')
    }
  }
})

export default store