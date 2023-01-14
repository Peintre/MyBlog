<template>
    <div class="banner bannerIn" :style="{'background':'url('+coverInfo.cover+') center center / cover no-repeat'}">
        <h1 class="banner-title">{{coverInfo.pageTitle}}</h1>
    </div>
</template>

<script>
export default {
    props: ['imageUrl','pageTitle'],
    computed :{
        coverInfo(){
            let coverInfo = {
                cover: '',
                pageTitle: ''
            }
            if(this.imageUrl && this.pageTitle){
                coverInfo.cover = this.imageUrl
                coverInfo.pageTitle = this.pageTitle
            }else{
                let page = this.$route.path.split('/')[1]
                Array.from(this.$store.state.blogInfo.pageList).forEach(item => {
                    if (item.pageLabel == page) {
                        coverInfo.cover = item.pageCover
                        coverInfo.pageTitle = item.pageName
                    }
                })
            }
            return coverInfo
        }
    }
}
</script>

<style scoped>
/*适应PC端 宽度大于760px*/
@media screen and (min-width: 760px) {
    .banner{
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        height: 400px;
        background-attachment: fixed;
        text-align: center;
        color: #fff!important;
    }   
    .banner-title {
        position: absolute;
        top: 12.5rem;
        padding: 0 0.5rem;
        width: 100%;
        font-size: 2.5rem;
        text-align: center;
        color: #eee;
    }
}
/*适应移动端 宽度小于760px*/
@media screen and (max-width: 759px){
    .banner{
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        height: 280px;
        background-attachment: fixed;
        text-align: center;
        color: #fff!important;
    } 
    .banner-title {
        position: absolute;
        top: 8.5rem;
        width: 100%;
        font-size: 1.625rem;
        text-align: center;
        color: #eee;
    }
}
</style>