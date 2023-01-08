<template>
  <div class="layout">
    <router-view />
  </div>
</template>
<script>
export default{
  created(){
    //设置默认主题
    this.setTheme()
  },
  methods: {
    setTheme(){
      let theme = this.$store.getters.getTheme
      document.documentElement.setAttribute('class', theme)
    }
  }
}
</script>



