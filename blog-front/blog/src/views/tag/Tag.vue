<template>
  <div class="my-main">
      <Banner :imageUrl="cover" :pageTitle="pageTitle"></Banner>
      <el-card class="card-content my-tag-container">
        <div class="tag-cloud-title">标签 - {{ count }}</div>
        <div class="tag-cloud">
          <router-link
            :style="{ 'font-size': Math.floor(Math.random() * 10) + 18 + 'px' }"
            v-for="item of tagList"
            :key="item.id"
            :to="'/tag/' + item.id"
          >
            {{ item.tagName }}
          </router-link>
        </div>
      </el-card>
  </div>
</template>

<script>
import Banner from "@/components/Banner.vue"
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
      //获取标签数据
      this.listTags()
      //获取网站数据
      this.webConfigData() 
  },
  data() {
      return {
          count: 0,
          tagList: [],
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
      listTags(){
          const this_ = this
          this.axios.get("/api/tag/blog/getTags")
          .then(({ data }) => {
            this_.tagList = data.data.recordList
            this_.count = data.data.count
          })
      },
      bannerBackShow(blogInfo){
        const this_ = this
          //banner背景显示
          if(blogInfo.pageList && blogInfo.pageList.length>0){
              blogInfo.pageList.forEach(item => {
              if (item.pageLabel == "tag") {
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
.tag-cloud-title {
  line-height: 2;
  font-size: 36px;
  text-align: center;
}
@media (max-width: 767px) {
  .tag-cloud-title {
    font-size: 25px;
  }
}
.tag-cloud {
  text-align: center;
}
.tag-cloud a {
  color: #616161;
  display: inline-block;
  text-decoration: none;
  padding: 0 8px;
  line-height: 2;
  transition: all 0.3s;
}
.tag-cloud a:hover {
  color: #03a9f4 !important;
  transform: scale(1.1);
}
</style>