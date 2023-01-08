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
        :disabled="this.categoryIdList.length == 0"
        @click="isDelete = true"
      >
      <el-icon><Delete/></el-icon>
        <span>批量删除</span>
      </el-button>
      <div style="margin-left:auto">
        <el-input
          v-model="keywords"
          size="default"
          placeholder="请输入分类名"
          style="width:200px"
          @keyup.enter.native="searchCategories"
        >
          <template #prefix>
              <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-button
          type="primary"
          size="default"
          style="margin-left:1rem"
          @click="searchCategories"
        >
          <el-icon><Search /></el-icon>
          <span>搜索</span>
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table
      border
      :data="categoryList"
      @selection-change="selectionChange"
      v-loading="loading"
    >
      <!-- 表格列 -->
      <el-table-column type="selection" width="35" />
      <!-- 分类名 -->
      <el-table-column prop="categoryName" label="分类名" align="center" />
      <!-- 文章量 -->
      <el-table-column prop="articleCount" label="文章量" align="center" />
      <!-- 分类创建时间 -->
      <el-table-column prop="createTime" label="创建时间" align="center">
        <template #default="scope">
            <el-icon style="margin-right:5px"><Timer /></el-icon>
            {{ dataFormat(scope.row.createTime)}}
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" width="160" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" @click="openModel(scope.row)">
            编辑
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left:1rem"
            @confirm="deleteCategory(scope.row.id)"
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
          <el-icon style="color:#ff9900"><Warning/></el-icon>
          提示
        </div>
      </template> 
      <div style="font-size:1rem">是否删除选中项？</div>
      <template #footer>
        <el-button @click="isDelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteCategory(null)">
          确 定
        </el-button>
      </template>
    </el-dialog>
    <!-- 添加/编辑对话框 -->
    <el-dialog v-model="addOrEdit" width="30%">
      <template #header>
        <div class="dialog-title-container">{{ categoryTitle }}</div>
      </template>
      <el-form label-width="80px" size="default" :model="categoryForm">
        <el-form-item label="分类名">
          <el-input v-model="categoryForm.categoryName" style="width:220px" />
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
    this.listCategories()
  },
  data: function() {
    return {
      categoryTitle: '',
      isDelete: false,
      loading: true,
      addOrEdit: false,
      keywords: null,
      categoryIdList: [],
      categoryList: [],
      categoryForm: {
        id: null,
        categoryName: ""
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
    selectionChange(categoryList) {
      this.categoryIdList = []
      categoryList.forEach(item => {
        this.categoryIdList.push(item.id)
      })
    },
    searchCategories() {
      this.current = 1
      this.listCategories()
    },
    sizeChange(size) {
      this.size = size
      this.listCategories()
    },
    currentChange(current) {
      this.current = current;
      this.listCategories()
    },
    deleteCategory(id) {
      var param = {}
      if (id == null) {
        param.idList = this.categoryIdList
      } else {
        param.idList = [id]
      }
      this.axios.post("/api/category/deleteCategories", param).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          })
          this.listCategories();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          })
        }
        this.isDelete = false
      });
    },
    listCategories() {
      this.axios
        .get("/api/category/getCategories", {
          params: {
            current: this.current,
            size: this.size,
            keywords: this.keywords
          }
        })
        .then(({ data }) => {
          this.categoryList = data.data.recordList
          this.count = data.data.count
          this.loading = false
        })
    },
    openModel(category) {
      if (category != null) {
        this.categoryForm = JSON.parse(JSON.stringify(category))
        this.categoryTitle = "修改分类"
      } else {
        this.categoryForm.id = null
        this.categoryForm.categoryName = ""
        this.categoryTitle = "添加分类"
      }
      this.addOrEdit = true
    },
    addOrEditCategory() {
      if (this.categoryForm.categoryName.trim() == "") {
        this.$message.error("分类名不能为空")
        return false
      }
      if(this.categoryForm.id != null){
        //修改分类名
        this.axios.post("/api/category/upDateCategory", this.categoryForm)
          .then(({ data }) => {
            if (data.flag) {
              this.$notify.success({
                title: "成功",
                message: data.message
              })
              this.listCategories()
            } else {
              this.$notify.error({
                title: "失败",
                message: data.message
              })
            }
            this.addOrEdit = false
          })
      }else{
        //添加分类
        this.axios.post("/api/category/addCategory", this.categoryForm)
          .then(({ data }) => {
            if (data.flag) {
              this.$notify.success({
                title: "成功",
                message: data.message
              })
              this.listCategories()
            } else {
              this.$notify.error({
                title: "失败",
                message: data.message
              })
            }
            this.addOrEdit = false
          })
      } 
    }
  }
}
</script>
