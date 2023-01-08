<template>
  <el-card class="main-card my-photo-card">
    <!-- 标题 -->
    <div class="title">{{ this.$route.name }}</div>
    <!-- 相册信息 -->
    <div class="album-info">
      <el-image fit="cover" class="album-cover" :src="albumInfo.albumCover" />
      <div class="album-detail">
        <div style="margin-bottom:0.6rem">
          <span class="album-name">{{ albumInfo.albumName }}</span>
          <span class="photo-count">{{ count }}张</span>
        </div>
        <div>
          <span v-if="albumInfo.albumDesc" class="album-desc">
            {{ albumInfo.albumDesc }}
          </span>
          <el-button
            type="primary"
            size="default"
            @click="uploadPhoto = true"
          >
            <el-icon><PictureFilled /></el-icon>
            <span>上传照片</span>
          </el-button>
        </div>
      </div>
      <!-- 相册操作 -->
      <div class="operation">
        <div class="all-check">
          <el-checkbox
            :indeterminate="isIndeterminate"
            v-model="checkAll"
            @change="handleCheckAllChange"
          >
            <span>全选</span>
          </el-checkbox>
          <div class="check-count">已选择{{ selectPhotoIdList.length }}张</div>
        </div>
        <el-button
          type="success"
          @click="movePhotos"
          :disabled="selectPhotoIdList.length == 0"
          size="default"
        >
        <el-icon><Switch /></el-icon>
        <span>批量移动</span>    
        </el-button>
        <el-button
          type="danger"
          @click="batchDeletePhoto = true"
          :disabled="selectPhotoIdList.length == 0"
          size="default"
        >
          <el-icon><Delete /></el-icon>
          <span>批量删除</span>    
        </el-button>
      </div>
    </div>
    <!-- 照片列表 -->
    <div class="photo-list">
      <!-- 空状态 -->
      <el-empty v-if="photoList.length == 0" description="暂无相片" />
      <el-checkbox-group 
        v-model="selectPhotoIdList" 
        @change="handleCheckedPhotoChange">
          <el-row class="photo-container" :gutter="20" v-loading="loading">
              <el-col class="photo-container-col" :md="4" v-for="item of photoList" :key="item.id" >
                <el-checkbox :label="item.id">
                  <div class="photo-item">
                    <!-- 照片操作 -->
                    <div class="photo-opreation">
                      <el-dropdown @command="handleCommand">
                        <el-icon style="color:#fff"><More /></el-icon> 
                        <template #dropdown>
                          <el-dropdown-menu>
                            <el-dropdown-item :command=" 'edit' + JSON.stringify(item)">
                              <el-icon><Edit /></el-icon>编辑
                            </el-dropdown-item>
                            <el-dropdown-item :command=" 'dele' + JSON.stringify(item)">
                              <el-icon><Delete /></el-icon>删除
                            </el-dropdown-item>
                            <el-dropdown-item :command=" 'move' + JSON.stringify(item)">
                              <el-icon><Switch /></el-icon>移动
                            </el-dropdown-item>
                          </el-dropdown-menu>
                        </template>
                      </el-dropdown>
                    </div>
                    <el-image
                      fit="cover"
                      class="photo-img"
                      :src="item.photoSrc"
                      :preview-photoSrc-list="photoList"
                    />
                    <div class="photo-name">{{ item.photoName }}</div>
                  </div>
                </el-checkbox>
              </el-col>
          </el-row>
      </el-checkbox-group>
    </div>
    <!-- 分页 -->
    <el-pagination
      class="pagination-container"
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page="current"
      :page-size="size"
      :total="count"
      layout="prev, pager, next"
      :hide-on-single-page="true"
    />
    <!-- 上传模态框 -->
    <el-dialog v-model="uploadPhoto" width="70%" top="10vh">
      <template #header>
          <div class="dialog-title-container" >
            上传照片
          </div>
      </template>
      <!-- 上传 -->
      <div class="upload-container">
        <!-- 图片列表(后续上传显示) -->
        <el-upload
          v-show="uploadList.length > 0"
          action="/api/photo/uploadPhoto"
          list-type="picture-card"
          :file-list="uploadList"
          multiple
          :before-upload="beforeUpload"
          :on-success="upload"
          :on-remove="handleRemove"
        >
          <el-icon><Plus /></el-icon>
        </el-upload>
        <!-- 指引图片(首次上传后隐藏) -->
        <div class="upload">
          <el-upload
            v-show="uploadList.length == 0"
            drag
            multiple
            action="/api/photo/uploadPhoto"
            :before-upload="beforeUpload"
            :on-success="upload"
            :show-file-list="false"
          >
            <el-icon><UploadFilled /></el-icon>
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <template #tip>
              <div class="el-upload__tip">
                支持上传jpg/png文件
              </div>
            </template>
          </el-upload>
        </div>
      </div>
      <template #footer>
        <div class="upload-footer">
          <div class="upload-count">共上传{{ uploadList.length }}张照片</div>
          <div style="margin-left:auto">
            <el-button @click="uploadPhoto = false">取 消</el-button>
            <el-button
              @click="savePhotos"
              type="primary"
              :disabled="uploadList.length == 0"
            >
              开始上传
            </el-button>
          </div>
        </div>
      </template>
    </el-dialog>
    <!-- 编辑对话框 -->
    <el-dialog v-model="editPhoto" width="30%">
      <template #header>
        <div class="dialog-title-container">
          修改信息
        </div>
      </template>
      <el-form label-width="80px" size="default" :model="photoForm">
        <el-form-item label="照片名称">
          <el-input style="width:220px" v-model="photoForm.photoName" />
        </el-form-item>
        <el-form-item label="照片描述">
          <el-input style="width:220px" v-model="photoForm.photoDesc" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="editPhoto = false">取 消</el-button>
        <el-button type="primary" @click="updatePhoto">
          确 定
        </el-button>
      </template>
    </el-dialog>
    <!-- 批量删除对话框 -->
    <el-dialog v-model="batchDeletePhoto" width="30%">
      <template #header>
        <div class="dialog-title-container" >
          <i class="el-icon-warning" style="color:#ff9900" />提示
        </div>
      </template>
      <div style="font-size:1rem">是否删除选中照片？</div>
      <template #footer>
        <el-button @click="batchDeletePhoto = false">取 消</el-button>
        <el-button type="primary" @click="updatePhotoDelete(null)">
          确 定
        </el-button>
      </template>
    </el-dialog>
    <!-- 移动对话框 -->
    <el-dialog v-model="movePhoto" width="30%">
      <template #header>
        <div class="dialog-title-container">
          移动照片
        </div>
      </template>
      <el-empty v-if="albumList.length < 2" description="暂无其他相册" />
      <div v-else class="show-albumList">
        <el-scrollbar max-height="250px">
          <el-radio-group v-model="albumId" style="display:block;">
            <div v-for="item of albumList" class="albumList-item">
              <el-radio-button :label="item.id">
              {{ item.photoAlbumName }}
              </el-radio-button> 
            </div>
          </el-radio-group>
        </el-scrollbar>
      </div>
      <template #footer>
        <el-button @click="movePhoto = false">取 消</el-button>
        <el-button
          :disabled="albumId == null"
          type="primary"
          @click="updatePhotoAlbum"
        >
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
    //获取相册信息
    this.getAlbumInfo()
    //获取相册下照片信息
    this.listPhotos()
  },
  data: function() {
    return {
      loading: true,
      checkAll: false,
      isIndeterminate: false,
      uploadPhoto: false,
      editPhoto: false,
      movePhoto: false,
      batchDeletePhoto: false,
      uploadList: [],
      photoList: [],
      photoIdList: [],
      selectPhotoIdList: [],
      albumList: [],
      albumInfo: {
        id: null,
        albumName: "",
        albumDesc: "",
        albumCover: "",
        photoCount: 0
      },
      photoForm: {
        id: null,
        photoName: "",
        photoDesc: ""
      },
      albumId: null,
      current: 1,
      size: 18,
      count: 0
    }
  },
  methods: {
    movePhotos(){
      //获取所有相册
      this.listAlbums()
    },
    getAlbumInfo() {
      this.axios
        .get("/api/photoAlbum/getAlbumById/" + this.$route.params.albumId)
        .then(({ data }) => {
          this.albumInfo = data.data
        })
    },
    listAlbums() {
      this.axios.get("/api/photoAlbum/getAlbumsAny").then(({ data }) => {
        this.albumList = data.data
        this.movePhoto = true
      })
    },
    listPhotos() {
      this.axios
        .get("/api/photo/getPhotos", {
          params: {
            current: this.current,
            size: this.size,
            albumId: this.$route.params.albumId,
            isDelete: 0
          }
        })
        .then(({ data }) => {
          this.photoList = data.data.recordList
          this.count = data.data.count
          this.loading = false
        })
    },
    sizeChange(size) {
      this.size = size
      this.listPhotos()
    },
    currentChange(current) {
      this.current = current
      this.listPhotos()
    },
    savePhotos() {
      var photoUrlList = []
      this.uploadList.forEach(item => {
        photoUrlList.push(item.url)
      })
      this.axios
        .post("/api/photo/addPhotosToAlbum", {
          albumId: this.$route.params.albumId,
          photoUrlList: photoUrlList
        })
        .then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: data.message
            })
            this.uploadList = []
            this.listPhotos()
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            })
          }
        })
      this.uploadPhoto = false
    },
    updatePhoto() {
      if (this.photoForm.photoName.trim() == "") {
        this.$message.error("照片名称不能为空")
        return false
      }
      this.axios.post("/api/photo/updatePhoto", this.photoForm).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          })
          this.listPhotos()
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          })
        }
        this.editPhoto = false
      })
    },
    updatePhotoAlbum() {
      this.axios
        .post("/api/photo/movePhotos", {
          albumId: this.albumId,
          photoIdList: this.selectPhotoIdList
        })
        .then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: data.message
            })
            this.selectPhotoIdList = []
            this.listPhotos()
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            })
          }
          this.movePhoto = false
        })
    },
    handleRemove(file) {
      this.uploadList.forEach((item, index) => {
        if (item.url == file.url) {
          this.uploadList.splice(index, 1)
        }
      })
    },
    upload(response) {
      this.uploadList.push({ url: response.data })
    },
    beforeUpload(file) {
      const typeList = ['image/jpeg', 'image/png']
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
    handleCheckAllChange(val) {
      this.selectPhotoIdList = val ? this.photoIdList : []
      this.isIndeterminate = false
    },
    handleCheckedPhotoChange(value) {
      let checkedCount = value.length
      this.checkAll = checkedCount === this.photoIdList.length
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.photoIdList.length
    },
    handleCommand(command) {
      const type = command.substring(0, 4)
      const data = command.substring(4)
      if (type === 'edit') {
        this.photoForm = JSON.parse(data)
        this.editPhoto = true
      } else if (type === 'move') {
        this.selectPhotoIdList = [JSON.parse(data).id]
        this.listAlbums() 
      } else{
        this.updatePhotoDelete(JSON.parse(data).id)
      }
    },
    updatePhotoDelete(id) {
      var param = {}
      if (id == null) {
        param.idList = this.selectPhotoIdList
      } else {
        param.idList = [id]
      }
      this.axios.post("/api/photo/deletePhotos", param).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          })
          this.selectPhotoIdList = []
          this.listPhotos()
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          })
        }
      })
      this.batchDeletePhoto = false
    }
  },
  watch: {
    photoList() {
      this.photoIdList = []
      this.photoList.forEach(item => {
        this.photoIdList.push(item.id)
      })
    }
  }
}
</script>

