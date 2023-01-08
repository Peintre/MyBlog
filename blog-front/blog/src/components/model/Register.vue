<template>
    <div class="register-body animated zoomIn">
        <el-icon 
            class="float-right pointer" 
            @click="closeBtn"
        >
            <CloseBold />
        </el-icon>
        <div class="register-form">
            <input 
                ref="userEmailInput"
                class="input-item" 
                type="text" 
                v-model="username" 
                placeholder="邮箱号" 
                autocomplete="off"
                @blur="nameBlur"
                @input="nameInput"
            />
            <div class="flex">
                <input 
                    class="input-item flex code-input" 
                    type="text" 
                    v-model="code" 
                    placeholder="验证码" 
                    autocomplete="off"
                    @input="validate('code')"
                    maxlength="6"
                />
                <el-button 
                    :disabled="disabled"
                    @click="sendCode"
                    size="default"
                >
                    {{codeMsg}}
                </el-button>
            </div>
            <input 
                ref="userPwdInput"
                class="input-item" 
                :type="pwdType" 
                v-model="userpassword" 
                placeholder="密码" 
                autocomplete="off"
                @blur="pwdBlur"
            />
            <input 
                class="input-item" 
                type="password"
                v-model="userpsd" 
                placeholder="确认密码" 
                autocomplete="off"
            />
            <div class="registerBtn pointer" @click="register">注册</div>
            <span class="toLogin pointer" @click="login">已有账号?登录</span>
        </div>
    </div>
</template>

<script>
export default{
    data:function(){
        return{
            username:'',
            userpassword:'',
            userpsd:'',
            pwdType:'password',
            code:'',
            disabled:true,
            codeMsg: '发送',
            time: 60,
            timer: null
        }
    },
    methods:{
        //关闭页面
        closeBtn(){
            this.$emit('clickEvent','close')
        },
        nameBlur(){
            let reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
            if(!reg.test(this.username) && this.username){
                this.$message.error('邮箱格式错误')
                this.$refs.userEmailInput.focus()
            }
        },
        nameInput(){
            let reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
            if(reg.test(this.username)){
                this.disabled = false
            }else{
                this.disabled = true
            }
        },
        pwdBlur(){
            if(this.userpassword.length<6){
                this.$message.warning('密码不能少于6位')
                this.$refs.userPwdInput.focus()
            }
        },
        validate(value){
            if(value === 'code'){
                this.code = this.code.replace(/[^\w\.\/]/ig,'')
            }
        },
        login(){
            this.$emit('clickEvent','login')
        },
        register(){
            if(!this.userpassword || !this.username){
                this.$message.warning('账号或密码不能为空')
                return false
            }
            if(!this.code){
                this.$message.warning('验证码不能为空')
                return false
            }
            if(this.userpassword.length<6){
                this.$message.warning('密码不能少于6位')
                return false
            }
            if(this.userpassword !== this.userpsd){
                this.$message.warning('密码不一致，请重新输入')
                return false
            }
            const this_ = this
            let user = {
                username: this.username,
                password: this.userpassword,
                code: this.code
            }
            this.axios.post('/api/userAuth/register',user).then(({ data }) => {
                if(data.flag){
                    this_.$message.success('注册成功')
                    this_.login()
                }else{
                    this_.$message.errot(data.message)
                }
            })
        },
        sendCode(){
            this.countDown()
            const this_ = this
            this.axios.get('/api/userAuth/sendCode',{
                params:{
                    username:this.username,
                    state:0
                }
            }).then(({ data }) => {
                if (data.flag) {
                    this_.$message.success("验证码发送成功")
              } else {
                    this_.$message.error(data.message)
              }
            })
        },
        //验证码计时器
        countDown() {
            this.disabled = true
            const this_ = this
            this.timer = setInterval(() => {
                this_.time--
                this_.codeMsg = this.time + "s"
                if (this_.time <= 0) {
                clearInterval(this_.timer)
                    this_.codeMsg = "发送"
                    this_.time = 60
                    this_.disabled = false
                }
            }, 1000)
        }
    }
}
</script>

<style scoped>
.float-right:hover{
    color: #49b1f5;
}
.toLogin:hover{
    color: #49b1f5;
}
.code-input{
    width: 100px !important;
}
.registerBtn {
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
.registerBtn:active{
    box-shadow: 0 4px 4px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
/*适应PC端 宽度大于768px*/
@media screen and (min-width: 768px) {
    .register-body{
        width:460px;
        border-radius: 4px;
        background: var(--my-theme-bg-color);
        padding: 20px;
    }
    .register-form{
        padding: 3.5rem 1.5rem 0 1.5rem;
    }
}
/*适应移动端 宽度小于767px*/
@media screen and (max-width: 767px){
    .register-body{
        max-width: 460px;
        width:90%;
        border-radius: 4px;
        background: var(--my-theme-bg-color);
        padding: 20px;
    }
    .register-form{
        padding: 18vh 0.5em 0 0.5rem;
    }
}
</style>