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

        <a-form-item :label="$t('分类名称')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['typeName', validatorRules.typeName]" :placeholder="$t('请输入')"></a-input>
        </a-form-item>
        <a-form-item :label="$t('分类名称英文')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['attr3']" :placeholder="$t('请输入')"></a-input>
        </a-form-item>
        <a-form-item :label="$t('分类编码')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['attr4', validatorRules.attr4]" :placeholder="$t('请输入')"></a-input>
        </a-form-item>
        <a-form-item :label="$t('海关编码')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['attr5']" :placeholder="$t('请输入')"></a-input>
        </a-form-item>
        <a-form-item :label="$t('用途')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['attr6', validatorRules.attr6]" :placeholder="$t('请输入')"></a-input>
        </a-form-item>
        <a-form-item :label="$t('所属部门')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag
                type="list"
                v-decorator="['sysOrgCode', validatorRules.sysOrgCode]"
                :trigger-change="true"
                dict="department"
                :placeholder="$t('请选择')"
                :disabled="title==$t('查看')?true:false"
              />
        </a-form-item>
        <!-- <a-form-item label="堆码极限" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['stackLimit', validatorRules.stackLimit]" placeholder="请输入堆码极限"></a-input>
        </a-form-item>
        <a-form-item label="最大库存量(件)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['maxStock', validatorRules.maxStock]" placeholder="请输入最大库存量(件)"></a-input>
        </a-form-item> -->

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'


  export default {
    name: "BaPartTypeModal",
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
          sm: { span: 13 },
        },
        confirmLoading: false,
        validatorRules: {
          typeName: {
            rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
          attr4: {
            rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
          attr5: {
            rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
          sysOrgCode: {
            rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
          stackLimit: {
            rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
          maxStock: {
            rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
        },
        url: {
          add: "/jeewms/baPartType/add",
          edit: "/jeewms/baPartType/edit",
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
          this.form.setFieldsValue(pick(this.model,'typeName','stackLimit','maxStock','attr3','attr4','attr5','attr6','sysOrgCode'))
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
                that.close();
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
        this.form.setFieldsValue(pick(row,'typeName','stackLimit','maxStock'))
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