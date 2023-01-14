<template>
  <div class="my-main">
    <div class="banner bannerIn" :style="{'background':'url('+cover+') center center / cover no-repeat'}">
        <h1 class="banner-title">{{photoAlbumName}}</h1>
    </div>
    <el-card class="card-content my-photo-container">
    <div class="photo-wrap">
        <img
        v-for="(item, index) of photoList"
        class="photo"
        :key="index"
        :src="item"
        />
    </div>
    </el-card>
  </div>
</template>

<script>
import {praseDateStr} from "@/assets/js/common.js"
export default {
    mounted() {
        //监听页面滚动事件
        window.addEventListener("scroll", this.scrollEvent,true)
    },
    unmounted() {
        window.removeEventListener("scroll", this.scrollEvent)
    },
  created(){
      //获取照片数据
      this.listPhotos()
  },
  computed :{
      dataFormat(){
          return function(date){
              return praseDateStr(date,"YYYY-MM-DD HH:mm:ss")
          }
      },
      cover(){
        return this.photoAlbumCover
      }
  },
  data() {
      return {
          isLoadComplete: false,
          size: 10,
          current: 1,
          photoAlbumCover: '',
          photoAlbumName: '',
          photoList: [],
      }
  },
  methods:{
      listPhotos(){
          const this_ = this 
          this.axios.get("/api/photo/blog/getPhotos/" + this.$route.params.id, {
              params: { 
                current: this.current,
                size: this.size 
              }
          })
          .then(({ data }) => {
              this_.photoAlbumCover = data.data.photoAlbumCover
              this_.photoAlbumName = data.data.photoAlbumName
              if(data.data.photoList.length<10){
                this_.isLoadComplete = true
              }
              if(data.data.photoList.length){
                this_.photoList.push(...data.data.photoList)
              }
          })
      },
      //实现下拉加载数据
      scrollEvent(){          
          let bufferHeight = 137  //缓冲距离
          let dscrollTop = document.documentElement.scrollTop || document.body.scrollTop  //滚动条滚动距离
          const clientHeight = document.documentElement.clientHeight || document.body.clientHeight  //浏览器可见窗口距离
          const pageHeight = document.documentElement.offsetHeight || document.body.offsetHeight  //整个页面内容长度，包括超出浏览器部分
          //console.log("滚动距离", dscrollTop, clientHeight,pageHeight)//打印看一下        
          if(pageHeight - clientHeight - dscrollTop - bufferHeight < 0){//到底了
              if(!this.isLoadComplete){
                  this.current++
                  //加载数据
                  this.listPhotos()
              }
          }    
      }
  }
}
</script>

<style scoped>
.photo-wrap {
  display: flex;
  flex-wrap: wrap;
}
.photo {
  margin: 3px;
  cursor: pointer;
  flex-grow: 1;
  object-fit: cover;
  height: 200px;
}
.photo-wrap::after {
  content: "";
  display: block;
  flex-grow: 9999;
}
/*适应PC端 宽度大于760px*/
@media screen and (min-width: 760px) {
    .banner{
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        height: 400px;
        background-attachment: fixed;
        text-align: center;
        color: #fff!important;
    }   
    .banner-title {
        position: absolute;
        top: 12.5rem;
        padding: 0 0.5rem;
        width: 100%;
        font-size: 2.5rem;
        text-align: center;
        color: #eee;
    }
}
/*适应移动端 宽度小于767px*/
@media screen and (max-width: 767px){
  .photo {
    width: 100%;
  }
  .banner{
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        height: 280px;
        background-attachment: fixed;
        text-align: center;
        color: #fff!important;
    } 
    .banner-title {
        position: absolute;
        top: 8.5rem;
        width: 100%;
        font-size: 1.625rem;
        text-align: center;
        color: #eee;
    }
}
</style>