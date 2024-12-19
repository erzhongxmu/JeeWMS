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

          <a-col :xs="24" :sm="8">
            <a-form-item label="货主编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-search-select-tag @change="e => cusChange(e)" type="list" v-decorator="['cusCode', validatorRules.cusCode]" :trigger-change="true" dict="md_cus,zhong_wen_qch,ke_hu_bian_ma" :placeholder="title==$t('查看')?'':'请选择货主编码'" :disabled="title==$t('查看')?true:false"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="8">
            <a-form-item label="订单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['orderType']" :trigger-change="true" dictCode="im_order_type" :placeholder="title==$t('查看')?'':'请选择订单类型'" :disabled="title==$t('查看')?true:false"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="8">
            <a-form-item label="预计到货时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date :placeholder="title==$t('查看')?'':'请选择预计到货时间'" v-decorator="['imData', validatorRules.imData]" :trigger-change="true" style="width: 100%" :disabled="title==$t('查看')?true:false"/>
            </a-form-item>
          </a-col>
<!--          <a-col :xs="24" :sm="8">-->
<!--            <a-form-item label="订单号" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['imCusCode']" :placeholder="title==$t('查看')?'':'请输入订单号'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--            </a-form-item>-->
<!--          </a-col>-->
          <a-col :xs="24" :sm="8">
            <a-form-item label="司机" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['imCarDri']" :placeholder="title==$t('查看')?'':'请输入司机'" :disabled="title==$t('查看')?true:false"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="8">
            <a-form-item label="司机电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['imCarMobile']" :placeholder="title==$t('查看')?'':'请输入司机电话'" :disabled="title==$t('查看')?true:false"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="8">
            <a-form-item label="车号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['imCarNo']" :placeholder="title==$t('查看')?'':'请输入车号'" :disabled="title==$t('查看')?true:false"></a-input>
            </a-form-item>
          </a-col>
<!--          <a-col :xs="24" :sm="8" v-if="title==$t('查看')">-->
<!--            <a-form-item label="订单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['orderTypeCode_dictText']" :disabled="true"/>-->
<!--            </a-form-item>-->
<!--          </a-col>-->
<!--          <a-col :xs="24" :sm="8" v-if="title==$t('查看')">-->
<!--            <a-form-item label="单据状态" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['imSta']" :disabled="true"></a-input>-->
<!--            </a-form-item>-->
<!--          </a-col>-->
<!--          <a-col :xs="24" :sm="8" v-if="title==$t('查看')">-->
<!--            <a-form-item label="进货通知单号" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['noticeId']" :disabled="true"></a-input>-->
<!--            </a-form-item>-->
<!--          </a-col>-->
          <a-col :xs="24" :sm="8">
            <a-form-item label="月台" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['platformCode']" :trigger-change="true" dictCode="ba_platform,platform_name,platform_code" :placeholder="title==$t('查看')?'':'请选择月台'" :disabled="title==$t('查看')?true:false"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="8">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['imBeizhu']" :placeholder="title==$t('查看')?'':'请输入备注'" :disabled="title==$t('查看')?true:false"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="8">
            <a-form-item label="附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-upload v-decorator="['fuJian']" :trigger-change="true" :disabled="title==$t('查看')?true:false"></j-upload>
            </a-form-item>
          </a-col>
<!--          <a-col :xs="24" :sm="8">-->
<!--            <a-form-item label="货主编码" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <j-dict-select-tag type="list" v-decorator="['supCode']" :trigger-change="true" dictCode="md_sup,zhong_wen_qch,gys_bian_ma" :placeholder="title==$t('查看')?'':'请选择货主编码'" :disabled="title==$t('查看')?true:false"/>-->
<!--            </a-form-item>-->
<!--          </a-col>-->
<!--          <a-col :xs="24" :sm="8" v-if="title==$t('查看')">-->
<!--            <a-form-item label="单据状态" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <a-input v-decorator="['imSta']" :disabled="true"></a-input>-->
<!--            </a-form-item>-->
<!--          </a-col>-->

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="商品" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="wmImNoticeITable.loading"
            :columns="wmImNoticeITable.columns"
            :dataSource="wmImNoticeITable.dataSource"
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
  import JSearchSelectTag from "@/components/dict/JSearchSelectTag"
  import { VALIDATE_NO_PASSED, getRefPromise, validateFormAndTables } from '@/utils/JEditableTableUtil'

  export default {
    name: 'WmImNoticeHModal',
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
          sm: { span: 6 },
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
        addDefaultRowNum: 1,
        paramData: {
          type: '',
          param: '',
        },
        validatorRules: {
          cusCode: {
            rules: [
              { required: true, message: '请选择货主编码!'},
            ]
          },
          imData: {
            rules: [
              { required: true, message: '请选择预计到货时间!'},
            ]
          },
        },
        refKeys: ['wmImNoticeI', ],
        tableKeys:['wmImNoticeI', ],
        activeKey: 'wmImNoticeI',
        // 商品
        wmImNoticeITable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '商品编码',
              key: 'goodsCode',
              type: FormTypes.popup,
              popupCode:"wv_goods_select",
              destFields:"goodsCode",
              orgFields:"goods_code",
              width:"200px",
              defaultValue: '',
            },
            {
              title: '数量',
              key: 'goodsCount',
              type: FormTypes.input,
              width:"200px",
              defaultValue: '',
            },
            {
              title: '计划库位',
              key: 'binPlan',
              type: FormTypes.input,
              width:"200px",
              defaultValue: '',
            },
            {
              title: '备注',
              key: 'imBeizhu',
              type: FormTypes.input,
              width:"200px",
              defaultValue: '',
            },

            {
              title: 'goods_code',
              key: 'goods_code',
              type:"hidden"
            },
          ]
        },
        url: {
          add: "/jeewms/wmImNoticeH/add",
          edit: "/jeewms/wmImNoticeH/edit",
          wmImNoticeI: {
            list: '/jeewms/wmImNoticeH/queryWmImNoticeIByMainId'
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
      add() {
        if (typeof this.addBefore === 'function') this.addBefore()
        // 默认新增空数据
        let rowNum = this.addDefaultRowNum
        if (typeof rowNum !== 'number') {
          rowNum = 1
          console.warn('由于你没有在 data 中定义 addDefaultRowNum 或 addDefaultRowNum 不是数字，所以默认添加一条空数据，如果不想默认添加空数据，请将定义 addDefaultRowNum 为 0')
        }
        this.eachAllTable((item) => {
          item.add(rowNum)
        })
        if (typeof this.addAfter === 'function') this.addAfter(this.model)
        this.edit({})
        this.$nextTick(() => {
          this.form.setFieldsValue({
            'orderType': '0'
          })
        })
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'createBy','createTime','cusCode','imData','imCusCode','imCarDri','imCarMobile','imCarNo','orderTypeCode','orderType','platformCode','imBeizhu','imSta','noticeId','fuJian','supCode','supName')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.noticeId) {
          let params = { id: this.model.noticeId }
          this.requestSubTableData(this.url.wmImNoticeI.list, params, this.wmImNoticeITable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)
        return {
          ...main, // 展开
          wmImNoticeIList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'createBy','createTime','cusCode','imData','imCusCode','imCarDri','imCarMobile','imCarNo','orderTypeCode','platformCode','imBeizhu','imSta','noticeId','fuJian','supCode','supName'))
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
  >>>.ant-modal-content .ant-modal-body{
    max-height: 500px !important;
    overflow-y: auto;
  }
  >>>.ant-input-affix-wrapper .ant-input-suffix{
    display: none !important;
  }
</style>