<template>
  <j-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>
<!--            <a-form-item label="创建人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['createName']" :placeholder="title==$t('查看')? '':'请输入创建人名称'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--            </a-form-item>-->
<!--            <a-form-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <j-date :placeholder="title==$t('查看')? '':'请选择创建日期'" v-decorator="['createTime']" :trigger-change="true" style="width: 100%" :disabled="title==$t('查看')?true:false"/>-->
<!--            </a-form-item>-->
<!--            <a-form-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['sysOrgCode']" :placeholder="title==$t('查看')? '':'请输入所属部门'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--            </a-form-item>-->
<!--            <a-form-item label="所属公司" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <j-dict-select-tag type="list" v-decorator="['sysCompanyCode']" :trigger-change="true" dictCode="ba_com,com_zh_aname,com_code" :placeholder="title==$t('查看')? '':'请选择所属公司'" :disabled="title==$t('查看')?true:false"/>-->
<!--            </a-form-item>-->
            <a-form-item label="货主编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-search-select-tag @change="e => cusChange(e)" type="list" v-decorator="['cusCode', validatorRules.cusCode]" :trigger-change="true" dict="md_cus,zhong_wen_qch,ke_hu_bian_ma" :placeholder="title==$t('查看')? '':'请选择货主'" :disabled="title==$t('查看')?true:false"/>
            </a-form-item>
            <a-form-item label="要求交货时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date :placeholder="title==$t('查看')? '':'请选择要求交货时间'" v-decorator="['delvData', validatorRules.delvData]" :trigger-change="true" style="width: 100%" :disabled="title==$t('查看')?true:false"/>
            </a-form-item>
          <a-form-item label="出库订单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['omNoticeId', validatorRules.omNoticeId]" :placeholder="title==$t('查看')? '':'请输入订单号'" :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="订单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-dict-select-tag type="list" v-decorator="['orderTypeCode']" :trigger-change="true" dictCode="out_type" :placeholder="title==$t('查看')?'':'请选择出库类型'" :disabled="title==$t('查看')?true:false"/>
          </a-form-item>
<!--            <a-form-item label="收货人" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['delvMember']" :placeholder="title==$t('查看')? '':'请输入收货人'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--            </a-form-item>-->
<!--            <a-form-item label="收货人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['delvMobile']" :placeholder="title==$t('查看')? '':'请输入收货人电话'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--            </a-form-item>-->
<!--            <a-form-item label="收货人地址" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['delvAddr']" :placeholder="title==$t('查看')? '':'请输入收货人地址'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--            </a-form-item>-->
<!--            <a-form-item label="承运人" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['reMember']" :placeholder="title==$t('查看')? '':'请输入承运人'" :disabled="title==$t('查看')?true:false"/>-->
<!--            </a-form-item>-->
<!--            <a-form-item label="承运人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['reMobile']" :placeholder="title==$t('查看')? '':'请输入承运人电话'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--            </a-form-item>-->
<!--            <a-form-item label="承运人车号" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['reCarno']" :placeholder="title==$t('查看')? '':'请输入承运人车号'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--            </a-form-item>-->
<!--            <a-form-item label="发货月台" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <j-dict-select-tag type="list" v-decorator="['omPlatNo']" :trigger-change="true" dictCode="ba_platform,platform_name,platform_code" :placeholder="title==$t('查看')? '':'请选择发货月台'" :disabled="title==$t('查看')?true:false"/>-->
<!--            </a-form-item>-->
<!--          <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol" v-if="title==$t('查看')">-->
<!--            <a-input v-decorator="['omSta']" :disabled="true"></a-input>-->
<!--          </a-form-item>-->
<!--          <a-form-item label="打印状态" :labelCol="labelCol" :wrapperCol="wrapperCol" v-if="title==$t('查看')">-->
<!--            <a-input v-decorator="['printStatus']" :disabled="true"></a-input>-->
<!--          </a-form-item>-->
<!--          <a-form-item label="出货单号" :labelCol="labelCol" :wrapperCol="wrapperCol" v-if="title==$t('查看')">-->
<!--            <a-input v-decorator="['omNoticeId']" :disabled="true"></a-input>-->
<!--          </a-form-item>-->
<!--          <a-form-item label="订单号" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--            <a-input v-decorator="['imCusCode']" :placeholder="title==$t('查看')? '':'请输入订单号'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--          </a-form-item>-->
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['omBeizhu']" :placeholder="title==$t('查看')? '':'请输入备注'" :disabled="title==$t('查看')?true:false"></a-input>
            </a-form-item>
