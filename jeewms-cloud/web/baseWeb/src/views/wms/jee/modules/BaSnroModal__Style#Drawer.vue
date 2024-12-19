<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="编号类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['snroTypeCode']" :trigger-change="true" dictCode="ba_snro_type,snro_type_code,snro_type_code" placeholder="请选择编号类型"/>
        </a-form-item>
        <a-form-item label="组织机构" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['snroOrg']" placeholder="请输入组织机构"></a-input>
        </a-form-item>
        <a-form-item label="前缀" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['snroFindex']" placeholder="请输入前缀"></a-input>
        </a-form-item>
        <a-form-item label="分隔符" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['snroSplit']" placeholder="请输入分隔符"></a-input>
        </a-form-item>
        <a-form-item label="年位数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['snroYear']" placeholder="请输入年位数"></a-input>
        </a-form-item>
        <a-form-item label="月位数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['snroMonth']" placeholder="请输入月位数"></a-input>
        </a-form-item>
        <a-form-item label="日位数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['snroDay']" placeholder="请输入日位数"></a-input>
        </a-form-item>
        <a-form-item label="序号位数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['snroSeri']" placeholder="请输入序号位数"></a-input>
        </a-form-item>
        <a-form-item label="示例号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['snroExp']" placeholder="请输入示例号码"></a-input>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
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
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>