<template>
  <el-card class="main-card my-album-card">
    <!-- 标题 -->
    <div class="title">{{ this.$route.name }}</div>
    <div class="operation-container">
      <el-button
        type="primary"
        size="default"
        @click="openModel(null)"
      >
      <el-icon><Plus/></el-icon> 
        <span>新建相册</span>
      </el-button>
      <div style="margin-left:auto">
        <el-button
          link
          type="primary"
          size="small"
          style="margin-right:1rem"
          @click="checkDelete"
        >
        <el-icon><Delete/></el-icon>
          <span>回收站</span>
        </el-button>
        <el-input
          v-model="keywords"
          size="default"
          placeholder="请输入相册名"
          style="width:200px"
          @keyup.enter.native="searchAlbums"
        >
          <template #prefix>
              <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-button
          type="primary"
          size="default"
          style="margin-left:1rem"
          @click="searchAlbums"
        >
        <el-icon><Search /></el-icon>
        <span>搜索</span>
        </el-button>
      </div>
    </div>  
    <!-- 空状态 -->
    <el-empty v-if="albumList == null" description="暂无相册" />
    <!-- 相册列表 -->
    <el-row class="album-container" :gutter="12" v-loading="loading">
      <el-col v-for="item of albumList" :key="item.id" :md="6">
        <div class="album-item" @click="checkPhoto(item)">
          <!-- 相册操作 -->
          <div class="album-opreation">
            <el-dropdown @command="handleCommand">
              <el-icon style="color:#fff"><More /></el-icon>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item :command="'update' + JSON.stringify(item)">
                    <el-icon><Edit /></el-icon>编辑
                  </el-dropdown-item>
                  <el-dropdown-item :command="'delete' + item.id">
                    <el-icon><Delete /></el-icon>删除
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
          <!-- 相片数量显示 -->
          <div class="album-photo-count">
            <div>{{ item.photoCount }}</div>
            <i v-if="item.status == 2" class="iconfont el-icon-mymima" />
          </div>
          <!-- 相册封面显示 -->
          <el-image fit="cover" class="album-cover" :src="item.albumCover" />
          <div class="album-name">{{ item.albumName }}</div>
        </div>
      </el-col>
    </el-row>
    <!-- 分页 -->
    <el-pagination
      :hide-on-single-page="true"
      class="pagination-container"
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page="current"
      :page-size="size"
      :total="count"
      layout="prev, pager, next"
    />
    <!-- 新增模态框 -->
    <el-dialog v-model="addOrEdit" width="35%" top="10vh">
      <template #header>
        <div class="dialog-title-container" >{{ albumTitle }}</div>
      </template>
      <el-form label-width="80px" size="default" :model="albumForum">
        <el-form-item label="相册名称">
          <el-input style="width:220px" v-model="albumForum.albumName" />
        </el-form-item>
        <el-form-item label="相册描述">
          <el-input style="width:220px" v-model="albumForum.albumDesc" />
        </el-form-item>
        <el-form-item label="相册封面">
          <el-upload
            class="upload-cover"
            drag
            :show-file-list="false"
            :before-upload="beforeUpload"
            action="/api/photoAlbum/uploadAlbumCover"
            :on-success="uploadCover"
          >
            <div class="upload-empty" v-if="albumForum.albumCover == ''">
              <el-icon ><upload-filled /></el-icon>
              <div class="el-upload__text">
                将文件拖到此处，或<em>点击上传</em>
              </div>
            </div>
            <img
              v-else
              :src="albumForum.albumCover"
              style="width:360px;height: 180px;"
            />
          </el-upload>
        </el-form-item>
        <el-form-item label="发布形式">
          <el-radio-group v-model="albumForum.status">
            <el-radio :label="1">公开</el-radio>
            <el-radio :label="2">私密</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addOrEdit = false">取 消</el-button>
        <el-button type="primary" @click="addOrEditAlbum">
          确 定
        </el-button>
      </template>
    </el-dialog>
    <!-- 删除对话框 -->
    <el-dialog v-model="isdelete" width="30%">
      <template #header>
        <div class="dialog-title-container">
          <el-icon style="color:#ff9900"><Warning/></el-icon>提示
        </div>
      </template>
      <div style="font-size:1rem">是否删除该相册？</div>
      <template #footer>
        <el-button @click="isdelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteAlbum">
          确 定
        </el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script>
