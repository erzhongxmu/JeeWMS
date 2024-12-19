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
          <a-input v-decorator="['createName']" :placeholder="title==$t('查看')? '':'请输入创建人名称'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="创建人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['createBy']" :placeholder="title==$t('查看')? '':'请输入创建人登录名称'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date :placeholder="title==$t('查看')? '':'请选择创建日期'" v-decorator="['createTime']" :trigger-change="true" style="width: 100%" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="更新人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['updateName']" :placeholder="title==$t('查看')? '':'请输入更新人名称'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="更新人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['updateBy']" :placeholder="title==$t('查看')? '':'请输入更新人登录名称'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="更新日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date :placeholder="title==$t('查看')? '':'请选择更新日期'" v-decorator="['updateTime']" :trigger-change="true" style="width: 100%" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['sysOrgCode']" :placeholder="title==$t('查看')? '':'请输入所属部门'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="所属公司" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['sysCompanyCode']" :placeholder="title==$t('查看')? '':'请输入所属公司'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsId']" :placeholder="title==$t('查看')? '':'请输入商品编码'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsQua']" :placeholder="title==$t('查看')? '':'请输入数量'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="完成数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsQuaok']" :placeholder="title==$t('查看')? '':'请输入完成数量'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="原始单据编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['orderId']" :placeholder="title==$t('查看')? '':'请输入原始单据编码'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="原始单据行项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['orderIdI']" :placeholder="title==$t('查看')? '':'请输入原始单据行项目'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="原始单据类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['orderType']" :placeholder="title==$t('查看')? '':'请输入原始单据类型'" :disabled="title==$t('查看')?true:false"></a-input>
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
        <a-form-item label="作业类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['actTypeCode']" :placeholder="title==$t('查看')? '':'请输入作业类型'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="库位编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['kuWeiBianMa']" :placeholder="title==$t('查看')? '':'请输入库位编码'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="目标托盘" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['binIdTo']" :placeholder="title==$t('查看')? '':'请输入目标托盘'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="源托盘" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['binIdFrom']" :placeholder="title==$t('查看')? '':'请输入源托盘'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['cusCode']" :trigger-change="true" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma" :placeholder="title==$t('查看')? '':'请选择货主'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['downSta']" :placeholder="title==$t('查看')? '':'请输入状态'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="基本单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['baseUnit']" :placeholder="title==$t('查看')? '':'请输入基本单位'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="基本单位数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['baseGoodscount']" :placeholder="title==$t('查看')? '':'请输入基本单位数量'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsName']" :placeholder="title==$t('查看')? '':'请输入商品名称'" :disabled="title==$t('查看')?true:false"></a-input>
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
  import JDate from '@/components/jeecg/JDate'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"


  export default {
    name: "WmToDownGoodsModal",
    components: {
      JDate,
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
          add: "/jeewms/wmToDownGoods/add",
          edit: "/jeewms/wmToDownGoods/edit",
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
          this.form.setFieldsValue(pick(this.model,'createName','createBy','createTime','updateName','updateBy','updateTime','sysOrgCode','sysCompanyCode','goodsId','goodsQua','goodsQuaok','orderId','orderIdI','orderType','goodsUnit','goodsProData','goodsBatch','actTypeCode','kuWeiBianMa','binIdTo','binIdFrom','cusCode','downSta','baseUnit','baseGoodscount','goodsName','imCusCode'))
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
        this.form.setFieldsValue(pick(row,'createName','createBy','createTime','updateName','updateBy','updateTime','sysOrgCode','sysCompanyCode','goodsId','goodsQua','goodsQuaok','orderId','orderIdI','orderType','goodsUnit','goodsProData','goodsBatch','actTypeCode','kuWeiBianMa','binIdTo','binIdFrom','cusCode','downSta','baseUnit','baseGoodscount','goodsName','imCusCode'))
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