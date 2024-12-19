<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="费用名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['costCode']" :trigger-change="true" dictCode="ba_cost,cost_name,cost_code" placeholder="请选择费用名称"/>
        </a-form-item>
        <a-form-item label="价格RMB" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['costJg']" placeholder="请输入价格RMB"></a-input>
        </a-form-item>
        <a-form-item label="税率" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['costSl']" placeholder="请输入税率"></a-input>
        </a-form-item>
        <a-form-item label="折扣" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['costZk']" placeholder="请输入折扣"></a-input>
        </a-form-item>
        <a-form-item label="不含税价RMB" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['costBhs']" placeholder="请输入不含税价RMB"></a-input>
        </a-form-item>
        <a-form-item label="含税价RMB" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['costHs']" placeholder="请输入含税价RMB"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"


  export default {
    name: "BaCostConfModal",
    components: { 
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
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
          add: "/jeewms/baCostConf/add",
          edit: "/jeewms/baCostConf/edit",
        }
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'costCode','costJg','costSl','costZk','costBhs','costHs'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
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
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(this.$t('操作成功'));
                that.$emit('ok');
              }else{
                that.$message.warning(this.$t('操作失败'));
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'costCode','costJg','costSl','costZk','costBhs','costHs'))
      },

      
    }
  }
</script>