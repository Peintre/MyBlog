<template>
  <div
    ref="editor"
    class="edit-container"
    v-html="innerText"
    :placeholder="placeholder"
    @input="onInput"
    @blur="onBlur"
  />
</template>
<script>
export default {
  props: {
    modelValue: {
      type: String,
      default: ""
    },
    placeholder: {
      type: String,
      default: ""
    }
  },
  data() {
    return {
      innerText: this.modelValue,
      range: null,
      isLocked: false,
    }
  },
  methods: {
    onInput() {
      this.$emit("update:modelValue", this.$el.innerHTML)
    },
    // 记录光标位置
    onBlur() {
      if (window.getSelection) {
        this.range = window.getSelection().getRangeAt(0)
      }
    },
    //在光标位置插入文本
    addText(text){
      // 还原光标
      if (window.getSelection) {
        var selection = window.getSelection()
        selection.removeAllRanges()
        // 为空初始化光标
        if (this.range == null) {
          this.$refs.editor.focus()
          this.range = selection.getRangeAt(0)
        }
        // 删除选中内容
        this.range.deleteContents()
        // 添加内容
        this.range.insertNode(this.range.createContextualFragment(text))
        this.range.collapse(false)
        selection.addRange(this.range)
        this.$emit("update:modelValue", this.$el.innerHTML)
      }
    },
    clear(){
      this.$el.innerHTML = ""
      this.$emit("update:modelValue", this.$el.innerHTML)
    }
  },
  watch: {
    modelValue(newData, oldData) {
      //当页面为修改说说时，监听modelValue的第一次改变
      //也就是父组件获取异步数据后同步数据，之后不再同步
      //当页面为发布说说时，监听改变后不同步数据
      if (!this.isLocked && this.$route.params.talkId) {
        this.$el.innerHTML = newData
        this.isLocked = true
      } 
    }
  },
  
}
</script>



<style scoped>
.edit-container {
  position: relative;
  width: 100%;
  height: 100%;
  border-radius: 8px;
  background: #f0f1f4;
  font-size: 14px;
  line-height: 1.5;
  padding: 6px 12px;
  box-sizing: border-box;
  overflow: auto;
  word-break: break-all;
  outline: none;
  user-select: text;
  white-space: pre-wrap;
  -webkit-user-modify: read-write-plaintext-only;
}
.edit-container:empty::before {
  cursor: text;
  content: attr(placeholder);
  color: #999;
}
</style>
