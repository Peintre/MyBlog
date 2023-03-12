<template>
  <div class="my-main">
      <Banner :imageUrl="cover" :pageTitle="pageTitle"></Banner>
      <!-- 主体布局 -->
      <div class="my-main-content my-article-container mainIn">
          <el-row :gutter="10">
            <!-- 左侧内容 -->
            <el-col :md="18" :sm="24" class="container-left">
              <el-card class="left-card">
                <div
                  class="article-content markdown-body"
                  v-html="article.articleContent"
                  ref="article"
                />
                <!-- 版权声明 -->
                <div class="aritcle-copyright">
                  <div>
                    <span>文章作者：</span>
                    <router-link to="/">
                      {{ blogInfo.websiteConfig.websiteAuthor }}
                    </router-link>
                  </div>
                  <div>
                    <span>文章链接：</span>
                    <a :href="articleHref" target="_blank">{{ articleHref }} </a>
                  </div>
                  <div>
                    <span>版权声明：</span>本博客所有文章除特别声明外，均采用
                    <a
                      href="https://creativecommons.org/licenses/by-nc-sa/4.0/"
                      target="_blank"
                    >
                      CC BY-NC-SA 4.0
                    </a>
                    许可协议。转载请注明文章出处。
                  </div>
                </div>
                <!-- 转发 -->

                <!-- 点赞打赏按钮 -->

                <!-- 上下篇导航 -->
                <div class="pagination-post">
                  <!-- 上一篇 -->
                  <div
                    :class="isFull(article.lastArticle.id)"
                    v-if="article.lastArticle && article.lastArticle.id"
                  >
                    <router-link :to="'/article/' + article.lastArticle.id">
                      <img
                        class="post-cover"
                        :src="article.lastArticle.articleCover"
                      />
                      <div class="post-info">
                        <div class="label">上一篇</div>
                        <div class="post-title">
                          {{ article.lastArticle.articleTitle }}
                        </div>
                      </div>
                    </router-link>
                  </div>
                  <!-- 下一篇 -->
                  <div
                    :class="isFull(article.nextArticle.id)"
                    v-if="article.nextArticle && article.nextArticle.id"
                  >
                    <router-link :to="'/article/' + article.nextArticle.id">
                      <img
                        class="post-cover"
                        :src="article.nextArticle.articleCover"
                      />
                      <div class="post-info" style="text-align: right">
                        <div class="label">下一篇</div>
                        <div class="post-title">
                          {{ article.nextArticle.articleTitle }}
                        </div>
                      </div>
                    </router-link>
                  </div>
                </div>
                <!-- 推荐文章 -->
                <div
                  class="recommend-container"
                  v-if="article.recommendArticleList"
                >
                  <div class="recommend-title">
                    <el-icon :size="20" color="#4c4948"><Star /></el-icon>
                    相关推荐
                  </div>
                  <div class="recommend-list">
                    <div
                      class="recommend-item"
                      v-for="item of article.recommendArticleList"
                      :key="item.id"
                    >
                      <router-link :to="'/article/' + item.id">
                        <img class="recommend-cover" :src="item.articleCover" />
                        <div class="recommend-info">
                          <div class="recommend-date">
                            <i class="iconfont iconrili" />
                            {{ dataFormat(item.createTime) }}
                          </div>
                          <div>{{ item.articleTitle }}</div>
                        </div>
                      </router-link>
                    </div>
                  </div>
                </div>
                <!-- 分割线 -->
                <hr />
                <!-- 评论回复 -->
              </el-card>
            </el-col>
            <!-- 右侧内容 -->
            <el-col :md="6" class="container-right hidden-sm-and-down">
              <!-- 文章目录 -->
              <el-card class="article_catalog">
                <div class="title">
                    目录
                </div>
                <div>
                  <div  v-for="title in titles"  
                    :key="title.id"
                    @click="scrollToView(title.scrollTop)"
                    :title="title.rawName"
                    :class="addCatalogClass(title.level)"
                  >
                  <span>{{ title.name }}</span>     
                  </div>
                </div>
              </el-card>
              <!-- 最新文章 -->
              <el-card class="newest_article">
                <div class="right-title">
                  <i class="iconfont icongengxinshijian" style="font-size:16.8px" />
                  <span style="margin-left:10px">最新文章</span>
                </div>
                <div class="article-list">
                  <div
                    class="article-item"
                    v-for="item of article.newestArticleList"
                    :key="item.id"
                  >
                    <router-link :to="'/article/' + item.id" class="content-cover">
                      <img :src="item.articleCover" />
                    </router-link>
                    <div class="content">
                      <div class="content-title">
                        <router-link :to="'/article/' + item.id">
                          {{ item.articleTitle }}
                        </router-link>
                      </div>
                      <div class="content-time">{{ dataFormat(item.createTime) }}</div>
                    </div>
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>
      </div>
  </div>
