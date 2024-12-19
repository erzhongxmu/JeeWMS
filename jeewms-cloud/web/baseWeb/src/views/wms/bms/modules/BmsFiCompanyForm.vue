<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="span">
            <a-form-model-item :label="$t('公司编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfiNo">
              <a-input v-model="model.comfiNo" :placeholder="$t('请输入公司编号')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('公司地址')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfiAddr">
              <a-input v-model="model.comfiAddr" :placeholder="$t('请输入公司地址')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('公司名字')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfiName">
              <a-input v-model="model.comfiName" :placeholder="$t('请输入公司名字')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('营业执照号码')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfiZhucehao">
              <a-input v-model="model.comfiZhucehao" :placeholder="$t('请输入营业执照号码')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('银行账号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfiBankid">
              <a-input v-model="model.comfiBankid" :placeholder="$t('请输入银行账号')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('电话')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfiTel">
              <a-input v-model="model.comfiTel" :placeholder="$t('请输入电话')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('开户行')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfiBankname">
              <a-input v-model="model.comfiBankname" :placeholder="$t('请输入开户行')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('logo')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="logoFile">
              <j-upload v-model="model.logoFile"   ></j-upload>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('运营章')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfiDzyz">
              <j-upload v-model="model.comfiDzyz"   ></j-upload>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('备注1')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfiBeizhu1">
              <a-input v-model="model.comfiBeizhu1" :placeholder="$t('请输入备注1')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('备注2')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfiBeizhu2">
              <a-input v-model="model.comfiBeizhu2" :placeholder="$t('请输入备注2')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('备注3')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfiBeizhu3">
              <a-input v-model="model.comfiBeizhu3" :placeholder="$t('请输入备注3')"  ></a-input>
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
    name: 'BmsFiCompanyForm',
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
          sm: { span: 8 },
        },
        span:'12',
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 15 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/bms/bmsFiCompany/add",
          edit: "/bms/bmsFiCompany/edit",
          queryById: "/bms/bmsFiCompany/queryById"
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