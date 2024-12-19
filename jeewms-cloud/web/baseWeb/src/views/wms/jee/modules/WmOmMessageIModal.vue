<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    style="height: 100vh !important;"
    @ok="handleOk"
    @cancel="handleCancel"
    >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="创建人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['createName']" placeholder="请输入创建人名称"></a-input>
        </a-form-item>
        <a-form-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择创建日期" v-decorator="['createTime']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['sysOrgCode']" placeholder="请输入所属部门"></a-input>
        </a-form-item>
        <a-form-item label="所属公司" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['sysCompanyCode']" placeholder="请输入所属公司"></a-input>
        </a-form-item>
        <a-form-item label="出货通知" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['omNoticeId']" placeholder="请输入出货通知"></a-input>
        </a-form-item>
        <a-form-item label="出货商品" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsId']" placeholder="请输入出货商品"></a-input>
        </a-form-item>
        <a-form-item label="出货数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsQua']" placeholder="请输入出货数量"></a-input>
        </a-form-item>
        <a-form-item label="出货单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsUnit']" placeholder="请输入出货单位"></a-input>
        </a-form-item>
        <a-form-item label="生产日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择生产日期" v-decorator="['goodsProData']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="批次" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsBatch']" placeholder="请输入批次"></a-input>
        </a-form-item>
        <a-form-item label="出货仓位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['binOm']" placeholder="请输入出货仓位"></a-input>
        </a-form-item>
        <a-form-item label="已出货数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsQuaok']" placeholder="请输入已出货数量"></a-input>
        </a-form-item>
        <a-form-item label="预约出货时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['delvData']" placeholder="请输入预约出货时间"></a-input>
        </a-form-item>
        <a-form-item label="货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['cusCode']" :trigger-change="true" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma" placeholder="请选择货主"/>
        </a-form-item>
        <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsText']" placeholder="请输入商品名称"></a-input>
        </a-form-item>
        <a-form-item label="波次号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['waveId']" placeholder="请输入波次号"></a-input>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['omSta']" placeholder="请输入状态"></a-input>
        </a-form-item>
        <a-form-item label="基本单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['baseUnit']" placeholder="请输入基本单位"></a-input>
        </a-form-item>
        <a-form-item label="基本单位数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['baseGoodscount']" placeholder="请输入基本单位数量"></a-input>
        </a-form-item>
        <a-form-item label="下架计划生成" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['planSta']" placeholder="请输入下架计划生成"></a-input>
        </a-form-item>
        <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsName']" placeholder="请输入商品名称"></a-input>
        </a-form-item>
        <a-form-item label="其他系统ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['otherId']" placeholder="请输入其他系统ID"></a-input>
        </a-form-item>
        <a-form-item label="订单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['imCusCode']" placeholder="请输入订单号"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['omBeiZhu']" placeholder="请输入备注"></a-input>
        </a-form-item>
        <a-form-item label="产品属性" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['chpShuXing']" placeholder="请输入产品属性"></a-input>
        </a-form-item>
        <a-form-item label="商品条码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['barcode']" placeholder="请输入商品条码"></a-input>
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
          add: "/jeewms/wmOmNoticeH/addWmOmNoticeI",
          edit: "/jeewms/wmOmNoticeH/editWmOmNoticeI",
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
          this.form.setFieldsValue(pick(this.model,'createName','createBy','createTime','updateName','updateBy','updateTime','sysOrgCode','sysCompanyCode','omNoticeId','goodsId','goodsQua','goodsUnit','goodsProData','goodsBatch','binOm','goodsQuaok','delvData','cusCode','cusName','goodsText','waveId','omSta','baseUnit','baseGoodscount','planSta','goodsName','otherId','binId','imCusCode','omBeiZhu','bzhiQi','chpShuXing','barcode'))
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
            formData['omNoticeId'] = this.mainId
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
        this.form.setFieldsValue(pick(row,'createName','createBy','createTime','updateName','updateBy','updateTime','sysOrgCode','sysCompanyCode','omNoticeId','goodsId','goodsQua','goodsUnit','goodsProData','goodsBatch','binOm','goodsQuaok','delvData','cusCode','cusName','goodsText','waveId','omSta','baseUnit','baseGoodscount','planSta','goodsName','otherId','binId','imCusCode','omBeiZhu','bzhiQi','chpShuXing','barcode'))
      },


    }
  }
</script>


