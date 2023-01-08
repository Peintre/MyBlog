import { createApp } from 'vue'
import App from '@/App.vue'
import router from "@/router"
import store from '@/store'

import axios from 'axios'
import VueAxios from 'vue-axios'


import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'element-plus/theme-chalk/display.css'
import 'element-plus/theme-chalk/dark/css-vars.css'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'

import "@/assets/css/iconfont.css"
import "@/assets/css/index.css"
import "@/assets/css/markdown.css"


const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }

app.use(router).use(ElementPlus).use(store).use(VueAxios, axios)
app.mount('#app')
