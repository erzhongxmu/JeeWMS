<template>
  <j-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    >

    <a-spin :spinning="confirmLoading">
      <a-form>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="模板标题">
          <a-input disabled v-model="templateName"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="模板内容">
          <a-textarea disabled v-model="templateContent" :autosize="{ minRows: 5, maxRows: 8 }"/>
        </a-form-item>
        <!-- <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="测试数据">
          <a-textarea placeholder="请输入json格式测试数据" v-model="testData" :autosize="{ minRows: 5, maxRows: 8 }"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="消息类型">
          <j-dict-select-tag
            v-model="msgType"
            placeholder="请选择消息类型"
            dictCode="msgType"/>
        </a-form-item> -->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="发布对象类型">
          <j-dict-select-tag
            v-model="msgType"
            placeholder="请选择发布对象类型"
            dictCode="msgUser"
            @change="handleChangemsgType"/>
        </a-form-item>
        <a-form-item
          v-if="msgType != 'QTYG'"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="消息接收方">
          <j-popup
        v-model="receiver"
        code="tj_user_report"
        org-fields="username"
        dest-fields="username"
        :multi="true"
        field="username"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>
  import {httpAction,getAction} from '@/api/manage'

  export default {
    name: "SysMessageTestModal",
    data() {
      return {
        title: "操作",
        visible: false,
        model: {},
        labelCol: {
          xs: {span: 24},
          sm: {span: 5},
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16},
        },

        confirmLoading: false,
        url: {
          send: "/sys/message/sysMessageTemplate/sendMsg",
          send2: "/sys/message/sysMessage/sendTemplateAnnouncement2",
        },
        templateName: "",
        templateContent: "",
        receiver: "",
        msgType: "",
        testData: "",
        sendParams: {}
      }
    },
    methods: {
      handleChangemsgType(E){
        console.log(this.receiver)
        this.receiver = ''
      },
      open(record) {
        this.sendParams.templateCode = record.templateCode;
        this.templateName = record.templateName;
        this.templateContent = record.templateContent;
        this.testData = record.templateTestJson;
        this.visible = true;
      },
      close() {
        this.receiver = "";
        this.msgType = "";
        this.sendParams = {};
        this.visible = false;
      },
      handleOk() {
        let httpurl = this.url.send2;
        let method = 'get';
        if(!this.msgType) return this.$message.warning(this.$t('请选择发布对象类型'));
        if(this.msgType != 'QTYG'){
          if(!this.receiver) return this.$message.warning(this.$t('请选择接受消息员工'));
          this.sendParams.toUser = this.receiver;
        }else{
          this.sendParams.toUser = this.msgType;
        }
        console.log(this.sendParams)
        getAction(httpurl, this.sendParams).then((res) => {
          if (res.success) {
            this.$message.success(this.$t('操作成功'));
            this.close();
          } else {
            this.$message.warning(this.$t('操作失败'));
          }
        }).finally(() => {
          this.confirmLoading = false;
        })


        // let httpurl = this.url.send;
        // let method = 'post';
        // this.sendParams.testData = this.testData;
        // this.sendParams.receiver = this.receiver;
        // this.sendParams.msgType = this.msgType;
        // httpAction(httpurl, this.sendParams, method).then((res) => {
        //   if (res.success) {
        //     this.$message.success(this.$t('操作成功'));
        //   } else {
        //     this.$message.warning(this.$t('操作失败'));
        //   }
        // }).finally(() => {
        //   this.confirmLoading = false;
        //   this.close();
        // })
      },
      handleCancel() {
        this.close()
      },
    }
  }
</script>

<style scoped>

</style>