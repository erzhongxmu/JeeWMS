<template>
  <j-modal
    :maskClosable="false"
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="handleCancel"
    :cancelText="$t('关闭')"
  >
    <di-it-rule-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit"></di-it-rule-form>
  </j-modal>
</template>

<script>
import DiItRuleForm from './DiItRuleForm'
export default {
  name: 'DiItRuleModal',
  components: {
    DiItRuleForm
  },
  data() {
    return {
      title: '',
      width: 780,
      visible: false,
      disableSubmit: false
    }
  },
  methods: {
    add() {
      this.visible = true
      this.$nextTick(() => {
        this.$refs.realForm.add()
      })
    },
    edit(record) {
      this.visible = true
      this.$nextTick(() => {
        this.$refs.realForm.edit(record)
      })
    },
    close() {
      this.$emit('close')
      this.visible = false
    },
    handleOk() {
      this.$refs.realForm.submitForm()
    },
    submitCallback() {
      this.$emit('ok')
      this.visible = false
    },
    handleCancel() {
      this.close()
    }
  }
}
</script>