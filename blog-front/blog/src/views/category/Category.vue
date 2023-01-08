<template>
  <div class="my-main">
      <Banner :imageUrl="cover" :pageTitle="pageTitle"></Banner>
      <el-card class="card-content my-category-container">
        <div class="category-title">分类 - {{ count }}</div>
        <ul class="category-list">
          <li
            class="category-list-item"
            v-for="item of categoryList"
            :key="item.id"
          >
            <router-link :to="'/category/' + item.id">
              {{ item.categoryName }}
              <span class="category-count">({{ item.articleCount }})</span>
            </router-link>
          </li>
        </ul>
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
      //获取分类数据
      this.listArchives()
      //获取网站数据
      this.webConfigData() 
  },
  data() {
      return {
          categoryList: [],
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
      listArchives(){
          const this_ = this 
          this.axios.get("/api/category/blog/getCategories")
          .then(({ data }) => {
            this_.categoryList = data.data.recordList
            this_.count = data.data.count
          })
      },
      bannerBackShow(blogInfo){
          //banner背景显示
          if(blogInfo.pageList && blogInfo.pageList.length>0){
              blogInfo.pageList.forEach(item => {
              if (item.pageLabel == "category") {
              this.cover = item.pageCover
              this.pageTitle = item.pageName
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
.category-title {
  text-align: center;
  font-size: 36px;
  line-height: 2;
}
@media (max-width: 767px) {
  .category-title {
    font-size: 28px;
  }
}
.category-list {
  margin: 0 1.8rem;
  list-style: none;
}
.category-list-item {
  padding: 8px 1.8rem 8px 0;
  margin-top: 4px;
}
.category-list-item:before {
  display: inline-block;
  position: relative;
  left: -0.75rem;
  width: 6px;
  height: 6px;
  border: 0.2rem solid #49b1f5;
  border-radius: 50%;
  background: var(--my-theme-white-color-1);
  content: "";
  transition-duration: 0.3s;
}
.category-list-item:hover:before {
  border: 0.2rem solid #ff7242;
}
.category-list-item a:hover {
  transition: all 0.3s;
  color: #8e8cd8;
}
.category-list-item a:not(:hover) {
  transition: all 0.3s;
}
.category-count {
  margin-left: 0.5rem;
  font-size: 0.75rem;
  color: #858585;
}
</style>