<template>
  <j-modal
    :maskClosable="false"
    :title="title"
    :width="880"
    :visible="visible"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="handleCancel">
    <BmsCostRuleHListBatchForm ref="realForm" :type="type" @ok="submitCallback" :disabled="disableSubmit"/>
  </j-modal>
</template> 

<script>
  import BmsCostRuleHListBatchForm from './BmsCostRuleHListBatchForm'
  export default {
    name: 'BmsCostRuleHModal',
    components: {
      BmsCostRuleHListBatchForm
    },
    data() {
      return {
        title:'',
        visible: false,
        disableSubmit: false,
        type:'',
      }
    },
    methods:{
      add (record,e) {
        console.log(e);
        this.visible=true
        this.$nextTick(()=>{
          this.type = e
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

<style scoped>
</style>