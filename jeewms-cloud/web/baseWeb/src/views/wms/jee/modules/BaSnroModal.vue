<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item :label="$t('编号类型')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag :disabled="title==$t('查看')?true:false" type="list" v-decorator="['snroTypeCode']" :trigger-change="true" dictCode="ba_order_type,order_type_code,order_type_code" :placeholder="$t('请输入')"/>
        </a-form-item>
        <a-form-item  :label="$t('组织机构')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['snroOrg']" :placeholder="$t('请输入')"></a-input>
        </a-form-item>
        <a-form-item  :label="$t('前缀')"  :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['snroFindex']" :placeholder="$t('请输入')"></a-input>
        </a-form-item>
        <a-form-item  :label="$t('分隔符')"  :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['snroSplit']" :placeholder="$t('请输入')"></a-input>
        </a-form-item>
        <a-form-item  :label="$t('年位数')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['snroYear']" :placeholder="$t('请输入')"></a-input>
        </a-form-item>
        <a-form-item  :label="$t('月位数')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['snroMonth']" :placeholder="$t('请输入')"></a-input>
        </a-form-item>
        <a-form-item  :label="$t('日位数')"  :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['snroDay']" :placeholder="$t('请输入')"></a-input>
        </a-form-item>
        <a-form-item  :label="$t('序号位数')"  :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['snroSeri']" :placeholder="$t('请输入')"></a-input>
        </a-form-item>
        <a-form-item  :label="$t('示例号码')"  :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['snroExp']" :placeholder="$t('请输入')"></a-input>
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
    name: "BaSnroModal",
    components: {
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:1200,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 7 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/jeewms/baSnro/add",
          edit: "/jeewms/baSnro/edit",
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
          this.form.setFieldsValue(pick(this.model,'snroTypeCode','snroOrg','snroFindex','snroSplit','snroYear','snroMonth','snroDay','snroSeri','snroExp'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        if(that.title == that.$t('查看')) {
          that.close();
          return
        }
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
        this.form.setFieldsValue(pick(row,'snroTypeCode','snroOrg','snroFindex','snroSplit','snroYear','snroMonth','snroDay','snroSeri','snroExp'))
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