import { createApp } from 'vue'
import App from '@/App.vue'
import router from "@/router"
import axios from "axios"
import VueAxios from "vue-axios"
import store from "@/store"

//element-plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

//alibaba矢量图标
import "@/assets/css/iconfont.css"

//静态CSS
import "@/assets/css/index.css"

//v-md-editor进阶版
import VMdEditor from '@kangc/v-md-editor/lib/codemirror-editor';
import '@kangc/v-md-editor/lib/style/codemirror-editor.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
// 代码高亮插件
import hljs from 'highlight.js';
//Tip提示信息插件
import createTipPlugin from '@kangc/v-md-editor/lib/plugins/tip/index';
import '@kangc/v-md-editor/lib/plugins/tip/tip.css';
//引入emoji插件支持表情
import createEmojiPlugin from '@kangc/v-md-editor/lib/plugins/emoji/index';
import '@kangc/v-md-editor/lib/plugins/emoji/emoji.css';
//引入代码行号插件
import createLineNumbertPlugin from '@kangc/v-md-editor/lib/plugins/line-number/index';
//引入内容定位插件
import createAlignPlugin from '@kangc/v-md-editor/lib/plugins/align';


// codemirror 编辑器的相关资源
import Codemirror from 'codemirror';
// mode
import 'codemirror/mode/markdown/markdown';
import 'codemirror/mode/javascript/javascript';
import 'codemirror/mode/css/css';
import 'codemirror/mode/htmlmixed/htmlmixed';
import 'codemirror/mode/vue/vue';
// edit
import 'codemirror/addon/edit/closebrackets';
import 'codemirror/addon/edit/closetag';
import 'codemirror/addon/edit/matchbrackets';
// placeholder
import 'codemirror/addon/display/placeholder';
// active-line
import 'codemirror/addon/selection/active-line';
// scrollbar
import 'codemirror/addon/scroll/simplescrollbars';
import 'codemirror/addon/scroll/simplescrollbars.css';
// style
import 'codemirror/lib/codemirror.css';

VMdEditor.Codemirror = Codemirror
VMdEditor.use(githubTheme, {
  Hljs: hljs,
})
  .use(createTipPlugin())
  .use(createEmojiPlugin())
  .use(createLineNumbertPlugin())
  .use(createAlignPlugin())



const app = createApp(App)
// 注册element图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.use(router)
   .use(VueAxios,axios)
   .use(store)
   .use(ElementPlus)
   .use(VMdEditor)
app.mount('#app')
