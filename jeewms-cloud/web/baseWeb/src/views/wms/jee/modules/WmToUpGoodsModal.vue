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
        <a-form-item :label="$t('客户')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['cusCode']" :trigger-change="true" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma" :placeholder="$t('请选择')" :disabled="true"/>
        </a-form-item>  
        <a-form-item :label="$t('商品编码')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsId']" :placeholder="$t('请输入')" :disabled="true"></a-input>
        </a-form-item>
        <a-form-item :label="$t('商品名称')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsName']" :placeholder="$t('请输入')" :disabled="true"></a-input>
        </a-form-item>
        <a-form-item :label="$t('数量')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['baseGoodscount']" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item :label="$t('生产日期')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsProData']" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item :label="$t('批次')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsBatch']" :placeholder="$t('请输入')" :disabled="true"></a-input>
        </a-form-item>
        <a-form-item :label="$t('库位编码')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['kuWeiBianMa']" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item :label="$t('箱码')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['binId']" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
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
    name: "WmToUpGoodsModal",
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
          add: "/jeewms/wmToUpGoods/add",
          edit: "/jeewms/wmToUpGoods/edit",
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
          this.form.setFieldsValue(pick(this.model,'goodsId','goodsName','goodsBatch','baseGoodscount','goodsProData','kuWeiBianMa','binId','cusCode'))
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
        this.form.setFieldsValue(pick(row,'goodsId','goodsName','goodsBatch','baseGoodscount','goodsProData','kuWeiBianMa','binId','cusCode'))
      },


    }
  }
</script>
<style scoped>
  >>>.ant-modal-content .ant-modal-body{
    max-height: 460px !important;
    overflow-y: auto;
  }
  >>>.ant-modal-content .ant-modal-body .ant-form-item{
    display: inline-block !important;
    width: 50%;
  }
</style>