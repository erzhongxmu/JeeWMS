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
      <a-form-model ref="form" :model="mineObj" slot="detail">
        <a-row>
          <a-col :span="8">
            <a-form-model-item label="子PO" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <a-input v-model="mineObj.query14" :placeholder="$t('请输入')" :disabled="true" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <a-input v-model="mineObj.query10" :placeholder="$t('请输入')" :disabled="true" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="总数量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <a-input v-model="mineObj.num01" :placeholder="$t('请输入')" :disabled="true" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="未完成数量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <a-input v-model="mineObj.num02" :placeholder="$t('请输入')" :disabled="true" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="已完成数量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <a-input v-model="mineObj.num03" :placeholder="$t('请输入')" :disabled="true" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <a-input v-model="mineObj.query12" :placeholder="$t('请输入')" :disabled="true" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="检验类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <a-input v-model="mineObj.query15" :placeholder="$t('请输入')" :disabled="true" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <a-input v-model="mineObj.text02" :placeholder="$t('请输入')" :disabled="true" ></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
    <j-editable-table
      :ref="refKeys[0]"
      :loading="busiPoItemTable.loading"
      :columns="busiPoItemTable.columns"
      :dataSource="busiPoItemTable.dataSource"
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

export default {
  name: 'BusiOmItemModal',
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
      title: '分批入库详情',
      width: 1000,
      formDisabled: false,
      visible: false,
      disableSubmit: false,
      id: '',
      mineObj: {},
      refKeys: ['busiPoItem2'],
      tableKeys: ['busiPoItem2'],
      activeKey: 'busiPoItem2',
      busiPoItemTable: {
        loading: false,
        dataSource: [],
        columns: [
          {
            title: '数量',
            key: 'num01',
            type: FormTypes.inputNumber,
            width: '200px',
            placeholder: '请输入${title}',
            validateRules: [{ required: true, message: '${title}不能为空' }],
          },
          {
            title: '要求交货时间',
            key: 'query21',
            type: FormTypes.date,
            width: '200px',
            placeholder: '请输入${title}',
            defaultValue: '',
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
    open(e, id,dataSource) {
      this.id = id
      this.mineObj = e.getValue()
      this.busiPoItemTable.dataSource = dataSource
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
                    arr.push({...that.mineObj,num01:'',num02:'',num03:'',...item})
                })
                this.close()
                that.$emit('ok', arr, that.id)
            })
        })
    },
  },
}
</script>

<style scoped>
</style>