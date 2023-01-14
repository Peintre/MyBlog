<template>
  <div class="my-main">
      <Banner />
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
  components: {
      Banner
  },
  created(){
      //获取标签数据
      this.listTags()
  },
  data() {
      return {
          count: null,
          tagList: []
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