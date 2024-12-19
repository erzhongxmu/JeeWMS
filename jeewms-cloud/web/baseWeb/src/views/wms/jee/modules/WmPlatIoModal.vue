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

        <a-form-item label="车号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['carno']" placeholder="请输入车号"></a-input>
        </a-form-item>
        <a-form-item label="单据编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['docId']" placeholder="请输入单据编号"></a-input>
        </a-form-item>
        <a-form-item label="月台编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['platId']" placeholder="请输入月台编号"></a-input>
        </a-form-item>
        <a-form-item label="进入时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择进入时间" v-decorator="['inData']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="驶出时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择驶出时间" v-decorator="['outData']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="月台状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['platSta']" placeholder="请输入月台状态"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['platBeizhu']" placeholder="请输入备注"></a-input>
        </a-form-item>
        <a-form-item label="计划进入时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择计划进入时间" v-decorator="['planIndata']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="计划驶出时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择计划驶出时间" v-decorator="['planOutdata']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="月台操作" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['platOper']" placeholder="请输入月台操作"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'  


  export default {
    name: "WmPlatIoModal",
    components: { 
      JDate,
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
          add: "/jeewms/wmPlatIo/add",
          edit: "/jeewms/wmPlatIo/edit",
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
          this.form.setFieldsValue(pick(this.model,'carno','docId','platId','inData','outData','platSta','platBeizhu','planIndata','planOutdata','platOper'))
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
        this.form.setFieldsValue(pick(row,'carno','docId','platId','inData','outData','platSta','platBeizhu','planIndata','planOutdata','platOper'))
      },

      
    }
  }
</script>