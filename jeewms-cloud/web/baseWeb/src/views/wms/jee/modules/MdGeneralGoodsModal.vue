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

        <div class="title-item">货主属性：</div>

        <a-form-item label="所属货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['suoShuKeHu', validatorRules.suoShuKeHu]" :trigger-change="true" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma" placeholder="请选择所属货主" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="产品属性" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['chpShuXing', validatorRules.chpShuXing]" :trigger-change="true" dictCode="ba_goods_type,goods_type_name,goods_type_code" placeholder="请选择产品属性" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="存放温层" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['cfWenCeng', validatorRules.cfWenCeng]" :trigger-change="true" dictCode="ba_deg_type,deg_type_name,deg_type_code" placeholder="请选择存放温层" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>

        <div class="title-item">基础属性：</div>

        <a-form-item label="商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shpBianMa', validatorRules.shpBianMa]" placeholder="请输入商品编码" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shpMingCheng', validatorRules.shpMingCheng]" placeholder="请输入商品名称" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="商品简称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shpJianCheng']" placeholder="请输入商品简称" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="商品型号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shpXingHao']" placeholder="请输入商品型号" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="英文名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['ywMingCheng']" placeholder="请输入英文名称" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="日文名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['rwMingCheng']" placeholder="请输入日文名称" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="商品品牌" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shpPinPai']" placeholder="请输入商品品牌" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="商品条码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shpTiaoMa']" placeholder="请输入商品条码" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="商品货主编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['shpBianMakh']" :trigger-change="true" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma" placeholder="请选择商品货主编码" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>

        <div class="title-item">单位属性：</div>


        <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['shlDanWei', validatorRules.shlDanWei]" :trigger-change="true" dictCode="ba_unit,unit_name,unit_code" placeholder="请选择单位" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="重量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['weight', validatorRules.weight]" placeholder="请输入重量" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="体积" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['tiJiCm']" placeholder="请输入体积" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="长单品" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['chDanPin']" placeholder="请输入长单品" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="宽单品" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['kuDanPin']" placeholder="请输入宽单品" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="高单品" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['gaoDanPin']" placeholder="请输入高单品" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="长整箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['chZhXiang']" placeholder="请输入长整箱" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="宽整箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['kuZhXiang']" placeholder="请输入宽整箱" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="高整箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['gaoZhXiang']" placeholder="请输入高整箱" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>

        <div class="title-item">拆零属性：</div>


        <a-form-item label="拆零控制" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['chlKongZhi']" :trigger-change="true" dictCode="is_or_not" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="拆零数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['chlShl']" placeholder="请输入拆零数量" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="拆零单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['jshDanWei']" :trigger-change="true" dictCode="ba_unit,unit_zh_name,unit_code" placeholder="请选择拆零单位" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>

        <div class="title-item">其他属性：</div>

        <a-form-item label="码盘单层数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['mpDanCeng']" placeholder="请输入码盘单层数量" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="码盘层高" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['mpCengGao']" placeholder="请输入码盘层高" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="计费商品类" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['jfShpLei']" :trigger-change="true" dictCode="ba_goods_class,goods_class_name,goods_class_code" placeholder="请选择计费商品类" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>

<!--        <a-form-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['sysOrgCode']" :trigger-change="true" dictCode="sys_depart,depart_name,id" placeholder="请选择所属部门" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="所属公司" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['sysCompanyCode']" :trigger-change="true" dictCode="ba_com,com_name,com_code" placeholder="请选择所属公司" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="商品规格" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['shpGuiGe']" placeholder="请输入商品规格" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="商品颜色" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['shpYanSe']" placeholder="请输入商品颜色" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="品牌图片" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['ppTuPian']" placeholder="请输入品牌图片" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="保质期" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['bzhiQi']" placeholder="请输入保质期" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="净重" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['zhlKg']" placeholder="请输入净重" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="件数与体积比" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['jtiJiBi']" placeholder="请输入件数与体积比" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="件数与毛重比" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['jmZhongBi']" placeholder="请输入件数与毛重比" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="件数与净重比" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['jjZhongBi']" placeholder="请输入件数与净重比" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="尺寸单位" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['chcDanWei']" placeholder="请输入尺寸单位" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->

