<template>
    <el-card class="main-card">
      <div class="title">{{ this.$route.name }}</div>
      <!-- 表格操作 -->
      <div class="operation-container">
        <el-button 
         type="primary" 
         size="small" 
         @click="openRoleModel">
          <el-icon><Plus/></el-icon>新增
        </el-button>
        <el-button
          type="danger"
          size="small"
          :disabled="this.roleIdList.length == 0"
          @click="isDelete = true"
        >
        <el-icon><Delete/></el-icon>批量删除
        </el-button>
        <!-- 条件筛选 -->
        <div style="margin-left:auto">
          <el-input
            v-model="keywords"
            size="small"
            placeholder="请输入角色名"
            style="width:200px"
            @keyup.enter.native="searchRoles"
          >
          <template #prefix>
              <el-icon><Search /></el-icon>
          </template>
          </el-input>
          <el-button
            type="primary"
            size="small"
            style="margin-left:1rem"
            @click="searchRoles"
          >
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
          <el-button
            type="primary"
            size="small"
            style="margin-left:1rem"
            @click="resetRoles"
          >
            重置
          </el-button>
        </div>
      </div>
      <!-- 表格展示 -->
      <el-table
        :data="roleList"
        border
        @selection-change="selectionChange"
        v-loading="loading"
      >
       <!-- 表格列 -->
        <el-table-column type="selection" width="55" />
        <el-table-column prop="roleName" label="角色名" align="center" />
        <el-table-column prop="roleLabel" label="权限标签" align="center">
          <template #default="scope">
            <el-tag>
              {{ scope.row.roleLabel }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="isDisable" label="禁用" align="center" width="100">
          <template #default="scope">
            <el-switch
              v-model="scope.row.isDisable"
              active-color="#13ce66"
              inactive-color="#F4F4F5"
              :active-value="1"
              :inactive-value="0"
              @change="changeDisable(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
          width="150"
          align="center"
        >
          <template #default="scope">
            <el-icon style="margin-right:5px"><Timer /></el-icon>
            {{ dataFormat(scope.row.createTime)}}
          </template>
        </el-table-column>
        <!-- 列操作 -->
        <el-table-column label="操作" align="center" width="280">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="openMenuModel(scope.row)">
                <el-icon><Edit /></el-icon> 菜单权限
            </el-button>
            <el-button
              link
              type="primary"
              size="small"
              @click="openResourceModel(scope.row)"
            >
                <el-icon><FolderChecked /></el-icon> 资源权限
            </el-button>
            <el-button
              link
              type="primary"
              size="small"
              @click="openRoleModel(scope.row)"
            >
                <el-icon><Edit /></el-icon> 编辑
            </el-button>
            <el-popconfirm
              title="确定删除吗？"
              style="margin-left:10px"
              confirm-button-text="确定"
              cancel-button-text="取消"
              @confirm="deleteRoles(scope.row.id)"
            >
                <template #reference>
                    <el-button size="small" link type="primary" slot="reference">
                        <el-icon><Delete /></el-icon> 删除
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
        :page-sizes="[5, 10]"
        layout="total, sizes, prev, pager, next, jumper"
      />
      <!-- 菜单对话框 -->
      <el-dialog v-model="roleMenu" width="30%" title="角色菜单权限修改" center>  
        <el-form label-width="80px" size="default" :model="roleForm">
          <el-form-item label="角色名:">
            <span>{{roleForm.roleName}}</span>
          </el-form-item>
          <el-form-item label="权限标签:">
              <el-tag>
                {{ roleForm.roleLabel }}
              </el-tag>
          </el-form-item>
          <el-form-item label="菜单权限:">
            <el-tree
              :data="menuList"
              :default-checked-keys="roleForm.menuIdList"
              check-strictly
              show-checkbox
              node-key="id"
              ref="menuTree"
            />
          </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="roleMenu = false">取 消</el-button>
                <el-button type="primary" @click="saveOrUpdateRoleMenu">确 定</el-button>
            </span>
        </template>
      </el-dialog>
      <!-- 资源对话框 -->
      <el-dialog v-model="roleResource" width="30%" top="9vh" title="角色资源权限修改" center>
        <el-form label-width="80px" size="default" :model="roleForm">
          <el-form-item label="角色名:">
            <span>{{roleForm.roleName}}</span>
          </el-form-item>
          <el-form-item label="权限标签:">
              <el-tag>
                {{ roleForm.roleLabel }}
              </el-tag>
          </el-form-item>
          <el-form-item label="资源权限:">
            <el-tree
              :data="resourceList"
              :default-checked-keys="roleForm.resourceIdList"
              show-checkbox
              node-key="id"
              ref="resourceTree"
            />
          </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="roleResource = false">取 消</el-button>
                <el-button type="primary" @click="saveOrUpdateRoleResource">
                    确 定
                </el-button>
            </span>
        </template>
      </el-dialog>
      <!-- 批量删除对话框 -->
      <el-dialog v-model="isDelete" width="30%">
        <template #header>
            <el-icon style="color:#ff9900"><Warning /></el-icon>提示
        </template>
        <div style="font-size:1rem">是否删除选中项？</div>
        <template #footer>
            <el-button @click="isDelete = false">取 消</el-button>
            <el-button type="primary" @click="deleteRoles(null)">
                确 定
            </el-button>
        </template>
      </el-dialog>
      <!-- 新增对话框 -->
      <el-dialog v-model="isAdd" width="30%" title="新增角色" center>
        <el-form label-width="80px" size="default" :model="roleForm">
          <el-form-item label="角色名称:">
            <el-input v-model="roleForm.roleName" style="width:250px" />
          </el-form-item>
          <el-form-item label="权限标签:">
            <el-input v-model="roleForm.roleLabel" style="width:250px" />
          </el-form-item>
          <el-form-item label="是否禁用:">
            <el-switch
              v-model="roleForm.isDisable"
              active-color="#13ce66"
              inactive-color="#F4F4F5"
              :active-value="1"
              :inactive-value="0"
            ></el-switch>
          </el-form-item>
          
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="isAdd = false">取 消</el-button>
                <el-button type="primary" @click="saveNewRole">确 定</el-button>
            </span>
        </template>
      </el-dialog>
    </el-card>
  </template>

<script>
import {praseDateStr} from "@/assets/js/common.js"
export default {
  created() {
    this.listRoles();
  },
  data: function() {
    return {
      loading: true,
      isDelete: false,
      isAdd: false,
      roleList: [],
      roleIdList: [],
      keywords: null,
      current: 1,
      size: 10,
      count: 0,
      roleMenu: false,
      roleResource: false,
      resourceList: [],
      menuList: [],
      roleForm: {
        roleName: "",
        roleLabel: "",
        isDisable: 0,
        resourceIdList: [],
        menuIdList: []
      }
    };
  },
  computed:{
    dataFormat(){
      return function(date){
        return praseDateStr(date,"YYYY-MM-DD")
      }
    }
  },
  methods: {
    searchRoles() {
      this.current = 1;
      this.listRoles();
    },
    resetRoles(){
      this.keywords = null;
      this.current = 1;
      this.listRoles();
    },
    sizeChange(size) {
      this.size = size;
      this.listRoles();
    },
    currentChange(current) {
      this.current = current;
      this.listRoles();
    },
    selectionChange(roleList) {
      this.roleIdList = [];
      roleList.forEach(item => {
        this.roleIdList.push(item.id);
      });
    },
    listRoles() {
      this.axios
        .get("/api/role/getAllRoles", {
          params: {
            current: this.current,
            size: this.size,
            keywords: this.keywords
          }
        })
        .then(({ data }) => {
          this.roleList = data.data.recordList;
          this.count = data.data.count;
          this.loading = false;
        });
      this.axios.get("/api/resource/getAllResources").then(({ data }) => {
        this.resourceList = data.data;
      });
      this.axios.get("/api/menu/getAllMenus").then(({ data }) => {
        this.menuList = data.data;
      });
    },
    deleteRoles(id) {
      var param = {};
      if (id == null) {
        param = { data: this.roleIdList };
      } else {
        param = { data: [id] };
      }
      this.axios.delete("/api/role/deleteRoles", param).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listRoles();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
        this.isDelete = false;
      });
    },
    openMenuModel(role) {
      this.roleForm = JSON.parse(JSON.stringify(role));
      this.roleMenu = true;
      this.$nextTick(() => {
            this.$refs.menuTree.setCheckedKeys(role.menuIdList)
      });
    },
    openResourceModel(role) {
      this.roleForm = JSON.parse(JSON.stringify(role));
      console.log(this.roleForm)
      this.roleResource = true;
      this.$nextTick(() => {
            this.$refs.resourceTree.setCheckedKeys(role.resourceIdList)
      });
    },
    openRoleModel(role){
      if(role){
        this.roleForm = JSON.parse(JSON.stringify(role));
      }else{
        this.roleForm.roleName = ''
        this.roleForm.roleLabel = ''
        this.roleForm.menuIdList = null
        this.roleForm.resourceIdList = null
      }
      this.isAdd = true
    },
    saveNewRole(){
      if ((!this.roleForm.roleName) || this.roleForm.roleName.trim() == "") {
        this.$message.error("角色名不能为空");
        return false;
      }
      if ((!this.roleForm.roleLabel) || this.roleForm.roleLabel.trim() == "") {
        this.$message.error("权限标签不能为空");
        return false;
      }
      if(this.roleForm.id){
        this.axios.post("/api/role/updateRole",this.roleForm).then(({data}) =>{
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listRoles();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
        this.isAdd = false
      })
      }else{
        this.axios.post("/api/role/addRole",this.roleForm).then(({data}) =>{
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listRoles();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
        this.isAdd = false
      })
      }
    },
    changeDisable(role){
      this.roleForm = JSON.parse(JSON.stringify(role));
      this.axios.post("/api/role/updateRole",this.roleForm).then(({data}) =>{
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listRoles();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
      })
    },
    saveOrUpdateRoleResource() {
      this.roleForm.menuIdList = null;
      this.roleForm.resourceIdList = this.$refs.resourceTree.getCheckedKeys();
      this.axios.post("/api/role/updateRoleMenuResource", this.roleForm).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listRoles();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
        this.roleResource = false;
      });
    },
    saveOrUpdateRoleMenu() {
      this.roleForm.resourceIdList = null;
      this.roleForm.menuIdList = this.$refs.menuTree
        .getCheckedKeys()
        .concat(this.$refs.menuTree.getHalfCheckedKeys());
      this.axios.post("/api/role/updateRoleMenuResource", this.roleForm).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listRoles();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
        this.roleMenu = false;
      });
    }
  }
};
</script>