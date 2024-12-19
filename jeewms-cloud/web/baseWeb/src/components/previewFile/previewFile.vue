<template>
<a-spin :spinning="confirmLoading">
  <j-modal
      :title="$t('图片预览')"
      :width="500"
      :visible="visible"
      :maskClosable="false"
      switchFullscreen
      @ok="handleCancel"
      @cancel="handleCancel"
    >
    <div style="height:500px" v-if="ImgList.length < 0"></div>
      <a-carousel arrows v-else>
        <div slot="prevArrow" class="custom-slick-arrow" style="left: 10px; z-index: 1000">
          <a-icon type="left-circle" />
        </div>
        <div slot="nextArrow" class="custom-slick-arrow" style="right: 10px">
          <a-icon type="right-circle" />
        </div>
        <div v-for="(item, index) in ImgList" :key="index"><img :src="item" alt="" /></div>
      </a-carousel>
    </j-modal>
</a-spin>
</template>

<script>

export default {
  name: 'previewFile',
  components: {},
  data() {
    return {
      confirmLoading:false,
      visible: false,
      ImgList: []
    }
  },
  
  methods: {
    open(imgUrlStr) {
      if(imgUrlStr) {
        let arrImg = imgUrlStr.split(',http')
        let arr = []
        arrImg.map((item,index)=>{
            if(item.indexOf('http') == -1){
                item = 'http' + item 
            }
            arr.push(item)
        })
        console.log(arr);
        this.ImgList = arr
        this.visible = true
      } else {
        this.$message.warning('暂无预览图片')
      }
    },

    handleCancel() {
      this.visible = false
    },
  },
}
</script>

<style scoped lang="less">
.ant-carousel {
  .slick-slide {
    text-align: center;
    height: 300px;
    line-height: 300px;
    overflow: hidden;
    img {
      width: 100%;
      height: 100%;
    }
  }
  .anticon{
    color: #000;
  }
  .custom-slick-arrow:hover {
    opacity: 0.5;
  }
  .custom-slick-arrow {
    width: 25px;
    height: 25px;
    font-size: 25px;
    color: #fff;
    opacity: 0.3;
  }
  .custom-slick-arrow:before {
    display: none;
  }
}
</style>