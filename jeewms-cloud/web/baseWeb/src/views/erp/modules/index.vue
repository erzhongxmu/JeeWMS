<template>
  <div ref="pdf">
    这是待转换的页面，点击 
    <button @click="handleExport">导出</button> 按钮，完成导出操作。
  </div>
</template>

<script>
// import {downloadPDF} from "@/util/pdf.js"  //工具方法，导出操作
export default {
  name: "pdf",
  data() {
    return {};
  },
  methods: {
    handleExport(){
    //   downloadPDF(this.$refs.pdf)

    }
  }
};
</script>

