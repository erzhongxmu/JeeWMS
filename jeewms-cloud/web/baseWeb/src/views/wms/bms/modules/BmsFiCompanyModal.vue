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
    <bms-fi-company-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit"></bms-fi-company-form>
  </j-modal>
</template>

<script>
import BmsFiCompanyForm from './BmsFiCompanyForm'
export default {
  name: 'BmsFiCompanyModal',
  components: {
    BmsFiCompanyForm
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