</template>

<script>
import Banner from "@/components/Banner.vue"
import Clipboard from 'clipboard'
import {praseDateStr,markdownToHtml,getCatalog} from "@/assets/js/common.js"
export default {
  components: {
      Banner
  },
  created(){
      //获取文章数据
      this.listArticle()
  },
  mounted(){
    this.$nextTick(() => {
      this.clipboard = new Clipboard('.copy-btn')
      // 复制成功失败的提示
      this.clipboard.on('success', (e) => {
        this.$message.success('复制成功')
      })
      this.clipboard.on('error', (e) => {
        this.$message.error('复制成功失败')
      })
    })
  },
  computed :{
      dataFormat(){
          return function(date){
              return praseDateStr(date,"YYYY-MM-DD")
          }
      },
      isFull() {
        return function(id) {
          return id ? "post full" : "post";
        }
      },
      blogInfo(){
        return this.$store.state.blogInfo
      },
      cover(){
        return this.article.articleCover
      },
      pageTitle(){
        return this.article.articleTitle
      }
  },
  data() {
      return {
          article: {},
          articleHref: window.location.href,
          titles: [],
          clipboard: ''
      }
  },
  methods:{
      listArticle(){
          const this_ = this 
          this.axios.get("/api/article/blog/getArticleById/"+this.$route.params.id)
          .then(({ data }) => {
            //解析markdown格式
            data.data.articleContent = markdownToHtml(data.data.articleContent)
            this_.article = data.data 
            this_.$nextTick(() => {
              this_.titles = getCatalog(this_.$refs.article)
              // buildCodeBlock(this_.$refs.article);
            })   
          })
      },
      // 滚动到指定的位置
      scrollToView(scrollTop) {
          window.scrollTo({ top: scrollTop+400, behavior: "smooth" })
      },
      addCatalogClass(level){
        if(level == 1){
          return 'catalog_one'
        }
      }
  },
  destroyed () {
    this.clipboard.destroy()
  }
}
</script>

