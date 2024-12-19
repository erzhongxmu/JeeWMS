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
<!--        <a-form-item label="更新人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['updateName']" :placeholder="title==$t('查看')? '':'请输入更新人名称'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="更新日期" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-date :placeholder="title==$t('查看')? '':'请选择更新日期'" v-decorator="['updateTime']" :trigger-change="true" style="width: 100%" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="原始单据类型" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['orderTypeCode']" :placeholder="title==$t('查看')? '':'请输入原始单据类型'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="原始单据编码" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['orderId']" :placeholder="title==$t('查看')? '':'请输入原始单据编码'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="原始单据行项目" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['orderIdI']" :placeholder="title==$t('查看')? '':'请输入原始单据行项目'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
        <a-form-item label="商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsId']" :placeholder="title==$t('查看')? '':'请输入商品编码'" :disabled="true"></a-input>
        </a-form-item>
        <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsName']" :placeholder="title==$t('查看')? '':'请输入商品名称'" :disabled="true"></a-input>
        </a-form-item>
        <a-form-item label="数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsQua']" :placeholder="title==$t('查看')? '':'请输入数量'" :disabled="true"></a-input>
        </a-form-item>
        <a-form-item label="移动数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['baseGoodscount']" :placeholder="title==$t('查看')? '':'请输入移动数量'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
<!--        <a-form-item label="生产日期" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['goodsProData']" :placeholder="title==$t('查看')? '':'请输入生产日期'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
        <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsUnit']" :placeholder="title==$t('查看')? '':'请输入单位'" :disabled="true"></a-input>
        </a-form-item>
        <a-form-item label="货主编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['cusCode']" :trigger-change="true" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma" :placeholder="title==$t('查看')? '':'请选择货主编码'" :disabled="true"/>
        </a-form-item>
        <a-form-item label="源托盘" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['tinFrom']" :placeholder="title==$t('查看')? '':'请输入源托盘'" :disabled="true"></a-input>
        </a-form-item>
        <a-form-item label="到托盘" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['tinId']" :placeholder="title==$t('查看')? '':'请输入到托盘'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="源库位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag :placeholder="title==$t('查看')? '':'请选择源库位'" v-decorator="['binFrom']" dict="md_bin,ku_wei_ming_cheng,ku_wei_bian_ma" :disabled="true"/>
        </a-form-item>
        <a-form-item label="到库位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag :placeholder="title==$t('查看')? '':'请选择到库位'" v-decorator="['binTo']" dict="md_bin,ku_wei_ming_cheng,ku_wei_bian_ma" :disabled="title==$t('查看')?true:false" />
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
<!--          <a-input v-decorator="['moveSta']" :placeholder="title==$t('查看')? '':'请输入状态'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <j-dict-select-tag type="radio" v-decorator="['moveSta']" :trigger-change="true" dictCode="int_transfer_status" :placeholder="title==$t('查看')?'':'请选择状态'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
        <a-form-item label="转移货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['toCusCode']" :trigger-change="true" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma" :placeholder="title==$t('查看')? '':'请选择转移货主'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
<!--        <a-form-item label="基本单位" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['baseUnit']" :placeholder="title==$t('查看')? '':'请输入基本单位'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="基本单位数量" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['baseGoodscount']" :placeholder="title==$t('查看')? '':'请输入基本单位数量'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="到生产日期" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['toGoodsProData']" :placeholder="title==$t('查看')? '':'请输入到生产日期'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="执行状态" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['runSta']" :placeholder="title==$t('查看')? '':'请输入执行状态'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"


  export default {
    name: "WmToMoveGoodsModal",
    components: {
      JDate,
      JDictSelectTag,
      JSearchSelectTag,
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
          add: "/jeewms/wmToMoveGoods/add",
          edit: "/jeewms/wmToMoveGoods/edit",
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
          this.form.setFieldsValue(pick(this.model,'createName','createTime','updateName','updateTime','orderTypeCode','orderId','orderIdI','goodsId','goodsName','goodsQua','goodsProData','goodsUnit','cusCode','tinFrom','tinId','binFrom','binTo','moveSta','toCusCode','baseUnit','baseGoodscount','toGoodsProData','runSta'))
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
        this.form.setFieldsValue(pick(row,'createName','createTime','updateName','updateTime','orderTypeCode','orderId','orderIdI','goodsId','goodsName','goodsQua','goodsProData','goodsUnit','cusCode','tinFrom','tinId','binFrom','binTo','moveSta','toCusCode','baseUnit','baseGoodscount','toGoodsProData','runSta'))
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