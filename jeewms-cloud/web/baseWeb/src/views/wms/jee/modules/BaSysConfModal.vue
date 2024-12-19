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

        <a-form-item label="系统参数类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag :disabled="title==$t('查看')?true:false" type="list" v-decorator="['sysConfType']" :trigger-change="true" dictCode="ba_sysp_type,sysp_type_code,sysp_type_code" :placeholder="title==$t('查看')? '':'请选择系统参数类型'" />
        </a-form-item>
        <a-form-item label="业务操作环节" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag :disabled="title==$t('查看')?true:false" type="list" v-decorator="['sysConfStep']" :trigger-change="true" dictCode="ba_oper_step,oper_step_code,oper_step_code" :placeholder="title==$t('查看')? '':'请选择业务操作环节'" />
        </a-form-item>
        <a-form-item label="组织" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['sysConfOrg']" :placeholder="title==$t('查看')? '':'请输入组织'"></a-input>
        </a-form-item>
        <a-form-item label="合作伙伴" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['sysConfPartner']" :placeholder="title==$t('查看')? '':'请输入合作伙伴'"></a-input>
        </a-form-item>
        <a-form-item label="参数1" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['sysPara1']" :placeholder="title==$t('查看')? '':'请输入参数1'"></a-input>
        </a-form-item>
        <a-form-item label="参数2" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['sysPara2']" :placeholder="title==$t('查看')? '':'请输入参数2'"></a-input>
        </a-form-item>
        <a-form-item label="参数3" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['sysPara3']" :placeholder="title==$t('查看')? '':'请输入参数3'"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['sysConfText']" :placeholder="title==$t('查看')? '':'请输入备注'"></a-input>
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
    name: "BaSysConfModal",
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
          sm: { span: 7 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 14 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/jeewms/baSysConf/add",
          edit: "/jeewms/baSysConf/edit",
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
          this.form.setFieldsValue(pick(this.model,'sysConfType','sysConfStep','sysConfOrg','sysConfPartner','sysPara1','sysPara2','sysPara3','sysConfText'))
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
        this.form.setFieldsValue(pick(row,'sysConfType','sysConfStep','sysConfOrg','sysConfPartner','sysPara1','sysPara2','sysPara3','sysConfText'))
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