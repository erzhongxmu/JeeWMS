<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="费用日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costData">
              <j-date placeholder="请选择费用日期" v-model="model.costData"  style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="货主" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cusCode">
              <a-input v-model="model.cusCode" placeholder="请输入货主"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="货主名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cusName">
              <a-input v-model="model.cusName" placeholder="请输入货主名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="费用" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costCode">
              <a-input v-model="model.costCode" placeholder="请输入费用"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="费用名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costName">
              <a-input v-model="model.costName" placeholder="请输入费用名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="是否已结算" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costJs">
              <a-input v-model="model.costJs" placeholder="请输入是否已结算"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="原价" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="yuanj">
              <a-input-number v-model="model.yuanj" placeholder="请输入原价" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="不含税" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="bhsj">
              <a-input-number v-model="model.bhsj" placeholder="请输入不含税" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="税" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shuie">
              <a-input-number v-model="model.shuie" placeholder="请输入税" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="是否结算" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costSl">
              <a-input-number v-model="model.costSl" placeholder="请输入是否结算" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="含税价" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="hansj">
              <a-input-number v-model="model.hansj" placeholder="请输入含税价" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="数量单位" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costUnit">
              <a-input v-model="model.costUnit" placeholder="请输入数量单位"  ></a-input>
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
    name: 'WvDayCostSumForm',
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
          add: "/jeewms/wvDayCostSum/add",
          edit: "/jeewms/wvDayCostSum/edit",
          queryById: "/jeewms/wvDayCostSum/queryById"
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