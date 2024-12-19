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

        <a-form-item label="单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['wmNoticeId']" placeholder="请输入单号"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['beizhu']" placeholder="请输入备注"></a-input>
        </a-form-item>
        <a-form-item label="货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['cusCode']" placeholder="请输入货主"></a-input>
        </a-form-item>
        <a-form-item label="回单时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择回单时间" v-decorator="['hdData']" :trigger-change="true" style="width: 100%"/>
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
    name: "WmNoticeConfModal",
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
          add: "/jeewms/wmNoticeConf/add",
          edit: "/jeewms/wmNoticeConf/edit",
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
          this.form.setFieldsValue(pick(this.model,'wmNoticeId','beizhu','cusCode','hdData'))
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
        this.form.setFieldsValue(pick(row,'wmNoticeId','beizhu','cusCode','hdData'))
      },


    }
  }
</script>
<style scoped>
  >>>.ant-modal-content .ant-modal-body{
    max-height: 460px !important;
    overflow-y: auto;
  }
  >>>.ant-modal-content .ant-modal-body .ant-form-item{
    display: inline-block !important;
    width: 50%;
  }
</style>