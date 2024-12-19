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

        <!-- <a-form-item  :label="$t('产品属性编码')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsTypeCode']" :placeholder="$t('请输入')"></a-input>
        </a-form-item> -->

        <a-form-item  :label="$t('产品属性名称')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsTypeName']" :placeholder="$t('请输入')"></a-input>
        </a-form-item>

        <a-form-item  :label="$t('产品属性英文名称')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsTypeEnname']" :placeholder="$t('请输入')"></a-input>
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
    name: "BaGoodsTypeModal",
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
          add: "/jeewms/baGoodsType/add",
          edit: "/jeewms/baGoodsType/edit",
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
          this.form.setFieldsValue(pick(this.model,'goodsTypeCode','goodsTypeName','goodsTypeEnname'))
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
            formData.goodsTypeCode = formData.goodsTypeName
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
        this.form.setFieldsValue(pick(row,'goodsTypeCode','goodsTypeName','goodsTypeEnname'))
      },

      
    }
  }
</script>