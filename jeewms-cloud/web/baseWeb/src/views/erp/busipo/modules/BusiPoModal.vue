<template>
  <j-modal
    :title="title"
    :width="1300"
    :visible="visible"
    :maskClosable="false"
    switchFullscreen
    @ok="handleOk"
    :confirm-loading="confirmLoading"
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="handleCancel">
    <!-- v-if="isType == 'add'" -->
    <busi-po-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit" @setconfirmLoading="setconfirmLoading"/>
    <!-- <busi-po-form-edit ref="realForm" @ok="submitCallback" :disabled="disableSubmit" v-else/> -->
  </j-modal>
</template>

<script>

  import BusiPoForm from './BusiPoForm'
  import BusiPoFormEdit from './BusiPoFormEdit'

  export default {
    name: 'BusiPoModal',
    components: {
      BusiPoForm,
      BusiPoFormEdit
    },
    data() {
      return {
        isType:'',
        title:'',
        visible: false,
        disableSubmit: false,
        confirmLoading:false
      }
    },
    methods:{
      copyAdd (orderId,refund) {
        this.title = '新增'
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.getCopyData(orderId,refund);
        })
      },
      setconfirmLoading(e){
        this.confirmLoading = e
      },
      add () {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.add();
        })
      },
      edit (record) {
        this.visible=true
        this.isType = 'edit'
        this.$nextTick(()=>{
          this.$refs.realForm.edit(record);
        })
      },
      close () {
        this.$emit('close');
        this.setconfirmLoading(false)
        this.visible = false;
      },
      handleOk () {
        this.setconfirmLoading(true)
        this.$refs.realForm.handleOk();
      },
      submitCallback(){
        this.setconfirmLoading(false)
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