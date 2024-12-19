<template>
  <j-modal
    :title="title"
    :width="500"
    :visible="visible"
    :maskClosable="false"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class: { 'jee-hidden': disableSubmit } }"
    @cancel="close"
  >
    <a-spin :spinning="confirmLoading">
      <j-form-container>
        <!-- 主表单区域 -->
        <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
          <a-row>
            <a-col :span="24">
              <a-form-model-item label="完成原因" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query22">
                <a-textarea :rows="5" v-model="model.query22" placeholder="请输入完成原因" />
              </a-form-model-item>
            </a-col>
          </a-row>
        </a-form-model>
      </j-form-container>
    </a-spin>
  </j-modal>
</template>

<script>
import { deleteAction, getAction, downFile, getFileAccessHttpUrl } from '@/api/manage'
export default {
  name: 'BusiPrdOrdModal',
  components: {},
  data() {
    return {
      title: '完成',
      width: 900,
      visible: false,
      confirmLoading: false,
      disableSubmit: false,
      model: {},
      validatorRules: {
        query22: [{ required: true, message: ' ' }],
      },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
    }
  },
  methods: {
    open(e, type) {
      this.model = JSON.parse(JSON.stringify(e))
      this.model.query22 = ""
      this.visible = true
    },
    close() {
      this.visible = false
      this.model = {}
    },
    handleOk() {
      let data = {
        ordId: this.model.query04,
        query22: this.model.query22,
      }
      if (!data.query22){
        return this.$message.error('请输入完成原因')
      } 
      this.confirmLoading = true
      getAction('/jeeerp/busiPo/finishCause', data).then((res) => {
        this.confirmLoading = false
        if (res.success) {
          this.$message.success(res.message)
          this.$emit('ok')
          this.close()
        } else {
          this.$message.error(res.message)
        }
      })
    },
  },
}
</script>

<style scoped>
</style>