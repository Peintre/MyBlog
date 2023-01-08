<template>
  <el-card class="main-card">
    <!-- 标题 -->
    <div class="title">{{ this.$route.name }}</div>
    <div class="operation-container">
      <!-- 数据筛选 -->
      <div style="margin-left:auto">
        <el-input
          v-model="keywords"
          size="small"
          placeholder="请输入用户昵称"
          style="width:200px"
          @keyup.enter.native="listOnlineUsers"
        >
          <template #prefix>
              <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-button
          type="primary"
          size="small"
          style="margin-left:1rem"
          @click="listOnlineUsers"
        >
          <el-icon><Search /></el-icon>
          搜索
        </el-button>
      </div>
    </div>
    <!-- 权限列表 -->
    <el-table v-loading="loading" :data="userList">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="avatar" label="头像" align="center" width="100">
        <template #default="scope">
          <img :src="scope.row.avatar" width="40" height="40" />
        </template>
      </el-table-column>
      <el-table-column prop="nickname" label="昵称" align="center" />
      <el-table-column prop="ipAddress" label="ip地址" align="center" />
      <el-table-column
        prop="ipSource"
        label="登录地址"
        align="center"
        width="200"
      />
      <el-table-column
        prop="browser"
        label="浏览器"
        align="center"
        width="160"
      />
      <el-table-column prop="os" label="操作系统" align="center" />
      <el-table-column
        prop="lastLoginTime"
        label="登录时间"
        align="center"
        width="200"
      >
        <template #default="scope">
          <el-icon style="margin-right:5px"><Timer /></el-icon>
          {{ dataFormat(scope.row.lastLoginTime)}}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150">
        <template #default="scope">
          <el-popconfirm
            title="确定下线吗？"
            style="margin-left:10px"
            @confirm="removeOnlineUser(scope.row)"
          >
            <template #reference>
              <el-button link size="small" type="primary" slot="reference">
                  <el-icon><Delete /></el-icon>  下线
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
  </el-card>
</template>

<script>
import {praseDateStr} from "@/assets/js/common.js"
export default {
  created() {
    this.listOnlineUsers();
  },
  data() {
    return {
      loading: true,
      userList: [],
      keywords: null,
      current: 1,
      size: 10,
      count: 0,
      isCheck: false,
      optLog: {}
    };
  },
  methods: {
    listOnlineUsers() {
      this.axios
        .get("/api/userInfo/listOnlineUsers", {
          params: {
            current: this.current,
            size: this.size,
            keywords: this.keywords
          }
        })
        .then(({ data }) => {
          this.userList = data.data.recordList;
          this.count = data.data.count;
          this.loading = false;
        });
    },
    sizeChange(size) {
      this.size = size;
      this.listOnlineUsers();
    },
    currentChange(current) {
      this.current = current;
      this.listOnlineUsers();
    },
    removeOnlineUser(user) {
      this.axios
        .delete("/api/admin/users/" + user.userInfoId + "/online")
        .then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: data.message
            });
            this.listOnlineUsers();
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            });
          }
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

<style scoped>
label {
  font-weight: bold !important;
}
</style>
