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
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="false">
      <!-- 主表单区域 -->
      <a-form-model ref="form" :model="model" slot="detail" :rules="validatorRules">
        <a-row>
          <a-col :span="8">
            <a-form-model-item label="仓库" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
              <j-search-select-tag
                type="list"
                v-model="model.query07"
                dict="ba_store,store_code,store_name"
                :placeholder="$t('请选择')"
              />
            </a-form-model-item>
          </a-col>
            <a-col :span="8" >
            <a-form-model-item label="供应商名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query09">
               <j-popup
                  v-model="model.query09"
                  field="supplyName"
                  org-fields="gys_bian_ma,gys_jian_cheng,zhong_wen_qch"
                  dest-fields="supplyCode,supplyName,supplyNick"
                  code="md_sup"
                  :multi="false"
                  @input="popupCallback2"
                />
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8" >
            <a-form-model-item label="供应商编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
              <a-input v-model="model.query08" placeholder="请输入供应商编码" disabled></a-input>
            </a-form-model-item>
          </a-col> -->
           <a-col :span="8" >
            <a-form-model-item label="主PO" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query13">
              <a-input v-model="model.query13" placeholder="请输入主PO" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="预计到货时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query21">
              <j-date v-model="model.query21" :placeholder="$t('请选择预计到货时间')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="内部发票号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query17">
              <a-input v-model="model.query17" placeholder="请输入内部发票号"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="销售单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="link03">
                <j-popup
                  v-model="model.link03"
                  field="query04"
                  org-fields="query04"
                  dest-fields="query04"
                  code="select_om_order_code"
                  :multi="false"
                  :disabled="formDisabled"
                />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item
              :label="$t('订单类型')"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="orderTypes"
            >
              <j-dict-select-tag
                v-model="model.query18"
                dictCode="rkOrderType"
                :placeholder="$t('请选择')"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="跟单员" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query28" >
              <j-search-select-tag
                type="list"
                v-model="model.query28"
                dict="sys_user,realname,realname,del_flag = '0'"
                :placeholder="$t('请选择')"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="业务员" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query30">
              <j-search-select-tag placeholder="请选择业务员" v-model="model.query30" dict="YEWUY"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text01">
              <a-input v-model="model.text01" placeholder="请输入备注" ></a-input>
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
  </a-spin>
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
      confirmLoading: false,
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
      validatorRules: {
        query17: [{required: true, message: ' '}],
      },
      title: '推送WMS',
      width: 1200,
      formDisabled: false,
      visible: false,
      disableSubmit: false,
      id: '',
      model: {},
      refKeys: ['busiPoItem2'],
      tableKeys: ['busiPoItem2'],
      activeKey: 'busiPoItem2',
      busiPoItemTable: {
        loading: false,
        dataSource: [],
        columns: [
         {
              title: '子PO',
              key: 'query14',
              type: FormTypes.input,
              width:"150px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '商品编码',
              key: 'query10',
              type: FormTypes.popup,
              popupCode: 'wv_goods_select',
              destFields: 'query11,query10,query12',
              orgFields: 'shp_ming_cheng,goods_code,baseunit',
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '商品名称',
              key: 'query11',
              type: FormTypes.input,
              width:"200px",
              disabled:true,
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '单位',
              key: 'query12',
              type: FormTypes.input,
              width:"100px",
              disabled:true,
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '单价',
              key: 'num06',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
          {
            title: '检验类型',
            key: 'query15',
            type: FormTypes.select,
            dictCode: 'Test_type',
            width: '150px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
            {
              title: '数量',
              key: 'num01',
              type: FormTypes.inputNumber,
              width:"100px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }, ],
            },
            {
              title: '未完成数量',
              key: 'num02',
              type: FormTypes.inputNumber,
              disabled:true,
              width:"100px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
          {
            title: '已完成数量',
            key: 'num03',
            type: FormTypes.inputNumber,
            disabled: true,
            width: '100px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
            {
              title: '备注',
              key: 'text02',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
        ],
      },
    }
  },
  methods: {
    popupCallback2(value,row) {
      this.model.query08 = row.supplyCode
    },
    getAllTable() {
      let values = this.tableKeys.map((key) => getRefPromise(this, key))
      return Promise.all(values)
    },
    open(data) {
      this.model = data[0]
      this.busiPoItemTable.dataSource = data
      this.model.query18 = '入库预约'
      this.visible = true
    },
    close() {
      this.visible = false
    },
    handleOk() {
        let that = this
        this.confirmLoading = true
        
        this.getAllTable().then(allValues=>{
          allValues[0].getAll(true,'').then(res=>{
              let arr = []
              res.values.map(item=>{
                  arr.push({...that.model,...item,query01:'RKYY'})
              })
              if(!that.model.query17) return that.$message.warning("内部发票号为空")
              postAction('/jeeerp/busiPo/batchAddItem', arr).then(res => {
                if(res.success) {
                  that.$message.success(res.message)
                  that.$emit('ok')
                  that.close()
                } else{
                  that.$message.warning(res.message)
                  // this.close()
                }
                that.confirmLoading = false
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