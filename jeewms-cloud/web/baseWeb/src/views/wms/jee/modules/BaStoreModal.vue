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

        <a-form-item :label="$t('仓库代码')" :labelCol="labelCol" :wrapperCol="wrapperCol"  >
          <a-input v-decorator="['storeCode']" :placeholder="$t('请输入')" ></a-input>
        </a-form-item>
        <a-form-item :label="$t('仓库名称')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['storeName']" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item :label="$t('备注')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['storeText']" :placeholder="$t('请输入')"></a-input>
        </a-form-item>
<!--        <a-form-item label="仓库属性" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['storeText']" :trigger-change="true" dictCode="ba_goods_type,goods_type_name,goods_type_code" :placeholder="title==$t('查看')?'':'请选择仓库属性'" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
         <a-form-item :label="$t('负责人')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['directId']" :placeholder="$t('请输入')"></a-input>
        </a-form-item>
        <a-form-item :label="$t('管理员')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['responseId']" :placeholder="$t('请输入')"></a-input>
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
    name: "BaStoreModal",
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
          sm: { span: 8 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/jeewms/baStore/add",
          edit: "/jeewms/baStore/edit",
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
          this.form.setFieldsValue(pick(this.model,'storeCode','storeName','storeText','directId','responseId'))
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
        this.form.setFieldsValue(pick(row,'storeCode','storeName','storeText','directId','responseId'))
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