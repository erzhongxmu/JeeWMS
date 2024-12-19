<template>
  <j-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    switchFullscreen
    :confirm-loading="confirmLoading"
    @ok="handleOk"
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="handleCancel">
    <SampleMakingOrderForm ref="realForm" @ok="submitCallback" :disabled="disableSubmit"  @setconfirmLoading="setconfirmLoading"/>
  </j-modal>
</template>

<script>

  import SampleMakingOrderForm from './SampleMakingOrderForm'

  export default {
    name: 'SampleMakingOrderModal',
    components: {
      SampleMakingOrderForm,
    },
    data() {
      return {
        isType:'',
        title:'',
        width:800,
        visible: false,
        disableSubmit: false,
        confirmLoading:false
      }
    },
    methods:{
      add () {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.add();
        })
      },
      setconfirmLoading(e){
        this.confirmLoading = e
      },
      edit (record) {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.edit(record);
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
        this.setconfirmLoading(false)
      },
      handleOk () {
        this.setconfirmLoading(true)
        this.$refs.realForm.handleOk();
      },
      submitCallback(){
        this.$emit('ok');
        this.visible = false;
        this.setconfirmLoading(false)
      },
      handleCancel () {
        this.close()
      }
    }
  }
</script>

<style scoped>
</style>