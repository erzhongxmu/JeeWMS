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
                <a-form-model-item label="期限" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query21">
                  <a-range-picker
                    @change="onDateChange"
                    v-model="query21"
                    dateFormat="YYYY/MM/DD'"
                    style="width: 100%"
                    :disabled="title == $t('查看') ? true : false"
                  />
                </a-form-model-item>
              </a-col>
            <a-col :span="24">
                <a-form-model-item label="质检人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query22">
                <j-search-select-tag
                    type="list"
                    v-model="model.query22"
                    dict="sys_user,realname,username,del_flag = '0'"
                    :placeholder="$t('请选择')"
                />
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
        title: '质检排班',
        width: 900,
        visible: false,
        confirmLoading: false,
        disableSubmit: false,
        model: {},
        query21: [],
        validatorRules: {
          query21: [{ required: true, message: ' ' }],
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
        type: 'timeLimit',
      }
    },
    methods: {
      onDateChange: function (value, dateString) {
        this.model.query21 = dateString[0] + '~' + dateString[1]
      },
      open(e, type) {
        if (type) {
          this.type = type
        }
        this.model = JSON.parse(JSON.stringify(e))
        this.model.query21 = ""
        this.model.query22 = ""
        this.visible = true
      },
      close() {
        this.visible = false
        this.model = {}
        this.query21 = []
      },
      handleOk() {
        let data = {
          ordId: this.model.query04,
          query21: this.model.query21,
          query22: this.model.query22,
          type: this.type,
        }
        if (!data.query21) return this.$message.error('期限不能为空')
        if (!data.query22) return this.$message.error('质检人不能为空')
        this.confirmLoading = true
        getAction('/jeeerp/busiPrdOrd/updateTimeLimit2', data).then((res) => {
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