<template>
  <el-card class="main-card my-deletePhoto-card">
    <!-- 标题 -->
    <div class="title">{{ this.$route.name }}</div>
    <!-- 相册操作 -->
    <div class="operation">
      <div class="all-check">
        <el-checkbox
          :indeterminate="isIndeterminate"
          v-model="checkAll"
          @change="handleCheckAllChange"
        >
          全选
        </el-checkbox>
        <div class="check-count">已选择{{ selectPhotoIdList.length }}张</div>
      </div>
      <el-button
        type="success"
        @click="updatePhotoDelete"
        :disabled="selectPhotoIdList.length == 0"
        size="default"
      >
      <el-icon><RefreshLeft /></el-icon>
        <span>批量恢复</span>
      </el-button>
      <el-button
        type="danger"
        @click="batchDeletePhoto = true"
        :disabled="selectPhotoIdList.length == 0"
        size="default"
      >
      <el-icon><Delete/></el-icon> 
        <span>批量删除</span>
      </el-button>
    </div>
    <!-- 照片列表 -->
    <!-- 空状态 -->
    <div class="photo-list">
      <el-empty v-if="photoList.length == 0" description="暂无照片" />
      <el-checkbox-group
          v-model="selectPhotoIdList"
          @change="handleCheckedPhotoChange"
        >
        <el-row class="photo-container" :gutter="20" v-loading="loading">
            <el-col :md="4" v-for="item of photoList" :key="item.id">
              <el-checkbox :label="item.id">
                <div class="photo-item">
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
      :hide-on-single-page="true"
      class="pagination-container"
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page="current"
      :page-size="size"
      :total="count"
      layout="prev, pager, next"
    />
    <!-- 批量删除对话框 -->
    <el-dialog v-model="batchDeletePhoto" width="30%">
      <template #header>
        <div class="dialog-title-container">
          <i class="el-icon-warning" style="color:#ff9900" />提示
        </div>
      </template>
      <div style="font-size:1rem">是否删除选中照片？</div>
      <template #footer>
        <el-button @click="batchDeletePhoto = false">取 消</el-button>
        <el-button type="primary" @click="deletePhotos">
          确 定
        </el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script>
export default {
  created() {
    this.listPhotos()
  },
  data: function() {
    return {
      loading: true,
      batchDeletePhoto: false,
      isIndeterminate: false,
      checkAll: false,
      photoList: [],
      photoIdList: [],
      selectPhotoIdList: [],
      current: 1,
      size: 18,
      count: 0
    }
  },
  methods: {
    listPhotos() {
      this.axios
        .get("/api/photo/getPhotos", {
          params: {
            current: this.current,
            size: this.size,
            isDelete: 1
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
    updatePhotoDelete() {
      let param = {}
      param.idList = this.selectPhotoIdList
      this.axios.post("/api/photo/recoverPhotos", param).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          })
          this.listPhotos()
          this.selectPhotoIdList = []
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          })
        }
      })
      this.batchDeletePhoto = false
    },
    deletePhotos() {
      let param = {}
      param.idList = this.selectPhotoIdList
      this.axios
        .post("/api/photo/deleteOkPhotos", param)
        .then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: data.message
            })
            this.listPhotos()
            this.selectPhotoIdList = []
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            })
          }
        })
      this.batchDeletePhoto = false
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
.photo-list{
  height: calc(100vh - 350px);
}
.pagination-container{
  margin-top: 40px;
}
.operation {
  display: flex;
  justify-content: flex-end;
  margin-top: 2.25rem;
  margin-bottom: 2rem;
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
.photo-item {
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
</style>
