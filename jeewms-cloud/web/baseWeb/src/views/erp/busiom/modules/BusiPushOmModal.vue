<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :maskClosable="false"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class: { 'jee-hidden': disableSubmit } }"
    @cancel="close"
  >
    <j-form-container :disabled="true">
      <!-- 主表单区域 -->
      <a-form-model ref="form" :model="model" slot="detail">
        <a-row>
          <a-col :span="8" >
            <a-form-model-item label="单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query04">
              <a-input v-model="model.query04" placeholder="请输入单号" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="供应商名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query09">
              <a-input v-model="model.query09" placeholder="请输入供应商名称" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="供应商编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
              <a-input v-model="model.query08" placeholder="请输入供应商编码"  ></a-input>
            </a-form-model-item>
          </a-col>
           <!-- <a-col :span="8" >
            <a-form-model-item label="库存地点" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
              <a-input v-model="model.query07" placeholder="请输入库存地点" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="公司" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query05">
              <a-input v-model="model.query05" placeholder="请输入公司" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="工厂" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query06">
              <a-input v-model="model.query06" placeholder="请输入工厂" ></a-input>
            </a-form-model-item>
          </a-col> -->
           <!-- <a-col :span="8" >
            <a-form-model-item label="单据类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query01">
              <a-input v-model="model.query01" placeholder="请输入单据类型" ></a-input>
            </a-form-model-item>
          </a-col> -->
          <!-- <a-col :span="8" >
            <a-form-model-item label="单据状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query02">
              <a-input v-model="model.query02" placeholder="请输入单据状态" ></a-input>
            </a-form-model-item>
          </a-col> -->
           <!-- <a-col :span="8" >
            <a-form-model-item label="建单日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <j-date v-model="model.query03" :placeholder="$t('请选择')" />
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8" >
            <a-form-model-item label="要求交货时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query21">
              <j-date v-model="model.query21" :placeholder="$t('请选择')" />
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
    <j-editable-table
      :ref="refKeys[0]"
      :loading="busiOmItemTable.loading"
      :columns="busiOmItemTable.columns"
      :dataSource="busiOmItemTable.dataSource"
      :maxHeight="300"
      :disabled="formDisabled"
      :rowNumber="true"
      :rowSelection="true"
      :actionButton="true"
    />
  </j-modal>
</template>

<script>
import { FormTypes, getRefPromise, VALIDATE_NO_PASSED } from '@/utils/JEditableTableUtil'
import { getAction,httpAction, postAction } from '@/api/manage'

export default {
  name: 'BusiPoPushModal',
  components: {},
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
      title: '推送WMS',
      width: 1200,
      formDisabled: false,
      visible: false,
      disableSubmit: false,
      id: '',
      model: {},
      refKeys: ['busiOmItem2'],
      tableKeys: ['busiOmItem2'],
      activeKey: 'busiOmItem2',
      busiOmItemTable: {
        loading: false,
        dataSource: [],
        columns: [
          {
            title: '子PO',
            key: 'query14',
            type: FormTypes.input,
            width: '150px',
            placeholder: '请输入${title}',
            defaultValue: '',
            disabled: true,
          },
          {
            title: '商品名称',
            key: 'query11',
            type: FormTypes.popup,
            popupCode: 'wv_goods_select',
            destFields: 'query11,query10,query12',
            orgFields: 'shp_ming_cheng,goods_code,baseunit',
            disabled: true,
            width: '200px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
          {
            title: '商品编码',
            key: 'query10',
            type: FormTypes.input,
            width: '200px',
            placeholder: '请输入${title}',
            disabled: true,
            defaultValue: '',
            validateRules: [{ required: true, message: '${title}不能为空' }],
          },
          {
            title: '出库数量',
            key: 'num01',
            type: FormTypes.inputNumber,
            width: '200px',
            placeholder: '请输入${title}',
            validateRules: [{ required: true, message: '${title}不能为空' }],
          },
          {
            title: '单位',
            key: 'query12',
            type: FormTypes.input,
            width: '100px',
            placeholder: '请输入${title}',
            disabled: true,
            defaultValue: '',
            validateRules: [{ required: true, message: '${title}不能为空' }],
          },
          {
            title: '备注',
            key: 'text02',
            type: FormTypes.input,
            width: '200px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
        ],
      },
    }
  },
  methods: {
    getAllTable() {
      let values = this.tableKeys.map((key) => getRefPromise(this, key))
      return Promise.all(values)
    },
    open(data) {
      this.model = data[0]
      this.busiOmItemTable.dataSource = data
      this.visible = true
    },
    close() {
      this.visible = false
    },
    handleOk() {
        let that = this
        this.getAllTable().then(allValues=>{
          allValues[0].getAll(true,'').then(res=>{
              let arr = []
              res.values.map(item=>{
                  arr.push({...that.model,...item,query01:'CKYY'})
              })
              postAction('/jeeerp/busiOm/batchAddItem', arr).then(res => {
                if(res.success) {
                  this.$message.success(res.message)
                  this.close()
                  this.$emit('ok')
                } else{
                  this.$message.warning(res.message)
                  // this.close()
                }
              })
              // console.log(arr,'99999999')
          })
        })
    },
  },
}
</script>

<style scoped>
</style>