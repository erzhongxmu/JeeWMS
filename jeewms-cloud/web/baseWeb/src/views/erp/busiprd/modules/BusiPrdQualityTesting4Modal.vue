<template>
  <j-modal :title="title" :width="width" :visible="visible" :confirmLoading="confirmLoading" switchFullscreen
    @ok="handleOk" @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <j-form-container :disabled="formDisabled">
        <a-form-model  ref="form" :model="model" :rules="validatorRules" slot="detail">
          <a-row>
            <a-col :span="span">
              <a-form-model-item :label="$t('子PO')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query01">
                <a-input v-model="model.query01" :placeholder="$t('请输入')" :disabled="title == '编辑'"  @keyup.enter.native="searchQuery"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('客户名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query02">
                <a-input v-model="model.query02" :placeholder="$t('请输入')" :disabled="title == '编辑'" ></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('企业属性')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
                <a-input v-model="model.query03" :placeholder="$t('请输入')"  disabled></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('供应商')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query04">
                <a-input v-model="model.query04" :placeholder="$t('请输入')"  disabled></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('采购员')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query05">
                <a-input v-model="model.query05" :placeholder="$t('请输入')"  disabled></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('SKU')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query06">
                <a-input v-model="model.query06" :placeholder="$t('请输入')"  disabled></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('产品名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
                <a-input v-model="model.query07" :placeholder="$t('请输入')"  disabled></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('订单数量')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
                <a-input v-model="model.query08" :placeholder="$t('请输入')"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('跟单员')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query09">
                <a-input v-model="model.query09" :placeholder="$t('请输入')"  disabled></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('验货类型')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query10">
                <j-dict-select-tag v-model="model.query10" :placeholder="$t('请选择验货类型')" dictCode="Test_type"  disabled/>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('验货工时')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query11">
                <a-input v-model="model.query11" :placeholder="$t('请输入')"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('验货数')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query12">
                <a-input v-model="model.query12" :placeholder="$t('请输入')" @input="queryInput(1)"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('良品数')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query13">
                <a-input v-model="model.query13" :placeholder="$t('请输入')" @input="queryInput(2)"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('不良品数')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query14">
                <a-input v-model="model.query14" :placeholder="$t('请输入')"  disabled></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('不良比例')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query15">
                <a-input v-model="model.query15" :placeholder="$t('请输入')"  disabled></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('不良描述')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query16">
                <a-input v-model="model.query16" :placeholder="$t('请输入')"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('最终判定')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query17">
                <j-dict-select-tag v-model="model.query17" :placeholder="$t('请选择最终判定')" dictCode="OQC_ZJ_XINXI" />
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('验货员')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query18">
                <a-input v-model="model.query18" :placeholder="$t('请输入')"  disabled></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('验货日期')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query19">
                <j-date v-model="model.query19" :disabled="title == '编辑'"></j-date>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('箱长宽高')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query20">
                <a-input v-model="model.query20" :placeholder="$t('请输入')"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('每箱重量')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query21">
                <a-input v-model="model.query21" :placeholder="$t('请输入')"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('尾箱备注')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query22">
                <a-textarea auto-size v-model="model.query22"></a-textarea>
              </a-form-model-item>
            </a-col>
            <a-col :span="span">
              <a-form-model-item :label="$t('附件')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr1">
                <j-upload v-model="model.attr1" :number="3"></j-upload>
              </a-form-model-item>
            </a-col>
          </a-row>
        </a-form-model>
      </j-form-container>
    </a-spin>
  </j-modal>
</template>

<script>
import { getAction, httpAction,postAction } from '@/api/manage'
import pick from 'lodash.pick'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import JMultiSelectTag from '@/components/dict/JMultiSelectTag'
import JEditableTable from '../../../../components/jeecg/JEditableTable.vue'
import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'
import { mapGetters } from 'vuex'
export default {
  name: 'MdGoodsModal',
  mixins: [JEditableTableModelMixin],
  components: {
    JDictSelectTag,
    JSearchSelectTag,
    JMultiSelectTag,
    JEditableTable
  },
  data() {
    return {
      span: 6,
      title: this.$t('操作'),
      width: 1200,
      visible: false,
      model: {},
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
        query11: [{ required: true, message:  this.$t('请输入')} ],
        query12: [{ required: true, message:  this.$t('请输入')} ],
        query13: [{ required: true, message:  this.$t('请输入')} ],
        query14: [{ required: true, message:  this.$t('请输入')} ],
        query15: [{ required: true, message:  this.$t('请输入')} ],
        query16: [{ required: true, message:  this.$t('请输入')} ],
        query17: [{ required: true, message:  this.$t('请输入')} ],
        query19: [{ required: true, message:  this.$t('请输入')} ],
        query20: [{ required: true, message:  this.$t('请输入')} ],
        query21: [{ required: true, message:  this.$t('请输入')} ],
        query22: [{ required: true, message:  this.$t('请输入')} ],
        query01: [{ required: true, message:  this.$t('请输入')} ],
      },
      url: {
        edit: '/jeeerp/busiPrdOrd/itemedit',
      },
    }
  },
  props: {
    //表单禁用
    disabled: {
      type: Boolean,
      default: false,
      required: false
    }
  },
  computed: {
    formDisabled() {
      return this.disabled
    },
  },
  mounted() {
    //this.getKwList()
  },
  created() { },
  methods: {
    ...mapGetters(['userInfo']),
    searchQuery(){
      if (!this.model.query01) return this.$message.warning(this.$t('请输入单号'));
      let username = this.userInfo().username
			let data = {
				username: username,
				listtype: 'QCJYJL',
				query01: this.model.query01
			}
			getAction('/jeewms/pdaGapi/getlist', data).then(res => {
				if (res.result.records.length > 0) {
					this.model = res.result.records[0]
				}
        console.log(username,'----');
				this.model.query18 = username
			})
    },
		queryInput(){
			if(this.model.query12 && this.model.query13){
					this.model.query14 = Number(this.model.query12) - Number(this.model.query13)
					this.model.query15 = Number(this.model.query14) / Number(this.model.query12).toFixed(2)
			}
		},




      add () {
        this.edit({});
      },
    edit(record) {
      this.model = Object.assign({}, record)
      this.visible = true
    },
    close() {
      this.$emit('close')
      this.visible = false
      this.model = {}
    },
    /** 获取所有的editableTable实例 */
    getAllTable() {
    },
    /** 整理成formData */
    classifyIntoFormData(allValues) {
      return {
        ...this.model,
      }
    },
    handleOk() {
      const that = this;
      // 触发表单验证
      this.$refs.form.validate(valid => {
        if (valid) {
          that.confirmLoading = true;
          let httpurl = '';
          let method = '';
          if(this.title == '编辑'){
            httpurl += this.url.edit;
            method = 'put';
            httpAction(httpurl, this.model, method).then((res) => {
              if (res.success) {
                that.$message.success(res.message);
                that.$emit('ok');
                that.handleCancel()
              } else {
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }else{
            let data = {
              ...this.model,
              username: this.userName,
              listtype: 'QCJYJL'
					  };
					postAction('/jeewms/pdaPapi/postdata', data).then(res => {
            that.confirmLoading = false;
            if (res.success) {
                that.$message.success(res.message);
                that.$emit('ok');
                that.handleCancel()
              } else {
                that.$message.warning(res.message);
              }
					})
          }
        }

      })
    },
    handleCancel() {
      this.close()
    },
  }
}
</script>
<style scoped>
/* >>> .ant-modal-content .ant-modal-body .ant-form-item {
  display: inline-block !important;
  width: 100%;
}
>>> .ant-collapse-content-active .ant-col-6{
  display: inline-block;
  float:none !important;
}
>>> .ant-modal-content .ant-modal-body {
  max-height: 700px !important;
  overflow-y: auto;
}
>>> .ant-collapse-header {
  color: #50bb79 !important;
  font-weight: 600;
}
>>> .ant-collapse-content {
  overflow: initial;
} */
</style>