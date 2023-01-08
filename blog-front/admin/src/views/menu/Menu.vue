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
      <el-icon><Plus/></el-icon>新增菜单
      </el-button>
      <!-- 数据筛选 -->
      <div style="margin-left:auto">
        <el-input
          v-model="keywords"
          size="small"
          placeholder="请输入菜单名"
          style="width:200px"
          @keyup.enter.native="listMenus"
        >
          <template #prefix>
              <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-button
          type="primary"
          size="small"
          style="margin-left:1rem"
          @click="listMenus"
        >
        <el-icon><Search /></el-icon>搜索
        </el-button>
      </div>
    </div>
    <!-- 权限列表 -->
    <el-table
      v-loading="loading"
      :data="menuList"
      row-key="id"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <!-- 菜单名称 -->
      <el-table-column prop="name" label="菜单名称" width="140" />
      <!-- 菜单icon -->
      <el-table-column prop="icon" align="center" label="图标" width="100">
        <template #default="scope">
          <i :class="'iconfont ' + scope.row.icon" />
        </template>
      </el-table-column>
      <!-- 菜单排序 -->
      <el-table-column
        prop="orderNum"
        align="center"
        label="排序"
        width="100"
      />
      <!-- 访问路径 -->
      <el-table-column prop="path" label="访问路径" />
      <!-- 组件路径 -->
      <el-table-column prop="component" label="组件路径" />
      <!-- 是否隐藏 -->
      <el-table-column prop="isHidden" label="隐藏" align="center" width="80">
        <template #default="scope">
          <el-switch
            v-model="scope.row.isHidden"
            active-color="#13ce66"
            inactive-color="#F4F4F5"
            :active-value="1"
            :inactive-value="0"
            @change="changeDisable(scope.row)"
          />
        </template>
      </el-table-column>
      <!-- 创建时间 -->
      <el-table-column
        prop="createTime"
        label="创建时间"
        align="center"
        width="150"
      >
        <template #default="scope">
            <el-icon style="margin-right:5px"><Timer /></el-icon>
            {{ dataFormat(scope.row.createTime)}}
        </template>
      </el-table-column>
      <!-- 操作 -->
      <el-table-column label="操作" align="center" width="250">
        <template #default="scope">
          <el-button
            link
            type="primary"
            size="small"
            @click="openModel(scope.row, 1)"
            v-if="scope.row.children"
          >
            <el-icon><Plus /></el-icon> 新增
          </el-button>
          <el-button link type="primary" size="small" @click="openModel(scope.row, 2)">
            <el-icon><Edit /></el-icon> 修改
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left:10px"
            @confirm="deleteMenu(scope.row.id)"
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
    <!-- 新增模态框 -->
    <el-dialog v-model="addMenu" width="30%" top="12vh" :title="menuTitle">
      <el-form label-width="80px" size="small" :model="menuForm">
        <!-- 菜单类型 -->
        <el-form-item label="菜单类型" v-if="show">
          <el-radio-group v-model="isCatalog">
            <el-radio :label="true">目录</el-radio>
            <el-radio :label="false">一级菜单</el-radio>
          </el-radio-group>
        </el-form-item>
        <!-- 菜单名称 -->
        <el-form-item label="菜单名称">
          <el-input v-model="menuForm.name" style="width:220px" />
        </el-form-item>
        <!-- 菜单图标 -->
        <el-form-item label="菜单图标">
          <el-popover placement="bottom-start" width="300" trigger="click">
            <!-- 输入框 -->
            <template #reference>
              <el-input
                v-model="menuForm.icon"
                style="width:220px"
              >
                <template #prefix>
                  <i :class="'iconfont ' + menuForm.icon"></i>
                </template>
              </el-input>
            </template>
            <!-- 显示内容 -->
            <el-row>
              <el-col
                v-for="(item, index) of iconList"
                :key="index"
                :md="12"
                :gutter="10"
              >
                <div class="icon-item" @click="checkIcon(item)">
                  <i :class="'iconfont ' + item" /> {{ item }}
                </div>
              </el-col>
            </el-row>
          </el-popover>
        </el-form-item>
        <!-- 组件路径 -->
        <el-form-item label="组件路径" v-show="!isCatalog">
          <el-input v-model="menuForm.component" style="width:220px" />
        </el-form-item>
        <!-- 路由地址 -->
        <el-form-item label="访问路径">
          <el-input v-model="menuForm.path" style="width:220px" />
        </el-form-item>
        <!-- 显示排序 -->
        <el-form-item label="显示排序">
          <el-input-number
            v-model="menuForm.orderNum"
            controls-position="right"
            :min="1"
            :max="10"
          />
        </el-form-item>
        <!-- 显示状态 -->
        <el-form-item label="显示状态">
          <el-radio-group v-model="menuForm.isHidden">
            <el-radio :label="0">显示</el-radio>
            <el-radio :label="1">隐藏</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addMenu = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateMenu">
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
    this.listMenus();
  },
  data() {
    return {
      menuTitle:'',
      keywords: "",
      loading: true,
      addMenu: false,
      isCatalog: true,
      show: true,
      menuList: [],
      menuForm: {
        id: null,
        name: "",
        icon: "",
        component: "",
        path: "",
        orderNum: 1,
        parentId: null,
        isHidden: 0
      },
      iconList: [
        "el-icon-myshouye",
        "el-icon-myfabiaowenzhang",
        "el-icon-myyonghuliebiao",
        "el-icon-myxiaoxi",
        "el-icon-myliuyan",
        "el-icon-myshouye",
        "el-icon-myfabiaowenzhang",
        "el-icon-myyonghuliebiao",
        "el-icon-myxiaoxi",
        "el-icon-myliuyan"
      ]
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
    listMenus() {
      this.axios
        .get("/api/menu/getMenusList", {
          params: {
            keywords: this.keywords
          }
        })
        .then(({ data }) => {
          this.menuList = data.data;
          this.loading = false;
        });
    },
    openModel(menu, type) {
      if (menu) {
        this.show = false;
        this.isCatalog = false;
        switch (type) {
          case 1:
            this.menuTitle = '新增子菜单'
            this.menuForm = {
              id: null,
              name: "",
              icon: "",
              component: "",
              path: "",
              orderNum: 1,
              parentId: null,
              isHidden: 0
            };
            this.menuForm.parentId = JSON.parse(JSON.stringify(menu.id));
            break;
          case 2: 
            this.menuTitle = '编辑菜单'
            this.menuForm = JSON.parse(JSON.stringify(menu));
            break;
        }
      } else {
        this.show = true;
        this.menuTitle = '新增菜单'
        this.menuForm = {
          id: null,
          name: "",
          icon: "",
          component: "Layout",
          path: "",
          orderNum: 1,
          parentId: null,
          isHidden: 0
        };
      }
      this.addMenu = true;
    },
    checkIcon(icon) {
      this.menuForm.icon = icon;
    },
    saveOrUpdateMenu() {
      if (this.menuForm.name.trim() == "") {
        this.$message.error("菜单名不能为空");
        return false;
      }
      if (this.menuForm.icon.trim() == "") {
        this.$message.error("菜单icon不能为空");
        return false;
      }
      if (this.menuForm.component.trim() == "") {
        this.$message.error("菜单组件路径不能为空");
        return false;
      }
      if (this.menuForm.path.trim() == "") {
        this.$message.error("菜单访问路径不能为空");
        return false;
      }
      this.axios.post("/api/menu/saveOrUpdateMenu", this.menuForm).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: "操作成功"
          });
          this.listMenus();
        } else {
          this.$notify.error({
            title: "失败",
            message: "操作失败"
          });
        }
        this.addMenu = false;
      });
    },
    deleteMenu(id) {
      this.axios.delete("/api/menu/deleteMenu/" + id).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: "删除成功"
          });
          this.listMenus();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
      });
    }
  }
};
</script>

<style scoped>
.icon-item {
  cursor: pointer;
  padding: 0.5rem 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
