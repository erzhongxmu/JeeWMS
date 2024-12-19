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
          <a-input v-decorator="['noticeId']" :placeholder="title==$t('查看')? '':'请输入单号'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="库位编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['binId']" :placeholder="title==$t('查看')? '':'请输入库位编码'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="托盘编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['tinId']" :placeholder="title==$t('查看')? '':'请输入托盘编码'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsId']" :placeholder="title==$t('查看')? '':'请输入商品编码'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsName']" :placeholder="title==$t('查看')? '':'请输入商品名称'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsQua']" :placeholder="title==$t('查看')? '':'请输入数量'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsUnit']" :placeholder="title==$t('查看')? '':'请输入单位'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="生产日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsProData']" :placeholder="title==$t('查看')? '':'请输入生产日期'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="批次" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsBatch']" :placeholder="title==$t('查看')? '':'请输入批次'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="盘点数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['sttQua']" :placeholder="title==$t('查看')? '':'请输入盘点数量'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="货主名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['cusName']" :placeholder="title==$t('查看')? '':'请输入货主名称'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['cusCode']" :placeholder="title==$t('查看')? '':'请输入货主'" :disabled="title==$t('查看')?true:false"></a-input>
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
    name: "WmSttInGoodsModal",
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
          add: "/jeewms/wmSttInGoods/add",
          edit: "/jeewms/wmSttInGoods/edit",
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
          this.form.setFieldsValue(pick(this.model,'noticeId','binId','tinId','goodsId','goodsName','goodsQua','goodsUnit','goodsProData','goodsBatch','sttQua','cusName','cusCode','sttSta'))
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
        this.form.setFieldsValue(pick(row,'noticeId','binId','tinId','goodsId','goodsName','goodsQua','goodsUnit','goodsProData','goodsBatch','sttQua','cusName','cusCode','sttSta'))
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