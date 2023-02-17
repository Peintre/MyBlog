<template>
    <div class="login-container" :style="'background: url('+ cover +') center center / cover no-repeat;'">
      <div class="login-card">
        <div class="login-title">管理员登录</div>
        <!-- 登录表单 -->
        <el-form
          status-icon
          :model="loginForm"
          :rules="rules"
          ref="ruleForm"
          class="login-form"
        >
          <!-- 用户名输入框 -->
          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
              placeholder="用户名"
              @keyup.enter.native="login"
            >
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-input>
          </el-form-item>
          <!-- 密码输入框 -->
          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"            
              show-password
              placeholder="密码"
              @keyup.enter.native="login"
            >
            <template #prefix>
                  <i class="iconfont el-icon-mymima"></i>
            </template>
          </el-input>
          </el-form-item>
        </el-form>
        <!-- 登录按钮 -->
        <el-button type="primary" @click="login">登录</el-button>
      </div>
    </div>
</template>

<script>
import store from "@/store"
import { generaMenu } from "@/assets/js/menu.js"
import axios from "axios";
export default {
    data:function(){
        return{
          loginForm: {
            username:"",
            password:"",
          },
          rules: {
            username: [
                { required: true, message: "用户名不能为空", trigger: "blur" }
            ],
            password: [
                { required: true, message: "密码不能为空", trigger: "blur" }
            ]
          },
          cover: 'http://47.92.51.50:83/images/792a4b6934fc491b81342206e3192e59.png'
        }
    },
    methods:{
        login() {
        //发送登录请求
        let param = new URLSearchParams();
        param.append("username", this.loginForm.username);
        param.append("password", this.loginForm.password);
        axios.post("/api/login", param).then(({ data }) => {
                  if (data.flag) {
                    // 登录后保存用户信息
                    store.commit("login", data.data);
                    this.$message.success("登录成功");
                    //加载用户菜单并跳转页面
                    generaMenu('/home');
                  } else {
                    this.$message.error(data.message);
                  }
                }
                )
      }
    }
}


</script>

<style scoped>
.login-container {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  left: 0;
}
.login-card {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  background: #fff;
  padding: 170px 60px 180px;
  width: 350px;
}
.login-title {
  color: #303133;
  font-weight: bold;
  font-size: 1rem;
}
.login-form {
  margin-top: 1.2rem;
}
.login-card button {
  margin-top: 1rem;
  width: 100%;
}
</style>