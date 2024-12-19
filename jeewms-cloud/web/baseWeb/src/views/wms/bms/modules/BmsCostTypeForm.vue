<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="span">
            <a-form-model-item :label="$t('类型编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costTypeNo">
              <a-input v-model="model.costTypeNo" :placeholder="$t('请输入类型编号')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('类型名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costTypeName">
              <a-input v-model="model.costTypeName" :placeholder="$t('请输入类型名称')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('类型单位')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costTypeUnit">
              <a-input v-model="model.costTypeUnit" :placeholder="$t('请输入类型单位')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('备注')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remark">
              <a-input v-model="model.remark" :placeholder="$t('请输入备注')"  ></a-input>
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
    name: 'BmsCostTypeForm',
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
        span:12,
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
            costTypeNo: [
              { required: true, message: '请输入类型编号!'},
            ],
            costTypeName: [
              { required: true, message: '请输入类型名称!'},
            ],
        },
        url: {
          add: "/bms/bmsCostType/add",
          edit: "/bms/bmsCostType/edit",
          queryById: "/bms/bmsCostType/queryById"
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