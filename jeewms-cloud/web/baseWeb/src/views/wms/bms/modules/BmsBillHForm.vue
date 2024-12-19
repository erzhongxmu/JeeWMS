<template>
   <a-spin :spinning="confirmLoading">
     <j-form-container :disabled="formDisabled">
       <!-- 主表单区域 -->
       <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
         <a-row>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('账单编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="billNo">
              <a-input v-model="model.billNo" :placeholder="$t('请输入账单编号')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('账单类型')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="billType">
              <a-input v-model="model.billType" :placeholder="$t('请输入账单类型')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('期间')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="billPeriod">
              <a-input v-model="model.billPeriod" :placeholder="$t('请输入期间')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('计费对象类型')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costObjType">
              <a-input v-model="model.costObjType" :placeholder="$t('请输入计费对象类型')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('计费对象编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costObjNo">
              <a-input v-model="model.costObjNo" :placeholder="$t('请输入计费对象编号')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('计费对象名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costObjName">
              <a-input v-model="model.costObjName" :placeholder="$t('请输入计费对象名称')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('账单金额')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="billSum">
              <a-input-number v-model="model.billSum" :placeholder="$t('请输入账单金额')" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('货币')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costHb">
              <!-- <a-input v-model="model.costHb" :placeholder="$t('请输入货币')" ></a-input> -->
              <j-dict-select-tag type="list" v-model="model.costHb" dictCode="currency_hb" :placeholder="$t('请选择货币')" />
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('备注')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remark">
              <a-input v-model="model.remark" :placeholder="$t('请输入备注')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('状态')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status">
              <!-- <a-input v-model="model.status" :placeholder="$t('请输入状态')" ></a-input> -->
              <j-dict-select-tag type="list" v-model="model.status" dictCode="bill_status" :placeholder="$t('请选择状态')" />
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-model-item :label="$t('文件')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr1">
              <!-- <a-input v-model="model.attr1" :placeholder="$t('请上传文件')" ></a-input> -->
              <j-upload v-model="model.attr1"></j-upload>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
     </j-form-container>
      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="账单详情子" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="bmsBillITable.loading"
            :columns="bmsBillITable.columns"
            :dataSource="bmsBillITable.dataSource"
            :maxHeight="300"
            :disabled="formDisabled"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
      </a-tabs>
    </a-spin>
</template>

<script>

  import { FormTypes,getRefPromise,VALIDATE_NO_PASSED } from '@/utils/JEditableTableUtil'
  import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'BmsBillHForm',
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
        validatorRules: {
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        refKeys: ['bmsBillI', ],
        tableKeys:['bmsBillI', ],
        activeKey: 'bmsBillI',
        // 账单详情子
        bmsBillITable: {
          loading: false,
          dataSource: [],
          columns: [
            // {
            //   title: '账单编号',
            //   key: 'billNo',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue:'',
            // },
        

               {
              title: '费用编号',
              key: 'costNo',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '费用名称',
              key: 'costName',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
             {
              title: '费用描述',
              key: 'costDesc',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '来源编号',
              key: 'costSoNo',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '来源名称',
              key: 'costSoName',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '来源日期',
              key: 'costSoDate',
              type: FormTypes.datetime,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '计费税率',
              key: 'costSl',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '货币',
              key: 'costHb',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '计费不含税价',
              key: 'costCoBhsj',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '计费原含税价',
              key: 'costCoYhsj',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
          ]
        },
        url: {
          add: "/bms/bmsBillH/add",
          edit: "/bms/bmsBillH/edit",
          bmsBillI: {
            list: '/bms/bmsBillH/queryBmsBillIByMainId'
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
            this.bmsBillITable.dataSource=[]
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
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.bmsBillI.list, params, this.bmsBillITable)
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
          bmsBillIList: allValues.tablesValue[0].values,
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

    }
  }
</script>

<style scoped>
</style>