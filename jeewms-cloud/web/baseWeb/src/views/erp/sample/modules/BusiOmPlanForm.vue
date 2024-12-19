<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="disabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <!-- <a-col :span="8">
            <a-form-model-item label="建单日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <j-date placeholder="请选择单据日期" v-model="model.query03" style="width: 100%" disabled/>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8">
            <a-form-model-item label="PAYMENT LIST" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query19">
              <a-input v-model="model.query19" placeholder="请输入PAYMENT LIST"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="主PO" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query13">
              <a-input v-model="model.query13" placeholder="请输入主PO" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="采购人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query16" >
              <j-search-select-tag
                type="list"
                v-model="model.query16"
                dict="sys_user,realname,realname,del_flag = '0'"
                :placeholder="$t('请选择')"
                disabled
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
                  disabled
                />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="内部发票号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query17">
              <a-input v-model="model.query17" placeholder="请输入内部发票号" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="销售单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="link03">
              <a-input v-model="model.link03" placeholder="请输入销售单号" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="供应商名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query09">
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
          <a-col :span="8">
            <a-form-model-item label="供应商编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
              <a-input v-model="model.query08" placeholder="请输入供应商编码" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="是否含税" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query25">
              <j-dict-select-tag type="radio"  v-model="model.query25" placeholder="请选择是否含税" dictCode="if_yes_no"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" v-if="model.query25 == '是'">
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
            <a-form-model-item label="尾款" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num11">
              <a-input v-model="model.num11" placeholder="请输入尾款" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8"  v-if="model.query25 == '是'">
            <a-form-model-item label="含税总金额" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num09">
              <a-input v-model="model.num09" placeholder="请输入含税总金额" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8"  v-if="model.query25 == '是'">
            <a-form-model-item label="尾款(含税)" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num12">
              <a-input v-model="model.num12" placeholder="请输入尾款(含税)" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="币种" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query22">
              <j-dict-select-tag  v-model="model.query22" dictCode="currency"/>
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
      <a-tab-pane tab="收款计划商品信息" :key="refKeys[0]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[0]"
          :loading="busiPoPlanItem.loading"
          :columns="busiPoPlanItem.columns"
          :dataSource="busiPoPlanItem.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          @valueChange="valueChange"
        />
        <!-- 
          :rowSelection="true"
          :actionButton="true" -->
      </a-tab-pane>
    </a-tabs>
  </a-spin>
</template>

<script>
import { httpAction, getAction } from '@/api/manage'
import { validateDuplicateValue } from '@/utils/util'
import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'
import { FormTypes, getRefPromise, VALIDATE_NO_PASSED } from '@/utils/JEditableTableUtil'
import { getCurrentTime } from '@/utils/util'

export default {
  name: 'SamplePlanForm',
  mixins: [JEditableTableModelMixin],
  components: {},
  props: {
    //表单禁用
    disabled: {
      type: Boolean,
      default: false,
      required: false,
    },
  },
  data() {
    return {
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      confirmLoading: false,
      validatorRules: {
        query05: [{required: true, message: ' '}],
        query16: [{ required: true, message: ' ' }],
        query07: [{ required: true, message: ' ' }],
        query06: [{ required: true, message: ' ' }],
        query03: [{ required: true, message: ' ' }],
        query09: [{ required: true, message: ' ' }],
        query13: [{ required: true, message: ' ' }],
        query08: [{ required: true, message: ' ' }],
        query23: [{ required: true, message: ' ' }],
        num08: [{ required: true, message: ' ' }],
        num09: [{ required: true, message: ' ' }],
        num10: [{ required: true, message: ' ' }],
      },
      refKeys: ['busiPoPlanItem'],
      tableKeys: ['busiPoPlanItem'],
      activeKey: 'busiPoPlanItem',
      // busi_po_item
      busiPoPlanItem: {
        loading: false,
        dataSource: [],
        columns: [
            {
              title: '子PO',
              key: 'query14',
              type: FormTypes.input,
              disabled: true,
              width:"150px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '商品名称',
              key: 'query11',
              type: FormTypes.popup,
              popupCode: 'wv_goods_select',
              destFields: 'query11,query10,query12',
              orgFields: 'shp_ming_cheng,goods_code,baseunit',
              disabled: true,
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
              validateRules: [{ required: true, message: '${title}不能为空' }],
              placeholder: '请输入${title}',
              disabled: true,
              defaultValue: '',
            },
            {
              title: '数量',
              key: 'num01',
              type: FormTypes.inputNumber,
              disabled: true,
              width: '100px',
              placeholder: '请输入${title}',
              validateRules: [{ required: true, message: '${title}不能为空' }],
              defaultValue: '',
            },
            {
              title: '单位',
              key: 'query12',
              type: FormTypes.input,
              width: '100px',
              placeholder: '请输入${title}',
              disabled: true,
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
        add: '/jeeerp/busiPaymentReceived/batchAdd',
        edit: '/jeeerp/busiPaymentReceived/batchEdit',
        queryById: '/jeeerp/busiPaymentReceived/queryById',
      },
      paymentSchedule: [],
    }
  },
  computed: {
    formDisabled() {
      return this.disabled
    },
  },
  created() {
    //备份model原始值
    this.modelDefault = JSON.parse(JSON.stringify(this.model))
  },
  methods: {
    num10Input(){
      this.model.num02 = this.model.num08
      this.model.num03 = this.model.num09
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
              that.model.num02 = that.model.num08
              that.model.num03 = that.model.num09
            })
          })
          
        })
      }
    },
    popupCallback2(value,row) {
      this.model.query08 = row.supplyCode
    },
    getPaymentSchedule(orderId) {
      console.log(1321312)
      let params = { query04: orderId, pageNo: 1, pageSize: 1000 }
      getAction('/jeeerp/busiPo/list', params).then((res) => {
        console.log(this.paymentSchedule,'rerrrrrrrrrrrrr');
        this.paymentSchedule = res.result.records
        if (this.paymentSchedule.length) {
          this.model = this.paymentSchedule[0]
          this.model.num02 = this.model.num08
          this.model.num03 = this.model.num09
          this.model.id = ''
          this.busiPoPlanItem.dataSource = this.paymentSchedule
        } else {
          this.model = {}
          this.busiPoPlanItem.dataSource = []
        }
      })
    },
    popupCallback1(value, row) {
      this.model.query05 = row.company
      this.model.query06 = row.factory
    },
    add() {
      this.busiPoPlanItem.dataSource = []
      this.edit(this.modelDefault)
    },
    edit(record) {
      this.model = Object.assign({}, record)
      if (this.model.id) {
        let params = { query04: this.model.query04, pageNo: 1, pageSize: 1000 }
        getAction('/jeeerp/busiPaymentReceived/list', params).then((res) => {
          this.busiPoPlanItem.dataSource = res.result.records
        })
      }else{
        // 新增
        this.model.query03 = getCurrentTime()
        this.model.query23 = "否"
      }
      this.visible = true
    },
    close() {
      this.model = {}
      this.busiPoPlanItem.dataSource = []
      this.paymentSchedule = []
    },
    classifyIntoFormData(allValues) {
      let list = []
      allValues.tablesValue[0].values.map((item, index) => {
        list.push({
          ...this.model,
          ...item,
          query01: 'YPSKJH',
          link01:this.model.query04?'打样通知单':null,
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
  },
}
</script>