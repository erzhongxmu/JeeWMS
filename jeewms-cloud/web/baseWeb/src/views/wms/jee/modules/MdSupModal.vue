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

        <a-form-item :label="$t('简称')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zhongWenQch', validatorRules.zhongWenQch]" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
         <a-form-item :label="$t('名称')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['gysJianCheng', validatorRules.gysJianCheng]" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item :label="$t('编码')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['gysBianMa', validatorRules.gysBianMa]" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
<!--        <a-form-item label="企业属性" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['xingYeFenLei', validatorRules.xingYeFenLei]" :trigger-change="true" dictCode="ba_com_type,com_type_name,com_type_code" :placeholder="title==$t('查看')?'':'请选择企业属性'" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="供应商等级" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['gysDengJi', validatorRules.gysDengJi]" :trigger-change="true" dictCode="ba_com_deg,com_deg_name,com_deg_code" :placeholder="title==$t('查看')?'':'请选择供应商等级'" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="所属行业" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['suoShuXingYe', validatorRules.suoShuXingYe]" :trigger-change="true" dictCode="ba_classfl,classfl_name,classfl_code" :placeholder="title==$t('查看')?'':'请选择所属行业'" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
       <a-form-item :label="$t('工厂地址')" :labelCol="labelCol" :wrapperCol="wrapperCol">
         <a-input v-decorator="['diZhi']" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
       </a-form-item>
       <a-form-item :label="$t('办公地址')" :labelCol="labelCol" :wrapperCol="wrapperCol">
         <a-input v-decorator="['zengYongQi']" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
       </a-form-item>
       <a-form-item :label="$t('主营产品')" :labelCol="labelCol" :wrapperCol="wrapperCol">
         <a-input  v-decorator="['zhuYingYeWu']" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
         <!-- <j-popup
                v-decorator="['zhuYingYeWu']"
                :trigger-change="true"
                  field="supplyCode"
                  org-fields="goods_code"
                  dest-fields="zhuYingYeWu"
                  code="wv_goods_select"
                  :multi="false"
                  @callback="popupCallback"
                /> -->
       </a-form-item>
        <a-form-item :label="$t('联系人')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zhuLianXiRen']" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
<!--        <a-form-item label="电话" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['dianHua']" :placeholder="title==$t('查看')?'':'请输入电话'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
        <a-form-item :label="$t('联系方式')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shouJi']" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item :label="$t('开票税点')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shuiWuDeng']" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item :label="$t('省份')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['province']" :placeholder="$t('请输入')" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        
        <a-form-item label="保密协议与准则" :labelCol="labelCol" :wrapperCol="wrapperCol">
         <a-textarea v-decorator="['wangYeDiZhi']" rows="4" :placeholder="title==$t('查看')?'':'请输入保密协议与行为准则'" :disabled="title==$t('查看')?true:false"/>
         </a-form-item>

        <a-form-item label="私人账户" :labelCol="labelCol" :wrapperCol="wrapperCol">
         <a-textarea v-decorator="['personalAccount']" rows="4" :placeholder="title==$t('查看')?'':'请输入私人账户'" :disabled="title==$t('查看')?true:false"/>
         </a-form-item>
         
        <a-form-item label="公司账户" :labelCol="labelCol" :wrapperCol="wrapperCol">
         <a-textarea v-decorator="['corporateAccount']" rows="4" :placeholder="title==$t('查看')?'':'请输入公司账户'" :disabled="title==$t('查看')?true:false"/>
         </a-form-item>
         
        <a-form-item label="外币账户" :labelCol="labelCol" :wrapperCol="wrapperCol">
         <a-textarea v-decorator="['foreignAccount']" rows="4" :placeholder="title==$t('查看')?'':'请输入外币账户'" :disabled="title==$t('查看')?true:false"/>
         </a-form-item>
         
         <!-- <a-form-item label="是否停用" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag  v-decorator="['gysZhuangTai']" placeholder="请选择" dictCode="sf_yn" :disabled="title==$t('查看')?true:false" type="radio"/>
         </a-form-item> -->
<!--          <a-textarea v-decorator="['beiZhu']" rows="4" :placeholder="title==$t('查看')?'':'请输入备注'" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>
<!--        <a-form-item label="Email地址" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['emaildiZhi', validatorRules.emaildiZhi]" :placeholder="title==$t('查看')?'':'请输入Email地址'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-textarea v-decorator="['beiZhu']" rows="4" :placeholder="title==$t('查看')?'':'请输入备注'" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"


  export default {
    name: "MdSupModal",
    components: {
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
          sm: { span: 7 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 14 },
        },
        confirmLoading: false,
        validatorRules: {
          zhongWenQch: {
            rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
          gysBianMa: {
            rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
          gysJianCheng: {
             rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
          xingYeFenLei: {
            rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
          gysDengJi: {
            rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
          suoShuXingYe: {
            rules: [
              { required: true, message: this.$t('请输入')},
            ]
          },
          shouJi: {
            rules: [
              { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
            ]
          },
          emaildiZhi: {
            rules: [
              { pattern: /^([\w]+\.*)([\w]+)@[\w]+\.\w{3}(\.\w{2}|)$/, message: '请输入正确的电子邮件!'},
            ]
          },
        },
        url: {
          add: "/jeewms/mdSup/add",
          edit: "/jeewms/mdSup/edit",
        },
        gysDengJi:''
      }
    },
    created () {
    },
    methods: {
      add (e) {
        this.gysDengJi = e
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,"gysZhuangTai","wangYeDiZhi","shuiWuDeng","zhuYingYeWu","personalAccount","corporateAccount",'foreignAccount',"gysJianCheng","province",'zhongWenQch','gysBianMa','zengYongQi','xingYeFenLei','gysDengJi','suoShuXingYe','diZhi','zhuLianXiRen','dianHua','shouJi','emaildiZhi','beiZhu'))
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
              this.model.gysDengJi = this.gysDengJi
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(this.$t('操作成功'));
                that.close();
                that.$emit('ok');
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
        this.form.setFieldsValue(pick(row,"gysZhuangTai","shuiWuDeng","wangYeDiZhi","zhuYingYeWu","personalAccount","corporateAccount",'foreignAccount',"gysJianCheng","province",'zhongWenQch','gysBianMa','xingYeFenLei','gysDengJi','suoShuXingYe','diZhi','zhuLianXiRen','dianHua','shouJi','emaildiZhi','beiZhu'))
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