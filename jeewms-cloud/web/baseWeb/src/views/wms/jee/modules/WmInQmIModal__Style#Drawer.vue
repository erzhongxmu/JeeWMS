<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="到货通知单" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['imNoticeId']" placeholder="请输入到货通知单"></a-input>
        </a-form-item>
        <a-form-item label="商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsId']" placeholder="请输入商品编码"></a-input>
        </a-form-item>
        <a-form-item label="到货数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['imQuat']" placeholder="请输入到货数量"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['itemText']" placeholder="请输入备注"></a-input>
        </a-form-item>
        <a-form-item label="生产日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['proData']" placeholder="请输入生产日期"></a-input>
        </a-form-item>
        <a-form-item label="托盘" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['tinId']" placeholder="请输入托盘"></a-input>
        </a-form-item>
        <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsUnit']" placeholder="请输入单位"></a-input>
        </a-form-item>
        <a-form-item label="体积" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['tinTj']" placeholder="请输入体积"></a-input>
        </a-form-item>
        <a-form-item label="重量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['tinZhl']" placeholder="请输入重量"></a-input>
        </a-form-item>
        <a-form-item label="是否已上架" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['binSta']" placeholder="请输入是否已上架"></a-input>
        </a-form-item>
        <a-form-item label="货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['cusCode']" placeholder="请输入货主"></a-input>
        </a-form-item>
        <a-form-item label="基本单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['baseUnit']" placeholder="请输入基本单位"></a-input>
        </a-form-item>
        <a-form-item label="基本单位数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['baseGoodscount']" placeholder="请输入基本单位数量"></a-input>
        </a-form-item>
        <a-form-item label="基本单位收货数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['baseQmcount']" placeholder="请输入基本单位收货数量"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: "WmInQmIModal",
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
          add: "/jeewms/wmInQmI/add",
          edit: "/jeewms/wmInQmI/edit",
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
          this.form.setFieldsValue(pick(this.model,'imNoticeId','goodsId','imQuat','itemText','proData','tinId','goodsUnit','tinTj','tinZhl','binSta','cusCode','baseUnit','baseGoodscount','baseQmcount'))
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
        this.form.setFieldsValue(pick(row,'imNoticeId','goodsId','imQuat','itemText','proData','tinId','goodsUnit','tinTj','tinZhl','binSta','cusCode','baseUnit','baseGoodscount','baseQmcount'))
      }

    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>