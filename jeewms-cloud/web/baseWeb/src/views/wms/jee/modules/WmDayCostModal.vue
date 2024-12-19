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
          <j-date placeholder="请选择创建日期" v-decorator="['createTime']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="更新人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['updateName']" placeholder="请输入更新人名称"></a-input>
        </a-form-item>
        <a-form-item label="更新人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['updateBy']" placeholder="请输入更新人登录名称"></a-input>
        </a-form-item>
        <a-form-item label="更新日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择更新日期" v-decorator="['updateTime']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['cusCode']" :trigger-change="true" dictCode="md_cus,zhong_wen_qch,id" placeholder="请选择货主"/>
        </a-form-item>
        <a-form-item label="费用" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['costCode']" placeholder="请输入费用"></a-input>
        </a-form-item>
        <a-form-item label="费用名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['costName']" placeholder="请输入费用名称"></a-input>
<!--          <j-dict-select-tag type="list" v-decorator="['costName']" :trigger-change="true" dictCode="" placeholder="请选择费用名称"/>-->
        </a-form-item>
        <a-form-item label="费用日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择费用日期" v-decorator="['costData']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="每日费用" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['dayCostYj']" placeholder="请输入每日费用"></a-input>
        </a-form-item>
        <a-form-item label="不含税价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['dayCostBhs']" placeholder="请输入不含税价"></a-input>
        </a-form-item>
        <a-form-item label="税额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['dayCostSe']" placeholder="请输入税额"></a-input>
        </a-form-item>
        <a-form-item label="含税价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['dayCostHsj']" placeholder="请输入含税价"></a-input>
        </a-form-item>
        <a-form-item label="费用来源" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['costOri']" placeholder="请输入费用来源"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['beizhu']" placeholder="请输入备注"></a-input>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['costSta']" placeholder="请输入状态"></a-input>
        </a-form-item>
        <a-form-item label="计费数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['costSl']" placeholder="请输入计费数量"></a-input>
        </a-form-item>
        <a-form-item label="数量单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['costUnit']" placeholder="请输入数量单位"></a-input>
        </a-form-item>
        <a-form-item label="是否已结算" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['costJs']" :trigger-change="true" dictCode="sf_yn" placeholder="请选择是否已结算"/>
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
    name: "WmDayCostModal",
    components: {
      JDate,
      JDictSelectTag,
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
          sm: { span: 9 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 14 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/jeewms/wmDayCost/add",
          edit: "/jeewms/wmDayCost/edit",
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
          this.form.setFieldsValue(pick(this.model,'createName','createBy','createTime','updateName','updateBy','updateTime','cusCode','costCode','costName','costData','dayCostYj','dayCostBhs','dayCostSe','dayCostHsj','costOri','beizhu','costSta','costSl','costUnit','costJs'))
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
        this.form.setFieldsValue(pick(row,'createName','createBy','createTime','updateName','updateBy','updateTime','cusCode','costCode','costName','costData','dayCostYj','dayCostBhs','dayCostSe','dayCostHsj','costOri','beizhu','costSta','costSl','costUnit','costJs'))
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