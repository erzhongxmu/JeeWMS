<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="close"
    
  >
    <a-spin :spinning="confirmLoading">
      <a-form ref="form" :model="model" :rules="validatorRules">
        <a-row>
          <a-col :span="12">
            <a-form-item label="字段Key" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query02">
              <a-input v-model="model.query02"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="字段文字" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <a-input v-model="model.query03" ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="字段类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query04">
              <j-search-select-tag type="list" v-model="model.query04" :dictOptions="Options1" />
            </a-form-item>
          </a-col>
          <a-col :span="12"  v-if="model.query04 == 'input'">
            <a-form-model-item label="input类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query2">
              <j-search-select-tag type="list" v-model="model.query09" :dictOptions="OptionsType" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="是否查询" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query05">
              <j-search-select-tag type="list" v-model="model.query05" :dictOptions="Options2" />
            </a-form-item>
          </a-col>
          <a-col :span="12" v-if="model.query05 == '是'">
            <a-form-item label="placeholder" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
              <a-input v-model="model.query08"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12" v-if="model.query05 == '是'">
            <a-form-item label="查询字段" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
              <a-input v-model="model.query10"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="排序" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <a-input v-model="model.query06"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="是否显示" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query06">
              <j-search-select-tag type="list" v-model="model.query07" :dictOptions="Options2" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>
import { validateDuplicateValue } from '@/utils/util'
import { httpAction, getAction } from '@/api/manage'
export default {
  name: 'WmPdaConfigModal',
  data() {
    return {
      title: '',
      width: 800,
      visible: false,
      disableSubmit: false,
      confirmLoading: false,
      model: {
        query04: 'text',
        query05: '否',
        query07: '是'
      },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      confirmLoading: false,
      OptionsType:[
        { text: 'text', value: 'text' },
        { text: 'number', value: 'number' },
      ],
      Options1: [
        { text: 'input', value: 'input' },
        { text: 'date', value: 'date' },
        { text: 'text', value: 'text' },
        { text: 'button', value: 'button' },
      ],
      Options2: [
        { text: '是', value: '是' },
        { text: '否', value: '否' }
      ],
      validatorRules: {
        query02: { rules: [{ required: true }] },
        query03: { rules: [{ required: true }] },
        query04: { rules: [{ required: true }] },
        query05: { rules: [{ required: true }] },
        query06: { rules: [{ required: true }] }
      },
      url: {
        add: '/pda/wmPdaConfig/add',
        edit: '/pda/wmPdaConfig/edit',
        queryById: '/pda/wmPdaConfig/queryById'
      }
    }
  },
  methods: {
    add(id) {
      this.title = '字段新增'
      this.model['query01'] = id
      this.visible = true
    },
    edit(record) {
      this.title = '字段编辑'
      this.visible = true
      this.model = Object.assign({}, record)
    },
    close() {
      this.model = {
        query04: 'text',
        query05: '否',
        query07: '是'
      }
      this.visible = false
    },
    handleOk() {
      const that = this
      // 触发表单验证
      if (!this.model.query02) return this.$message.error('数据不全')
      if (!this.model.query03) return this.$message.error('数据不全')
      that.confirmLoading = true
      let httpurl = ''
      let method = ''
      if (!this.model.id) {
        httpurl += this.url.add
        method = 'post'
      } else {
        httpurl += this.url.edit
        method = 'put'
      }
      httpAction(httpurl, this.model, method)
        .then(res => {
          if (res.success) {
            that.$message.success(this.$t('操作成功'))
            that.$emit('ok')
            that.close()
          } else {
            that.$message.warning(this.$t('操作失败'))
          }
        })
        .finally(() => {
          that.confirmLoading = false
        })
    }
  }
}
</script>