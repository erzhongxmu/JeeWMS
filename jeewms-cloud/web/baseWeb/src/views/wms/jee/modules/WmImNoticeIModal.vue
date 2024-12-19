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

        <a-form-item label="商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-popup  v-decorator="['goodsCode']" :trigger-change="true" org-fields="goods_code"
                    dest-fields="goodsCode" code="wv_goods_select" @callback="popupCallback" />
        </a-form-item>
        <a-form-item label="数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsCount']" :placeholder="title==$t('查看')? '':'请输入数量'"></a-input>
        </a-form-item>
        <a-form-item label="计划库位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['binPlan']" :placeholder="title==$t('查看')? '':'请输入库位'"/>
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
    name: "WmImNoticeIModal",
    components: {
    },
    props:{
      mainId:{
        type:String,
        required:false,
        default:''
      }
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
          add: "/jeewms/wmImNoticeH/addWmImNoticeI",
          edit: "/jeewms/wmImNoticeH/editWmImNoticeI",
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
          this.form.setFieldsValue(pick(this.model,'createName','createBy','createTime','updateName','updateBy','updateTime','sysOrgCode','sysCompanyCode','imNoticeId','imNoticeItem','goodsCode','goodsCount','goodsPrdData','goodsBatch','binPre','goodsFvol','goodsWeight','binPlan','goodsUnit','goodsWqmCount','goodsQmCount','noticeiSta','baseUnit','baseGoodscount','baseQmcount','goodsName','otherId','imCusCode','imBeizhu','barcode','shpGuiGe','bzhiQi','chpShuXing','tinId'))
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
            formData['imNoticeId'] = this.mainId
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
        this.form.setFieldsValue(pick(row,'goodsCode'))
      },
      popupCallback1(row) {
        this.form.setFieldsValue(pick(row,'binPlan'))
      }
      // getFormFieldValue(field) {
      //   return this.form.getFieldValue(field)
      // }

    }
  }
</script>
