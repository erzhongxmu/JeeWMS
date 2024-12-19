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

        <a-form-item :label="$t('库区编码')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['areaCode', validatorRules.areaCode]" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item :label="$t('库区名称')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['areaName', validatorRules.areaName]" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item :label="$t('库区名称英文')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['areaCodeEn', validatorRules.areaCodeEn]" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item :label="$t('仓库')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['wareCode', validatorRules.wareCode]" :trigger-change="true" dictCode="ba_store,store_name,store_code" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item :label="$t('备注')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['remark']" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <!-- <a-form-item :label="$t('良次品')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['areaStatus']" :trigger-change="true"  dictCode="ba_qm_qa,qm_qa_code,qm_qa_name" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"/>
        </a-form-item> -->
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
    name: "BaStoreAreaModal",
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
          sm: { span: 8 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 13 },
        },
        confirmLoading: false,
        validatorRules: {
          areaCode: {
            rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
          areaName: {
            rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
          wareCode: {
            rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
          areaCodeEn: {
            rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
        },
        url: {
          add: "/jeewms/baStoreArea/add",
          edit: "/jeewms/baStoreArea/edit",
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
          this.form.setFieldsValue(pick(this.model,'areaCode','areaName','wareCode','remark', 'areaStatus',"areaCodeEn"))
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
                that.close();
                that.$emit('ok');
              }else{
                that.$message.warning(this.$t(res.message));
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }

        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'areaCode','areaName','wareCode','remark'))
      },


    }
  }
</script>
<style scoped>
  >>>.ant-modal-content .ant-modal-body .ant-form-item{
    display: inline-block !important;
    width: 50%;
  }
  >>>.ant-modal-content .ant-modal-body{
    max-height: 460px !important;
    overflow-y: auto;
  }
</style>