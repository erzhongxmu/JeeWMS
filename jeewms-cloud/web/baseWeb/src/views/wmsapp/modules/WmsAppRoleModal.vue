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

        <a-form-item label="角色编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['approleCode']" placeholder="请输入角色编号"></a-input>
        </a-form-item>
        <a-form-item label="角色名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['approleName']" placeholder="请输入角色名称"></a-input>
        </a-form-item>
        <a-form-item label="app模块编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-popup
            v-decorator="['appmodelCode']"
            :trigger-change="true"
            org-fields="id,appmodel_code,appmodel_name"
            dest-fields="appmodelId,appmodelCode,appmodelName"
            code="app_function"
            @callback="popupCallback"/>
        </a-form-item>
        <a-form-item label="app模块名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['appmodelName']" placeholder="请输入app模块名称"></a-input>
        </a-form-item>
        <a-form-item v-show="false" label="app模块ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-show="false" v-decorator="['appmodelId']" placeholder="请输入app模块ID"></a-input>
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
    name: "WmsAppRoleModal",
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
          add: "/wmsapp/wmsAppRole/add",
          edit: "/wmsapp/wmsAppRole/edit",
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
          this.form.setFieldsValue(pick(this.model,'approleCode','approleName','appmodelCode','appmodelId','appmodelName'))
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
        this.form.setFieldsValue(pick(row,'approleCode','approleName','appmodelCode','appmodelId','appmodelName'))
      },


    }
  }
</script>