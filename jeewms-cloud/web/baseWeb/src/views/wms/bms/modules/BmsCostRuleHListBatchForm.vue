<template>
   <a-spin :spinning="confirmLoading">
     <j-form-container :disabled="formDisabled">
       <!-- 主表单区域 -->
       <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
         <a-row>
          <!-- <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('合同编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="contNo">
              <a-input v-model="model.contNo" :placeholder="$t('请输入合同编号')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('合同名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="contName">
              <a-input v-model="model.contName" :placeholder="$t('请输入合同名称')" ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('规则编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costRuleNo">
              <a-input v-model="model.costRuleNo" :placeholder="$t('请输入规则编号')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('规则名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costRuleName">
              <a-input v-model="model.costRuleName" :placeholder="$t('请输入规则名称')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('费用编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costNo">
              <j-popup
                v-model="model.costNo"
                field="costNo"
                org-fields="cost_no,cost_name"
                dest-fields="costNo,costName"
                code="bms_cost"
                :multi="false"
                @input="popupCallback"
                :placeholder="$t('请选择')"
                />
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('费用名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costName">
              <a-input v-model="model.costName" :placeholder="$t('请输入费用名称')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('来源类型编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costSNo">
              <j-popup
                v-model="model.costSNo"
                field="costSNo"
                org-fields="cost_s_no,cost_s_name"
                dest-fields="costSNo,costSName"
                code="bms_cs_type"
                :multi="false"
                @input="popupCallback"
                :placeholder="$t('请选择')"
                />
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('来源类型名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costSName">
              <a-input v-model="model.costSName" :placeholder="$t('请输入来源类型名称')" ></a-input>
            </a-form-model-item>
          </a-col>
           <a-col :xs="24" :sm="12">
            <a-form-item :label="$t('计费对象类型')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costObjType">
              <j-dict-select-tag disabled @change="costObjTypeChange" v-model="model.costObjType" type="list" :triggerChange="true" dictCode="object_Type" :placeholder="$t('请选择计费对象类型')" /> 
            </a-form-item>
          </a-col>
            <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('计费对象编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costObjNo">
              <j-popup
                  v-model="model.costObjNo"
                  field="costObjNo"
                  :org-fields="orgFields"
                  dest-fields="costObjNo,costObjName"
                  :code="tableCode"
                  :multi="false"
                  @input="popupCallback"
                  :placeholder="$t('请选择')"
                  />
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('计费对象名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costObjName">
              <a-input v-model="model.costObjName" :placeholder="$t('请输入计费对象名称')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('最低收费')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costMin">
              <a-input-number v-model="model.costMin" :placeholder="$t('请输入最低收费')" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('最高收费')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costMax">
              <a-input-number v-model="model.costMax" :placeholder="$t('请输入最高收费')" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <!-- <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('计费周期')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costPeriod">
              <a-input v-model="model.costPeriod" :placeholder="$t('请输入计费周期')" ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('有效开始时间')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costValidBegin">
              <j-date :placeholder="$t('请选择有效开始时间')" v-model="model.costValidBegin" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('有效结束时间')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costValidEnd">
              <j-date :placeholder="$t('请选择有效结束时间')" v-model="model.costValidEnd" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
     </j-form-container>
      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane :tab="$t('计费规则子')" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="bmsCostRuleITable.loading"
            :columns="bmsCostRuleITable.columns"
            :dataSource="bmsCostRuleITable.dataSource"
            :maxHeight="300"
            :disabled="formDisabled"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"
            />
        </a-tab-pane>
      </a-tabs>
    </a-spin>
</template>

<script>

  import { FormTypes,getRefPromise,VALIDATE_NO_PASSED } from '@/utils/JEditableTableUtil'
  import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'
  import { validateDuplicateValue } from '@/utils/util'
  import { getAction,postAction,getOperateList} from '@/api/manage'
  // import Linkage from '@/components/linkage/Linkage'

  export default {
    name: 'BmsCostRuleHListBatchForm',
    mixins: [JEditableTableModelMixin],
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
          costObjType:undefined
        },
        orgFields:'contract_no,cus_zh_name',
        tableCode:'base_customer',
        validatorRules: {
           contNo: [
              { required: true, message: '请输入合同编号!'},
           ],
           contName: [
              { required: true, message: '请输入合同名称!'},
           ],
           costRuleNo: [
              { required: true, message: '请输入规则编号!'},
           ],
           costRuleName: [
              { required: true, message: '请输入规则名称!'},
           ],
           costNo: [
              { required: true, message: '请输入费用编号!'},
           ],
           costName: [
              { required: true, message: '请输入费用名称!'},
           ],
           costSNo: [
              { required: true, message: '请输入来源类型编号!'},
           ],
           costSName: [
              { required: true, message: '请输入来源类型名称!'},
           ],
           costObjType: [
              { required: true, message: '请输入计费对象类型!'},
           ],
           costObjNo: [
              { required: true, message: '请输入计费对象编号!'},
           ],
           costObjName: [
              { required: true, message: '请输入计费对象名称!'},
           ],
           costMin: [
              { required: true, message: '请输入最低收费!'},
           ],
           costMax: [
              { required: true, message: '请输入最高收费!'},
           ],
           costPeriod: [
              { required: true, message: '请输入计费周期!'},
           ],
           costValidBegin: [
              { required: true, message: '请输入有效开始时间!'},
           ],
           costValidEnd: [
              { required: true, message: '请输入有效结束时间!'},
           ],
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        refKeys: ['bmsCostRuleI', ],
        tableKeys:['bmsCostRuleI', ],
        activeKey: 'bmsCostRuleI',
        // 计费规则子
        bmsCostRuleITable: {
          loading: false,
          dataSource: [],
          columns: [
            // {
            //   title: this.$t('合同编号'),
            //   key: 'contNo',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue:'',
            //   validateRules: [{ required: true, message: '${title}不能为空' }],
            // },
            // {
            //   title: this.$t('行项目号'),
            //   key: 'itemNo',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue:'',
            //   validateRules: [{ required: true, message: '${title}不能为空' }],
            // },
            {
              title: this.$t('计费类型'),
              key: 'costTypeNo',
              type: FormTypes.popup,
              popupCode:"bms_cost_type",
              destFields:"costTypeNo,costTypeName,unit",
              orgFields:"cost_type_no,cost_type_name,cost_type_unit",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: this.$t('类型名称'),
              key: 'costTypeName',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: this.$t('开始数量'),
              key: 'beginSum',
              type: FormTypes.input,
              width:"100px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: this.$t('结束数量'),
              key: 'endSum',
              type: FormTypes.input,
              width:"100px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: this.$t('单位'),
              key: 'unit',
              type: FormTypes.input,
              width:"100px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: this.$t('不含税价'),
              key: 'costUnit',
              type: FormTypes.input,
              width:"100px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: this.$t('税率'),
              key: 'costSl',
              type: FormTypes.input,
              width:"100px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: this.$t('含税价'),
              key: 'costHsj',
              type: FormTypes.input,
              width:"100px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: this.$t('原含税价'),
              key: 'costYhsj',
              type: FormTypes.input,
              width:"100px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: this.$t('区间开始数量'),
              key: 'qjbeginSum',
              type: FormTypes.input,
              width:"150px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: this.$t('区间结束数量'),
              key: 'qjendSum',
              type: FormTypes.input,
              width:"150px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: this.$t('单位数量'),
              key: 'unitnumber',
              type: FormTypes.input,
              width:"100px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: this.$t('区间价格'),
              key: 'qjPrice',
              type: FormTypes.input,
              width:"100px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: this.$t('货币'),
              key: 'costHb',
              type: FormTypes.select,
              dictCode: 'currency_hb',
              width:"100px",
              placeholder: '请选择${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },


            // {
            //   title: this.$t('costTypeName'),
            //   key: 'costTypeName',
            //   type:"hidden"
            // },
          ]
        },
        url: {
          add: "/bms/bmsCostRuleH/add",
          edit: "/bms/bmsCostRuleH/edit",
          getCusList: "/base/baseCustomer/list",
          getSupList: "/base/baseSupply/list",
          bmsCostRuleI: {
            list: '/bms/bmsCostRuleH/queryBmsCostRuleIByMainId'
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
      },
      type:{
        type:String,
        default:false,
        required:true,
      }
    },
    watch:{
      type(n,v){
        console.log(n)
        this.costObjTypeChange(n)
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
            this.bmsCostRuleITable.dataSource=[]
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        this.$nextTick(() => {
        })
        this.costObjTypeChange(this.model.costObjType)
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.contNo }
          this.requestSubTableData(this.url.bmsCostRuleI.list, params, this.bmsCostRuleITable)
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

        return {
          ...main, // 展开
          bmsCostRuleIList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     close() {
        this.visible = false
        this.$emit('close')
        this.$refs.form.clearValidate();
      },
      costObjTypeChange(val){
        this.model.costObjType = val
        console.log('调用了',val)
        if(val == 'sup'){
          console.log('进来了')
          this.orgFields = 'supply_no,supply_zh_name'
          this.tableCode = 'base_supply'
        }else{
          this.orgFields = 'contract_no,cus_zh_name'
          this.tableCode = 'base_customer'
        }
      },
     popupCallback(value,row){
        this.model = Object.assign(this.model, row);
        console.log(this.model,'我是model',row)
     },

    }
  }
</script>

<style scoped>
</style>