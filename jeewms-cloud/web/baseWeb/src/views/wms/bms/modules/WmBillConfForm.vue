<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="货主" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cusCode">
              <j-dict-select-tag type="list" v-model="model.cusCode" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma" placeholder="请选择货主" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="logo文件" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="logoFile">
              <j-upload v-model="model.logoFile"   ></j-upload>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="盖章图片" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfidzyzFile">
              <j-upload v-model="model.comfidzyzFile"   ></j-upload>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfiname">
              <a-input v-model="model.comfiname" placeholder="请输入名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="地址" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfiaddr">
              <a-input v-model="model.comfiaddr" placeholder="请输入地址"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="电话" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfitel">
              <a-input v-model="model.comfitel" placeholder="请输入电话"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="银行卡号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfibankid">
              <a-input v-model="model.comfibankid" placeholder="请输入银行卡号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="开户行" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfibankname">
              <a-input v-model="model.comfibankname" placeholder="请输入开户行"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="营业执照" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfizhucehao">
              <a-input v-model="model.comfizhucehao" placeholder="请输入营业执照"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="备注1" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfibeizhu1">
              <a-textarea v-model="model.comfibeizhu1" rows="4" placeholder="请输入备注1" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="备注2" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfibeizhu2">
              <a-textarea v-model="model.comfibeizhu2" rows="4" placeholder="请输入备注2" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="备注3" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfibeizhu3">
              <a-textarea v-model="model.comfibeizhu3" rows="4" placeholder="请输入备注3" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="备注4" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfibeizhu4">
              <a-input v-model="model.comfibeizhu4" placeholder="请输入备注4"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="备注5" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfibeizhu5">
              <a-input v-model="model.comfibeizhu5" placeholder="请输入备注5"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="备注6" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfibeizhu6">
              <a-input v-model="model.comfibeizhu6" placeholder="请输入备注6"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="备注7" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfibeizhu7">
              <a-input v-model="model.comfibeizhu7" placeholder="请输入备注7"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="备注8" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="comfibeizhu8">
              <a-input v-model="model.comfibeizhu8" placeholder="请输入备注8"  ></a-input>
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
    name: 'WmBillConfForm',
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
          add: "/jeebms/wmBillConf/add",
          edit: "/jeebms/wmBillConf/edit",
          queryById: "/jeebms/wmBillConf/queryById"
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
                that.$message.success(this.$t('操作成功'));
                that.$emit('ok');
              }else{
                that.$message.warning(this.$t('操作失败'));
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