<!--            <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['omSta']" :placeholder="title==$t('查看')? '':'请输入状态'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--            </a-form-item>-->
<!--            <a-form-item label="出货单号" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['omNoticeId']" :placeholder="title==$t('查看')? '':'请输入出货单号'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--            </a-form-item>-->
            <a-form-item label="附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-upload v-decorator="['fuJian']" :trigger-change="true" :disabled="title==$t('查看')?true:false"></j-upload>
            </a-form-item>
<!--            <a-form-item label="订单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['orderTypeCode']" :placeholder="title==$t('查看')? '':'请输入订单类型'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--            </a-form-item>-->
<!--            <a-form-item label="三方货主" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <j-search-select-tag v-decorator="['ocusCode']" dict="md_cus_other,zhong_wen_qch,ke_hu_bian_ma" :placeholder="title==$t('查看')? '':'请选择'" :disabled="title==$t('查看')?true:false"/>-->
<!--            </a-form-item>-->
<!--            <a-form-item label="三方货主名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['ocusName']" :placeholder="title==$t('查看')? '':'请输入三方货主名称'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--            </a-form-item>-->
<!--            <a-form-item label="打印状态" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['printStatus']" :placeholder="title==$t('查看')? '':'请输入打印状态'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--            </a-form-item>-->
<!--            <a-form-item label="对接单据类型" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['piClass']" :placeholder="title==$t('查看')? '':'请输入对接单据类型'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--            </a-form-item>-->
<!--            <a-form-item label="账套" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['piMaster']" :placeholder="title==$t('查看')? '':'请输入账套'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--            </a-form-item>-->
        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="出货详情" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="wmOmNoticeITable.loading"
            :columns="wmOmNoticeITable.columns"
            :dataSource="wmOmNoticeITable.dataSource"
            :maxHeight="300"
            :disabled="title==$t('查看')?true:false"
            :rowSelection="title==$t('查看')?false:true"
            :actionButton="title==$t('查看')?false:true"
            :paramData="paramData"/>
        </a-tab-pane>

      </a-tabs>

    </a-spin>
  </j-modal>
