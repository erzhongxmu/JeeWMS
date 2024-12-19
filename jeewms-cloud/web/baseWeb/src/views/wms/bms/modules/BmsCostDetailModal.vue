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
    <bms-cost-detail-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit"></bms-cost-detail-form>
  </j-modal>
</template>

<script>
import BmsCostDetailForm from './BmsCostDetailForm'
export default {
  name: 'BmsCostDetailModal',
  components: {
    BmsCostDetailForm
  },
  data() {
    return {
      title: '',
      width: 980,
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