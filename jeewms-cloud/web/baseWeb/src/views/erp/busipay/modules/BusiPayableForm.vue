<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="8">
            <a-form-model-item label="供应商名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query09">
                <j-popup
                  v-model="model.query09"
                  field="supplyName"
                  org-fields="gys_bian_ma,gys_jian_cheng,zhong_wen_qch"
                  dest-fields="supplyCode,supplyName,supplyNick"
                  code="md_sup"
                  :multi="false"
                  disabled
                  @input="popupCallback2"
                />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="供应商编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
              <a-input v-model="model.query08" placeholder="请输入供应商编码" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8">
            <a-form-model-item label="建单日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <j-date placeholder="请选择建单日期" v-model="model.query03" style="width: 100%" disabled/>
            </a-form-model-item>
          </a-col> -->
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
            <a-form-model-item label="主PO" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query13">
              <a-input v-model="model.query13" placeholder="请输入主PO" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="是否含税" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query25">
              <j-dict-select-tag type="radio"  v-model="model.query25" placeholder="请选择是否含税" dictCode="if_yes_no" disabled/>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" v-if="model.query25 == '是'">
            <a-form-model-item label="税率%" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num10">
              <a-input v-model="model.num10" placeholder="请输入税率" @input="num10Input" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="总金额" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num08">
              <a-input v-model="model.num08" placeholder="请输入总金额" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" v-if="model.query25 == '是'">
            <a-form-model-item label="含税总金额" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num09">
              <a-input v-model="model.num09" placeholder="请输入含税总金额" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="币种" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query22">
              <j-dict-select-tag  v-model="model.query22" dictCode="currency" disabled />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="付款方式" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query26">
              <j-dict-select-tag  v-model="model.query26" dictCode="payment_method"  />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="付款比例%" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query18">
              <a-input v-model="model.query18" placeholder="请输入付款比例" @input="query18Input"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="计划付款金额" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num13">
              <a-input v-model="model.num02" placeholder="请输入计划付款金额" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="计划付款含税金额" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num13">
              <a-input v-model="model.num03" placeholder="请输入计划付款金额" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text01">
              <a-input v-model="model.text01" placeholder="请输入备注" ></a-input>
            </a-form-model-item>
          </a-col> 
           </a-row>
      </a-form-model>
    </j-form-container>

    <!-- 子表单区域 -->
    <a-tabs v-model="activeKey" @change="handleChangeTabs">
      <a-tab-pane tab="应付商品信息" :key="refKeys[0]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[0]"
          :loading="busiPayableItem.loading"
          :columns="busiPayableItem.columns"
          :dataSource="busiPayableItem.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :actionButton="true"
          @valueChange="valueChange"
          />
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
    name: 'BusiPayableForm',
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
        refKeys: ['busiPayableItem', ],
        tableKeys:['busiPayableItem', ],
        activeKey: 'busiPayableItem',
        // busi_po_item
        busiPayableItem: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '计划付款总价',
              key: 'num14',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '计划付款含税总价',
              key: 'num15',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
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
      add () {
        this.busiPayableItem.dataSource = []
        this.edit(this.modelDefault);
      },
      edit(record) {
        this.model = Object.assign({}, record)
        if (this.model.id) {
          let params = { query04: this.model.query04, pageNo: 1, pageSize: 1000 }
          getAction('/jeeerp/busiPaymentReceived/list', params).then((res) => {
            this.busiPayableItem.dataSource = res.result.records
          })
        }
        this.visible = true
      },
      close() {
        this.model = {}
        this.busiPayableItem.dataSource = []
        // this.collectionSchedule = []
      },
      classifyIntoFormData(allValues) {
        let list = []
        allValues.tablesValue[0].values.map((item, index) => {
          list.push({
            ...this.model,
            ...item,
            query01: 'CGFKJH',
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