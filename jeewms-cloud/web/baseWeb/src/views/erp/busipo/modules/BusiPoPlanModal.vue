<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="handleCancel">
    <busi-po-plan-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit" class="unselectable"/>
  </j-modal>
</template>

<script>

  import BusiPoPlanForm from './BusiPoPlanForm'
  export default {
    name: 'BusiPoPlanModal',
    components: {
      BusiPoPlanForm
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
      add2(orderId){
        this.title = '新增'
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.getPaymentSchedule(orderId);
        })
      },
      add () {
        this.title = '新增'
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
        this.$nextTick(()=>{
          this.$refs.realForm.close();
        })
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