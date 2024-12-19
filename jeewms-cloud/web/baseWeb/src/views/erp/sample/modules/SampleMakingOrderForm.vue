<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <!-- 主表单区域 -->
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="8">
            <a-form-model-item :label="$t('仓库')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
              <j-search-select-tag
                type="list"
                v-model="model.query07"
                dict="ba_store,store_code,store_name"
                :placeholder="$t('请选择')"
              />
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8">
            <a-form-model-item :label="$t('主PO')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query13">
              <a-input v-model="model.query13" :placeholder="$t('请输入主PO')"></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8">
            <a-form-model-item :label="$t('采购人')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query16" >
              <j-search-select-tag
                type="list"
                v-model="model.query16"
                dict="sys_user,realname,realname,del_flag = '0'"
                :placeholder="$t('请选择')"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('业务类型')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query31">
              <j-dict-select-tag  v-model="model.query31" dictCode="businessType"/>
            </a-form-model-item>
          </a-col>
         <a-col :span="8">
            <a-form-model-item :label="$t('客户')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query24">
                <j-popup
                  v-model="model.query24"
                  field="query08"
                  org-fields="ke_hu_jian_cheng,ke_hu_bian_ma"
                  dest-fields="query09,query08"
                  code="md_cus"
                  :multi="false"
                />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('内部发票号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query17">
              <a-input v-model="model.query17" :placeholder="$t('请输入内部发票号')"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('销售单号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="link03">
              <a-input v-model="model.link03" :placeholder="$t('请输入销售单号')"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('制造商')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query09">
                <j-popup
                  v-model="model.query09"
                  field="supplyName"
                  org-fields="gys_bian_ma,gys_jian_cheng,zhong_wen_qch"
                  dest-fields="supplyCode,supplyName,supplyNick"
                  code="md_sup"
                  :multi="false"
                  :disabled="formDisabled"
                  @input="popupCallback2"
                />
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8">
            <a-form-model-item :label="$t('制造商编码')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
              <a-input v-model="model.query08" :placeholder="$t('请输入供应商编码')" disabled></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8" v-if="model.query25 != '是'">
            <a-form-model-item :label="$t('供应商')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query42">
                <j-popup
                  v-model="model.query42"
                  field="supplyName"
                  org-fields="gys_bian_ma,gys_jian_cheng,zhong_wen_qch"
                  dest-fields="supplyCode,supplyName,supplyNick"
                  code="md_sup2"
                  :multi="false"
                  :disabled="formDisabled"
                  @input="popupCallback3"
                />
            </a-form-model-item>
          </a-col>
           <!-- <a-col :span="8">
            <a-form-model-item :label="$t('总价')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num05" >
              <a-input v-model="model.num05" :placeholder="$t('请输入总价')" ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8">
            <a-form-model-item :label="$t('是否含税')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query25">
              <j-dict-select-tag type="radio"  v-model="model.query25" :placeholder="$t('请选择是否含税')" dictCode="if_yes_no"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" v-if="model.query25 == '是'">
            <a-form-model-item :label="$t('税率%')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num10">
              <j-search-select-tag :placeholder="$t('请选择税率')" v-model="model.num10" dict="taxRate" @change="num10Input"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('总金额')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num08">
              <a-input v-model="model.num08" :placeholder="$t('请输入总金额')" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8">
            <a-form-model-item :label="$t('尾款')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num11">
              <a-input v-model="model.num11" :placeholder="$t('请输入尾款')" disabled></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8"  v-if="model.query25 == '是'">
            <a-form-model-item :label="$t('含税总金额')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num09">
              <a-input v-model="model.num09" :placeholder="$t('请输入含税总金额')" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8"  v-if="model.query25 == '是'">
            <a-form-model-item :label="$t('尾款')(含税)" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num12">
              <a-input v-model="model.num12" :placeholder="$t('请输入尾款')(含税)" disabled></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8">
            <a-form-model-item :label="$t('币种')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query22">
              <j-search-select-tag type="select" v-model="model.query22" dict="currency"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('预计到货时间')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query21">
              <j-date v-model="model.query21" :placeholder="$t('请选择预计到货时间')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('是否退样品费')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query27">
              <j-dict-select-tag type="radio"  v-model="model.query27" :placeholder="$t('请选择是否退样品费')" dictCode="if_yes_no"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="8"  v-if="model.query27 == '是'">
            <a-form-model-item :label="$t('可退金额')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num16">
              <a-input v-model="model.num16" :placeholder="$t('请输入可退金额')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('业务员')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query30">
              <j-search-select-tag :placeholder="$t('请选择业务员')" v-model="model.query30" dict="YEWUY"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('INV')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query36">
              <a-input v-model="model.query36" :placeholder="$t('请输入INV')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('备注')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text01">
              <a-input v-model="model.text01" :placeholder="$t('请输入备注')"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('Google Folder') " :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query38">
              <a-input v-model="model.query38" :placeholder="$t('请输入') "></a-input>
            </a-form-model-item>
          </a-col>
          
          <!-- <a-col :span="8">
            <a-form-model-item :label="$t('单据附件')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr1">
              <j-upload v-model="model.attr1" :trigger-change="true" :disabled="title==$t('查看')?true:false"></j-upload>
            </a-form-model-item>
          </a-col> -->
        </a-row>
      </a-form-model>
    </j-form-container>
    <!-- 子表单区域 -->
    <a-tabs v-model="activeKey" @change="handleChangeTabs">
      <a-tab-pane :tab="$t('样品信息')" :key="refKeys[0]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[0]"
          :loading="sampleMakingOrderTable.loading"
          :columns="sampleMakingOrderTable.columns"
          :dataSource="sampleMakingOrderTable.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :actionButton="true"
          @valueChange="valueChange"
          @deleted="valueDeleted"
        >
        </j-editable-table>
      </a-tab-pane>
    </a-tabs>
  </a-spin>
</template>

<script>
import { getAction,httpAction } from '@/api/manage'
import { FormTypes, getRefPromise, VALIDATE_NO_PASSED,validateTables } from '@/utils/JEditableTableUtil'
import { getCurrentTime } from '@/utils/util'
import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'
import { validateDuplicateValue,accDiv,accMul,addd } from '@/utils/util'
import JEditableTable from '../../../../components/jeecg/JEditableTable.vue'

export default {
  name: 'SampleMakingOrderForm',
  mixins: [JEditableTableModelMixin],
  components: {
    JEditableTable,
  },
  data() {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 },
      },
      labelCol2: {
        xs: { span: 24 },
        sm: { span: 3 },
      },
      wrapperCol2: {
        xs: { span: 24 },
        sm: { span: 20 },
      },
      model: {},
      // 新增时子表默认添加几行空数据
      addDefaultRowNum: 1,
      validatorRules: {
        query05: [{required: true, message: ' '}],
        link03: [{required: true, message: ' '}],
        query16: [{ required: true, message: ' ' }],
        query31: [{ required: true, message: ' ' }],
        query07: [{ required: true, message: ' ' }],
        query06: [{ required: true, message: ' ' }],
        query03: [{ required: true, message: ' ' }],
        query09: [{ required: true, message: ' ' }],
        query42: [{ required: true, message: ' ' }],
        query08: [{ required: true, message: ' ' }],
        query23: [{ required: true, message: ' ' }],
        query27: [{ required: true, message: ' ' }],
        query30: [{ required: true, message: ' ' }],
        query24: [{ required: true, message: ' ' }],
        query25: [{ required: true, message: ' ' }],
        query21: [{ required: true, message: ' ' }],
        query21: [{ required: true, message: ' ' }],
        query22: [{ required: true, message: ' ' }],
        num08: [{ required: true, message: ' ' }],
        num09: [{ required: true, message: ' ' }],
        num10: [{ required: true, message: ' ' }],
        num16: [{ required: true, message: ' ' }],
      },
      inBatchesList:[],
      refKeys: ['busiPoItem'],
      tableKeys: ['busiPoItem'],
      activeKey: 'busiPoItem',
      // busi_po_item
      sampleMakingOrderTable: {
        loading: false,
        dataSource: [],
        columns: [
          {
            title: this.$t('子PO'),
            key: 'query14',
            type: FormTypes.input,
            width: '150px',
            placeholder: '请输入${title}',
            disabled: true,
            defaultValue: '',
          },
           {
            title: this.$t('商品名称'),
            key: 'query11',
            type: FormTypes.popup,
            popupCode: 'wv_goods_select',
            destFields: 'query11,query10,query12,query32,query33',
            orgFields: 'shp_ming_cheng,goods_code,baseunit,model,classification',
            width: '200px',
            placeholder: '请输入${title}',
            validateRules: [{ required: true, message: '${title}不能为空' }],
            defaultValue: '',
          },
          {
            title: this.$t('商品编码'),
            key: 'query10',
            type: FormTypes.input,
            width: '200px',
            placeholder: '请输入${title}',
            disabled: true,
            defaultValue: '',
            validateRules: [{ required: true, message: '${title}不能为空' }],
          },
          {
            title: this.$t('数量'),
            key: 'num01',
            type: FormTypes.inputNumber,
            width: '100px',
            placeholder: '请输入${title}',
            // disabled: true,
            defaultValue: '',
            validateRules: [{ required: true, message: '${title}不能为空' }],
          },
          // {
          //   title: this.$t('未完成数量'),
          //   key: 'num02',
          //   type: FormTypes.inputNumber,
          //   disabled: true,
          //   width: '100px',
          //   placeholder: '请输入${title}',
          //   defaultValue: '',
          // },
          // {
          //   title: this.$t('已完成数量'),
          //   key: 'num03',
          //   type: FormTypes.inputNumber,
          //   disabled: true,
          //   width: '100px',
          //   placeholder: '请输入${title}',
          //   defaultValue: '',
          // },
          {
            title: this.$t('单位'),
            key: 'query12',
            type: FormTypes.input,
            width: '100px',
            placeholder: '请输入${title}',
            disabled: true,
            defaultValue: '',
            validateRules: [{ required: true, message: '${title}不能为空' }],
          },
            {
              title: this.$t('单价'),
              key: 'num04',
              type: FormTypes.inputNumber,
              disabled: true,
              width: '100px',
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: this.$t('含税单价'),
              key: 'num06',
              type: FormTypes.inputNumber,
              width: '100px',
              placeholder: '请输入${title}',
              validateRules: [{ required: true, message: '${title}不能为空' }],
              defaultValue: '',
            },
            {
              title: this.$t('总价'),
              key: 'num05',
              type: FormTypes.input,
              width: '200px',
              disabled: true,
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: this.$t('含税总价'),
              key: 'num07',
              type: FormTypes.input,
              width: '200px',
              disabled: true,
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          {
            title: this.$t('检验类型'),
            key: 'query15',
            type: FormTypes.select,
            dictCode: 'Test_type',
            width: '150px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
          {
            title: this.$t('分类编码'),
            key: 'query32',
            type: FormTypes.input,
              disabled: true,
            width: '200px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
          {
            title: this.$t('分类名称'),
            key: 'query33',
            type: FormTypes.input,
              disabled: true,
            width: '200px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
          {
            title: this.$t('状态'),
            key: 'query02',
            type: FormTypes.input,
              disabled: true,
            width: '200px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
          {
            title: this.$t('备注'),
            key: 'text02',
            type: FormTypes.input,
            width: '200px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
        ],
      },
      url: {
        add: '/jeeerp/busiPo/batchYPAdd',
        edit: '/jeeerp/busiPo/batchEdit',
        queryById: '/jeeerp/busiPo/queryById',
        busiPoItem: {
          list: '/jeeerp/busiPo/queryBusiPoItemByMainId',
        },
      },
    }
  },
  props: {
    //表单禁用
    disabled: {
      type: Boolean,
      default: false,
      required: false,
    },
  },
  computed: {
    formDisabled() {
      return this.disabled
    },
  },
  created() {},
  methods: {
    valueDeleted(e){
      this.num10Input()
    },
    num10Input(){
      this.getAllTable().then(tables => {
        tables[0].getAll(false).then((tables)=>{
            let arr = []
            tables.values.map((item,idnex)=>{
              arr.push({row:item})
            })
            arr.map(item=>{
              this.valueChange(item)
            })
        })
      })
      
    },
    valueChange(e){
      let that = this
      if(e.row.num01 && e.row.num06){
        this.getAllTable().then(tables => {
          // 税率
          let num10 = that.model.num10?Number(this.model.num10)/100:''
          // 含税单价
          let num04 = accDiv(Number(e.row.num06),(Number(num10) + 1 )) 
          let num05 = accMul(e.row.num01,num04)
          let num07 = accMul(e.row.num01,e.row.num06)
          num05 = num05 + ''
          num07 = num07+ ''
          tables[0].setValues([
            {
              rowKey: e.row.id, // 行的id
              values:{
                num05:that.setNum(num05), // 金额
                num04,
                num07:that.setNum(num07) // 含税金额
              }
            }
          ])
          tables[0].getAll(false).then((v)=>{
            let num08 = 0
            let num09 = 0
            let num11 = 0
            let num12 = 0
            v.values.map((item,idnex)=>{
              // 总金额
              num08 = addd(num08,Number(item.num05) ) 
              // 含税总金额
              num09  = addd(num09,Number(item.num07) ) 
              // 尾款
              // num11 += Number(item.num05) 
              // // 尾款(含税)
              // num12 += Number(item.num07)
            })
            num08 = num08 + ''
            num09 = num09 + ''
            that.$nextTick(()=>{
              num08 = that.setNum(num08)
              num09 = that.setNum(num09)
              that.model = {...that.model,num08,num09,num11,num12}
            })
          })
        })
      }
    },
    setNum(e){
        let w = e.split(".")
        let num = e
        if(w[1]){
          if(w[1].length == 1){
            num = e + '0'
          }else if(w[1].length == 2){
             num = e
          }else{
            num =  e.substr(0,e.indexOf(".")+3)
          }
        }else{
          num = e + '.00'
        }
        return num 
    },
    popupCallback1(value,row) {
      this.model.query05 = row.company
      this.model.query06 = row.factory
    },
    popupCallback2(value,row) {
      this.model.query08 = row.supplyCode
    },
    popupCallback3(value,row) {
      this.model.query41 = row.supplyCode
    },
    // 分批详情保存
    BusiPoItemModalOK(e, id) {
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
        this.$refs.BusiPoItemModal.open(e, e.rowId,arr)
      }).catch(e => {})
    },
    addBefore() {
      this.model.id = null
      this.sampleMakingOrderTable.dataSource = []
    },
    getAllTable() {
      let values = this.tableKeys.map((key) => getRefPromise(this, key))
      return Promise.all(values)
    },
    /** 调用完edit()方法之后会自动调用此方法 */
    editAfter() {
      this.$nextTick(() => {})
      // 加载子表数据
      if (this.model.id) {
        let params = { query04: this.model.query04,pageNo:1,pageSize:1000}
        this.sampleMakingOrderTable.dataSource = []
        getAction('/jeeerp/busiPo/list',params).then(res => {
          this.sampleMakingOrderTable.dataSource = res.result.records
        }) 
      }else{
        // 新增
        this.model.query03 = getCurrentTime()
        this.model.query07 = "Office"
        this.model.query25 = "否"
        this.model.query27 = "否"
        this.model.num08 = ''
        this.model.num09 = ''
        this.model.query21 = ""
      }
    },
    //校验所有一对一子表表单
    validateSubForm(allValues) {
      if(allValues.tablesValue[0].values.length == 0){
        this.$emit("setconfirmLoading")
        return this.$message.warning(this.$t('子表为空'))
      }
      return new Promise((resolve, reject) => {
        Promise.all([])
          .then(() => {
            resolve(allValues)
          })
          .catch((e) => {
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
      if(main.query25 == '是'){
        main.query41 = main.query08
        main.query42 = main.query09
      }
      let list = allValues.tablesValue[0].values
      console.log(list);
      let dateFormat = []
      list.map((item) => {
        let arr = this.inBatchesDetail(item.id)
        dateFormat.push({
            ...main, 
            ...item,
            num16:main.query27 == '是'?main.num16:'',
            query01:'YP',
            query02:item.query02?item.query02:'计划中'
        })
      }) 
      console.log(dateFormat);
      return dateFormat
    },
    validateError(msg) {
      this.$message.error(msg)
    },
  },
}
</script>

<style scoped>
</style>