<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <el-button
        type="primary"
        size="default"
        @click="openModel(null)"
      >
      <el-icon><Plus/></el-icon>
        <span>新增</span>
      </el-button>
      <el-button
        type="danger"
        size="default"
        :disabled="linkIdList.length == 0"
        @click="deleteFlag = true"
      >
      <el-icon><Delete/></el-icon>
        <span>批量删除</span>
      </el-button>
      <!-- 条件筛选 -->
      <div style="margin-left:auto">
        <el-input
          v-model="keywords"
          size="default"
          placeholder="请输入友链名"
          style="width:200px"
          @keyup.enter.native="searchLinks"
        >
          <template #prefix>
              <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-button
          type="primary"
          size="default"
          style="margin-left:1rem"
          @click="searchLinks"
        >
        <el-icon><Search /></el-icon>
        <span>搜索</span>
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table
      border
      :data="linkList"
      @selection-change="selectionChange"
      v-loading="loading"
    >
      <!-- 表格列 -->
      <el-table-column type="selection" width="35" />
      <el-table-column
        prop="linkAvatar"
        label="链接头像"
        align="center"
        width="180"
      >
        <template #default="scope">
          <img :src="scope.row.linkAvatar" width="40" height="40" />
        </template>
      </el-table-column>
      <el-table-column prop="linkName" label="链接名" align="center" />
      <el-table-column prop="linkAddress" label="链接地址" align="center" />
      <el-table-column prop="linkIntro" label="链接介绍" align="center" />
      <el-table-column
        prop="createTime"
        label="创建时间"
        width="140"
        align="center"
      >
        <template #default="scope">
            <el-icon style="margin-right:5px"><Timer /></el-icon>
            {{ dataFormat(scope.row.createTime)}}
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="160">
        <template #default="scope">
          <el-button type="primary" size="small" @click="openModel(scope.row)">
            编辑
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left:1rem"
            @confirm="deleteLink(scope.row.id)"
          >
            <template #reference>
              <el-button size="small" type="danger" slot="reference">
                删除
              </el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      class="pagination-container"
      background
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page="current"
      :page-size="size"
      :total="count"
      :page-sizes="[10, 20]"
      layout="total, sizes, prev, pager, next, jumper"
    />
    <!-- 批量删除对话框 -->
    <el-dialog v-model="deleteFlag" width="30%">
      <template #header>
        <div class="dialog-title-container">
          <el-icon style="color:#ff9900"><Warning/></el-icon>提示
        </div>
      </template>  
      <div style="font-size:1rem">是否删除选中项？</div>
      <template #footer>
        <el-button @click="deleteFlag = false">取 消</el-button>
        <el-button type="primary" @click="deleteLink(null)">
          确 定
        </el-button>
      </template>
    </el-dialog>
    <!-- 添加对话框 -->
    <el-dialog v-model="addOrEdit" width="30%">
      <template #header>
        <div class="dialog-title-container">{{linkTitle}}</div>
      </template>
      <el-form label-width="80px" size="default" :model="linkForm">
        <el-form-item label="链接名">
          <el-input style="width:250px" v-model="linkForm.linkName" />
        </el-form-item>
        <el-form-item label="链接头像">
          <el-input style="width:250px" v-model="linkForm.linkAvatar" />
        </el-form-item>
        <el-form-item label="链接地址">
          <el-input style="width:250px" v-model="linkForm.linkAddress" />
        </el-form-item>
        <el-form-item label="链接介绍">
          <el-input style="width:250px" v-model="linkForm.linkIntro" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addOrEdit = false">取 消</el-button>
        <el-button type="primary" @click="addOrEditCategory">
          确 定
        </el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script>
import {praseDateStr} from "@/assets/js/common.js"
export default {
  created() {
    this.listLinks()
  },
  data: function() {
    return {
      linkTitle:'',
      loading: true,
      deleteFlag: false,
      addOrEdit: false,
      linkIdList: [],
      linkList: [],
      linkForm: {
        id: null,
        linkName: "",
        linkAvatar: "",
        linkIntro: "",
        linkAddress: ""
      },
      keywords: null,
      current: 1,
      size: 10,
      count: 0
    }
  },
  computed: {
    dataFormat(){
      return function(date){
        return praseDateStr(date,"YYYY-MM-DD")
      }
    }
  },
  methods: {
    selectionChange(linkList) {
      this.linkIdList = []
      linkList.forEach(item => {
        this.linkIdList.push(item.id)
      })
    },
    searchLinks() {
      this.current = 1
      this.listLinks()
    },
    sizeChange(size) {
      this.size = size
      this.listLinks()
    },
    currentChange(current) {
      this.current = current
      this.listLinks()
    },
    deleteLink(id) {
      var param = {}
      if (id == null) {
        param.idList = this.linkIdList 
      } else {
        param.idList = [id] 
      }
      this.axios.post("/api/friendLink/deleteLinks", param).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          })
          this.listLinks()
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          })
        }
        this.deleteFlag = false
      })
    },
    openModel(link) {
      if (link != null) {
        this.linkForm = JSON.parse(JSON.stringify(link))
        this.linkTitle = "修改友链"
      } else {
        this.linkForm.id = null
        this.linkForm.linkName = ""
        this.linkForm.linkAvatar = ""
        this.linkForm.linkIntro = ""
        this.linkForm.linkAddress = ""
        this.linkTitle = "添加友链"
      }
      this.addOrEdit = true
    },
    addOrEditCategory() {
      if (this.linkForm.linkName.trim() == "") {
        this.$message.error("友链名不能为空")
        return false
      }
      if (this.linkForm.linkAvatar.trim() == "") {
        this.$message.error("友链头像不能为空")
        return false
      }
      if (this.linkForm.linkIntro.trim() == "") {
        this.$message.error("友链介绍不能为空")
        return false
      }
      if (this.linkForm.linkAddress.trim() == "") {
        this.$message.error("友链地址不能为空")
        return false
      }
      if(this.linkForm.id == null){
        this.axios.post("/api/friendLink/addLink", this.linkForm).then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: data.message
            })
            this.listLinks()
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            })
          }
          this.addOrEdit = false
        })
      }else{
        this.axios.post("/api/friendLink/updateLink", this.linkForm).then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: data.message
            })
            this.listLinks()
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            })
          }
          this.addOrEdit = false
        })
      }
    },
    listLinks() {
      this.axios
        .get("/api/friendLink/getLinks", {
          params: {
            current: this.current,
            size: this.size,
            keywords: this.keywords
          }
        })
        .then(({ data }) => {
          this.linkList = data.data.recordList
          this.count = data.data.count
          this.loading = false
        })
    }
  }
}
</script>
