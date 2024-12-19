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

        <a-form-item label="单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['fadh', validatorRules.fadh]" placeholder="请输入单号"></a-input>
        </a-form-item>
        <a-form-item label="发货人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['fahuoren']" placeholder="请输入发货人"></a-input>
        </a-form-item>
        <a-form-item label="发货人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['fhrdh']" placeholder="请输入发货人电话"></a-input>
        </a-form-item>
        <a-form-item label="发货人地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['fhrdz']" placeholder="请输入发货人地址"></a-input>
        </a-form-item>
        <a-form-item label="收货人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shouhuoren']" placeholder="请输入收货人"></a-input>
        </a-form-item>
        <a-form-item label="收货人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shrsj']" placeholder="请输入收货人电话"></a-input>
        </a-form-item>
        <a-form-item label="收货人地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shrdh']" placeholder="请输入收货人地址"></a-input>
        </a-form-item>
        <a-form-item label="长米" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['chang']" placeholder="请输入长米"></a-input>
        </a-form-item>
        <a-form-item label="宽米" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['kuan']" placeholder="请输入宽米"></a-input>
        </a-form-item>
        <a-form-item label="高米" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['gao']" placeholder="请输入高米"></a-input>
        </a-form-item>
        <a-form-item label="立方米" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['tiji']" placeholder="请输入立方米"></a-input>
        </a-form-item>
        <a-form-item label="重量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zhongl']" placeholder="请输入重量"></a-input>
        </a-form-item>
        <a-form-item label="代收款金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['daishouk']" placeholder="请输入代收款金额"></a-input>
        </a-form-item>
        <a-form-item label="是否等通知" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['dengtongzhi']" :trigger-change="true" dictCode="yn" placeholder="请选择是否等通知"/>
        </a-form-item>
        <a-form-item label="货主单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['ywkhdh']" placeholder="请输入货主单号"></a-input>
        </a-form-item>
        <a-form-item label="送货方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['hwshfs']" :trigger-change="true" dictCode="ys_type" placeholder="请选择送货方式"/>
        </a-form-item>
        <a-form-item label="货物" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['huowu']" rows="4" placeholder="请输入货物"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"


  export default {
    name: "TmsYwDingdanModal",
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
          sm: { span: 9 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 14 },
        },
        confirmLoading: false,
        validatorRules: {
          fadh: {
            rules: [
              { required: true, message: '请输入单号!'},
            ]
          },
        },
        url: {
          add: "/jeewms/tmsYwDingdan/add",
          edit: "/jeewms/tmsYwDingdan/edit",
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
          this.form.setFieldsValue(pick(this.model,'fadh','fahuoren','fhrdh','fhrdz','shouhuoren','shrsj','shrdh','huowu','chang','kuan','gao','tiji','zhongl','daishouk','dengtongzhi','zhuangtai','ywkhdh','hwshfs'))
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
        this.form.setFieldsValue(pick(row,'fadh','fahuoren','fhrdh','fhrdz','shouhuoren','shrsj','shrdh','huowu','chang','kuan','gao','tiji','zhongl','daishouk','dengtongzhi','zhuangtai','ywkhdh','hwshfs'))
      },


    }
  }
</script>
<style scoped>
  .ant-modal-content .ant-modal-body .ant-form-item{
    display: inline-block !important;
    width: 33%;
  }
  .ant-modal-content{
    width: 1000px !important;
  }
</style>