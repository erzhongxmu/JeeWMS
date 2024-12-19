<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="span">
            <a-form-model-item label="计费对象类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costObjType">
              <a-input v-model="model.costObjType" placeholder="请输入计费对象类型"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item label="计费对象编号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costObjNo">
              <a-input v-model="model.costObjNo" placeholder="请输入计费对象编号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item label="计费对象名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costObjName">
              <a-input v-model="model.costObjName" placeholder="请输入计费对象名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item label="开票编号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="invoiceNo">
              <a-input v-model="model.invoiceNo" placeholder="请输入开票编号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item label="开票类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="invoiceType">
              <a-input v-model="model.invoiceType" placeholder="请输入开票类型"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item label="发票号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="invoiceDocNo">
              <a-input v-model="model.invoiceDocNo" placeholder="请输入发票号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item label="开票金额" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="invoiceSum">
              <a-input-number v-model="model.invoiceSum" placeholder="请输入开票金额" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item label="税额" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="invoiceSe">
              <a-input-number v-model="model.invoiceSe" placeholder="请输入税额" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item label="税率" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costSl">
              <a-input-number v-model="model.costSl" placeholder="请输入税率" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item label="账单编号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="billNo">
              <j-popup
                v-model="model.billNo"
                field="billNo"
                org-fields="bill_no"
                dest-fields="billNo"
                code="bms_bill_h"
                :multi="true"
                @input="popupCallback"
                />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status">
              <!-- <a-input v-model="model.status" placeholder="请输入状态"  ></a-input> -->
              <j-dict-select-tag type="list" v-model="model.status" dictCode="invoice_status" :placeholder="$t('请选择状态')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remark">
              <a-textarea v-model="model.remark" rows="1" placeholder="请输入备注" />
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
    name: 'BmsInvoiceForm',
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
        },
        url: {
          add: "/bms/bmsInvoice/add",
          edit: "/bms/bmsInvoice/edit",
          queryById: "/bms/bmsInvoice/queryById"
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
      popupCallback(value,row){
         this.model = Object.assign(this.model, row);
      },
    }
  }
</script>