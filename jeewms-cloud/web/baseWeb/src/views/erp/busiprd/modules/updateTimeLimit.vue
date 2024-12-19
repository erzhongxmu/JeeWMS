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
            <a-col :span="24" v-if="titles == 'bz'">
              <a-form-model-item :label="$t('加工费')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query19">
                <a-input v-model="query19" :placeholder="$t('请输入')"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="24" v-if="titles == 'zj'">
              <a-form-model-item :label="$t('加工费')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query19">
                <a-input v-model="query19" :placeholder="$t('请输入')"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="24" v-if="type == 'timeLimit'">
              <a-form-model-item :label="$t('期限')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query21">
                <a-range-picker
                  @change="onDateChange"
                  v-model="query21"
                  dateFormat="YYYY/MM/DD'"
                  style="width: 100%"
                  :disabled="title == $t('查看') ? true : false"
                />
              </a-form-model-item>
            </a-col>
            <a-col :span="24" v-if="type == 'finish'">
              <a-form-model-item :label="$t('完成时间')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query22">
                <j-date
                  :showTime="true"
                  dateFormat="YYYY-MM-DD HH:mm:ss"
                  :placeholder="$t('请选择')"
                  v-model="model.query22"
                  style="width: 100%"
                  :disabled="title == $t('查看') ? true : false"
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
  props: {
    titles: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      title:  this.$t('完成'),
      width: 900,
      visible: false,
      confirmLoading: false,
      disableSubmit: false,
      model: {},
      query19:'',
      num04:'',
      query21: [],
      validatorRules: {
        query19: [{ required: true, message: ' ' }],
        num04: [{ required: true, message: ' ' }],
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
      this.query21 = this.model.query21.split('~')
      this.query19 = this.model.query19
      this.visible = true
    },
    close() {
      this.visible = false
      this.model = {}
      this.query21 = []
      this.query19 = ''
      this.num04 = ''
    },
    handleOk() {
      let data = {
        ordId: this.model.query04,
        query21: this.model.query21,
        type: this.type,
      }
      if (this.titles == 'bz') {
        data.query19 = this.query19
      }
      if (this.titles == 'zj') {
        data.query19 = this.query19
      }
      if (this.type == 'finish') {
        data.query22 = this.model.query22
        if (!data.query22) return this.$message.error('完成时间不能为空')
      } else {
        if (!data.query21) return this.$message.error('期限不能为空')
      }
      this.confirmLoading = true
      getAction('/jeeerp/busiPrdOrd/updateTimeLimit', data).then((res) => {
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