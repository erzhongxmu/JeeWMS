<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <!-- 主表单区域 -->
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <!-- <a-col :span="8" >
            <a-form-model-item label="单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query04">
              <a-input v-model="model.query04" placeholder="请输入单号" ></a-input>
            </a-form-model-item>
          </a-col> -->
            <!-- <a-col :span="8">
            <a-form-model-item label="建单日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <j-date v-model="model.query03" :placeholder="$t('请选择')" />
            </a-form-model-item>
          </a-col> -->
          <!-- <a-col :span="8">
            <a-form-model-item label="库存地点" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
               <j-popup
                  v-model="model.query07"
                  field="storage"
                  org-fields="query01,query02,query03"
                  dest-fields="company,factory,storage"
                  code="organization"
                  :multi="false"
                  :disabled="formDisabled"
                  @input="popupCallback1"
                />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="公司" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query05" >
              <a-input v-model="model.query05" placeholder="请输入公司" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="工厂" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query06" >
              <a-input v-model="model.query06" placeholder="请输入工厂" disabled></a-input>
            </a-form-model-item>
          </a-col> -->
           <a-col :span="8">
            <a-form-model-item label="客户编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
                <j-popup
                  v-model="model.query08"
                  field="query08"
                  org-fields="ke_hu_jian_cheng,ke_hu_bian_ma"
                  dest-fields="query09,query08"
                  code="md_cus"
                  :multi="false"
                  @input="popupCallback2"
                />
              <!-- <a-input v-model="model.query08" placeholder="请输入客户编码" disabled></a-input> -->
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="客户名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query09">
              <a-input v-model="model.query09" placeholder="请输入客户名称" disabled></a-input>
            </a-form-model-item>
          </a-col>
           <!-- <a-col :span="8">
            <a-form-model-item label="总价" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num05" >
              <a-input v-model="model.num05" placeholder="请输入总价" ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8">
            <a-form-model-item label="主PO" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query13">
              <a-input v-model="model.query13" placeholder="请输入主PO"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="要求交货时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query21">
              <j-date v-model="model.query21" :placeholder="$t('请选择预计到货时间')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="装箱单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query17">
              <a-input v-model="model.query17" placeholder="请输入装箱单号"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="出货方式" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query18">
              <j-dict-select-tag  v-model="model.query18" dictCode="shipmentWay"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="出货地址" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query19">
              <j-dict-select-tag  v-model="model.query19" dictCode="shipmentAddress"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text01">
              <a-input v-model="model.text01" placeholder="请输入备注"></a-input>
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
          :actionButton="true"
          @valueChange="valueChange"
          >
          <template v-slot:action="props">
            <a @click="handleDetails(props)">{{ $t('分批出库') }}</a>
          </template>
        </j-editable-table>
      </a-tab-pane>
    </a-tabs>
    <BusiOmItemModal ref="BusiOmItemModal" @ok="BusiOmItemModalOK" />
  </a-spin>
</template>

