<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="创建人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['createName']" placeholder="请输入创建人名称"></a-input>
        </a-form-item>
        <a-form-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择创建日期" v-decorator="['createTime']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="货主编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['cusCode']" :trigger-change="true" dictCode="" placeholder="请选择货主编码"/>
        </a-form-item>
        <a-form-item label="预计到货时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择预计到货时间" v-decorator="['imData']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="订单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['imCusCode']" placeholder="请输入订单号"></a-input>
        </a-form-item>
        <a-form-item label="司机" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['imCarDri']" placeholder="请输入司机"></a-input>
        </a-form-item>
        <a-form-item label="司机电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['imCarMobile']" placeholder="请输入司机电话"></a-input>
        </a-form-item>
        <a-form-item label="车号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['imCarNo']" placeholder="请输入车号"></a-input>
        </a-form-item>
        <a-form-item label="订单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['orderTypeCode']" :trigger-change="true" dictCode="" placeholder="请选择订单类型"/>
        </a-form-item>
        <a-form-item label="月台" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['platformCode']" placeholder="请输入月台"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['imBeizhu']" placeholder="请输入备注"></a-input>
        </a-form-item>
        <a-form-item label="单据状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['imSta']" placeholder="请输入单据状态"></a-input>
        </a-form-item>
        <a-form-item label="进货通知单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['noticeId']" placeholder="请输入进货通知单号"></a-input>
        </a-form-item>
        <a-form-item label="附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['fuJian']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="readOnly" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['readOnly']" placeholder="请输入readOnly"></a-input>
        </a-form-item>
        <a-form-item label="whereCon" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['whereCon']" placeholder="请输入whereCon"></a-input>
        </a-form-item>
        <a-form-item label="货主编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['supCode']" :trigger-change="true" dictCode="" placeholder="请选择货主编码"/>
        </a-form-item>
        <a-form-item label="货主名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['supName']" placeholder="请输入货主名称"></a-input>
        </a-form-item>
        <a-form-item label="对接单据类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['piClass']" placeholder="请输入对接单据类型"></a-input>
        </a-form-item>
        <a-form-item label="账套" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['piMaster']" placeholder="请输入账套"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'
  import JUpload from '@/components/jeecg/JUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: "WmImNoticeHModal",
    components: {
      JDate,
      JUpload,
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
          add: "/jeewms/wmImNoticeH/add",
          edit: "/jeewms/wmImNoticeH/edit",
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
          this.form.setFieldsValue(pick(this.model,'createName','createTime','cusCode','imData','imCusCode','imCarDri','imCarMobile','imCarNo','orderTypeCode','platformCode','imBeizhu','imSta','noticeId','fuJian','readOnly','whereCon','supCode','supName','piClass','piMaster'))
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
        this.form.setFieldsValue(pick(row,'createName','createTime','cusCode','imData','imCusCode','imCarDri','imCarMobile','imCarNo','orderTypeCode','platformCode','imBeizhu','imSta','noticeId','fuJian','readOnly','whereCon','supCode','supName','piClass','piMaster'))
      }

    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>