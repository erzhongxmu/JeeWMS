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

          <a-col :xs="24" :sm="12">
            <a-form-item label="报损理由" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['reason', validatorRules.reason]" placeholder="请输入报损理由"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="报损类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['type', validatorRules.type]" :trigger-change="true" dictCode="loss_type" placeholder="请选择报损类型"/>
            </a-form-item>
          </a-col>
<!--          <a-col :xs="24" :sm="12">-->
<!--            <a-form-item label="仓库" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--              <j-dict-select-tag type="list" v-decorator="['wareId']" :trigger-change="true" dictCode="ba_store,store_name,id" placeholder="请选择仓库"/>-->
<!--            </a-form-item>-->
<!--          </a-col>-->
          <a-col :xs="24" :sm="12">
            <a-form-item label="库位代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['kwCode', validatorRules.kwCode]" @change="e => kwChange(e)" :trigger-change="true" dictCode="md_bin,ku_wei_ming_cheng,ku_wei_bian_ma" placeholder="请选择库位代码"/>
<!--              <j-popup-normal-->
<!--                v-decorator="['kwCode', validatorRules.kwCode]"-->
<!--                :trigger-change="true"-->
<!--                org-fields="ku_wei_bian_ma"-->
<!--                dest-fields="kwCode"-->
<!--                code="loss_bin_select"-->
<!--                :multi="false"-->
<!--                @callback="popupCallback"/>-->
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="备注" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-textarea v-decorator="['remark']" rows="4" placeholder="请输入备注"/>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="报损详细" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="wmLossDetailTable.loading"
            :columns="wmLossDetailTable.columns"
            :dataSource="wmLossDetailTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
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
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { SpecificJEditableTableMixin } from '@/mixins/SpecificJEditableTableMixin'
  import { validateDuplicateValue } from '@/utils/util'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: 'WmLossModal',
    mixins: [SpecificJEditableTableMixin],
    components: {
      JDictSelectTag
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
        paramData: {
          type: 'bs',
          param: '',
        },
        addDefaultRowNum: 1,
        validatorRules: {
          reason: {
            rules: [
              { required: true, message: '请输入报损理由!'},
            ]
          },
          type: {
            rules: [
              { required: true, message: '请选择报损类型!'},
            ]
          },
          kwCode: {
            rules: [
              { required: true, message: '请选择库位!'},
            ]
          },
        },
        refKeys: ['wmLossDetail', ],
        tableKeys:['wmLossDetail', ],
        activeKey: 'wmLossDetail',
        // 报损详细
        wmLossDetailTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '商品编码',
              key: 'goodsCode',
              type: FormTypes.popup,
              popupCode:"loss_goods_select",
              destFields:"goodsCode,mater_name",
              orgFields:"goods_code,shp_ming_cheng",
              width:"200px",
              defaultValue: '',
            },
            {
              title: '商品名称',
              key: 'materName',
              type: FormTypes.input,
              width:"200px",
              defaultValue: '',
            },
            {
              title: '报损数量',
              key: 'num',
              type: FormTypes.input,
              width:"200px",
              defaultValue: '',
            },
            {
              title: '处理方式',
              key: 'result',
              type: FormTypes.input,
              width:"200px",
              defaultValue: '',
            },

            {
              title: 'mater_code',
              key: 'mater_code',
              type:"hidden"
            },

            {
              title: 'mater_name',
              key: 'mater_name',
              type:"hidden"
            },
          ]
        },
        url: {
          add: "/jeewms/wmLoss/add",
          edit: "/jeewms/wmLoss/edit",
          wmLossDetail: {
            list: '/jeewms/wmLoss/queryWmLossDetailByMainId'
          },
        }
      }
    },
    methods: {
      kwChange(val) {
        this.paramData.param = val
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'reason','type','userName','thTime','wareId','kwCode','kwName','remark','approName','approTime','status','approRemark')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.wmLossDetail.list, params, this.wmLossDetailTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          wmLossDetailList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'reason','type','userName','thTime','wareId','kwCode','kwName','remark','approName','approTime','status','approRemark'))
     },

    }
  }
</script>

<style scoped>
</style>