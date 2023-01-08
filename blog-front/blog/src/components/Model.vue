<template>
    <div v-if="modelFlag" class="my-model">
        <Login v-if="loginFlag" v-bind="$attrs" @clickEvent="btnClick"></Login>
        <Register v-if="registerFlag" @clickEvent="btnClick"></Register>
        <Forget v-if="forgetFlag" @clickEvent="btnClick"></Forget>
    </div>
</template>

<script>
import Login from '@/components/model/Login.vue'
import Register from '@/components/model/Register.vue'
import Forget from '@/components/model/Forget.vue'
export default{
    data:function(){
        return{
            blogInfo: {
                websiteConfig:{
                    socialLoginList:[]
                }
            },
            loginFlag: true,
            registerFlag: false,
            forgetFlag: false,
        }
    },
    components:{
        Login,
        Register,
        Forget
    },
    computed:{
        modelFlag: {
            get() {
                if(this.$store.state.modelFlag){
                    this.stopMove()
                }
                return this.$store.state.modelFlag
            }
        },
    },
    methods:{
        //关闭页面滚动
        stopMove(){
            let m = function(e){e.preventDefault()}
            document.body.style.overflow='hidden'
            document.addEventListener("touchmove",m,{ passive:false })//禁止页面滑动
        },
        //开启页面滚动
        Move(){
            let m =function(e){e.preventDefault()}
            document.body.style.overflow=''//出现滚动条
            document.removeEventListener("touchmove",m,{ passive:true })
        },
        //关闭页面
        closeBtn(){
            this.loginFlag = true
            this.registerFlag = false
            this.forgetFlag = false
            this.$store.commit('setModelFlag',false)
            this.Move() 
        },
        //子组件事件
        btnClick(str){
            if(str === 'close'){
                this.closeBtn()
            }else if(str === 'register'){
                this.loginFlag = false
                this.registerFlag = true
            }else if( str=== 'login'){
                this.registerFlag = false
                this.forgetFlag = false
                this.loginFlag = true     
            }else{
                this.loginFlag = false  
                this.forgetFlag = true  
            }
        }     
    }
}
</script>

<style scoped>
.my-model{
    position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
    z-index: 3000;
    background-color: rgba(18,18,18,.4);
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>