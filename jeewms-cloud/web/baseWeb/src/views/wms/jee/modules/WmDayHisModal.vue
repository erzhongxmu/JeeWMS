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

        <a-form-item label="hisDate" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['hisDate']" placeholder="请输入hisDate"></a-input>
        </a-form-item>
        <a-form-item label="hisDayIn" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['hisDayIn']" placeholder="请输入hisDayIn"></a-input>
        </a-form-item>
        <a-form-item label="hisDayOut" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['hisDayOut']" placeholder="请输入hisDayOut"></a-input>
        </a-form-item>
        <a-form-item label="hisDayAmount" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['hisDayAmount']" placeholder="请输入hisDayAmount"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'


  export default {
    name: "WmDayHisModal",
    components: { 
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
          add: "/jeewms/wmDayHis/add",
          edit: "/jeewms/wmDayHis/edit",
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
          this.form.setFieldsValue(pick(this.model,'hisDate','hisDayIn','hisDayOut','hisDayAmount'))
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
        this.form.setFieldsValue(pick(row,'hisDate','hisDayIn','hisDayOut','hisDayAmount'))
      },

      
    }
  }
</script>