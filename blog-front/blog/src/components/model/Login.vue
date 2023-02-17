<template>
    <div class="login-body animated zoomIn">
        <el-icon 
            class="float-right pointer" 
            @click="closeBtn"
        >
            <CloseBold />
        </el-icon>
        <div class="login-form">
            <input 
                ref="userNameInput"
                class="input-item" 
                type="text" 
                v-model="username" 
                placeholder="邮箱号" 
                autocomplete="off"
                @blur="nameBlur"
            />
            <input 
                class="input-item" 
                :type="pwdType" 
                v-model="userpassword" 
                placeholder="密码" 
                autocomplete="off"
            />
            <div class="loginBtn pointer" @click="login">登录</div>
            <div class="login-others">
                <span class="btn pointer" @click="register">立即注册</span>
                <span class="btn float-right pointer" @click="forget">忘记密码?</span>
            </div>
            <div v-if="blogInfo.websiteConfig.socialLoginList.length > 0">
                <div class="social-login-title">社交账号登录</div>
                <div class="social-login-wrapper">
                    <!-- 微博登录 -->
                    <a
                    v-if="blogInfo.websiteConfig.socialLoginList.indexOf('weibo')!=-1"
                    class="mr-3 iconfont iconweibo pointer my-mr-1"
                    style="color:#e05244"
                    @click="loginQuick('weibo')"
                    />
                    <!-- qq登录 -->
                    <a
                    v-if="blogInfo.websiteConfig.socialLoginList.indexOf('qq')!=-1"
                    class="iconfont iconqq pointer my-ml-1"
                    style="color:#00AAEE"
                    @click="loginQuick('qq')"
                    />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default{
    data:function(){
        return{
            username:'',
            userpassword:'',
            pwdType:'password'
        }
    },
    methods:{
        //点击登录
        login(){
            if(!this.username){
                this.$message.error('账号不能为空')
                return false
            }
            if(!this.userpassword){
                this.$message.error('密码不能为空')
                return false
            }
            let param = new FormData()
            param.append("username", this.username)
            param.append("password", this.userpassword)
            const this_ = this
            this.axios.post("/api/login", param).then(({ data }) => {
                if (data.flag) {
                    this_.$store.commit('setUserInfo',data.data)
                    this_.$message.success('登录成功') 
                    this_.closeBtn() 
                    this_.username = ''
                    this_.userpassword = ''                  
                } else {
                    this_.$message.error(data.message)
                }
            })
        },
        nameBlur(){
            let reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
            if(!reg.test(this.username) && this.username){
                this.$message.error('邮箱格式错误')
                this.$refs.userNameInput.focus()
            }      
        },
        //关闭页面
        closeBtn(){
            this.$emit('clickEvent','close')
        },
        //注册
        register(){
            this.$emit('clickEvent','register')
        },
        //忘记密码
        forget(){
            this.$emit('clickEvent','forget')
        },
        //快速登录
        loginQuick(type){
            if(type==='weibo'){
                this.$message.warning('暂未实现') 
            }
            if(type==='qq'){
                this.$message.warning('暂未实现') 
            }
        }
    },
    computed:{
        blogInfo(){
            return this.$store.state.blogInfo
        }
    }
}
</script>

<style scoped>
.float-right :hover{
    color: #49b1f5;
}
.loginBtn {
    text-align: center;
    padding: 10px;
    width: 100%;
    margin-top: 40px;
    margin-bottom: 40px;
    background: linear-gradient(to right, #a6c1ee, #fbc2eb);
    color: #ffffff;
    border-radius: 4px;
    user-select: none;
}
.loginBtn:active{
    box-shadow: 0 4px 4px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
.login-others .btn:hover{
    color: #49b1f5;
}
.social-login-title {
  margin-top: 1.5rem;
  color: #b5b5b5;
  font-size: 0.75rem;
  text-align: center;
}
.social-login-title::before {
  content: "";
  display: inline-block;
  background-color: #d8d8d8;
  width: 60px;
  height: 1px;
  margin: 0 12px;
  vertical-align: middle;
}
.social-login-title::after {
  content: "";
  display: inline-block;
  background-color: #d8d8d8;
  width: 60px;
  height: 1px;
  margin: 0 12px;
  vertical-align: middle;
}
.social-login-wrapper {
  margin-top: 1rem;
  font-size: 2rem;
  text-align: center;
}
.social-login-wrapper a {
  text-decoration: none;
}
/*适应PC端 宽度大于768px*/
@media screen and (min-width: 768px) {
    .login-body{
        width:460px;
        border-radius: 4px;
        background: var(--my-theme-bg-color);
        padding: 20px;
    }
    .login-form{
        padding: 3.5rem 1.5rem 0 1.5rem;
    }
}
/*适应移动端 宽度小于767px*/
@media screen and (max-width: 767px){
    .login-body{
        max-width: 460px;
        width:90%;
        border-radius: 4px;
        background: var(--my-theme-bg-color);
        padding: 20px;
    }
    .login-form{
        padding: 18vh 0.5em 0 0.5rem;
    }
}
</style>