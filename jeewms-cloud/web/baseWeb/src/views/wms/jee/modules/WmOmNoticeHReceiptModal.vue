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
        <a-form-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['sysOrgCode']" :placeholder="title==$t('查看')? '':'请输入所属部门'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="所属公司" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['sysCompanyCode']" :placeholder="title==$t('查看')? '':'请输入所属公司'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['cusCode']" :trigger-change="true" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma" :placeholder="title==$t('查看')? '':'请选择货主'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="要求交货时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date :placeholder="title==$t('查看')? '':'请选择要求交货时间'" v-decorator="['delvData']" :trigger-change="true" style="width: 100%" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="收货人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['delvMember']" :placeholder="title==$t('查看')? '':'请输入收货人'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="收货人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['delvMobile']" :placeholder="title==$t('查看')? '':'请输入收货人电话'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="收货人地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['delvAddr']" :placeholder="title==$t('查看')? '':'请输入收货人地址'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="承运人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['reMember']" :placeholder="title==$t('查看')? '':'请输入承运人'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="承运人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['reMobile']" :placeholder="title==$t('查看')? '':'请输入承运人电话'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="承运人车号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['reCarno']" :placeholder="title==$t('查看')? '':'请输入承运人车号'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="发货月台" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['omPlatNo']" :trigger-change="true" dictCode="ba_platform,platform_name,platform_code" :placeholder="title==$t('查看')? '':'请选择发货月台'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['omBeizhu']" :placeholder="title==$t('查看')? '':'请输入备注'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['omSta']" :placeholder="title==$t('查看')? '':'请输入状态'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="出货单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['omNoticeId']" :placeholder="title==$t('查看')? '':'请输入出货单号'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['fuJian']" :placeholder="title==$t('查看')? '':'请输入附件'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['readOnly']" :placeholder="title==$t('查看')? '':'请输入所属部门'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="订单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['orderTypeCode']" :placeholder="title==$t('查看')? '':'请输入订单类型'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="三方货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['ocusCode']" :placeholder="title==$t('查看')? '':'请输入三方货主'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="三方货主名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['ocusName']" :placeholder="title==$t('查看')? '':'请输入三方货主名称'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="订单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['imCusCode']" :placeholder="title==$t('查看')? '':'请输入订单号'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="打印状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['printStatus']" :placeholder="title==$t('查看')? '':'请输入打印状态'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="对接单据类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['piClass']" :placeholder="title==$t('查看')? '':'请输入对接单据类型'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="账套" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['piMaster']" :placeholder="title==$t('查看')? '':'请输入账套'" :disabled="title==$t('查看')?true:false"></a-input>
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
    name: "WmOmNoticeHReceiptModal",
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
          add: "/jeewms/wmOmNoticeH/add",
          edit: "/jeewms/wmOmNoticeH/edit",
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
          this.form.setFieldsValue(pick(this.model,'createName','createTime','sysOrgCode','sysCompanyCode','cusCode','delvData','delvMember','delvMobile','delvAddr','reMember','reMobile','reCarno','omPlatNo','omBeizhu','omSta','omNoticeId','fuJian','readOnly','orderTypeCode','ocusCode','ocusName','imCusCode','printStatus','piClass','piMaster'))
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
        this.form.setFieldsValue(pick(row,'createName','createTime','sysOrgCode','sysCompanyCode','cusCode','delvData','delvMember','delvMobile','delvAddr','reMember','reMobile','reCarno','omPlatNo','omBeizhu','omSta','omNoticeId','fuJian','readOnly','orderTypeCode','ocusCode','ocusName','imCusCode','printStatus','piClass','piMaster'))
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