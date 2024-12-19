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

        <!--            <a-form-item label="创建人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['createName']" :placeholder="title==$t('查看')? '':'请输入创建人名称'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--            </a-form-item>-->
<!--            <a-form-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <j-date :placeholder="title==$t('查看')? '':'请选择创建日期'" v-decorator="['createTime']" :trigger-change="true" style="width: 100%" :disabled="title==$t('查看')?true:false"/>-->
<!--            </a-form-item>-->
        <a-form-item label="出货通知" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['omNoticeId']" :placeholder="title==$t('查看')? '':'请输入出货通知'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="出货商品" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsId']" :placeholder="title==$t('查看')? '':'请输入出货商品'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="出货数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsQua']" :placeholder="title==$t('查看')? '':'请输入出货数量'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="出货单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsUnit']" :placeholder="title==$t('查看')? '':'请输入出货单位'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="生产日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date :placeholder="title==$t('查看')? '':'请选择生产日期'" v-decorator="['goodsProData']" :trigger-change="true" style="width: 100%" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="批次" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsBatch']" :placeholder="title==$t('查看')? '':'请输入批次'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="出货仓位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['binOm']" :placeholder="title==$t('查看')? '':'请输入出货仓位'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="已出货数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsQuaok']" :placeholder="title==$t('查看')? '':'请输入已出货数量'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="预约出货时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['delvData']" :placeholder="title==$t('查看')? '':'请输入预约出货时间'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['cusCode']" :trigger-change="true" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma" :placeholder="title==$t('查看')? '':'请选择货主'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsText']" :placeholder="title==$t('查看')? '':'请输入商品名称'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="波次号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['waveId']" :placeholder="title==$t('查看')? '':'请输入波次号'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['omSta']" :placeholder="title==$t('查看')? '':'请输入状态'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="基本单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['baseUnit']" :placeholder="title==$t('查看')? '':'请输入基本单位'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="基本单位数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['baseGoodscount']" :placeholder="title==$t('查看')? '':'请输入基本单位数量'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="下架计划生成" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['planSta']" :placeholder="title==$t('查看')? '':'请输入下架计划生成'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsName']" :placeholder="title==$t('查看')? '':'请输入商品名称'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="其他系统ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['otherId']" :placeholder="title==$t('查看')? '':'请输入其他系统ID'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="订单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['imCusCode']" :placeholder="title==$t('查看')? '':'请输入订单号'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['omBeiZhu']" :placeholder="title==$t('查看')? '':'请输入备注'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="产品属性" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['chpShuXing']" :placeholder="title==$t('查看')? '':'请输入产品属性'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="商品条码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['barcode']" :placeholder="title==$t('查看')? '':'请输入商品条码'" :disabled="title==$t('查看')?true:false"></a-input>
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
  import JDictSelectTag from "@/components/dict/JDictSelectTag"


  export default {
    name: "WmOmNoticeIModal",
    components: {
      JDate,
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:1000,
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
        },
        url: {
          add: "/jeewms/wmOmNoticeI/add",
          edit: "/jeewms/wmOmNoticeI/edit",
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
          this.form.setFieldsValue(pick(this.model,'createName','createTime','omNoticeId','goodsId','goodsQua','goodsUnit','goodsProData','goodsBatch','binOm','goodsQuaok','delvData','cusCode','goodsText','waveId','omSta','baseUnit','baseGoodscount','planSta','goodsName','otherId','imCusCode','omBeiZhu','chpShuXing','barcode'))
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
        this.form.setFieldsValue(pick(row,'createName','createTime','omNoticeId','goodsId','goodsQua','goodsUnit','goodsProData','goodsBatch','binOm','goodsQuaok','delvData','cusCode','goodsText','waveId','omSta','baseUnit','baseGoodscount','planSta','goodsName','otherId','imCusCode','omBeiZhu','chpShuXing','barcode'))
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
    width: 33%;
  }
</style>