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

        <a-form-item label="库存类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['kuctype', validatorRules.kuctype]" :placeholder="title==$t('查看')? '':'请输入库存类型'"></a-input>
        </a-form-item>
        <a-form-item label="库位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['binId']" :placeholder="title==$t('查看')? '':'请输入库位'"></a-input>
        </a-form-item>
        <a-form-item label="货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zhongWenQch']" :placeholder="title==$t('查看')? '':'请输入货主'"></a-input>
        </a-form-item>
        <a-form-item label="商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsId']" :placeholder="title==$t('查看')? '':'请输入商品编码'"></a-input>
        </a-form-item>
        <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shpMingCheng']" :placeholder="title==$t('查看')? '':'请输入商品名称'"></a-input>
        </a-form-item>
        <a-form-item label="商品数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['goodsQua']" :placeholder="title==$t('查看')? '':'请输入商品数量'" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="保质期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['bzhiQi']" :placeholder="title==$t('查看')? '':'请输入保质期'"></a-input>
        </a-form-item>
        <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsUnit']" :placeholder="title==$t('查看')? '':'请输入单位'"></a-input>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['sttSta']" :placeholder="title==$t('查看')? '':'请输入状态'"></a-input>
        </a-form-item>
        <a-form-item label="移动日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date :placeholder="title==$t('查看')? '':'请选择移动日期'" v-decorator="['lastMove']" :trigger-change="true" style="width: 100%"/>
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
    name: "WvStockSttModal",
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
          kuctype: {
            rules: [
              { required: true, message: '请输入库存类型!'},
            ]
          },
        },
        url: {
          add: "/jeewms/wvStockStt/add",
          edit: "/jeewms/wvStockStt/edit",
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
          this.form.setFieldsValue(pick(this.model,'kuctype','binId','zhongWenQch','goodsId','shpMingCheng','goodsQua','bzhiQi','goodsUnit','sttSta','lastMove'))
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
        this.form.setFieldsValue(pick(row,'kuctype','binId','zhongWenQch','goodsId','shpMingCheng','goodsQua','bzhiQi','goodsUnit','sttSta','lastMove'))
      },


    }
  }
</script>