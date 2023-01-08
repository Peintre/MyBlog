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
        :disabled="tagIdList.length == 0"
        @click="isDelete = true"
      >
      <el-icon><Delete/></el-icon>
        <span>批量删除</span>
      </el-button>
      <div style="margin-left:auto">
        <el-input
          v-model="keywords"
          size="default"
          placeholder="请输入标签名"
          style="width:200px"
          @keyup.enter.native="searchTags"
        >
          <template #prefix>
              <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-button
          type="primary"
          size="default"
          style="margin-left:1rem"
          @click="searchTags"
        >
          <el-icon><Search /></el-icon>
          <span>搜索</span>
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table
      border
      :data="tagList"
      v-loading="loading"
      @selection-change="selectionChange"
    >
      <!-- 表格列 -->
      <el-table-column type="selection" width="35" />
      <!-- 标签名 -->
      <el-table-column prop="tagName" label="标签名" align="center">
        <template #default="scope">
          <el-tag>
            {{ scope.row.tagName }}
          </el-tag>
        </template>
      </el-table-column>
      <!-- 文章量 -->
      <el-table-column prop="articleCount" label="文章量" align="center" />
      <!-- 标签创建时间 -->
      <el-table-column prop="createTime" label="创建时间" align="center">
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
            @confirm="deleteTag(scope.row.id)"
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
    <el-dialog v-model="isDelete" width="30%">
      <template #header>
        <div class="dialog-title-container">
          <el-icon style="color:#ff9900"><Warning/></el-icon>提示
        </div>
      </template> 
      <div style="font-size:1rem">是否删除选中项？</div>
      <template #footer>
        <el-button @click="isDelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteTag(null)">
          确 定
        </el-button>
      </template>
    </el-dialog>
    <!-- 编辑对话框 -->
    <el-dialog v-model="addOrEdit" width="30%">
      <template #header>
        <div class="dialog-title-container">{{ tagTitle }}</div>
      </template>
      <el-form label-width="80px" size="default" :model="tagForm">
        <el-form-item label="标签名">
          <el-input style="width:220px" v-model="tagForm.tagName" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addOrEdit = false">取 消</el-button>
        <el-button type="primary" @click="addOrEditTag">
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
    this.listTags()
  },
  data: function() {
    return {
      tagTitle: '',
      isDelete: false,
      loading: true,
      addOrEdit: false,
      keywords: null,
      tagList: [],
      tagIdList: [],
      tagForm: {
        id: null,
        tagName: ""
      },
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
    selectionChange(tagList) {
      this.tagIdList = []
      tagList.forEach(item => {
        this.tagIdList.push(item.id)
      })
    },
    searchTags() {
      this.current = 1
      this.listTags()
    },
    sizeChange(size) {
      this.size = size
      this.listTags()
    },
    currentChange(current) {
      this.current = current
      this.listTags()
    },
    deleteTag(id) {
      var param = {}
      if (id == null) {
        param.idList = this.tagIdList 
      } else {
        param.idList = [id] 
      }
      this.axios.post("/api/tag/deleteTags", param).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          })
          this.listTags()
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          })
        }
      })
      this.isDelete = false
    },
    listTags() {
      this.axios
        .get("/api/tag/getTags", {
          params: {
            current: this.current,
            size: this.size,
            keywords: this.keywords
          }
        })
        .then(({ data }) => {
          this.tagList = data.data.recordList
          this.count = data.data.count
          this.loading = false
        })
    },
    openModel(tag) {
      if (tag != null) {
        this.tagForm = JSON.parse(JSON.stringify(tag))
        this.tagTitle = "修改标签"
      } else {
        this.tagForm.id = null
        this.tagForm.tagName = ""
        this.tagTitle = "添加标签"
      }
      this.addOrEdit = true
    },
    addOrEditTag() {
      if (this.tagForm.tagName.trim() == "") {
        this.$message.error("标签名不能为空")
        return false
      }
      this.axios.post("/api/tag/addTag", this.tagForm).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          })
          this.listTags()
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          })
        }
      })
      this.addOrEdit = false
    }
  }
}
</script>
