<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="12">
            <a-form-model-item :label="$t('表名')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="tableName">
              <a-input v-model="model.tableName" :placeholder="$t('请输入表名')"  ></a-input>
              <!-- <j-dict-select-tag type="list" v-model="model.codeType" dictCode="code_type" :placeholder="$t('请选择编码类型')" /> -->
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item :label="$t('编码类型')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="codeType">
              <a-input v-model="model.codeType" :placeholder="$t('请输入编码类型')"  ></a-input>
              <!-- <j-dict-select-tag type="list" v-model="model.codeType" dictCode="code_type" :placeholder="$t('请选择编码类型')" /> -->
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item :label="$t('编码前缀')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="codePer">
              <a-input v-model="model.codePer" :placeholder="$t('请输入编码前缀')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item :label="$t('时间格式')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="timeForm">
              <a-input v-model="model.timeForm" :placeholder="$t('请输入时间格式')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item :label="$t('编码后缀')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="codeLast">
              <a-input v-model="model.codeLast" :placeholder="$t('请输入编码后缀')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item :label="$t('备注')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr1">
              <a-input v-model="model.attr1" :placeholder="$t('请输入备注')"  ></a-input>
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
    name: 'ConfCodeForm',
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
    data () {
      return {
        model:{
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/jeeerp/confCode/add",
          edit: "/jeeerp/confCode/edit",
          queryById: "/jeeerp/confCode/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
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