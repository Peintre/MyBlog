<template>
  <el-card class="main-card my-page-card">
    <!-- 标题 -->
    <div class="title">{{ this.$route.name }}</div>
    <div class="operation-container">
      <el-button
        type="primary"
        size="default"
        @click="openModel(null)"
      >
      <el-icon><Plus/></el-icon>
        <span>新建页面</span>
      </el-button>
    </div>
    <!-- 相册列表 -->
    <el-row class="page-container" :gutter="12" v-loading="loading">
      <!-- 空状态 -->
      <el-empty v-if="pageList.length == 0" description="暂无页面" />
      <el-col v-for="item of pageList" :key="item.id" :md="6">
        <div class="page-item">
          <!-- 相册操作 -->
          <div class="page-opreation">
            <el-dropdown @command="handleCommand">
              <el-icon style="color:#fff"><MoreFilled /></el-icon>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item :command="'update' + JSON.stringify(item)">
                    <el-icon><Edit/></el-icon>编辑
                  </el-dropdown-item>
                  <el-dropdown-item :command="'delete' + item.id">
                    <el-icon><Delete/></el-icon>删除
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
          <el-image fit="cover" class="page-cover" :src="item.pageCover" />
          <div class="page-name">{{ item.pageName }}</div>
        </div>
      </el-col>
    </el-row>
    <!-- 新增/修改模态框 -->
    <el-dialog v-model="addOrEdit" width="35%" top="10vh">
      <template #header>
        <div class="dialog-title-container">{{ pageTitle }}</div>
      </template>
      <el-form label-width="80px" size="default" :model="pageForum">
        <el-form-item label="页面名称">
          <el-input style="width:220px" v-model="pageForum.pageName" />
        </el-form-item>
        <el-form-item label="页面标签">
          <el-input style="width:220px" v-model="pageForum.pageLabel" />
        </el-form-item>
        <el-form-item label="页面封面">
          <el-upload
            class="upload-cover"
            drag
            :show-file-list="false"
            action="/api/websiteConfig/image"
            :before-upload="beforeUpload"
            :on-success="uploadCover"
          >
            <div class="upload-empty" v-if="pageForum.pageCover == ''">
              <el-icon ><upload-filled /></el-icon>
              <div class="el-upload__text">
                将文件拖到此处，或<em>点击上传</em>
              </div>
            </div>
            <img
                v-else
                :src="pageForum.pageCover"
                style="width:360px;height: 180px;"
            />
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addOrEdit = false">取 消</el-button>
        <el-button type="primary" @click="addOrEditPage">
          确 定
        </el-button>
      </template>
    </el-dialog>
    <!-- 删除对话框 -->
    <el-dialog v-model="isdeletePage" width="30%">
      <template #header>
        <div class="dialog-title-container">
          <el-icon style="color:#ff9900"><Warning/></el-icon>提示
        </div>
      </template>
      <div style="font-size:1rem">是否删除该页面？</div>
      <template #footer>
        <el-button @click="isdeletePage = false">取 消</el-button>
        <el-button type="primary" @click="deletePage">
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
    this.listPages()
  },
  data: function() {
    return {
      pageTitle:'',
      keywords: "",
      loading: true,
      current: 1,
      size: 8,
      count: 0,
      isdeletePage: false,
      addOrEdit: false,
      pageForum: {
        id: null,
        pageName: "",
        pageLabel: "",
        pageCover: ""
      },
      pageList: []
    }
  },
  methods: {
    openModel(item) {
      if (item) {
        this.pageForum = JSON.parse(item)
        this.pageTitle = '修改页面'
      } else {
        this.pageForum = {
          id: null,
          pageName: "",
          pageLabel: "",
          pageCover: ""
        }
        this.pageTitle = '新建页面'
      }
      this.addOrEdit = true
    },
    listPages() {
      this.axios.get("/api/page/getPages").then(({ data }) => {
        this.pageList = data.data
        this.loading = false
      })
    },
    addOrEditPage() {
      if (this.pageForum.pageName.trim() == "") {
        this.$message.error("页面名称不能为空")
        return false
      }
      if (this.pageForum.pageLabel.trim() == "") {
        this.$message.error("页面标签不能为空")
        return false
      }
      if (this.pageForum.pageCover == null) {
        this.$message.error("页面封面不能为空")
        return false
      }
      if(this.pageForum.id == null){
        this.axios.post("/api/page/addPage", this.pageForum).then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: data.message
            })
            this.listPages()
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            })
          }
        })
      }else{
        this.axios.post("/api/page/updatePage", this.pageForum).then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: data.message
            })
            this.listPages()
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            })
          }
        })
      }
      this.addOrEdit = false
    },
    uploadCover(response) {
      console.log(response)
      this.pageForum.pageCover = response.data
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
        this.pageForum.id = data
        this.isdeletePage = true
      } else {
        this.openModel(data)
      }
    },
    deletePage() {
      this.axios
        .delete("/api/page/deletePage/" + this.pageForum.id)
        .then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: data.message
            })
            this.listPages()
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            })
          }
          this.isdeletePage = false
        })
    }
  }
}
</script>

<style scoped>
.upload-empty{
  height: 180px;
  width: 360px;
}
.page-cover {
  position: relative;
  border-radius: 4px;
  width: 100%;
  height: 170px;
}
.page-name {
  text-align: center;
  margin-top: 0.5rem;
}
.page-item {
  position: relative;
  cursor: pointer;
  margin-bottom: 1rem;
}
.page-opreation {
  position: absolute;
  z-index: 1000;
  top: 0.5rem;
  right: 0.8rem;
}
</style>
