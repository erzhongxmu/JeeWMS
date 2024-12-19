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

        <a-form-item label="创建人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['createBy']" :placeholder="title==$t('查看')?'':'请输入创建人登录名称'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date :placeholder="title==$t('查看')?'':'请选择创建日期'" v-decorator="['createTime']" :trigger-change="true" style="width: 100%" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['rfidType']" :placeholder="title==$t('查看')?'':'请输入类型'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="业务编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['rfidBuseno']" :placeholder="title==$t('查看')?'':'请输入业务编号'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="业务内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['rfidBusecont']" :placeholder="title==$t('查看')?'':'请输入业务内容'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="RFID1" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['rfidId1']" :placeholder="title==$t('查看')?'':'请输入RFID1'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="RFID2" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['rfidId2']" :placeholder="title==$t('查看')?'':'请输入RFID2'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="RFID3" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['rfidId3']" :placeholder="title==$t('查看')?'':'请输入RFID3'" :disabled="title==$t('查看')?true:false"></a-input>
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
    name: "RfidBuseModal",
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
          sm: { span: 8 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 14 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/jeewms/rfidBuse/add",
          edit: "/jeewms/rfidBuse/edit",
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
          this.form.setFieldsValue(pick(this.model,'createBy','createTime','rfidType','rfidBuseno','rfidBusecont','rfidId1','rfidId2','rfidId3'))
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
        this.form.setFieldsValue(pick(row,'createBy','createTime','rfidType','rfidBuseno','rfidBusecont','rfidId1','rfidId2','rfidId3'))
      },


    }
  }
</script>
<style scoped>
  >>>.ant-modal-content .ant-modal-body .ant-form-item{
    display: inline-block !important;
    width: 50%;
  }
  >>>.ant-modal-content .ant-modal-body{
    max-height: 460px !important;
    overflow-y: auto;
  }
</style>