</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes } from '@/utils/JEditableTableUtil'
  import { SpecificJEditableTableMixin } from '@/mixins/SpecificJEditableTableMixin'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'
  import JUpload from '@/components/jeecg/JUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import { VALIDATE_NO_PASSED, getRefPromise, validateFormAndTables } from '@/utils/JEditableTableUtil'

  export default {
    name: 'WmOmNoticeHModal',
    mixins: [SpecificJEditableTableMixin],
    components: {
      JDate,
      JUpload,
      JDictSelectTag,
      JSearchSelectTag,
    },
    data() {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 7 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        labelCol2: {
          xs: { span: 24 },
          sm: { span: 3 },
        },
        wrapperCol2: {
          xs: { span: 24 },
          sm: { span: 20 },
        },
        // 新增时子表默认添加几行空数据
        paramData: {
          type: '',
          param: '',
        },
        addDefaultRowNum: 1,
        validatorRules: {
          cusCode: {
            rules: [
              { required: true, message: '请选择货主编码!'},
            ]
          },
          delvData: {
            rules: [
              { required: true, message: '请选择预计到货时间!'},
            ]
          },
          omNoticeId: {
            rules: [
              { required: true, message: '请输入出库订单号!'},
            ]
          },
        },
        refKeys: ['wmOmNoticeI', ],
        tableKeys:['wmOmNoticeI', ],
        activeKey: 'wmOmNoticeI',
        // 出货详情
        wmOmNoticeITable: {
          loading: false,
          dataSource: [],
          columns: [
            // {
            //   title: '创建人名称',
            //   key: 'createName',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '创建日期',
            //   key: 'createTime',
            //   type: FormTypes.date,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '所属公司',
            //   key: 'sysCompanyCode',
            //   type: FormTypes.select,
            //   dictCode:"ba_com,com_zh_aname,com_code",
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '出货通知',
            //   key: 'omNoticeId',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '出货商品',
            //   key: 'goodsId',
            //   type: FormTypes.popup,
            //   popupCode:"wv_goods_select",
            //   destFields:"goodsId,goodsUnit",
            //   orgFields:"goods_code,baseunit",
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            {
              title: '商品编码',
              key: 'goodsId',
              type: FormTypes.popup,
              popupCode:"wv_goods_select",
              destFields:"goodsId,goodsUnit",
              orgFields:"goods_code,baseunit",
              width:"200px",
              defaultValue: '',
            },
            {
              title: '出货数量',
              key: 'goodsQua',
              type: FormTypes.input,
              width:"200px",
              defaultValue: '',
            },
            // {
            //   title: '出货单位',
            //   key: 'goodsUnit',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '生产日期',
            //   key: 'goodsProData',
            //   type: FormTypes.date,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '批次',
            //   key: 'goodsBatch',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '出货仓位',
            //   key: 'binOm',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '已出货数量',
            //   key: 'goodsQuaok',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '预约出货时间',
            //   key: 'delvData',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '货主',
            //   key: 'cusCode',
            //   type: FormTypes.select,
            //   dictCode:"md_cus,zhong_wen_qch,ke_hu_bian_ma",
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '商品名称',
            //   key: 'goodsText',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '波次号',
            //   key: 'waveId',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '状态',
            //   key: 'omSta',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '基本单位',
            //   key: 'baseUnit',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '基本单位数量',
            //   key: 'baseGoodscount',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '下架计划生成',
            //   key: 'planSta',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '商品名称',
            //   key: 'goodsName',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '其他系统ID',
            //   key: 'otherId',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '订单号',
            //   key: 'imCusCode',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            {
              title: '备注',
              key: 'omBeiZhu',
              type: FormTypes.input,
              width:"200px",
              defaultValue: '',
            },
            // {
            //   title: '产品属性',
            //   key: 'chpShuXing',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            // {
            //   title: '商品条码',
            //   key: 'barcode',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },


          ]
        },
        url: {
          add: "/jeewms/wmOmNoticeH/add",
          edit: "/jeewms/wmOmNoticeH/edit",
          wmOmNoticeI: {
            list: '/jeewms/wmOmNoticeH/queryWmOmNoticeIByMainId'
          },
        }
      }
    },
    methods: {
      cusChange(val) {
        this.paramData.param = val
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'createName','createTime','sysOrgCode','sysCompanyCode','cusCode','delvData','delvMember','delvMobile','delvAddr','reMember','reMobile','reCarno','omPlatNo','omBeizhu','omSta','omNoticeId','fuJian','orderTypeCode','ocusCode','ocusName','imCusCode','printStatus','piClass','piMaster')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.omNoticeId) {
          let params = { id: this.model.omNoticeId }
          this.requestSubTableData(this.url.wmOmNoticeI.list, params, this.wmOmNoticeITable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          wmOmNoticeIList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'createName','createTime','sysOrgCode','sysCompanyCode','cusCode','delvData','delvMember','delvMobile','delvAddr','reMember','reMobile','reCarno','omPlatNo','omBeizhu','omSta','omNoticeId','fuJian','orderTypeCode','ocusCode','ocusName','imCusCode','printStatus','piClass','piMaster'))
     },
      /** 确定按钮点击事件 */
      handleOk() {
        /** 触发表单验证 */
        if(this.title == this.$t('查看')) {
          this.close();
          return
        }
        this.getAllTable().then(tables => {
          /** 一次性验证主表和所有的次表 */
          return validateFormAndTables(this.form, tables)
        }).then(allValues => {
          if (typeof this.classifyIntoFormData !== 'function') {
            throw this.throwNotFunction('classifyIntoFormData')
          }
          let formData = this.classifyIntoFormData(allValues)
          formData.orderTypeCode = '19'
          // 发起请求
          return this.request(formData)
        }).catch(e => {
          if (e.error === VALIDATE_NO_PASSED) {
            // 如果有未通过表单验证的子表，就自动跳转到它所在的tab
            this.activeKey = e.index == null ? this.activeKey : this.refKeys[e.index]
          } else {
            console.error(e)
          }
        })
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
    max-height: 700px !important;
    overflow-y: auto;
  }
  >>>.ant-input-affix-wrapper .ant-input-suffix{
    display: none !important;
  }
</style>