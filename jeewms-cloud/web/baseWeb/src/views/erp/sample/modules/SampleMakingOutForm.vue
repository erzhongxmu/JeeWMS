<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <!-- 主表单区域 -->
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <!-- <a-col :span="8" >
            <a-form-model-item label="建单日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <a-input v-model="model.query03" placeholder="请输入建单日期" ></a-input>
            </a-form-model-item>
          </a-col> -->
          <!-- <a-col :span="8" >
            <a-form-model-item label="公司" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query05">
              <a-input v-model="model.query05" placeholder="请输入公司" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="工厂" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query06">
              <a-input v-model="model.query06" placeholder="请输入工厂" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="库存地点" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
              <a-input v-model="model.query07" placeholder="请输入库存地点" ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8" >
            <a-form-model-item label="客户编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
              <a-input v-model="model.query08" placeholder="请输入客户编码" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="客户名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query09">
              <a-input v-model="model.query09" placeholder="请输入客户名称" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query10">
              <a-input v-model="model.query10" placeholder="请输入商品编码" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query11">
              <a-input v-model="model.query11" placeholder="请输入商品名称" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="主PO" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query13">
              <a-input v-model="model.query13" placeholder="请输入主PO" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="装箱单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query17">
              <a-input v-model="model.query17" placeholder="请输入装箱单号" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="要求交货时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query21">
              <a-input v-model="model.query21" placeholder="请输入要求交货时间" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text01">
              <a-input v-model="model.text01" placeholder="请输入备注" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item label="单据附件" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr1">
              <a-input v-model="model.attr1" placeholder="请输入单据附件" ></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
      <!-- 子表单区域 -->
    <a-tabs v-model="activeKey" @change="handleChangeTabs">
      <a-tab-pane tab="出库商品信息" :key="refKeys[0]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[0]"
          :loading="busiOmItemTable.loading"
          :columns="busiOmItemTable.columns"
          :dataSource="busiOmItemTable.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :actionButton="true">
        </j-editable-table>
      </a-tab-pane>
    </a-tabs>
  </a-spin>
</template>

<script>

  import { getAction,httpAction } from '@/api/manage'
  import { FormTypes,getRefPromise,VALIDATE_NO_PASSED,validateTables } from '@/utils/JEditableTableUtil'
  import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'SampleMakingOutForm',
    mixins: [JEditableTableModelMixin],
    components: {
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
        model:{
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
        },
        inBatchesList:[],
        refKeys: ['busiOmItem', ],
        tableKeys:['busiOmItem', ],
        activeKey: 'busiOmItem',
        // busi_om_item
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
            validateRules: [{ required: true, message: '${title}不能为空' }],
          },
          {
            title: '商品名称',
            key: 'query11',
            type: FormTypes.popup,
            popupCode: 'wv_goods_select',
            destFields: 'query11,query10,query12',
            orgFields: 'shp_ming_cheng,goods_code,baseunit',
            // disabled: true,
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
            title: '数量',
            key: 'num01',
            type: FormTypes.inputNumber,
            width: '100px',
            placeholder: '请输入${title}',
            defaultValue: '',
            validateRules: [{ required: true, message: '${title}不能为空' }],
          },
          {
            title: '是否报关',
            key: 'query16',
            type: FormTypes.radio,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'0',
            options:[
              {value:'1',text:"是"},
              {value:'0',text:"否"},
            ]
          },
          {
            title: '未完成数量',
            key: 'num02',
            type: FormTypes.inputNumber,
            disabled: true,
            width: '100px',
            placeholder: '请输入${title}',
            defaultValue: '',
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
            width: '200px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
          ]
        },
        url: {
          add: "/jeeerp/busiOm/batchAdd",
          edit: "/jeeerp/busiOm/batchBusiOmItemEdit",
          queryById: "/jeeerp/busiOm/queryById",
          busiOmItem: {
            list: '/jeeerp/busiOm/queryBusiOmItemByMainId'
          },
        }
      }
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
    },
    methods: {
      addBefore(){
        this.model.id = null
        this.busiOmItemTable.dataSource=[]
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        this.$nextTick(() => {
        })
       // 加载子表数据
        if (this.model.id) {
          let params = { query04: this.model.query04,pageNo:1,pageSize:1000 }
          getAction('/jeeerp/busiOm/BatchQueryBusiOmItemByMainId',params).then(res => {
            this.busiOmItemTable.dataSource = res.result
          })
          // this.requestSubTableData(this.url.busiPoItem.list, params, this.busiPoItemTable)
        }
      },
      //校验所有一对一子表表单
      validateSubForm(allValues){
          return new Promise((resolve,reject)=>{
            Promise.all([
            ]).then(() => {
              resolve(allValues)
            }).catch(e => {
              if (e.error === VALIDATE_NO_PASSED) {
                // 如果有未通过表单验证的子表，就自动跳转到它所在的tab
                this.activeKey = e.index == null ? this.activeKey : this.refKeys[e.index]
              } else {
                console.error(e)
              }
            })
          })
      },
       /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)
        let busiOmItemList = allValues.tablesValue[0].values
        let arr = []
        busiOmItemList.map(item=>{
            arr.push({...main,...item,query01: 'CYGL'})
        })
        return arr
      },
      validateError(msg){
        this.$message.error(msg)
      },
    }
  }
</script>

<style scoped>
</style>