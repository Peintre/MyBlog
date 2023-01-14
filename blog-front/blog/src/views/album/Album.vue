<template>
  <div class="my-main">
      <Banner />
      <div class="card-content">
        <el-row class="my-album-container" :gutter="60">
            <el-col :md="12" v-for="item of photoAlbumList" :key="item.id">
                <div class="album-item">
                    <el-image fit="cover" class="album-cover" :src="item.photoAlbumCover" />
                    <router-link class="album-wrapper" :to="'/album/' + item.id" >
                        <div class="album-name">{{ item.photoAlbumName }}</div>
                        <div class="album-desc">{{ item.photoAlbumDesc }}</div>
                    </router-link>
                </div>
            </el-col>
        </el-row>
      </div>      
  </div>
</template>

<script>
import Banner from "@/components/Banner.vue"
export default {
  components: {
      Banner
  },
  created(){
      //获取相册数据
      this.listPhotoAlbums()
  },
  data() {
      return {
          photoAlbumList: []
      }
  },
  methods:{
      listPhotoAlbums(){
        const this_ = this
          this.axios.get("/api/photoAlbum/blog/getAlbums")
          .then(({ data }) => {
            this_.photoAlbumList = data.data
          })
      }
  }
}
</script>

<style scoped>
.my-album-container{
    display: flex;
    flex-wrap: wrap;
    position: relative;
    box-sizing: border-box;
}
.album-item{
    overflow: hidden;
    position: relative;
    cursor: pointer;
    height: 250px;
    border-radius: 0.8rem;
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
.album-item a:-webkit-any-link{
    color:var(--my-theme-white-color-1);
}
.album-wrapper{
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 1.8rem 2rem;
    color: var(--my-theme-bg-color);
}
.album-cover {
    transition: opacity 0.35s, transform 0.35s;
    transform: translate3d(-10px, 0, 0);
    max-width: none;
    position: relative;
    width: calc(100% + 1.25rem);
    height: 250px;
}
.album-item:hover .album-cover {
  transform: translate3d(0, 0, 0);
}
.album-item:hover .album-wrapper{
    background: rgba(18,18,18,.4);
}
.album-item:hover .album-name:after {
  transform: translate3d(0, 0, 0);
}
.album-item:hover .album-desc {
  opacity: 1;
  filter: none;
  transform: translate3d(0, 0, 0);
}
.album-name {
  font-weight: bold;
  font-size: 1.25rem;
  overflow: hidden;
  padding: 0.7rem 0;
  position: relative;
}
.album-name:after {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 2px;
  background: #fff;
  content: "";
  transition: transform 0.35s;
  transform: translate3d(-101%, 0, 0);
}
.album-desc {
  margin: 0;
  padding: 0.4rem 0 0;
  line-height: 1.5;
  opacity: 0;
  transition: opacity 0.35s, transform 0.35s;
  transform: translate3d(100%, 0, 0);
}
/*适应PC端 宽度大于768px*/
@media screen and (min-width: 768px) {
    .album-item{
        margin-top: 60px;
    }
    .card-content{
        margin: 300px auto 40px auto;
    }
}
/*适应移动端 宽度小于767px*/
@media screen and (max-width: 767px){
    .album-item{
        margin-top: 30px;
    }
    .card-content{
        margin: 190px 5px 20px 5px;
    }
}
</style>