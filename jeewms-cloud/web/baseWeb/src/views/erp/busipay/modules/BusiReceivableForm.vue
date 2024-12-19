<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <!-- <a-col :span="8">
            <a-form-model-item label="创建人名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="createName">
              <a-input v-model="model.createName" placeholder="请输入创建人名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="更新人名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="updateName">
              <a-input v-model="model.updateName" placeholder="请输入更新人名称"  ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8">
            <a-form-model-item label="PAYMENT LIST" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query19">
              <a-input v-model="model.query19" placeholder="请输入PAYMENT LIST" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="单据类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query01">
              <a-input v-model="model.query01" placeholder="请输入单据类型"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="单据状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query02">
              <a-input v-model="model.query02" placeholder="请输入单据状态"  ></a-input>
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8">
            <a-form-model-item label="建单日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <j-date placeholder="请选择建单日期" v-model="model.query03"  style="width: 100%" />
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8">
            <a-form-model-item label="单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query04">
              <a-input v-model="model.query04" placeholder="请输入单号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="公司" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query05">
              <a-input v-model="model.query05" placeholder="请输入公司"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="工厂" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query06">
              <a-input v-model="model.query06" placeholder="请输入工厂"  ></a-input>
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8">
            <a-form-model-item label="库存地点" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
              <a-input v-model="model.query07" placeholder="请输入库存地点"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="对象编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
              <a-input v-model="model.query08" placeholder="请输入对象编码"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="对象名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query09">
              <a-input v-model="model.query09" placeholder="请输入对象名称"  ></a-input>
            </a-form-model-item>
          </a-col> -->
          <!-- <a-col :span="8">
            <a-form-model-item label="商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query10">
              <a-input v-model="model.query10" placeholder="请输入商品编码"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query11">
              <a-input v-model="model.query11" placeholder="请输入商品名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query12">
              <a-input v-model="model.query12" placeholder="请输入单位"  ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8">
            <a-form-model-item label="主PO" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query13">
              <a-input v-model="model.query13" placeholder="请输入主PO"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="税率%" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num10">
              <a-input v-model="model.num10" placeholder="请输入税率" @input="num10Input"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="总金额" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num08">
              <a-input v-model="model.num08" placeholder="请输入总金额" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="含税总金额" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num09">
              <a-input v-model="model.num09" placeholder="请输入含税总金额" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="币种" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query22">
              <j-dict-select-tag  v-model="model.query22" dictCode="currency"/>
            </a-form-model-item>
          </a-col>
         
          <!-- <a-col :span="8">
            <a-form-model-item label="子PO" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query14">
              <a-input v-model="model.query14" placeholder="请输入子PO"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query15" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query15">
              <a-input v-model="model.query15" placeholder="请输入query15"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query16" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query16">
              <a-input v-model="model.query16" placeholder="请输入query16"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query17" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query17">
              <a-input v-model="model.query17" placeholder="请输入query17"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query18" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query18">
              <a-input v-model="model.query18" placeholder="请输入query18"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query19" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query19">
              <a-input v-model="model.query19" placeholder="请输入query19"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query20" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query20">
              <a-input v-model="model.query20" placeholder="请输入query20"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query21" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query21">
              <a-input v-model="model.query21" placeholder="请输入query21"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query22" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query22">
              <a-input v-model="model.query22" placeholder="请输入query22"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query23" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query23">
              <a-input v-model="model.query23" placeholder="请输入query23"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query24" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query24">
              <a-input v-model="model.query24" placeholder="请输入query24"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query25" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query25">
              <a-input v-model="model.query25" placeholder="请输入query25"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query26" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query26">
              <a-input v-model="model.query26" placeholder="请输入query26"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query27" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query27">
              <a-input v-model="model.query27" placeholder="请输入query27"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query28" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query28">
              <a-input v-model="model.query28" placeholder="请输入query28"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query29" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query29">
              <a-input v-model="model.query29" placeholder="请输入query29"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query30" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query30">
              <a-input v-model="model.query30" placeholder="请输入query30"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="数量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num01">
              <a-input-number v-model="model.num01" placeholder="请输入数量" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="未完成数量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num02">
              <a-input-number v-model="model.num02" placeholder="请输入未完成数量" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="已完成数量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num03">
              <a-input-number v-model="model.num03" placeholder="请输入已完成数量" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="num04" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num04">
              <a-input-number v-model="model.num04" placeholder="请输入num04" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="num05" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num05">
              <a-input-number v-model="model.num05" placeholder="请输入num05" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="关联单据类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="link01">
              <a-input v-model="model.link01" placeholder="请输入关联单据类型"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="关联单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="link02">
              <a-input v-model="model.link02" placeholder="请输入关联单号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="link03" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="link03">
              <a-input v-model="model.link03" placeholder="请输入link03"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="link04" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="link04">
              <a-input v-model="model.link04" placeholder="请输入link04"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="link05" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="link05">
              <a-input v-model="model.link05" placeholder="请输入link05"  ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text01">
              <a-input v-model="model.text01" placeholder="请输入备注"  ></a-input>
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text02">
              <a-input v-model="model.text02" placeholder="请输入备注"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="text03" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text03">
              <a-input v-model="model.text03" placeholder="请输入text03"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="text04" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text04">
              <a-input v-model="model.text04" placeholder="请输入text04"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="text05" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text05">
              <a-input v-model="model.text05" placeholder="请输入text05"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="单据附件" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr1">
              <a-input v-model="model.attr1" placeholder="请输入单据附件"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="attr2" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr2">
              <a-input v-model="model.attr2" placeholder="请输入attr2"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="attr3" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr3">
              <a-input v-model="model.attr3" placeholder="请输入attr3"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="attr4" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr4">
              <a-input v-model="model.attr4" placeholder="请输入attr4"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="attr5" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr5">
              <a-input v-model="model.attr5" placeholder="请输入attr5"  ></a-input>
            </a-form-model-item>
          </a-col> -->
        </a-row>
      </a-form-model>
    </j-form-container>

    <!-- 子表单区域 -->
    <a-tabs v-model="activeKey" @change="handleChangeTabs">
      <a-tab-pane tab="应收商品信息" :key="refKeys[0]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[0]"
          :loading="busiReceivableItem.loading"
          :columns="busiReceivableItem.columns"
          :dataSource="busiReceivableItem.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :actionButton="true"
          @valueChange="valueChange"/>
      </a-tab-pane>
    </a-tabs>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'
  import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'
  import { FormTypes,getRefPromise,VALIDATE_NO_PASSED } from '@/utils/JEditableTableUtil'

  export default {
    name: 'BusiReceivableForm',
    mixins: [JEditableTableModelMixin],
    components: {
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        model:{
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        refKeys: ['busiReceivableItem', ],
        tableKeys:['busiReceivableItem', ],
        activeKey: 'busiReceivableItem',
        // busi_po_item
        busiReceivableItem: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '子PO',
              key: 'query14',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '商品名称',
              key: 'query11',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '商品编码',
              key: 'query10',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '单位',
              key: 'query12',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '数量',
              key: 'num01',
              type: FormTypes.input,
              width: '200px',
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '单价',
              key: 'num04',
              type: FormTypes.input,
              width: '200px',
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '单价',
              key: 'num04',
              type: FormTypes.inputNumber,
              width: '100px',
              placeholder: '请输入${title}',
              validateRules: [{ required: true, message: '${title}不能为空' }],
              defaultValue: '',
            },
            {
              title: '含税单价',
              key: 'num06',
              type: FormTypes.input,
              disabled: true,
              width: '100px',
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '总价',
              key: 'num05',
              type: FormTypes.input,
              width: '200px',
              disabled: true,
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '含税总价',
              key: 'num07',
              type: FormTypes.input,
              width: '200px',
              disabled: true,
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ],
        },
        url: {
          add: "/jeeerp/busiPaymentReceived/batchAdd",
          edit: "/jeeerp/busiPaymentReceived/batchEdit",
          queryById: "/jeeerp/busiPaymentReceived/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
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
      if(e.row.num01 && e.row.num04){
        this.getAllTable().then(tables => {
          // 税率
          let num10 = that.model.num10?Number(this.model.num10)/100:''
          // 含税单价
          let num06 = Number(e.row.num04) * Number(num10) + Number(e.row.num04) 
          // 含税金额
          let num07 = Number(e.row.num01) * Number(num06)
          tables[0].setValues([
            {
              rowKey: e.row.id, // 行的id
              values:{
                num05:Number(e.row.num01) * Number(e.row.num04),
                num06,
                num07
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
              num08 += Number(item.num05) 
              // 含税总金额
              num09 += Number(item.num07)
              // 尾款
              num11 += Number(item.num05) 
              // 尾款(含税)
              num12 += Number(item.num07)
            })

            that.$nextTick(()=>{
              that.model = {...that.model,num08,num09,num11,num12}
            })
          })
        })
      }
    },
      // getCollectionSchedule(orderId) {
      //   let params = { query04: orderId,pageNo:1,pageSize:1000}
      //   console.log(orderId,'orderId')
      //   getAction('/jeeerp/busiOm/list',params).then(res => {
      //     this.collectionSchedule = res.result.records
      //     console.log(this.collectionSchedule, '89899889')
      //     if(this.collectionSchedule.length) {
      //       this.model =  this.collectionSchedule[0]
      //       this.model.id = ''
      //       this.busiOmPlanItem.dataSource = this.collectionSchedule
      //     } else {
      //       this.model =  {}
      //       this.busiOmPlanItem.dataSource = []
      //     }
      //   })
      // },
      add () {
        this.busiReceivableItem.dataSource = []
        this.edit(this.modelDefault);
      },
      edit(record) {
        this.model = Object.assign({}, record)
        if (this.model.id) {
          let params = { query04: this.model.query04, pageNo: 1, pageSize: 1000 }
          getAction('/jeeerp/busiPaymentReceived/list', params).then((res) => {
            this.busiReceivableItem.dataSource = res.result.records
          })
        }
        this.visible = true
      },
      close() {
        this.model = {}
        this.busiReceivableItem.dataSource = []
        // this.collectionSchedule = []
      },
      classifyIntoFormData(allValues) {
        let list = []
        allValues.tablesValue[0].values.map((item, index) => {
          list.push({
            ...this.model,
            ...item,
            query01: 'XSSKJH',
          })
        })
        return list
      },
    //   submitForm () {
    //     const that = this;
    //     // 触发表单验证
    //     this.$refs.form.validate(valid => {
    //       if (valid) {
    //         that.confirmLoading = true;
    //         let httpurl = '';
    //         let method = '';
    //         if(!this.model.id){
    //           httpurl+=this.url.add;
    //           method = 'post';
    //         }else{
    //           httpurl+=this.url.edit;
    //            method = 'put';
    //         }
    //         httpAction(httpurl,this.model,method).then((res)=>{
    //           if(res.success){
    //             that.$message.success(res.message);
    //             that.$emit('ok');
    //           }else{
    //             that.$message.warning(res.message);
    //           }
    //         }).finally(() => {
    //           that.confirmLoading = false;
    //         })
    //       }
         
    //     })
    //   },
    }
  }
</script>