<style scoped>
.albumList-item{
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  margin: 10px;
  text-align: center;
  border-radius: 4px;
  background: #ecf5ff;
}

.show-albumList{
  height: 250px;
  border:1px solid #dcdfe6;
}
.photo-list{
  height: calc(100vh - 350px);
}
.album-info {
  display: flex;
  margin-top: 2.25rem;
  margin-bottom: 2rem;
}
.album-cover {
  border-radius: 4px;
  width: 5rem;
  height: 5rem;
}
.album-check-cover {
  border-radius: 4px;
  width: 4rem;
  height: 4rem;
}
.album-detail {
  padding-top: 0.4rem;
  margin-left: 0.8rem;
}
.album-desc {
  font-size: 14px;
  margin-right: 0.8rem;
}
.operation {
  padding-top: 1.5rem;
  margin-left: auto;
}
.all-check {
  display: inline-flex;
  align-items: center;
  margin-right: 1rem;
}
.check-count {
  margin-left: 1rem;
  font-size: 12px;
}
.album-name {
  font-size: 1.25rem;
}
.photo-count {
  font-size: 12px;
  margin-left: 0.5rem;
}
.photo-item {
  width: 100%;
  position: relative;
  cursor: pointer;
  margin-bottom: 1rem;
}
.photo-img {
  width: 100%;
  height: 6rem;
  border-radius: 4px;
}
.photo-name {
  font-size: 14px;
  margin-top: 0.3rem;
  text-align: center;
}
.upload-container {
  height: 400px;
}
.upload {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.upload-footer {
  display: flex;
  align-items: center;
}
.photo-opreation {
  position: absolute;
  z-index: 1000;
  top: 0.3rem;
  right: 0.5rem;
}
.album-check {
  display: flex;
  align-items: center;
}
.photo-container{
  margin-left: 0 !important;
  margin-right: 0 !important;
}
.pagination-container{
  margin-top: 0;
}

</style>
