<template>
    <el-dialog title="附件上传" v-model="visible" width="800px"  :close-on-click-modal="false" @close="cancel">
        <div class="upload-file" style="min-height:200px">
        <el-upload
            :action="action"
            :before-upload="handleBeforeUpload"
            :file-list="fileList"
            :limit="3"
            multiple
            :accept="accept"
            :on-exceed="handleExceed"
            :show-file-list="true"  
            :http-request="httpRequest"
            :before-remove="handleDelete" 
            class="upload-file-uploader"
            ref="uploadArticles"
        >
            <!-- 上传按钮 -->
            <template #trigger>
              <el-button size="small" type="primary">选取文件</el-button>
            </template>
            <el-button style="margin-left: 10px;" :disabled="fileList.length < 1" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
            <!-- 上传提示 -->
            <template #tip > 
              <div class="el-upload__tip" v-if="isShowTip">
                请上传
                <template v-if="fileSize">大小不超过<b style="color: #f56c6c">{{ fileSize }}MB</b> </template>
                <template v-if="fileType">格式为 <b style="color: #f56c6c">{{ fileType.join("/") }}</b> </template>
                的文件
              </div>
            </template>
        </el-upload>
        <!-- 文件列表 -->
        <transition-group class="upload-file-list el-upload-list el-upload-list--text" name="el-fade-in-linear" tag="ul">
            <li :key="file.uid"  class="el-upload-list__item ele-upload-list__item-content" v-for="file in fileList">
                <span class="el-icon-document"> {{ getFileName(file.name) }} </span>
            </li>
        </transition-group>
        </div>
    </el-dialog>
</template>

<script>
export default {
  props: {
    // 值
    value: [String, Object, Array],
    // 大小限制(MB)
    fileSize: {
      type: Number,
      default: 2,
    },
    // 文件类型, 例如['png', 'jpg', 'jpeg']
    fileType: {
      type: Array,
      default: () => [".jpg", ".jpeg", ".png", ".doc", ".xls", ".xlsx", ".ppt", ".txt", ".pdf"],
    },
    // 是否显示提示
    isShowTip: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      // 已选择文件列表
      fileList: [],
      // 是否显示文件上传弹窗
      visible: false,
      // 可上传的文件类型
      accept: '',
      action: 'action'
    };
  },
  created() {
    // 拼接
    this.fileType.forEach(el => {
      this.accept += el
      this.accept += ','
    })
  },
  methods:{
    // 外部调用这个方法，显示文件上传弹窗
    show() {
      this.visible = true
    },
    // 上传前校检格式和大小
    handleBeforeUpload(file) {
      // 校检文件类型
      if (this.fileType) {
        let fileExtension = "";
        if (file.name.lastIndexOf(".") > -1) {
          fileExtension = file.name.slice(file.name.lastIndexOf("."));
        }
        const isTypeOk = this.fileType.some((type) => {
          if (file.type.indexOf(type) > -1) return true;
          if (fileExtension && fileExtension.indexOf(type) > -1) return true;
          return false;
        });
        if (!isTypeOk) {
          this.$message.error(`您选取的文件中存在文件格式不正确, 请上传${this.fileType.join("/")}格式文件!`);
          return false;
        }
      }
      // 校检文件大小
      if (this.fileSize) {
        const isLt = file.size / 1024 / 1024 < this.fileSize;
        if (!isLt) {
          this.$message.error(`您选取的文件中存在文件大小超过 ${this.fileSize} MB!`);
          return false;
        }
      }
    },
    // 文件个数超出
    handleExceed() {
      this.$message.error(`只允许上传3个文件`);
    },
    // 删除文件
    handleDelete(file) { 
      let index =this.fileList.findIndex(item => item.uid==file.uid)
      if(index != -1){
        this.fileList.splice(index,1)
      }
    },
    // 获取文件名称
    getFileName(name) {
      if (name.lastIndexOf("/") > -1) {
        return name.slice(name.lastIndexOf("/") + 1).toLowerCase();
      } else {
        return "";
      }
    },
    /** 手动提交上传 */
    submitUpload() {
      if (this.fileList.length <= 0) {
        this.$message.error('请选择文件上传')
        return false
      }
      const formData = new FormData()
      // 把多个文件放到同一个请求里，这样只会请求一次接口。否则一个文件就会请求一次
      this.fileList.filter(item => {
        formData.append('files', item)
      })
      this.axios.post('/api/article/importArticles',formData).then(({ data }) => {
          if(data.flag){
            this.$refs.uploadArticles.clearFiles()
            this.fileList.splice(0,this.fileList.length)
            this.$message.success("上传成功")
            this.$emit("uploadSuccess")
          }else{
            this.$message.error(data.message)
          }
      })
    },
    /** 关闭上传弹框 */
    cancel() {
      this.fileList = []
      this.visible = false
    },
    httpRequest(param){
      this.fileList.push(param.file)
    }
  }
}
</script>
