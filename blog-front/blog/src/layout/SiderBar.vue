<template>
    <div class="my-sider">
        <el-drawer 
            ref="myDrawer"
            v-model="mbMenu"
            :show-close="false"
            direction="rtl"
            size="250"
            :with-header="false"
        >
            <!-- 博主介绍 -->
            <div class="blogger-info">
                <el-avatar :size="110" shape="circle" style="margin-bottom:0.5rem">
                    <img :src="blogInfo.websiteConfig.websiteAvatar" />
                </el-avatar>
            </div>
            <!-- 博客信息 -->
            <div class="blog-info-wrapper">
                <div class="blog-info-data" @click="closeDrawer">
                    <router-link to="/archive">
                        <div style="font-size: 0.875rem">文章</div>
                        <div style="font-size: 1.125rem;">
                            {{ blogInfo.articleCount }}
                        </div>
                    </router-link>
                </div>
                <div class="blog-info-data" @click="closeDrawer">
                    <router-link to="/category">
                        <div style="font-size: 0.875rem">分类</div>
                        <div style="font-size: 1.125rem">
                            {{ blogInfo.categoryCount }}
                        </div>
                    </router-link>
                </div>
                <div class="blog-info-data" @click="closeDrawer">
                    <router-link to="/tag">
                        <div style="font-size: 0.875rem">标签</div>
                        <div style="font-size: 1.125rem">
                            {{ blogInfo.tagCount }}
                        </div>
                    </router-link>
                </div>
            </div>
            <hr />
            <!-- 页面导航 -->
            <div class="menu-container">
                <div class="menus-item" @click="closeDrawer">
                    <router-link to="/">
                    <i class="iconfont iconzhuye" /> 首页
                    </router-link>
                </div>
                <div class="menus-item" @click="closeDrawer">
                    <router-link to="/archive">
                    <i class="iconfont iconguidang" /> 归档
                    </router-link>
                </div>
                <div class="menus-item" @click="closeDrawer">
                    <router-link to="/album">
                    <i class="iconfont iconxiangce1" /> 相册
                    </router-link>
                </div>
                <div class="menus-item" @click="closeDrawer">
                    <router-link to="/talk">
                    <i class="iconfont iconpinglun" /> 说说
                    </router-link>
                </div>
                <div class="menus-item" @click="closeDrawer">
                    <router-link to="/category">
                    <i class="iconfont iconfenlei" /> 分类
                    </router-link>
                </div>
                <div class="menus-item" @click="closeDrawer">
                    <router-link to="/tag">
                    <i class="iconfont iconbiaoqian" /> 标签
                    </router-link>
                </div>
                <div class="menus-item pointer">
                    <a @click="openLogin"><i class="iconfont icondenglu" /> 登录 </a>
                </div>
            </div>
        </el-drawer>
    </div>
</template>

<script>
export default {
    props:{
        blogInfos:{
            type: Object,
            default() {
                return {}
            }
        }
    },
    data:function(){
        return{
            blogInfo:{
                websiteConfig:{
                    websiteAvatar:''
                },
                articleCount: 0,
                categoryCount: 0,
                tagCount: 0
            } 
        }
    },
    methods:{
        closeDrawer(){
           this.$refs.myDrawer.handleClose()
        },
        openLogin(){
            this.$store.commit('changeLoginFlag',true)
            this.$refs.myDrawer.handleClose()
        }
    },
    computed: {
        mbMenu: {
            set(value){
                this.$store.commit('openMbMenu',value)
            },
            get(){
                return this.$store.state.mbMenu
            }
        }
    },
    watch:{
        blogInfos(newVal){
            this.blogInfo = newVal
        }
    }
}
</script>

<style scoped>
.my-sider a{
    color: #000000;
}
.blogger-info {
  padding: 26px 30px 0;
  text-align: center;
}
.blog-info-wrapper {
  display: flex;
  align-items: center;
  padding: 12px 10px 0;
}
.blog-info-data {
  flex: 1;
  line-height: 2;
  text-align: center;
}
hr {
  border: 2px dashed #d2ebfd;
  margin: 20px 0;
}
.menu-container {
  padding: 0 10px 40px;
  animation: 0.8s ease 0s 1 normal none running sidebarItem;
}
.menus-item a {
  padding: 6px 30px;
  display: block;
  line-height: 2;
}
.menus-item i {
  margin-right: 2rem;
}
@keyframes sidebarItem {
  0% {
    transform: translateX(200px);
  }
  100% {
    transform: translateX(0);
  }
}
</style>