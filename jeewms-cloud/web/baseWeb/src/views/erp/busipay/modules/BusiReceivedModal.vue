<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="handleCancel">
    <busi-received-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit"/>
  </j-modal>
</template>

<script>

  import BusiReceivedForm from './BusiReceivedForm'
  export default {
    name: 'BusiReceivedModal',
    components: {
      BusiReceivedForm
    },
    data () {
      return {
        title:'',
        width:1200,
        visible: false,
        disableSubmit: false
      }
    },
    methods: {
      chargeAgainst(e){
        this.title = '冲销'
        this.visible=true
        this.disableSubmit = false
        this.$nextTick(()=>{
          this.$refs.realForm.chargeAgainst(e);
        })
      },
      add2(orderId){
        this.title = '收款'
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.getReceived(orderId);
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
        this.$emit('close');
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