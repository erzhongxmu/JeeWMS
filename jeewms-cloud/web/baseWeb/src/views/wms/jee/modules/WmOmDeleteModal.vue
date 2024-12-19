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

        <a-form-item label="到货通知单" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['omNoticeId']" :placeholder="title==$t('查看')? '':'请输入到货通知单'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsId']" :placeholder="title==$t('查看')? '':'请输入商品编码'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="出货数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['omQuat']" :placeholder="title==$t('查看')? '':'请输入出货数量'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['qmOkQuat']" :placeholder="title==$t('查看')? '':'请输入数量'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['itemText']" :placeholder="title==$t('查看')? '':'请输入备注'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="生产日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['proData']" :placeholder="title==$t('查看')? '':'请输入生产日期'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="托盘" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['tinId']" :placeholder="title==$t('查看')? '':'请输入托盘'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsUnit']" :placeholder="title==$t('查看')? '':'请输入单位'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="批次" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsBatch']" :placeholder="title==$t('查看')? '':'请输入批次'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="仓位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['binId']" :placeholder="title==$t('查看')? '':'请输入仓位'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="体积" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['tinTj']" :placeholder="title==$t('查看')? '':'请输入体积'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="重量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['tinZhl']" :placeholder="title==$t('查看')? '':'请输入重量'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="是否已下架" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['binSta']" :trigger-change="true" dictCode="sf_yn" :placeholder="title==$t('查看')? '':'请选择是否已下架'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['cusCode']" :trigger-change="true" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma" :placeholder="title==$t('查看')? '':'请选择货主'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="温度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['recDeg']" :placeholder="title==$t('查看')? '':'请输入温度'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="任务接收人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['assignTo']" :placeholder="title==$t('查看')? '':'请输入任务接收人'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="基本单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['baseUnit']" :placeholder="title==$t('查看')? '':'请输入基本单位'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="基本单位数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['baseGoodscount']" :placeholder="title==$t('查看')? '':'请输入基本单位数量'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="货主名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['cusName']" :placeholder="title==$t('查看')? '':'请输入货主名称'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsName']" :placeholder="title==$t('查看')? '':'请输入商品名称'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="波次编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['waveId']" :placeholder="title==$t('查看')? '':'请输入波次编号'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="订单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['imCusCode']" :placeholder="title==$t('查看')? '':'请输入订单号'" :disabled="title==$t('查看')?true:false"></a-input>
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
    name: "WmOmQmIModal",
    components: {
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:1200,
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
          add: "/jeewms/wmOmQmI/add",
          edit: "/jeewms/wmOmQmI/edit",
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
          this.form.setFieldsValue(pick(this.model,'omNoticeId','goodsId','omQuat','qmOkQuat','itemText','proData','tinId','goodsUnit','goodsBatch','binId','tinTj','tinZhl','binSta','cusCode','recDeg','assignTo','baseUnit','baseGoodscount','cusName','goodsName','waveId','imCusCode'))
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
        this.form.setFieldsValue(pick(row,'omNoticeId','goodsId','omQuat','qmOkQuat','itemText','proData','tinId','goodsUnit','goodsBatch','binId','tinTj','tinZhl','binSta','cusCode','recDeg','assignTo','baseUnit','baseGoodscount','cusName','goodsName','waveId','imCusCode'))
      },


    }
  }
</script>
<style scoped>
  >>>.ant-modal-content .ant-modal-body .ant-form-item{
    display: inline-block !important;
    width: 33%;
  }
  >>>.ant-modal-content .ant-modal-body{
    max-height: 460px !important;
    overflow-y: auto;
  }
</style>