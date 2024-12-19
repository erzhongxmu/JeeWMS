<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <!-- 主表单区域 -->
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
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
          <a-col :span="8" >
            <a-form-model-item label="供应商编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
              <a-input v-model="model.query08" placeholder="请输入供应商编码" disabled></a-input>
            </a-form-model-item>
          </a-col>
           <!-- <a-col :span="8" >
            <a-form-model-item label="主PO" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query13">
              <a-input v-model="model.query13" placeholder="请输入主PO" ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8">
            <a-form-model-item label="采购人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-search-select-tag
                type="list"
                v-model="model.query16"
                dict="sys_user,realname,realname,del_flag = '0'"
                :placeholder="$t('请选择')"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="客户" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query24">
                <j-popup
                  v-model="model.query24"
                  field="query08"
                  org-fields="ke_hu_jian_cheng,ke_hu_bian_ma"
                  dest-fields="query09,query08"
                  code="md_cus"
                  :multi="false"
                  @input="popupCallback2"
                />
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
              <a-input v-model="model.link03" placeholder="请输入销售单号"></a-input>
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
                :disabled="title == $t('查看') ? true : false"
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
          <!-- <a-col :span="8" >
            <a-form-model-item label="单据附件" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr1">
              <j-upload v-model="model.attr1" :trigger-change="true" :disabled="title==$t('查看')?true:false"></j-upload>
            </a-form-model-item>
          </a-col> -->
        </a-row>
      </a-form-model>
    </j-form-container>
      <!-- 子表单区域 -->
    <a-tabs v-model="activeKey" @change="handleChangeTabs">
      <a-tab-pane tab="采购商品信息" :key="refKeys[0]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[0]"
          :loading="busiPoItemTable.loading"
          :columns="busiPoItemTable.columns"
          :dataSource="busiPoItemTable.dataSource"
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

  import { getAction } from '@/api/manage'
  import { FormTypes,getRefPromise,VALIDATE_NO_PASSED } from '@/utils/JEditableTableUtil'
  import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'
  import { validateDuplicateValue,getCurrentTime } from '@/utils/util'

  export default {
    name: 'BusiPoOrderForm',
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
          query05: [{required: true, message: ' '}],
          query16: [{ required: true, message: ' ' }],
          query07: [{ required: true, message: ' ' }],
          query06: [{ required: true, message: ' ' }],
          query03: [{ required: true, message: ' ' }],
          query09: [{ required: true, message: ' ' }],
          query13: [{ required: true, message: ' ' }],
          query08: [{ required: true, message: ' ' }],
          query17: [{ required: true, message: ' ' }],
          query18: [{ required: true, message: ' ' }],
          link03: [{ required: true, message: ' ' }],
        },
        refKeys: ['busiPoItem', ],
        tableKeys:['busiPoItem', ],
        activeKey: 'busiPoItem',
        // busi_po_item
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
              disabled:true,
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
            // {
            //   title: '主PO',
            //   key: 'query13',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue:'',
            // },
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
          ]
        },
        url: {
          add: "/jeeerp/busiPo/batchAddItem",
          edit: "/jeeerp/busiPo/batchBusiPoItemEdit",
          queryById: "/jeeerp/busiPo/queryById",
          busiPoItem: {
            list: '/jeeerp/busiPo/queryBusiPoItemByMainId'
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
      popupCallback1(value,row) {
        this.model.query05 = row.company
        this.model.query06 = row.factory
      },
      popupCallback2(value,row) {
        this.model.query08 = row.supplyCode
      },
      popupCallback3(value,row) {
        this.model.query10 = row.goodsCode
      },
      addBefore(){
        this.busiPoItemTable.dataSource=[]
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
          getAction('/jeeerp/busiPo/BatchQueryBusiPoItemByMainId',params).then(res => {
            this.busiPoItemTable.dataSource = res.result
          })
          // this.requestSubTableData(this.url.busiPoItem.list, params, this.busiPoItemTable)
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
        let busiPoItemList = allValues.tablesValue[0].values
        let arr = []
        busiPoItemList.map(item=>{
            arr.push({...main,...item, query01: 'RKYY', link01: this.model.query04?'采购订单':null,})
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