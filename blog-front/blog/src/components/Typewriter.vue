<!-- 打字机组件实现 -->
<template>
    <span v-for="word in sentence" >{{ word }}</span>
</template>

<script>
export default{
    created(){
        this.fetchData()
    },
    data:function(){
        return{
            sentence:[],
            start: 300,
            back: 100,
            stop: 3000,
        }
    },
    methods:{
        fetchData(){
            let this_ = this
            this.axios.get('https://v1.hitokoto.cn/?c=i').then(({ data }) => {
                this_.startSentence(data.hitokoto)
            })
        },
        startSentence(newSentence){
            if(this.sentence.length > 0){
                this.removeSentence(newSentence)
            }else{
                this.addSentence(newSentence)
            }   
        },
        removeSentence(newSentence){
            const this_ = this
            let backTimer,i=0,sentenceLength=this.sentence.length
            setTimeout(function(){
                backTimer = setInterval(function(){
                    if(i < sentenceLength){
                        this_.sentence.pop()
                        i++
                    }else{
                        clearInterval(backTimer)
                        this_.addSentence(newSentence)
                    }
                },this_.back)
            },this.stop)
        },
        addSentence(newSentence){
            const this_ = this
            let startTimer,i = 0,sentenceArray
            sentenceArray = newSentence.split('')           
            startTimer = setInterval(function(){
                if(i < sentenceArray.length){
                    this_.sentence.push(sentenceArray[i])
                    i++
                }else{
                    clearInterval(startTimer)
                    this_.fetchData()
                }    
            },this.start)
        }
    }
}
</script>