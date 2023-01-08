<template>
  <el-card class="main-card">
    <!-- 标题 -->
    <div class="title">{{ this.$route.name }}</div>
    <div class="operation-container">
      <el-button
        type="primary"
        size="small"
        @click="openModel(null)"
      >
      <el-icon><Plus/></el-icon>
        新增模块
      </el-button>
      <!-- 数据筛选 -->
      <div style="margin-left:auto">
        <el-input
          v-model="keywords"
          size="small"
          placeholder="请输入资源名"
          style="width:200px"
          @keyup.enter.native="listResources"
        >
          <template #prefix>
              <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-button
          type="primary"
          size="small"
          style="margin-left:1rem"
          @click="listResources"
        >
          <template #prefix>
              <el-icon><Search /></el-icon>
          </template>
          搜索
        </el-button>
      </div>
    </div>
    <!-- 权限列表 -->
    <el-table
      v-loading="loading"
      :data="resourceList"
      row-key="id"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="resourceName" label="资源名" width="220" />
      <el-table-column prop="url" label="资源路径" width="300" />
      <el-table-column prop="requetMethod" label="请求方式">
        <template #default="scope">
          <el-tag :type="tagType(scope.row.requestMethod)" v-if="scope.row.requestMethod">
            {{ scope.row.requestMethod }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="isAnonymous" label="匿名访问" align="center">
        <template #default="scope">
          <el-switch
            v-model="scope.row.isAnonymous"
            active-color="#13ce66"
            inactive-color="#F4F4F5"
            :active-value="1"
            :inactive-value="0"
            @change="changeResource(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" align="center">
        <template #default="scope">
          <el-icon style="margin-right:5px"><Timer /></el-icon>
          {{ dataFormat(scope.row.createTime)}}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template #default="scope">
          <el-button
            link
            size="small"
            type="primary"
            @click="openAddResourceModel(scope.row)"
            v-if="scope.row.children"
          >
           <el-icon><Plus /></el-icon> 新增
          </el-button>
          <el-button
            link
            size="small"
            type="primary"
            @click="openEditResourceModel(scope.row)"
          >
            <el-icon><Edit /></el-icon> 修改
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left:10px"
            @confirm="deleteResource(scope.row.id)"
          >
          <template #reference>
            <el-button link size="small" type="primary" slot="reference">
              <el-icon><Delete /></el-icon> 删除
            </el-button>
          </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增模态框 -->
    <el-dialog v-model="addModule" width="30%" :title="moduleTitle">
      <el-form label-width="80px" size="small" :model="resourceForm">
        <el-form-item label="模块名">
          <el-input v-model="resourceForm.resourceName" style="width:220px" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addModule = false">取 消</el-button>
          <el-button type="primary" @click="addOrEditResource">
            确 定
          </el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 新增模态框 -->
    <el-dialog v-model="addResource" width="30%" :title="resourceTitle">
      <el-form label-width="80px" size="medium" :model="resourceForm">
        <el-form-item label="资源名">
          <el-input v-model="resourceForm.resourceName" style="width:220px" />
        </el-form-item>
        <el-form-item label="资源路径">
          <el-input v-model="resourceForm.url" style="width:220px" />
        </el-form-item>
        <el-form-item label="请求方式">
          <el-radio-group v-model="resourceForm.requestMethod">
            <el-radio :label="'GET'">GET</el-radio>
            <el-radio :label="'POST'">POST</el-radio>
            <el-radio :label="'PUT'">PUT</el-radio>
            <el-radio :label="'DELETE'">DELETE</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addResource = false">取 消</el-button>
          <el-button type="primary" @click="addOrEditResource">
            确 定
          </el-button>
        </span>
      </template>
    </el-dialog>
  </el-card>
</template>

<script>
import {praseDateStr} from "@/assets/js/common.js"
export default {
  created() {
    this.listResources();
  },
  data() {
    return {
      loading: true,
      keywords: "",
      resourceList: [],
      addModule: false,
      addResource: false,
      resourceForm: {},
      moduleTitle: '',
      resourceTitle: ''
    };
  },
  methods: {
    listResources() {
      this.axios
        .get("/api/resource/getAllResourcesList", {
          params: {
            keywords: this.keywords
          }
        })
        .then(({ data }) => {
          this.resourceList = data.data;
          this.loading = false;
        });
    },
    changeResource(resource) {
      this.axios.post("/api/resource/saveOrUpdateResource", resource).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listResources();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
      });
    },
    openModel(resource) {
      if (resource != null) {
        this.moduleTitle = '修改模块'
        this.resourceForm = JSON.parse(JSON.stringify(resource));
      } else {
        this.moduleTitle = '新增模块'
        this.resourceForm = {};
      }
      this.addModule = true;
    },
    openEditResourceModel(resource) {
      if (resource.url == null) {
        this.openModel(resource);
        return false;
      }
      this.resourceForm = JSON.parse(JSON.stringify(resource));
      this.resourceTitle = "修改资源";
      this.addResource = true;
    },
    openAddResourceModel(resource) {
      this.resourceForm = {};
      this.resourceForm.parentId = resource.id;
      this.resourceTitle = "新增资源";
      this.addResource = true;
    },
    deleteResource(id) {
      this.axios.delete("/api/resource/deleteResource/" + id).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listResources();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
      });
    },
    addOrEditResource() {
      if (this.resourceForm.resourceName.trim() == "") {
        this.$message.error("资源名不能为空");
        return false;
      }
      this.axios
        .post("/api/resource/saveOrUpdateResource", this.resourceForm)
        .then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: data.message
            });
            this.listResources();
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            });
          }
          this.addModule = false;
          this.addResource = false;
        });
    }
  },
  computed: {
    tagType() {
      return function(type) {
        switch (type) {
          case "GET":
            return "";
          case "POST":
            return "success";
          case "PUT":
            return "warning";
          case "DELETE":
            return "danger";
        }
      };
    },
    dataFormat(){
      return function(date){
        return praseDateStr(date,"YYYY-MM-DD")
      }
    }
  }
};
</script>
