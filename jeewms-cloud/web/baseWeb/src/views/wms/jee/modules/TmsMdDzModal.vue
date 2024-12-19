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

        <a-form-item label="用户" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['username']" placeholder="请输入用户"></a-input>
        </a-form-item>
        <a-form-item label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['lianxiren']" placeholder="请输入联系人"></a-input>
        </a-form-item>
        <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['dianhua']" placeholder="请输入联系电话"></a-input>
        </a-form-item>
        <a-form-item label="详细地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['xiangxidizhi']" placeholder="请输入详细地址"></a-input>
        </a-form-item>
        <a-form-item label="省份" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shengfen']" placeholder="请输入省份"></a-input>
        </a-form-item>
        <a-form-item label="城市" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['chengshi']" placeholder="请输入城市"></a-input>
        </a-form-item>
        <a-form-item label="区域" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['quyu']" placeholder="请输入区域"></a-input>
        </a-form-item>
        <a-form-item label="默认地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['morendizhi']" placeholder="请输入默认地址"></a-input>
        </a-form-item>
        <a-form-item label="地址类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['dizhileixing']" placeholder="请输入地址类型"></a-input>
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
    name: "TmsMdDzModal",
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
          add: "/jeewms/tmsMdDz/add",
          edit: "/jeewms/tmsMdDz/edit",
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
          this.form.setFieldsValue(pick(this.model,'username','lianxiren','dianhua','xiangxidizhi','shengfen','chengshi','quyu','morendizhi','dizhileixing'))
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
        this.form.setFieldsValue(pick(row,'username','lianxiren','dianhua','xiangxidizhi','shengfen','chengshi','quyu','morendizhi','dizhileixing'))
      },

      
    }
  }
</script>