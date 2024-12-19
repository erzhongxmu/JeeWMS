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

        <a-form-item :label="$t('名称')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zhongWenQch', validatorRules.zhongWenQch]" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item :label="$t('简称')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['keHuJianCheng', validatorRules.keHuJianCheng]" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item :label="$t('编码')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['keHuBianMa', validatorRules.keHuBianMa]" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        
         <a-form-item :label="$t('企业属性')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['xingYeFenLei', validatorRules.xingYeFenLei]" :trigger-change="true" dictCode="wms_com_type,com_type_code,com_type_name" :placeholder="$t('请选择')" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        
        <!--<a-form-item :label="$t('等级')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['keHuDengJi']" :trigger-change="true" dictCode="ba_com_deg,com_deg_code,com_deg_name" :placeholder="title==$t('查看')?'':'请选择货主等级'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item :label="$t('企业属性')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['suoShuXingYe']" :trigger-change="true" dictCode="ba_com_classfy,classfl_code,classfl_name" :placeholder="title==$t('查看')?'':'请选择企业属性'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item :label="$t('计费属性')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['keHuShuXing']" :trigger-change="true" dictCode="ba_kehushuxing,kehushuxing_code,kehushuxing_name" :placeholder="title==$t('查看')?'':'请选择货主属性'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item> -->
        
        <a-form-item :label="$t('联系人')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zhuLianXiRen']" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
<!--        <a-form-item :label="$t('电话')" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['dianHua']" :placeholder="title==$t('查看')?'':'请输入电话'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
        <a-form-item :label="$t('联系方式')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shouJi', validatorRules.shouJi]" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item :label="$t('地址')" :labelCol="labelCol" :wrapperCol="wrapperCol">
           <a-textarea v-decorator="['diZhi']" rows="6" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"/>
          <!-- <j-search-select-tag v-decorator="['diZhi']" :dictOptions="nation" :placeholder="$t('请选择')"/> -->
        </a-form-item>
        <a-form-model-item label="币种" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag  v-decorator="['biBie']"  dictCode="currency" :trigger-change="true" />
        </a-form-model-item>
<!--        <a-form-item :label="$t('Email地址')" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['emaildiZhi', validatorRules.emaildiZhi]" :placeholder="title==$t('查看')?'':'请输入Email地址'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
        <a-form-item :label="$t('备注')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['beiZhu']" rows="6" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"/>
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
  import { countries} from '@/utils/PLTN_EXCEL/countries'

  export default {
    name: "MdCusModal",
    components: {
      JDictSelectTag,
    },
    data () {
      return {
        nation:countries,
        form: this.$form.createForm(this),
        title:"操作",
        width:1000,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 7 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          zhongWenQch: {
            rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
          keHuJianCheng: {
            rules: [
              { required: true, message: this.$t('请输入')},
              { pattern: /^[a-zA-Z]{4}$/, required: true, message: '简称只能为字母且不能超过4位'},
            ]
          },
          xingYeFenLei: {
            rules: [
              { required: true, message: this.$t('请选择')},
            ]
          },
          keHuBianMa: {
            rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
          shouJi: {
            rules: [
              { pattern: /^1[3456789]\d{9}$/, message: this.$t('请输入正确的联系方式')},
            ]
          },
          emaildiZhi: {
            rules: [
              { pattern: /^([\w]+\.*)([\w]+)@[\w]+\.\w{3}(\.\w{2}|)$/, message: '请输入正确的电子邮件!'},
            ]
          },
        },
        url: {
          add: "/jeewms/mdCus/add",
          edit: "/jeewms/mdCus/edit",
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
          this.form.setFieldsValue(pick(this.model,'zhongWenQch','keHuBianMa','xingYeFenLei','keHuDengJi','suoShuXingYe','keHuJianCheng','diZhi','zhuLianXiRen','dianHua','shouJi','emaildiZhi','beiZhu'))
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
                that.close();
              }else{
                that.$message.warning(this.$t(res.message));
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }

        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'zhongWenQch','keHuBianMa','xingYeFenLei','keHuJianCheng','keHuDengJi','suoShuXingYe','diZhi','zhuLianXiRen','dianHua','shouJi','emaildiZhi','beiZhu'))
      },


    }
  }
</script>
<style scoped>
  >>>.ant-modal-content .ant-modal-body{
    max-height: 560px !important;
    overflow-y: auto;
  }
  >>>.ant-modal-content .ant-modal-body .ant-form-item{
    display: inline-block !important;
    width: 50%;
  }
</style>