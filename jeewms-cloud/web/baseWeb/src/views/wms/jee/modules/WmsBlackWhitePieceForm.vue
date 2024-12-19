<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="黑件编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="blackNo">
              <a-input v-model="model.blackNo" placeholder="请输入黑件编码"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="黑件名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="blackName">
              <a-input v-model="model.blackName" placeholder="请输入黑件名称"  ></a-input>
            </a-form-model-item>
          </a-col>
<!--          <a-col :span="24">-->
<!--            <a-form-model-item label="黑件描述" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="blackDescribe">-->
<!--              <a-input v-model="model.blackDescribe" placeholder="请输入黑件描述"  ></a-input>-->
<!--            </a-form-model-item>-->
<!--          </a-col>-->
          <a-col :span="24">
            <a-form-model-item label="白件编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="whiteNo">
              <a-input v-model="model.whiteNo" placeholder="请输入白件编码"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="白件名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="whiteName">
              <a-input v-model="model.whiteName" placeholder="请输入白件名称"  ></a-input>
            </a-form-model-item>
          </a-col>
<!--          <a-col :span="24">-->
<!--            <a-form-model-item label="白件描述" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="whiteDescribe">-->
<!--              <a-input v-model="model.whiteDescribe" placeholder="请输入白件描述"  ></a-input>-->
<!--            </a-form-model-item>-->
<!--          </a-col>-->
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'WmsBlackWhitePieceForm',
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
          add: "/jeewms/wmsBlackWhitePiece/add",
          edit: "/jeewms/wmsBlackWhitePiece/edit",
          queryById: "/jeewms/wmsBlackWhitePiece/queryById"
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