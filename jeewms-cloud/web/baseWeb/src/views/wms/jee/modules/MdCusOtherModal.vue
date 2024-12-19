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
        <div class="title-item">基础属性：</div>

        <a-form-item label="所属货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['suoShuKeHu']" :trigger-change="true" dictCode="md_cus,zhong_wen_qch,id" :placeholder="title==$t('查看')?'':'请选择所属货主'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="中文全称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zhongWenQch']" :placeholder="title==$t('查看')?'':'请输入中文全称'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="助记码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zhuJiMa']" :placeholder="title==$t('查看')?'':'请输入助记码'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="客户简称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['keHuJianCheng']" :placeholder="title==$t('查看')?'':'请输入客户简称'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="客户编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['keHuBianMa']" :placeholder="title==$t('查看')?'':'请输入客户编码'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="客户英文名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['keHuYingWen']" :placeholder="title==$t('查看')?'':'请输入客户英文名称'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="曾用企业代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zengYongQi']" :placeholder="title==$t('查看')?'':'请输入曾用企业代码'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="曾用企业名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zengYongQiYe']" :placeholder="title==$t('查看')?'':'请输入曾用企业名称'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="客户状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['keHuZhuangTai']" :placeholder="title==$t('查看')?'':'请输入客户状态'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="企业属性" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['xingYeFenLei']" :trigger-change="true" dictCode="wms_com_type,com_type_name,com_type_code" :placeholder="title==$t('查看')?'':'请选择企业属性'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <!-- <a-form-item label="客户等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['keHuDengJi']" :trigger-change="true" dictCode="ba_com_deg,com_deg_name,com_deg_code" :placeholder="title==$t('查看')?'':'请选择客户等级'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item> -->
        <a-form-item label="所属行业" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['suoShuXingYe']" :trigger-change="true" dictCode="ba_com_classfy,classfl_name,classfl_code" :placeholder="title==$t('查看')?'':'请选择所属行业'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <!-- <a-form-item label="客户属性" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['keHuShuXing']" :placeholder="title==$t('查看')?'':'请输入客户属性'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item> -->
        <a-form-item label="归属组织代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['guiShuZuZh']" :placeholder="title==$t('查看')?'':'请输入归属组织代码'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="归属省份代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['guiShuSheng']" :placeholder="title==$t('查看')?'':'请输入归属省份代码'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="归属市代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['guiShuShiDai']" :placeholder="title==$t('查看')?'':'请输入归属市代码'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="归属县区代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['guiShu']" :placeholder="title==$t('查看')?'':'请输入归属县区代码'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>

        <div class="title-item">时间属性：</div>

        <a-form-item label="首签日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date :placeholder="title==$t('查看')?'':'请选择首签日期'" v-decorator="['shouQianRiQi']" :trigger-change="true" style="width: 100%" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="终止合作时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date :placeholder="title==$t('查看')?'':'请选择终止合作时间'" v-decorator="['zhongZhiHeShiJian']" :trigger-change="true" style="width: 100%" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="申请时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date :placeholder="title==$t('查看')?'':'请选择申请时间'" v-decorator="['shenQingShiJian']" :trigger-change="true" style="width: 100%" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="注册日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date :placeholder="title==$t('查看')?'':'请选择注册日期'" v-decorator="['zhuCeRiQi']" :trigger-change="true" style="width: 100%" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>

        <div class="title-item">联系属性：</div>

        <a-form-item label="地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['diZhi']" :placeholder="title==$t('查看')?'':'请输入地址'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="邮政编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['youZhengBianMa']" :placeholder="title==$t('查看')?'':'请输入邮政编码'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="主联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zhuLianXiRen']" :placeholder="title==$t('查看')?'':'请输入主联系人'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['dianHua']" :placeholder="title==$t('查看')?'':'请输入电话'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="手机" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shouJi']" :placeholder="title==$t('查看')?'':'请输入手机'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="Email地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['emaildiZhi']" :placeholder="title==$t('查看')?'':'请输入Email地址'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="网页地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['wangYeDiZhi']" :placeholder="title==$t('查看')?'':'请输入网页地址'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="联系人1" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zhuLianXiRen1']" :placeholder="title==$t('查看')?'':'请输入联系人1'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="电话1" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['dianHua1']" :placeholder="title==$t('查看')?'':'请输入电话1'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>

        <div class="title-item">其他属性：</div>

        <a-form-item label="法人代表" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['faRenDaiBiao']" :placeholder="title==$t('查看')?'':'请输入法人代表'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="法人身份证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['faRenShenFen']" :placeholder="title==$t('查看')?'':'请输入法人身份证号'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="注册资金万元" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zhuCeZiJin']" :placeholder="title==$t('查看')?'':'请输入注册资金万元'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="币别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['biBie']" :placeholder="title==$t('查看')?'':'请输入币别'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="营业执照号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['yingYeZhiZhao']" :placeholder="title==$t('查看')?'':'请输入营业执照号'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="税务登记证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shuiWuDeng']" :placeholder="title==$t('查看')?'':'请输入税务登记证号'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="道路运输经营许可证" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['daoLuYunShu']" :placeholder="title==$t('查看')?'':'请输入道路运输经营许可证'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="主营业务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['zhuYingYeWu']" :placeholder="title==$t('查看')?'':'请输入主营业务'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="合作意向" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['heYiXiang']" :placeholder="title==$t('查看')?'':'请输入合作意向'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="批准机关" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['piZhunJiGuan']" :placeholder="title==$t('查看')?'':'请输入批准机关'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="批准文号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['piZhunWenHao']" :placeholder="title==$t('查看')?'':'请输入批准文号'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['beiZhu']" rows="4" :placeholder="title==$t('查看')?'':'请输入备注'" :disabled="title==$t('查看')?true:false"/>
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
    name: "MdCusOtherModal",
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
          sm: { span: 10 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 14 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/jeewms/mdCusOther/add",
          edit: "/jeewms/mdCusOther/edit",
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
          this.form.setFieldsValue(pick(this.model,'suoShuKeHu','zhongWenQch','zhuJiMa','keHuJianCheng','keHuBianMa','keHuYingWen','zengYongQi','zengYongQiYe','keHuZhuangTai','xingYeFenLei','keHuDengJi','suoShuXingYe','shouQianRiQi','zhongZhiHeShiJian','shenQingShiJian','keHuShuXing','guiShuZuZh','guiShuSheng','guiShuShiDai','guiShu','diZhi','youZhengBianMa','zhuLianXiRen','dianHua','shouJi','emaildiZhi','wangYeDiZhi','faRenDaiBiao','faRenShenFen','zhuCeZiJin','biBie','yingYeZhiZhao','shuiWuDeng','daoLuYunShu','zhuYingYeWu','heYiXiang','piZhunJiGuan','piZhunWenHao','zhuCeRiQi','beiZhu','zhuLianXiRen1','dianHua1'))
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
        this.form.setFieldsValue(pick(row,'suoShuKeHu','zhongWenQch','zhuJiMa','keHuJianCheng','keHuBianMa','keHuYingWen','zengYongQi','zengYongQiYe','keHuZhuangTai','xingYeFenLei','keHuDengJi','suoShuXingYe','shouQianRiQi','zhongZhiHeShiJian','shenQingShiJian','keHuShuXing','guiShuZuZh','guiShuSheng','guiShuShiDai','guiShu','diZhi','youZhengBianMa','zhuLianXiRen','dianHua','shouJi','emaildiZhi','wangYeDiZhi','faRenDaiBiao','faRenShenFen','zhuCeZiJin','biBie','yingYeZhiZhao','shuiWuDeng','daoLuYunShu','zhuYingYeWu','heYiXiang','piZhunJiGuan','piZhunWenHao','zhuCeRiQi','beiZhu','zhuLianXiRen1','dianHua1'))
      },


    }
  }
</script>

<style scoped>
  .ant-modal-content .ant-modal-body .ant-form-item{
    display: inline-block !important;
    width: 33%;
  }
 >>>.ant-modal-content .ant-modal-body{
   max-height: 460px !important;
   overflow-y: auto;
 }
</style>