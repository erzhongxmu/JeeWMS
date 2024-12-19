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

<!--        <a-form-item label="创建人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['createName']" placeholder="请输入创建人名称"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="创建人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['createBy']" placeholder="请输入创建人登录名称"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-date placeholder="请选择创建日期" v-decorator="['createTime']" :trigger-change="true" style="width: 100%"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="更新人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['updateName']" placeholder="请输入更新人名称"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="更新人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['updateBy']" placeholder="请输入更新人登录名称"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="更新日期" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-date placeholder="请选择更新日期" v-decorator="['updateTime']" :trigger-change="true" style="width: 100%"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['sysOrgCode']" placeholder="请输入所属部门"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="所属公司" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['sysCompanyCode']" placeholder="请输入所属公司"></a-input>-->
<!--        </a-form-item>-->
        <a-form-item label="货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['cusCode']" placeholder="请输入货主"></a-input>
        </a-form-item>
        <a-form-item label="要求交货时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择要求交货时间" v-decorator="['delvData']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="收货人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['delvMember']" placeholder="请输入收货人"></a-input>
        </a-form-item>
        <a-form-item label="收货人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['delvMobile']" placeholder="请输入收货人电话"></a-input>
        </a-form-item>
        <a-form-item label="收货人地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['delvAddr']" placeholder="请输入收货人地址"></a-input>
        </a-form-item>
        <a-form-item label="承运人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['reMember']" placeholder="请输入承运人"></a-input>
        </a-form-item>
        <a-form-item label="承运人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['reMobile']" placeholder="请输入承运人电话"></a-input>
        </a-form-item>
        <a-form-item label="承运人车号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['reCarno']" placeholder="请输入承运人车号"></a-input>
        </a-form-item>
        <a-form-item label="发货月台" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['omPlatNo']" placeholder="请输入发货月台"></a-input>
        </a-form-item>
<!--        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol" v-if="title='查看'">-->
<!--          <a-input v-decorator="['omSta']" :placeholder="title==$t('查看')? '':'请输入状态'" :disabled="true"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="打印状态" :labelCol="labelCol" :wrapperCol="wrapperCol" v-if="title='查看'">-->
<!--          <a-input v-decorator="['printStatus']" :placeholder="title==$t('查看')? '':'请输入打印状态'" :disabled="true"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="出货单号" :labelCol="labelCol" :wrapperCol="wrapperCol" v-if="title='查看'">-->
<!--          <a-input v-decorator="['omNoticeId']" :placeholder="title==$t('查看')? '':'请输入出货单号'" :disabled="true"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['omSta']" placeholder="请输入状态"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="出货单号" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['omNoticeId']" placeholder="请输入出货单号"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="readOnly" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['readOnly']" placeholder="请输入readOnly"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="whereCon" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['whereCon']" placeholder="请输入whereCon"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="订单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['orderTypeCode']" placeholder="请输入订单类型"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="三方货主" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['ocusCode']" placeholder="请输入三方货主"></a-input>-->
<!--        </a-form-item>-->
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['omBeizhu']" placeholder="请输入备注"></a-input>
        </a-form-item>
        <a-form-item label="附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['fuJian']" placeholder="请输入附件"></a-input>
        </a-form-item>
<!--        <a-form-item label="三方货主名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['ocusName']" placeholder="请输入三方货主名称"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="imCusCode" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['imCusCode']" placeholder="请输入imCusCode"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="printStatus" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['printStatus']" placeholder="请输入printStatus"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="对接单据类型" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['piClass']" placeholder="请输入对接单据类型"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="账套" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['piMaster']" placeholder="请输入账套"></a-input>-->
<!--        </a-form-item>-->

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
    name: "WmOmNoticeHModal",
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
          this.form.setFieldsValue(pick(this.model,'createName','createBy','createTime','updateName','updateBy','updateTime','sysOrgCode','sysCompanyCode','cusCode','delvData','delvMember','delvMobile','delvAddr','reMember','reMobile','reCarno','omPlatNo','omBeizhu','omSta','omNoticeId','fuJian','readOnly','whereCon','orderTypeCode','ocusCode','ocusName','imCusCode','printStatus','piClass','piMaster'))
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
        this.form.setFieldsValue(pick(row,'createName','createBy','createTime','updateName','updateBy','updateTime','sysOrgCode','sysCompanyCode','cusCode','delvData','delvMember','delvMobile','delvAddr','reMember','reMobile','reCarno','omPlatNo','omBeizhu','omSta','omNoticeId','fuJian','readOnly','whereCon','orderTypeCode','ocusCode','ocusName','imCusCode','printStatus','piClass','piMaster'))
      },


    }
  }
</script>
<style scoped>
  .ant-modal-content .ant-modal-body .ant-form-item{
    display: inline-block !important;
    width: 33%;
  }
  .ant-modal-content{
    width: 1000px !important;
  }
</style>