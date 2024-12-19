<template>
<a-spin :spinning="confirmLoading">
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :maskClosable="false"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class: { 'jee-hidden': disableSubmit } }"
    @cancel="close"
  > 
  
    <j-form-container>
      <!-- 主表单区域 -->
      <a-form-model ref="form" :model="mineObj" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item :label="$t('上传凭证')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr4">
                <j-upload v-model="mineObj.attr4" :trigger-change="true" fileType='image' :number='8'></j-upload>
              <!-- <a-input v-model="mineObj.attr1" :placeholder="$t('请输入')" ></a-input> -->
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="24">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text03">
              <a-input v-model="mineObj.text03" :placeholder="$t('请输入')" ></a-input>
            </a-form-model-item>
          </a-col> -->
        </a-row>
      </a-form-model>
    </j-form-container>
  </j-modal>
</a-spin>
</template>

<script>
import { FormTypes, getRefPromise, VALIDATE_NO_PASSED } from '@/utils/JEditableTableUtil'
import { putAction, httpAction, postAction } from '@/api/manage'

export default {
  name: 'BusiPoItemModal',
  components: {},
  data() {
    return {
      confirmLoading:false,
      id: null,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      labelCol2: {
        xs: { span: 24 },
        sm: { span: 3 },
      },
      wrapperCol2: {
        xs: { span: 24 },
        sm: { span: 20 },
      },
      title: this.$t('上传凭证'),
      width: 600,
      visible: false,
      disableSubmit: false,
      mineObj: {},
      validatorRules: {
        attr4:  [{required: true, message: ''}],
        //    text03:  [{required: true, message: ''}]
      },
      data: {}
    }
  },
  methods: {
    getAllTable() {
      let values = this.tableKeys.map((key) => getRefPromise(this, key))
      return Promise.all(values)
    },
    open(e) {
      this.mineObj = e
      this.visible = true
    },
    close() {
      this.visible = false
      this.mineObj = {}
    },
    handleOk() {
      let formData = {
          ...this.mineObj,
      }
      this.confirmLoading = true
      putAction('/jeeerp/busiPaymentReceived/uploadingBankReceipt', formData).then(res => {
          if(res.success) {
              this.$message.success(res.message)
              this.$emit("ok")
              this.close()
          } else {
              this.$message.warning(res.message)
          }
        this.confirmLoading = false
      })
      this.$emit('ok')
    },
  },
}
</script>

<style scoped>
</style>