<style scoped>
.copy-btn{
  position:absolute;
  top: 100px;
}
.catalog_one{
  padding-left: 25px;
}
.left-card{
  padding: 50px 40px;
}
.container-right{
  position: sticky;
  top: 20px;
  height: 100%;
}
.article_catalog{
  padding: 20px 24px;
  font-size: 14px;
}
.newest_article{
  padding: 20px 24px;
  font-size: 14px;
  margin-top: 20px;
}
.article-content{
  list-style-position:inside;
}
.aritcle-copyright {
  position: relative;
  margin-top: 40px;
  margin-bottom: 10px;
  font-size: 0.875rem;
  line-height: 2;
  padding: 0.625rem 1rem;
  border: 1px solid #eee;
}
.aritcle-copyright span {
  color: #49b1f5;
  font-weight: bold;
}
.aritcle-copyright a {
  text-decoration: underline !important;
  color: #99a9bf !important;
}
.aritcle-copyright:before {
  position: absolute;
  top: 0.7rem;
  right: 0.7rem;
  width: 1rem;
  height: 1rem;
  border-radius: 1rem;
  background: #49b1f5;
  content: "";
}
.aritcle-copyright:after {
  position: absolute;
  top: 0.95rem;
  right: 0.95rem;
  width: 0.5rem;
  height: 0.5rem;
  border-radius: 0.5em;
  background: #fff;
  content: "";
}
.pagination-post {
  display: flex;
  background: rgba(18,18,18,.8);;
}
.post {
  width: 50%;
}
.post-info {
  position: absolute;
  top: 50%;
  padding: 20px 40px;
  width: 100%;
  transform: translate(0, -50%);
  line-height: 2;
  font-size: 14px;
}
.post-cover {
  position: absolute;
  width: 100%;
  height: 100%;
  opacity: 0.4;
  transition: all 0.6s;
  object-fit: cover;
}
.post a {
  position: relative;
  display: block;
  overflow: hidden;
  height: 150px;
}
.post:hover .post-cover {
  opacity: 0.9;
  transform: scale(1.1);
}
.label {
  font-size: 90%;
  color: #eee;
}
.post-title {
  font-weight: 500;
  color: #fff;
}
hr {
  position: relative;
  margin: 40px auto;
  border: 2px dashed #d2ebfd;
  width: calc(100% - 4px);
}
.full {
  width: 100% !important;
}
.recommend-container {
  margin-top: 40px;
}
.recommend-title {
  font-size: 20px;
  line-height: 2;
  font-weight: bold;
  margin-bottom: 5px;
}
.recommend-cover {
  width: 100%;
  height: 100%;
  opacity: 0.4;
  transition: all 0.6s;
  object-fit: cover;
}
.recommend-info {
  line-height: 2;
  color: #fff;
  position: absolute;
  top: 50%;
  padding: 0 20px;
  width: 100%;
  transform: translate(0, -50%);
  text-align: center;
  font-size: 14px;
}
.recommend-date {
  font-size: 90%;
}
.recommend-item:hover .recommend-cover {
  opacity: 0.9;
  transform: scale(1.1);
}
.right-title {
  display: flex;
  align-items: center;
  line-height: 2;
  font-size: 16.8px;
  margin-bottom: 6px;
}
.right-title i {
  font-weight: bold;
}
.article-item {
  display: flex;
  align-items: center;
  padding: 6px 0;
}
.article-item:first-child {
  padding-top: 0;
}
.article-item:last-child {
  padding-bottom: 0;
}
.article-item:not(:last-child) {
  border-bottom: 1px dashed #f5f5f5;
}
.article-item img {
  width: 100%;
  height: 100%;
  transition: all 0.6s;
  object-fit: cover;
}
.article-item img:hover {
  transform: scale(1.1);
}
.content {
  flex: 1;
  padding-left: 10px;
  word-break: break-all;
  display: -webkit-box;
  overflow: hidden;
  -webkit-box-orient: vertical;
}
.content-cover {
  width: 58.8px;
  height: 58.8px;
  overflow: hidden;
}
.content-title a {
  transition: all 0.2s;
  font-size: 95%;
}
.content-title a:hover {
  color: #2ba1d1;
}
.content-time {
  color: #858585;
  font-size: 85%;
  line-height: 2;
}
/*适应PC端 宽度大于768px*/
@media screen and (min-width: 768px) {
  .recommend-item{
    position: relative;
    display: inline-block;
    overflow: hidden;
    margin: 3px;
    width: calc(33.333% - 6px);
    height: 200px;
    background: rgba(18,18,18,.9);
    vertical-align: bottom;
  }
  
}
/*适应移动端 宽度小于767px*/
@media screen and (max-width: 767px){
  .recommend-item{
    position: relative;
    display: inline-block;
    overflow: hidden;
    margin: 3px;
    width: calc(100% - 4px);
    height: 150px;
    margin: 2px;
    background: rgba(18,18,18,.9);
    vertical-align: bottom;
  }
  .my-article-container{
    margin: 240px auto 0 auto !important;
  }
}
</style>