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
            <a-form-item label="货主编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['cusCode']" @change="e => cusChange(e)" :trigger-change="true" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma" placeholder="请选择货主编码" :disabled="title==$t('查看')?true:false"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-popup
                v-decorator="['goodsCode', validatorRules.goodsCode]"
                :trigger-change="true"
                org-fields="goods_code,shp_ming_cheng"
                dest-fields="goodsCode,goodsName"
                code="wv_goods_select"
                @callback="popupCallback"
                :popupParam="popupParam"
                :disabled="title==$t('查看')?true:false"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['goodsName', validatorRules.goodsName]" :disabled="true" placeholder="请输入商品名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['num', validatorRules.num]" :disabled="title==$t('查看')?true:false" placeholder="请输入数量" style="width: 100%"/>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="互斥件详细" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="wmMutualItemDetailTable.loading"
            :columns="wmMutualItemDetailTable.columns"
            :dataSource="wmMutualItemDetailTable.dataSource"
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
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { SpecificJEditableTableMixin } from '@/mixins/SpecificJEditableTableMixin'
  import { validateDuplicateValue } from '@/utils/util'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JPopup from '@/specificCompontents/JPopup'

  export default {
    name: 'WmMutualItemModal',
    mixins: [SpecificJEditableTableMixin],
    components: {
      JDictSelectTag,
      JPopup
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
        popupParam: {
          type: '',
          param: '',
        },
        paramData: {
          type: '',
          param: '',
        },
        validatorRules: {
          cusCode: {
            rules: [
              { required: true, message: '请输入货主编码!'},
            ]
          },
          goodsCode: {
            rules: [
              { required: true, message: '请输入商品编码!'},
            ]
          },
          goodsName: {
            rules: [
              { required: true, message: '请输入商品名称!'},
            ]
          },
          num: {
            rules: [
              { required: true, message: '请输入数量!'},
            ]
          },
        },
        refKeys: ['wmMutualItemDetail', ],
        tableKeys:['wmMutualItemDetail', ],
        activeKey: 'wmMutualItemDetail',
        // 互斥件详细
        wmMutualItemDetailTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '商品编码',
              key: 'goodsCode',
              type: FormTypes.popup,
              popupCode:"wv_goods_select",
              destFields:"goodsCode,goodsName,cusCode",
              orgFields:"goods_code,shp_ming_cheng,cus_code",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '商品名称',
              key: 'goodsName',
              type: FormTypes.input,
              disabled: true,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              // validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '货主编码',
              key: 'cusCode',
              type: FormTypes.input,
              disabled: true,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              // validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '数量',
              key: 'num',
              type: FormTypes.input,
              width:"200px",
              // placeholder: '请输入${title}',
              defaultValue: '1',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },

            {
              title: 'goods_code',
              key: 'goods_code',
              type:"hidden"
            },

            {
              title: 'goods_name',
              key: 'goods_name',
              type:"hidden"
            },

            {
              title: 'cus_code',
              key: 'cus_code',
              type:"hidden"
            },
          ]
        },
        url: {
          add: "/jeewms/wmMutualItem/add",
          edit: "/jeewms/wmMutualItem/edit",
          wmMutualItemDetail: {
            list: '/jeewms/wmMutualItem/queryWmMutualItemDetailByMainId'
          },
        }
      }
    },
    methods: {
      cusChange(val) {
        this.popupParam.param = val
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'cusCode','goodsCode','goodsName','num')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.wmMutualItemDetail.list, params, this.wmMutualItemDetailTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          wmMutualItemDetailList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'cusCode','goodsCode','goodsName'))
       this.form.setFieldsValue({
         'num': 1
       })
     },

    }
  }
</script>

<style scoped>
</style>