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
            <a-form-model-item label="上传发票" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr3">
                <j-upload v-model="mineObj.attr3" :trigger-change="true" :number='8'></j-upload>
              <!-- <a-input v-model="mineObj.attr1" :placeholder="$t('请输入')" ></a-input> -->
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="24">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text03">
              <a-input v-model="mineObj.text0" :placeholder="$t('请输入')" ></a-input>
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
import { getAction, httpAction, postAction } from '@/api/manage'

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
      title: '上传发票',
      width: 600,
      visible: false,
      disableSubmit: false,
      mineObj: {},
      validatorRules: {
           attr3:  [{required: true, message: ''}],
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
      this.data = {
        query04: e.query04,
        query01: e.query01
      }
      this.visible = true
    },
    close() {
      this.visible = false
      this.mineObj = {}
      this.data = {}
    },
    handleOk() {
      let formData = {
          ...this.mineObj,
          ...this.data
      }
      this.confirmLoading = true
      getAction('/jeeerp/busiPo/QcReport', formData).then(res => {
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