import * as imageConversion from "image-conversion"
export default {
  created() {
    this.listAlbums()
  },
  data: function() {
    return {
      albumTitle:'',
      keywords: "",
      loading: true,
      isdelete: false,
      addOrEdit: false,
      albumForum: {
        id: null,
        albumName: "",
        albumDesc: "",
        albumCover: "",
        status: 1
      },
      albumList: [],
      current: 1,
      size: 8,
      count: 0
    }
  },
  methods: {
    openModel(item) {
      if (item) {
        this.albumForum = JSON.parse(item)
        this.albumTitle = "修改相册"
      } else {
        this.albumForum = {
          id: null,
          albumName: "",
          albumLabel: "",
          albumCover: "",
          status: 1
        }
        this.albumTitle = "新建相册"
      }
      this.addOrEdit = true
    },
    checkPhoto(item) {
      this.$router.push({ path: "/albums/" + item.id })
    },
    checkDelete() {
      this.$router.push({ path: "/photos/delete" })
    },
    listAlbums() {
      this.axios
        .get("/api/photoAlbum/getAlbums", {
          params: {
            current: this.current,
            size: this.size,
            keywords: this.keywords
          }
        })
        .then(({ data }) => {
          this.albumList = data.data.recordList
          this.count = data.data.count
          this.loading = false
        })
    },
    addOrEditAlbum() {
      if (this.albumForum.albumName.trim() == "") {
        this.$message.error("相册名称不能为空")
        return false
      }
      if (this.albumForum.albumDesc.trim() == "") {
        this.$message.error("相册描述不能为空")
        return false
      }
      if (this.albumForum.albumCover == null) {
        this.$message.error("相册封面不能为空")
        return false
      }
      if(this.albumForum.id == null){
        this.axios.post("/api/photoAlbum/addAlbum", this.albumForum)
          .then(({ data }) => {
            if (data.flag) {
              this.$notify.success({
                title: "成功",
                message: data.message
              })
              this.listAlbums()
            } else {
              this.$notify.error({
                title: "失败",
                message: data.message
              })
            }
        } )
      }else{
        this.axios.post("/api/photoAlbum/updateAlbum", this.albumForum)
          .then(({ data }) => {
            if (data.flag) {
              this.$notify.success({
                title: "成功",
                message: data.message
              })
              this.listAlbums()
            } else {
              this.$notify.error({
                title: "失败",
                message: data.message
              })
            }
        } )
      }
      this.addOrEdit = false
    },
    uploadCover(response) {
      this.albumForum.albumCover = response.data
    },
    beforeUpload(file) {
      const typeList = ['image/jpeg', 'image/png', 'image/gif']
      const isTypeValid = typeList.includes(file.type)
      if (!isTypeValid) {
        this.$message.error('图片格式只能是 JPG/PNG/GIF!')
        return false
      } 
      return new Promise((resolve,reject) => {  
        // 图片小于200kb
        if (file.size / 1024 < 200) { 
          resolve(file)
        }
        // 大于 200 kb 就压缩
        imageConversion.compressAccurately(file, 200).then(res => {
          resolve(res)
        })
      })
    },
    handleCommand(command) {
      const type = command.substring(0, 6)
      const data = command.substring(6)
      if (type == "delete") {
        this.albumForum.id = data
        this.isdelete = true
      } else {
        this.openModel(data)
      }
    },
    deleteAlbum() {
      this.axios
        .delete("/api/photoAlbum/deleteAlbum/" + this.albumForum.id)
        .then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: data.message
            })
            this.listAlbums()
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            })
          }
          this.isdelete = false
        })
    },
    searchAlbums() {
      this.current = 1
      this.listAlbums()
    },
    sizeChange(size) {
      this.size = size
      this.listAlbums()
    },
    currentChange(current) {
      this.current = current
      this.listAlbums()
    }
  }
}
</script>

<style scoped>
.album-container{
  height: calc(100vh - 310px);
}
.upload-empty{
  height: 180px;
  width: 360px;
}
.album-cover {
  position: relative;
  border-radius: 4px;
  width: 100%;
  height: 170px;
}
.album-cover::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
}
.album-photo-count {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 1.5rem;
  z-index: 1000;
  position: absolute;
  left: 0;
  right: 0;
  padding: 0 0.5rem;
  bottom: 2.6rem;
  color: #fff;
}
.album-name {
  text-align: center;
  margin-top: 0.5rem;
}
.album-item {
  position: relative;
  cursor: pointer;
  margin-bottom: 1rem;
}
.album-opreation {
  position: absolute;
  z-index: 1000;
  top: 0.5rem;
  right: 0.8rem;
}
</style>
