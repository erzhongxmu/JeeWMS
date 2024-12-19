<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="span">
            <a-form-model-item :label="$t('计费对象类型')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costObjType">
              <a-input v-model="model.costObjType" placeholder="请输入计费对象类型"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('计费对象编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costObjNo">
              <a-input v-model="model.costObjNo" placeholder="请输入计费对象编号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('计费对象名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costObjName">
              <a-input v-model="model.costObjName" placeholder="请输入计费对象名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('付款编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="paymetNo">
              <a-input v-model="model.paymetNo" placeholder="请输入付款编号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('付款方式')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="paymetType">
              <!-- <a-input v-model="model.paymetType" placeholder="请输入付款方式"  ></a-input> -->
              <!-- <j-search-select-tag v-model="model.paymetType" dict="conf_mat_stor_type_marked,mat_stor_type_desc,mat_stor_type_no" placeholder="请选择付款方式"  /> -->
              <j-dict-select-tag type="list" v-model="model.paymetType" dictCode="payment_ways" placeholder="请选择付款方式" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('发票号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="invoiceDocNo">
              <a-input v-model="model.invoiceDocNo" placeholder="请输入发票号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('付款金额')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="paymetSum">
              <a-input-number v-model="model.paymetSum" placeholder="请输入付款金额" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('发票金额')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="invoiceSum">
              <a-input-number v-model="model.invoiceSum" placeholder="请输入发票金额" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('流水号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="bankId">
              <a-input v-model="model.bankId" placeholder="请输入流水号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('状态')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status">
              <a-input v-model="model.status" placeholder="请输入状态"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('备注')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remark">
              <a-input v-model="model.remark" placeholder="请输入备注"  ></a-input>
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
    name: 'BmsPaymentForm',
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
          sm: { span: 8 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 15 },
        },
        confirmLoading: false,
        validatorRules: {
          paymetNo: [
              { required: true, message: '请输入付款编号!'},
           ],
           paymetType: [
              { required: true, message: '请选择付款方式!'},
           ],
           invoiceDocNo: [
              { required: true, message: '请输入发票号!'},
           ],
           paymetSum: [
              { required: true, message: '请输入付款金额!'},
           ],
           invoiceSum: [
              { required: true, message: '请输入发票金额!'},
           ],
           bankId: [
              { required: true, message: '请输入流水号!'},
           ],
           status: [
              { required: true, message: '请选择状态!'},
           ],
        },
        url: {
          add: "/bms/bmsPayment/add",
          edit: "/bms/bmsPayment/edit",
          queryById: "/bms/bmsPayment/queryById"
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