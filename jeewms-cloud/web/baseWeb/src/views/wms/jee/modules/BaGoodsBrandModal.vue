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

        <a-form-item label="品牌代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsBrandCode']" placeholder="请输入品牌代码"></a-input>
        </a-form-item>
        <a-form-item label="品牌名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsBrandName']" placeholder="请输入品牌名称"></a-input>
        </a-form-item>
        <a-form-item label="品牌网址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsBrandWww']" placeholder="请输入品牌网址"></a-input>
        </a-form-item>
        <a-form-item label="品牌图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-image-upload isMultiple v-decorator="['goodsBrandPic']"></j-image-upload>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsBrandText']" placeholder="请输入备注"></a-input>
        </a-form-item>
        <a-form-item label="停用" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['goodsBrandDel']" :trigger-change="true" dictCode="is_del" placeholder="请选择停用"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"


  export default {
    name: "BaGoodsBrandModal",
    components: { 
      JImageUpload,
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
          add: "/jeewms/baGoodsBrand/add",
          edit: "/jeewms/baGoodsBrand/edit",
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
          this.form.setFieldsValue(pick(this.model,'goodsBrandCode','goodsBrandName','goodsBrandWww','goodsBrandPic','goodsBrandText','goodsBrandDel'))
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
        this.form.setFieldsValue(pick(row,'goodsBrandCode','goodsBrandName','goodsBrandWww','goodsBrandPic','goodsBrandText','goodsBrandDel'))
      },

      
    }
  }
</script>