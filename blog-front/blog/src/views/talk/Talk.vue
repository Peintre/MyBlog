<template>
  <div class="my-main">
      <Banner :imageUrl="cover" :pageTitle="pageTitle"></Banner>
      <div class="card-content">
            <!-- 卡片 -->
            <el-card 
            class="my-talk-container" 
            shadow="always" 
            v-for="item of talkList" 
            :key="item.id"
            >
                <router-link :to="'/talk/' + item.id">
                    <div class="user-info-wrapper">
                        <!-- 左侧头像 -->
                        <div class="left-info">
                            <el-avatar class="user-avatar" :size="36" :src="item.avatar"/>
                        </div>
                        <!-- 右侧内容 -->
                        <div class="right-info">
                            <!-- 用户名 -->
                            <div class="user-nickname">
                                {{ item.nickname }}
                                <el-icon :size="20" color="#ffa51e"><StarFilled /></el-icon>
                            </div>
                            <!-- 发表时间 -->
                            <div class="time">
                                {{ dataFormat(item.createTime)}}
                                <span class="top" v-if="item.isTop == 1">
                                    <i class="iconfont iconzhiding" /> 置顶
                                </span>
                            </div>
                            <!-- 说说信息 -->
                            <div class="talk-content" v-html="item.content" />
                            <!-- 图片列表 -->
                            <el-row class="talk-images" v-if="item.imgList">
                                <el-col
                                :md="8"
                                :cols="12"
                                v-for="(img, index) of item.imgList"
                                :key="index"
                                >
                                <el-image
                                    class="images-items"
                                    :src="img"
                                    aspect-ratio="1"
                                    max-height="200"
                                    @click.prevent="previewImg(img)"
                                />
                                </el-col>
                            </el-row>
                            <!-- 说说操作 -->
                            <div class="talk-operation">
                                <div class="talk-operation-item">
                                    <el-icon
                                        :size="16"
                                        :color="isLike(item.id)"
                                        class="like-btn"
                                        @click.prevent="like(item)"
                                    >
                                        <StarFilled />
                                    </el-icon>
                                    <div class="operation-count">
                                        {{ item.likeCount == null ? 0 : item.likeCount }}
                                    </div>
                                </div>
                                <div class="talk-operation-item">
                                    <el-icon
                                        :size="16" 
                                        color="#999" 
                                    >
                                        <Comment />
                                    </el-icon>
                                    <div class="operation-count">
                                        {{ item.commentCount == null ? 0 : item.commentCount }}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </router-link>
            </el-card>
      </div>
  </div>
</template>

<script>
import Banner from "@/components/Banner.vue"
import {praseDateStr} from "@/assets/js/common.js"
export default {
  props:{
      blogInfos:{
          type: Object,
          default() {
              return {}
          }
      }
  },
  components: {
      Banner
  },
  created(){
      //获取说说数据
      this.listTalks()
      //获取网站数据
      this.webConfigData() 
  },
  computed :{
      dataFormat(){
          return function(date){
              return praseDateStr(date,"YYYY-MM-DD HH:mm:ss")
          }
      },
      isLike() {
        return function(talkId) {
            return "#999"
        }
      }
  },
  data() {
      return {
          current: 1,
          size: 10,
          talkList: [],
          previewList: [],
          count: 0,
          blogInfo:{
              pageList:{
                  pageCover:'',
                  pageName:''
              }
          },
          cover: '',
          pageTitle:''
      }
  },
  methods:{
    listTalks(){
        const this_ = this 
        this.axios.get("/api/talk/blog/getTalkList", {
            params: {
                current: this.current,
                size: this.size
            }
        })
        .then(({ data }) => {
            if (this.current == 1) {
                this_.talkList = data.data.recordList
            } else {
                this_.talkList.push(...data.data.recordList)
            }
            this.talkList.forEach(item => {
                if (item.imgList) {
                    this_.previewList.push(...item.imgList)
                }
            })
            this_.current++
            this_.count = data.data.count
        })
    },
    previewImg(){

    },
    like(talk) {
        return false
    },
    bannerBackShow(blogInfo){
        const this_ = this
        //banner背景显示
        if(blogInfo.pageList && blogInfo.pageList.length>0){
            blogInfo.pageList.forEach(item => {
            if (item.pageLabel == "archive") {
                this_.cover = item.pageCover
                this_.pageTitle = item.pageName
            }
        })
        }
    },
    webConfigData(){ 
        if(this.blogInfos.pageList){
            this.blogInfo = this.blogInfos
        }
        this.bannerBackShow(this.blogInfo)
    }
  },
  watch:{
      blogInfos(newVal){
          this.blogInfo = newVal
          this.bannerBackShow(this.blogInfo)
      }
  }
}
</script>

<style scoped>
.my-talk-container{
    padding: 16px 20px;
    margin-top: 20px;
}
.user-info-wrapper {
  width: 100%;
  display: flex;
}
.right-info {
  flex: 1;
  margin-left: 10px;
  width: 0;
}
.user-nickname {
  font-size: 15px;
  font-weight: bold;
  vertical-align: middle;
}
.user-sign {
  margin-left: 4px;
}
.time {
  color: #999;
  margin-top: 2px;
  font-size: 12px;
}
.talk-content {
  margin-top: 8px;
  font-size: 14px;
  white-space: pre-line;
  word-wrap: break-word;
  word-break: break-all;
}
.talk-images {
  margin-top: 8px;
}
.images-items {
  cursor: pointer;
  border-radius: 4px;
}
.talk-operation {
  margin-top: 10px;
  display: flex;
  align-items: center;
}
.talk-operation-item {
  display: flex;
  align-items: center;
  margin-right: 40px;
  font-size: 12px;
}
.operation-count {
  margin-left: 4px;
}
.load-wrapper {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.load-wrapper button {
  background-color: #49b1f5;
  color: #fff;
}
.like-btn:hover {
  color: #eb5055 !important;
}
</style>