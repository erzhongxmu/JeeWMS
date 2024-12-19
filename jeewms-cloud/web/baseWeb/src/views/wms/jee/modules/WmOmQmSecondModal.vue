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

        <a-form-item label="创建人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['createName']" placeholder="请输入创建人名称"></a-input>
        </a-form-item>
        <a-form-item label="创建人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['createBy']" placeholder="请输入创建人登录名称"></a-input>
        </a-form-item>
        <a-form-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择创建日期" v-decorator="['createDate']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="更新人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['updateName']" placeholder="请输入更新人名称"></a-input>
        </a-form-item>
        <a-form-item label="更新人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['updateBy']" placeholder="请输入更新人登录名称"></a-input>
        </a-form-item>
        <a-form-item label="更新日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择更新日期" v-decorator="['updateDate']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['sysOrgCode']" placeholder="请输入所属部门"></a-input>
        </a-form-item>
        <a-form-item label="所属公司" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['sysCompanyCode']" placeholder="请输入所属公司"></a-input>
        </a-form-item>
        <a-form-item label="商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsId']" placeholder="请输入商品编码"></a-input>
        </a-form-item>
        <a-form-item label="数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsQua']" placeholder="请输入数量"></a-input>
        </a-form-item>
        <a-form-item label="完成数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsQuaok']" placeholder="请输入完成数量"></a-input>
        </a-form-item>
        <a-form-item label="原始单据编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['orderId']" placeholder="请输入原始单据编码"></a-input>
        </a-form-item>
        <a-form-item label="原始单据行项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['orderIdI']" placeholder="请输入原始单据行项目"></a-input>
        </a-form-item>
        <a-form-item label="原始单据类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['orderType']" placeholder="请输入原始单据类型"></a-input>
        </a-form-item>
        <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsUnit']" placeholder="请输入单位"></a-input>
        </a-form-item>
        <a-form-item label="生产日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsProData']" placeholder="请输入生产日期"></a-input>
        </a-form-item>
        <a-form-item label="批次" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsBatch']" placeholder="请输入批次"></a-input>
        </a-form-item>
        <a-form-item label="作业类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['actTypeCode']" placeholder="请输入作业类型"></a-input>
        </a-form-item>
        <a-form-item label="库位编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['kuWeiBianMa']" placeholder="请输入库位编码"></a-input>
        </a-form-item>
        <a-form-item label="目标托盘" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['binIdTo']" placeholder="请输入目标托盘"></a-input>
        </a-form-item>
        <a-form-item label="源托盘" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['binIdFrom']" placeholder="请输入源托盘"></a-input>
        </a-form-item>
        <a-form-item label="货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['cusCode']" placeholder="请输入货主"></a-input>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['downSta']" placeholder="请输入状态"></a-input>
        </a-form-item>
        <a-form-item label="基本单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['baseUnit']" placeholder="请输入基本单位"></a-input>
        </a-form-item>
        <a-form-item label="基本单位数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['baseGoodscount']" placeholder="请输入基本单位数量"></a-input>
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
    name: "WmToDownGoodsModal",
    components: {
      JDate,
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
          add: "/jeewms/wmOmQmI/addWmToDownGoods",
          edit: "/jeewms/wmOmQmI/editWmToDownGoods",
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
          this.form.setFieldsValue(pick(this.model,'createName','createBy','createDate','updateName','updateBy','updateDate','sysOrgCode','sysCompanyCode','goodsId','goodsQua','goodsQuaok','orderId','orderIdI','orderType','goodsUnit','goodsProData','goodsBatch','actTypeCode','kuWeiBianMa','binIdTo','binIdFrom','cusCode','downSta','baseUnit','baseGoodscount','goodsName','imCusCode','omBeiZhu'))
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
            formData['orderIdI'] = this.mainId
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
        this.form.setFieldsValue(pick(row,'createName','createBy','createDate','updateName','updateBy','updateDate','sysOrgCode','sysCompanyCode','goodsId','goodsQua','goodsQuaok','orderId','orderIdI','orderType','goodsUnit','goodsProData','goodsBatch','actTypeCode','kuWeiBianMa','binIdTo','binIdFrom','cusCode','downSta','baseUnit','baseGoodscount','goodsName','imCusCode','omBeiZhu'))
      },


    }
  }
</script>
