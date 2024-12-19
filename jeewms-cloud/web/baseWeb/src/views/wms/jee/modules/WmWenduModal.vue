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

        <a-form-item label="创建人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['createName']" placeholder="请输入创建人名称"></a-input>
        </a-form-item>
        <a-form-item label="创建人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['createBy']" placeholder="请输入创建人登录名称"></a-input>
        </a-form-item>
        <a-form-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择创建日期" v-decorator="['createDate']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="更新人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['updateName']" placeholder="请输入更新人名称"></a-input>
        </a-form-item>
        <a-form-item label="更新人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['updateBy']" placeholder="请输入更新人登录名称"></a-input>
        </a-form-item>
        <a-form-item label="更新日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择更新日期" v-decorator="['updateDate']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['sysOrgCode']" placeholder="请输入所属部门"></a-input>
        </a-form-item>
        <a-form-item label="所属公司" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['sysCompanyCode']" placeholder="请输入所属公司"></a-input>
        </a-form-item>
        <a-form-item label="流程状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['bpmStatus']" placeholder="请输入流程状态"></a-input>
        </a-form-item>
        <a-form-item label="温度地点" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['wenduDd']" placeholder="请输入温度地点"></a-input>
        </a-form-item>
        <a-form-item label="采集时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择采集时间" v-decorator="['wenduCjsj']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="温度值摄氏度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['wenduZhi']" placeholder="请输入温度值摄氏度"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['wenduBz']" placeholder="请输入备注"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'  


  export default {
    name: "WmWenduModal",
    components: { 
      JDate,
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
          add: "/jeewms/wmWendu/add",
          edit: "/jeewms/wmWendu/edit",
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
          this.form.setFieldsValue(pick(this.model,'createName','createBy','createDate','updateName','updateBy','updateDate','sysOrgCode','sysCompanyCode','bpmStatus','wenduDd','wenduCjsj','wenduZhi','wenduBz'))
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
        this.form.setFieldsValue(pick(row,'createName','createBy','createDate','updateName','updateBy','updateDate','sysOrgCode','sysCompanyCode','bpmStatus','wenduDd','wenduCjsj','wenduZhi','wenduBz'))
      },

      
    }
  }
</script>