<script>

  import { getAction,httpAction } from '@/api/manage'
  import { FormTypes,getRefPromise,VALIDATE_NO_PASSED,validateTables } from '@/utils/JEditableTableUtil'
  import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'
  import { validateDuplicateValue } from '@/utils/util'
  import JEditableTable from '../../../../components/jeecg/JEditableTable.vue'
  import BusiOmItemModal from './BusiOmItemModal'
  import { getCurrentTime } from '@/utils/util'

  export default {
    name: 'BusiOmForm',
    mixins: [JEditableTableModelMixin],
    components: {
      JEditableTable,
      BusiOmItemModal
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
          query05: [{required: true, message: ' '}],
          query16: [{ required: true, message: ' ' }],
          query07: [{ required: true, message: ' ' }],
          query06: [{ required: true, message: ' ' }],
          query03: [{ required: true, message: ' ' }],
          query09: [{ required: true, message: ' ' }],
          query13: [{ required: true, message: ' ' }],
          query08: [{ required: true, message: ' ' }],
          query17: [{ required: true, message: ' ' }],
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
          },
          {
            title: '商品名称',
            key: 'query11',
            type: FormTypes.popup,
            popupCode: 'wv_goods_select',
            destFields: 'query11,query10,query12',
            orgFields: 'shp_ming_cheng,goods_code,baseunit',
            // disabled: true,
            validateRules: [{ required: true, message: '${title}不能为空' }],
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
            title: '未完成数量',
            key: 'num02',
            type: FormTypes.inputNumber,
            width: '100px',
            placeholder: '请输入${title}',
            disabled: true,
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
          // {
          //   title: '单价',
          //   key: 'num04',
          //   type: FormTypes.inputNumber,
          //   width: '100px',
          //   placeholder: '请输入${title}',
          //   defaultValue: '',
          //   validateRules: [{ required: true, message: '${title}不能为空' }],
          // },
          // {
          //   title: '总价',
          //   key: 'num05',
          //   type: FormTypes.input,
          //   width: '100px',
          //   placeholder: '请输入${title}',
          //   defaultValue: '',
          //   disabled: true,
          // },
          
          {
            title: '是否报关',
            key: 'query16',
            type: FormTypes.radio,
            width:"150px",
            placeholder: '请输入${title}',
            defaultValue:'0',
            options:[
              {value:'1',text:"是"},
              {value:'0',text:"否"},
            ]
          },
          // {
          //   title: '未完成数量',
          //   key: 'num02',
          //   type: FormTypes.inputNumber,
          //   disabled: true,
          //   width: '100px',
          //   placeholder: '请输入${title}',
          //   defaultValue: '',
          // },
          // {
          //   title: '已完成数量',
          //   key: 'num03',
          //   type: FormTypes.inputNumber,
          //   disabled: true,
          //   width: '100px',
          //   placeholder: '请输入${title}',
          //   defaultValue: '',
          // },
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
          edit: "/jeeerp/busiOm/batchEdit",
          queryById: "/jeeerp/busiOm/queryById",
          busiOmItem: {
            list: '/jeeerp/busiOm/queryBusiOmItemByMainId'
          },
        },
        copyData: []
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
      getCopyData(orderId) {
        let params = { query04: orderId, pageNo: 1, pageSize: 1000 }
        getAction('/jeeerp/busiOm/list', params).then((res) => {
          this.copyData = res.result.records
          this.copyData.map((item,index)=>{
            item.query04 = null
            item.id = null
            item.createBy = null
            item.createName = null
            item.createTime = null
            item.query23 = null
            item.updateBy = null
            item.updateName = null
            item.updateTime = null
            item.query03 = getCurrentTime()
          })
          if (this.copyData.length) {
            this.model = this.copyData[0]
            this.busiOmItemTable.dataSource = this.copyData
          } else {
            this.model = {}
            this.busiOmItemTable.dataSource = []
          }
        })
      },
      valueChange(e){
        if(e.row.num01 && e.row.num04){
          this.getAllTable().then(tables => {
            tables[0].setValues([
              {
                rowKey: e.row.id, // 行的id
                values:{
                  num05:Number(e.row.num01) * Number(e.row.num04)
                }
              }
            ])
          })
        }
      },
      popupCallback1(value,row) {
        this.model.query05 = row.company
        this.model.query06 = row.factory
      },
      popupCallback2(value,row) {
        console.log(row,687678678687)
        this.model.query09 = row.query09
      },
      // 分批详情保存
      BusiOmItemModalOK(e, id) {
        let i = -1
        this.inBatchesList.map((item,index)=>{
          if(item.id == id){
            i =  index
            this.inBatchesList.splice(index,1,{id:id,list:e})
          }
        })
        if(i == -1){
          this.inBatchesList.push({id:id,list:e})
        }
      },
      // 通过ID获取分批数组中属于这个id的数据
      inBatchesDetail(id){
          let arr = []
          this.inBatchesList.map(item=>{
            if(item.id == id){
              arr = item.list
            }
          })
          return arr
      },
      // 点击分批详情
      handleDetails(e) {
        this.getAllTable().then(tables => {
          return validateTables(tables)
        }).then(allValues => {
          let arr = this.inBatchesDetail(e.rowId)
          this.$refs.BusiOmItemModal.open(e, e.rowId,arr)
        }).catch(e => {})
      },
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
        let params = { query04: this.model.query04,pageNo:1,pageSize:1000}
        getAction('/jeeerp/busiOm/list',params).then(res => {
          this.busiOmItemTable.dataSource = res.result.records
        }) 
      }else{
        // 新增
        this.model.query03 = getCurrentTime()
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
        let list = allValues.tablesValue[0].values
        console.log(list,4334343);
        
        let dateFormat = []
        list.map((item) => {
          // let arr = this.inBatchesDetail(item.id)
          dateFormat.push({
              ...main, 
              ...item, 
              query01:'XSD'
          })
        }) 
        console.log(dateFormat)
        return dateFormat
      },
      validateError(msg){
        this.$message.error(msg)
      },

    }
  }
</script>

<style scoped>
</style>