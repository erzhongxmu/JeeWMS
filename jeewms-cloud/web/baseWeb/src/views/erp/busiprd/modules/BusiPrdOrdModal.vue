<template>
  <j-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="handleCancel">
    <busi-prd-ord-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit"/>
  </j-modal>
</template>

<script>

  import BusiPrdOrdForm from './BusiPrdOrdForm'

  export default {
    name: 'BusiPrdOrdModal',
    components: {
      BusiPrdOrdForm
    },
    data() {
      return {
        title:'',
        width:900,
        visible: false,
        disableSubmit: false
      }
    },
    methods:{
      copyAdd (orderId) {
        this.title = '新增'
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.getCopyData(orderId);
        })
      },
      add () {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.add();
        })
      },
      edit (record) {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.edit(record);
        })
      },
      close () {
        this.$refs.realForm.closedata();
        this.visible = false;
      },
      handleOk () {
        this.$refs.realForm.handleOk();
      },
      submitCallback(){
        this.$emit('ok');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      }
    }
  }
</script>

<style scoped>
</style>