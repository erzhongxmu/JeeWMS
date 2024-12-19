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

        <a-form-item label="港口代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['portCode']" :placeholder="title==$t('查看')? '':'请输入港口代码'"></a-input>
        </a-form-item>
        <a-form-item label="中文名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['portZhName']" :placeholder="title==$t('查看')? '':'请输入中文名称'"></a-input>
        </a-form-item>
        <a-form-item label="英文名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['portEnName']" :placeholder="title==$t('查看')? '':'请输入英文名称'"></a-input>
        </a-form-item>
        <a-form-item label="港口三字代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['portThCode']" :placeholder="title==$t('查看')? '':'请输入港口三字代码'"></a-input>
        </a-form-item>
        <a-form-item label="港口UNCODE" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['portUcode']" :placeholder="title==$t('查看')? '':'请输入港口UNCODE'"></a-input>
        </a-form-item>
        <a-form-item label="港口代码1" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['portCode1']" :placeholder="title==$t('查看')? '':'请输入港口代码1'"></a-input>
        </a-form-item>
        <a-form-item label="港口代码2" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['portCode2']" :placeholder="title==$t('查看')? '':'请输入港口代码2'"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :disabled="title==$t('查看')?true:false" v-decorator="['portText']" :placeholder="title==$t('查看')? '':'请输入备注'"></a-input>
        </a-form-item>
        <a-form-item label="停用" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag :disabled="title==$t('查看')?true:false" type="list" v-decorator="['portDel']" :trigger-change="true" dictCode="is_del" placeholder="请选择停用"/>
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
    name: "BaPortModal",
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
          add: "/jeewms/baPort/add",
          edit: "/jeewms/baPort/edit",
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
          this.form.setFieldsValue(pick(this.model,'portCode','portZhName','portEnName','portThCode','portUcode','portCode1','portCode2','portText','portDel'))
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
        this.form.setFieldsValue(pick(row,'portCode','portZhName','portEnName','portThCode','portUcode','portCode1','portCode2','portText','portDel'))
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