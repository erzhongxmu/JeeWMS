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

        <a-form-item label="车牌号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['chepaihao']" placeholder="请输入车牌号"></a-input>
        </a-form-item>
        <a-form-item label="商品分类" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['chexing']" placeholder="请输入商品分类"></a-input>
        </a-form-item>
        <a-form-item label="最大体积" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zuidatiji']" placeholder="请输入最大体积"></a-input>
        </a-form-item>
        <a-form-item label="载重" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zaizhong']" placeholder="请输入载重"></a-input>
        </a-form-item>
        <a-form-item label="载人数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zairen']" placeholder="请输入载人数"></a-input>
        </a-form-item>
        <a-form-item label="准假驾照" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['jiazhao']" placeholder="请输入准假驾照"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['beizhu']" placeholder="请输入备注"></a-input>
        </a-form-item>
        <a-form-item label="默认司机" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['username']" placeholder="请输入默认司机"></a-input>
        </a-form-item>
        <a-form-item label="区域" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['quyu']" placeholder="请输入区域"></a-input>
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
    name: "TmsMdCheliangModal",
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
          add: "/jeewms/tmsMdCheliang/add",
          edit: "/jeewms/tmsMdCheliang/edit",
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
          this.form.setFieldsValue(pick(this.model,'chepaihao','chexing','zuidatiji','zaizhong','zairen','jiazhao','zhuangtai','beizhu','username','quyu'))
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
        this.form.setFieldsValue(pick(row,'chepaihao','chexing','zuidatiji','zaizhong','zairen','jiazhao','zhuangtai','beizhu','username','quyu'))
      },


    }
  }
</script>