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

        <a-form-item label="港口代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['portCode']" placeholder="请输入港口代码"></a-input>
        </a-form-item>
        <a-form-item label="中文名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['portZhName']" placeholder="请输入中文名称"></a-input>
        </a-form-item>
        <a-form-item label="英文名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['portEnName']" placeholder="请输入英文名称"></a-input>
        </a-form-item>
        <a-form-item label="港口三字代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['portThCode']" placeholder="请输入港口三字代码"></a-input>
        </a-form-item>
        <a-form-item label="港口UNCODE" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['portUcode']" placeholder="请输入港口UNCODE"></a-input>
        </a-form-item>
        <a-form-item label="港口代码1" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['portCode1']" placeholder="请输入港口代码1"></a-input>
        </a-form-item>
        <a-form-item label="港口代码2" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['portCode2']" placeholder="请输入港口代码2"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['portText']" placeholder="请输入备注"></a-input>
        </a-form-item>
        <a-form-item label="停用" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['portDel']" :trigger-change="true" dictCode="is_del" placeholder="请选择停用"/>
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