<!--        <a-form-item label="商品描述" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['shpMiaoShu']" placeholder="请输入商品描述" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="停用" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="radio" v-decorator="['zhuangTai']" :trigger-change="true" dictCode="is_or_not" placeholder="请选择停用" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="毛重" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['zhlKgm']" placeholder="请输入毛重" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="基准温度" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['jizhunWendu']" placeholder="请输入基准温度" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="货主名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['cusName']" placeholder="请输入货主名称" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="配送点" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['peisongdian']" placeholder="请输入配送点" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="上架推荐开关" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['recommend']" placeholder="请输入上架推荐开关" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="是否翻包" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['ifBackpacking']" :trigger-change="true" dictCode="is_or_not" placeholder="请选择是否翻包" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="翻包前SNP" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['beforeSnp']" placeholder="请输入翻包前SNP" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="翻包后SNP" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['afterSnp']" placeholder="请输入翻包后SNP" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="工艺路线" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['routing']" placeholder="请输入工艺路线" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="Dock管理" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['dock']" placeholder="请输入Dock管理" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="库位属性" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['kuWeiShuXing']" placeholder="请输入库位属性" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="包装页签" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['packingTag']" placeholder="请输入包装页签" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="货主ID" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['cusId']" :trigger-change="true" dictCode="md_cus,zhong_wen_qch,id" placeholder="请选择货主ID" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['partName']" placeholder="请输入商品名称" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="单位ID" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['orgId']" :trigger-change="true" dictCode="ba_unit,unit_code,id" placeholder="请选择单位ID" :disabled="title==$t('查看')?true:false"/>-->
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
    name: "MdGoodsModal",
    components: {
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
          sm: { span: 14 },
        },
        confirmLoading: false,
        validatorRules: {
          suoShuKeHu: {
            rules: [
              { required: true, message: '请选择所属货主!'},
            ]
          },
          chpShuXing: {
            rules: [
              { required: true, message: '请选择产品属性!'},
            ]
          },
          cfWenCeng: {
            rules: [
              { required: true, message: '请选择存放温层!'},
            ]
          },
          shpBianMa: {
            rules: [
              { required: true, message: '请输入商品编码!'},
            ]
          },
          shpMingCheng: {
            rules: [
              { required: true, message: '请输入商品名称!'},
            ]
          },
          shlDanWei: {
            rules: [
              { required: true, message: '请选择单位!'},
            ]
          },
          weight: {
            rules: [
              { required: true, message: '请输入重量!'},
            ]
          },
        },
        url: {
          add: "/jeewms/mdGoods/add",
          edit: "/jeewms/mdGoods/edit",
        }
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
        this.$nextTick(() => {
          this.form.setFieldsValue({
            'chlKongZhi': 'Y'
          })
        })
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'sysOrgCode','sysCompanyCode','suoShuKeHu','shpMingCheng','shpJianCheng','shpBianMa','shpXingHao','shpGuiGe','shpYanSe','chpShuXing','cfWenCeng','chlKongZhi','mpDanCeng','mpCengGao','jfShpLei','shpPinPai','shpTiaoMa','ppTuPian','bzhiQi','shlDanWei','jshDanWei','tiJiCm','zhlKg','chlShl','jtiJiBi','jmZhongBi','jjZhongBi','chcDanWei','chDanPin','kuDanPin','gaoDanPin','chZhXiang','kuZhXiang','gaoZhXiang','shpMiaoShu','zhuangTai','zhlKgm','shpBianMakh','jizhunWendu','ywMingCheng','rwMingCheng','cusName','peisongdian','recommend','ifBackpacking','beforeSnp','afterSnp','routing','dock','kuWeiShuXing','packingTag','cusId','partName','orgId','weight','volume'))
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
        this.form.setFieldsValue(pick(row,'sysOrgCode','sysCompanyCode','suoShuKeHu','shpMingCheng','shpJianCheng','shpBianMa','shpXingHao','shpGuiGe','shpYanSe','chpShuXing','cfWenCeng','chlKongZhi','mpDanCeng','mpCengGao','jfShpLei','shpPinPai','shpTiaoMa','ppTuPian','bzhiQi','shlDanWei','jshDanWei','tiJiCm','zhlKg','chlShl','jtiJiBi','jmZhongBi','jjZhongBi','chcDanWei','chDanPin','kuDanPin','gaoDanPin','chZhXiang','kuZhXiang','gaoZhXiang','shpMiaoShu','zhuangTai','zhlKgm','shpBianMakh','jizhunWendu','ywMingCheng','rwMingCheng','cusName','peisongdian','recommend','ifBackpacking','beforeSnp','afterSnp','routing','dock','kuWeiShuXing','packingTag','cusId','partName','orgId','weight','volume'))
      },


    }
  }
</script>

<style scoped>
  >>>.ant-modal-content .ant-modal-body .ant-form-item{
    display: inline-block !important;
    width: 33%;
  }
  >>>.ant-modal-content .ant-modal-body{
    max-height: 460px !important;
    overflow-y: auto;
  }
</style>