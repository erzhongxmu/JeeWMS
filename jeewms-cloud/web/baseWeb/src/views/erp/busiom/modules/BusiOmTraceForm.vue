<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="8">
            <a-form-model-item :label="$t('装箱单号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query01">
              <a-input v-model="model.query01" :placeholder="$t('请输入')" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('出库日期')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query02">
              <j-date v-model="model.query02" :placeholder="$t('请输入')" disabled/>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('公司属性')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <a-input v-model="model.query03" :placeholder="$t('请输入')" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('客户名')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query04">
              <a-input v-model="model.query04" :placeholder="$t('请输入')" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('目的地')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query05">
              <a-input v-model="model.query05" :placeholder="$t('请输入')" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('类型')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query06">
              <j-dict-select-tag type="list" v-model="model.query06" dictCode="busi_om_trace_query06"
                :placeholder="$t('请输入')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('产品清单')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
              <a-textarea v-model="model.query07" rows="1" :placeholder="$t('请输入')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('出货人')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
              <j-dict-select-tag type="list" v-model="model.query08" dictCode="busi_om_trace_query08"
                :placeholder="$t('请输入')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('出货方式')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query09">
              <j-dict-select-tag type="list" v-model="model.query09" dictCode="busi_om_trace_query09"
                :placeholder="$t('请输入')" disabled/>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('贸易条款')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query10">
              <j-dict-select-tag type="list" v-model="model.query10" dictCode="busi_om_trace_query10"
                :placeholder="$t('请输入')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('状态')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query11">
              <j-dict-select-tag type="list" v-model="model.query11" dictCode="busi_om_trace_query11"
                :placeholder="$t('请输入')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('离开日期')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query12">
              <j-date v-model="model.query12" :placeholder="$t('请输入')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('到达日期')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query13">
              <j-date v-model="model.query13" :placeholder="$t('请输入')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('货代')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query14">
              <j-dict-select-tag type="list" v-model="model.query14" dictCode="busi_om_trace_query14"
                :placeholder="$t('请输入')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('追踪单号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query15">
              <a-textarea v-model="model.query15" rows="1" :placeholder="$t('请输入')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('追踪链接')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query16">
              <a-textarea v-model="model.query16" rows="1" :placeholder="$t('请输入')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('目的地地址')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query17">
              <a-textarea v-model="model.query17" rows="1" :placeholder="$t('请输入')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('总体积')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query18">
              <a-input v-model="model.query18" :placeholder="$t('请输入')" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('总重量')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query19">
              <a-input v-model="model.query19" :placeholder="$t('请输入')" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('总箱数')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query20">
              <a-input v-model="model.query20" :placeholder="$t('请输入')" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('总费用')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query21">
              <a-input v-model="model.query21" :placeholder="$t('请输入')"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('Google Folder') " :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query22">
              <a-textarea v-model="model.query22" rows="1" :placeholder="$t('请输入')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('是否含税')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query23">
              <j-dict-select-tag type="list" v-model="model.query23" dictCode="busi_om_trace_query23"
                :placeholder="$t('请输入')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('报价金额')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query24">
              <a-input v-model="model.query24" :placeholder="$t('请输入')"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('账单金额')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query25">
              <a-input v-model="model.query25" :placeholder="$t('请输入')"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('备注')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query26">
              <a-input v-model="model.query26" :placeholder="$t('请输入')"></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

import { httpAction, getAction } from '@/api/manage'
import { validateDuplicateValue } from '@/utils/util'

export default {
  name: 'BusiOmTraceForm',
  components: {
  },
  props: {
    //表单禁用
    disabled: {
      type: Boolean,
      default: false,
      required: false
    }
  },
  data() {
    return {
      model: {
      },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      confirmLoading: false,
      validatorRules: {
        query01: [
          { required: true, message: ' ' },
        ],
        query02: [
          { required: true, message: ' ' },
        ],
        query03: [
          { required: true, message: ' ' },
        ],
        query04: [
          { required: true, message: ' ' },
        ],
        query05: [
          { required: true, message: ' ' },
        ],
        query06: [
          { required: true, message: ' ' },
        ],
        query07: [
          { required: true, message: ' ' },
        ],
        query08: [
          { required: true, message: ' ' },
        ],
        query09: [
          { required: true, message: ' ' },
        ],
        query10: [
          { required: true, message: ' ' },
        ],
        query11: [
          { required: true, message: ' ' },
        ],
        query12: [
          { required: true, message: ' ' },
        ],
        query13: [
          { required: true, message: ' ' },
        ],
        query14: [
          { required: true, message: ' ' },
        ],
        query15: [
          { required: true, message: ' ' },
        ],
        query16: [
          { required: true, message: ' ' },
        ],
        query17: [
          { required: true, message: ' ' },
        ],
        query18: [
          { required: true, message: ' ' },
        ],
        query19: [
          { required: true, message: ' ' },
        ],
        query20: [
          { required: true, message: ' ' },
        ],
        query21: [
          { required: true, message: ' ' },
        ],
        query22: [
          { required: true, message: ' ' },
        ],
        query23: [
          { required: true, message: ' ' },
        ],
        query24: [
          { required: true, message: ' ' },
        ],
        query25: [ { required: true, message: ' ' }, ],
      },
      url: {
        add: "/jeeerp/busiOmTrace/add",
        edit: "/jeeerp/busiOmTrace/edit",
        queryById: "/jeeerp/busiOmTrace/queryById"
      }
    }
  },
  computed: {
    formDisabled() {
      return this.disabled
    },
  },
  created() {
    //备份model原始值
    this.modelDefault = JSON.parse(JSON.stringify(this.model));
  },
  methods: {
    add() {
      this.edit(this.modelDefault);
    },
    edit(record) {
      this.model = Object.assign({}, record);
      this.visible = true;
    },
    submitForm() {
      const that = this;
      // 触发表单验证
      this.$refs.form.validate(valid => {
        if (valid) {
          that.confirmLoading = true;
          let httpurl = '';
          let method = '';
          if (!this.model.id) {
            httpurl += this.url.add;
            method = 'post';
          } else {
            httpurl += this.url.edit;
            method = 'put';
          }
          httpAction(httpurl, this.model, method).then((res) => {
            if (res.success) {
              that.$message.success(res.message);
              that.$emit('ok');
            } else {
              that.$message.warning(res.message);
            }
          }).finally(() => {
            that.confirmLoading = false;
          })
        }

      })
    },
  }
}
</script>